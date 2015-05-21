#
# Makefile
#

APACHE_HOME ?= $(HOME)/uni/profiler/tomcat/apache-tomcat-8.0.0-RC5
PROFILER_BACKEND ?= $(APACHE_HOME)/../backend

AXIS2 = axis2-1.6.2
AXIS2_WAR = var/$(AXIS2)-war/axis2.war
AXIS2_HOME = var/$(AXIS2)
WSDL2JAVA = $(AXIS2_HOME)/bin/wsdl2java.sh
ANT_OPTS = -Dbuild.sysclasspath=ignore
PROFILER_AAR = build/lib/ProfilerWebService.aar
PROFILER_JAR = build/lib/ProfilerWebService.jar
PROFILER_DIR = build/ProfilerWebService
WEB_INF_DIR = $(PROFILER_DIR)/WEB-INF
PROFILER_CONF_DIR = $(WEB_INF_DIR)/conf
PROFILER_LIB_DIR = $(PROFILER_DIR)/lib
PROFILER_INI = $(PROFILER_CONF_DIR)/profiler.ini
SERVICES_XML = resources/services.xml
WSDL = resources/ProfilerWebService.wsdl
HOST ?= http://localhost
PORT ?= 8080
TEST_HOST ?= $(HOST):$(PORT)
PWS_URL ?= $(TEST_HOST)/axis2/services/ProfilerWebService

# TOOLS
ANT ?= ant
MKDIR ?= mkdir -p
JAR ?= jar
CP ?= cp

# DEFAULT
default: deploy

# -ss: server-side
# -sd: service-description
# -or: override
# -ssi service-interface
build.xml resources/services.xml: $(WSDL) $(WSDL2JAVA)
	$(WSDL2JAVA) -uri $< -p cis.profiler.web -s -d adb -sd -ss -ssi -g -or

$(PROFILER_AAR): build.xml resources/services.xml $(wildcard src/cis/profiler/web/*.java)
	ANT_OPTS=$(ANT_OPTS) AXIS2_HOME=$(AXIS2_HOME) $(ANT)

var/$(AXIS2).zip:
	@$(MKDIR) -p var
	wget -O$@ http://mirror.nexcess.net/apache/axis/axis2/java/core/1.6.2/$(AXIS2)-bin.zip

var/$(AXIS2)-war.zip:
	@$(MKDIR) -p var
	wget -O$@ http://mirror.nexcess.net/apache/axis/axis2/java/core/1.6.2/$(AXIS2)-war.zip

$(WSDL2JAVA): var/$(AXIS2).zip
	unzip -d var -u $<
	touch $@
$(AXIS2_WAR): var/$(AXIS2)-war.zip
	unzip -d $(dir $@) -u $<
	touch $@

$(PROFILER_INI): scripts/generate_profiler_ini.sh
	@$(MKDIR) $(PROFILER_CONF_DIR)
	$< $@ $(PROFILER_BACKEND)

deploy: $(PROFILER_AAR) $(PROFILER_INI) $(AXIS2_WAR) backend
	@$(MKDIR) $(APACHE_HOME)/webapps/axis2/WEB-INF/conf
	@$(MKDIR) $(APACHE_HOME)/webapps/axis2/WEB-INF/services
#	$(CP) $(AXIS2_WAR) $(APACHE_HOME)/webapps/
	$(CP) $(PROFILER_INI) $(APACHE_HOME)/webapps/axis2/WEB-INF/conf
	$(CP) $(PROFILER_AAR) $(APACHE_HOME)/webapps/axis2/WEB-INF/services

backend:
	BACKEND=$(PROFILER_BACKEND) $(MAKE) -C gsm/lexicon backend
include make/test.make
include make/clean.make
