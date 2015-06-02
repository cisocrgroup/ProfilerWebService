#
# Makefile
#

TOMCAT_HOME ?= $(HOME)/uni/profiler/tomcat/apache-tomcat-8.0.0-RC5
PROFILER_BACKEND ?= $(TOMCAT_HOME)/../backend

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
BUILD_XML = build.xml
WSDL = resources/ProfilerWebService.wsdl
PWS_INTERFACE = src/cis/profiler/web/ProfilerWebServiceSkeletonInterface.java
PWS_STUB = src/cis/profiler/web/ProfilerWebServiceStub.java
PWS_MRIO = src/cis/profiler/web/ProfilerWebServiceMessageReceiverInOut.java
PWS_HOST ?= http://localhost
PWS_PORT ?= 8080
TEST_HOST ?= $(PWS_HOST):$(PWS_PORT)
PWS_URL ?= $(TEST_HOST)/axis2/services/ProfilerWebService
SUDO ?=

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

$(BUILD_XML) $(SERVICES_XML): $(WSDL2JAVA) $(WSDL)
	$(WSDL2JAVA) -uri $(WSDL) -p cis.profiler.web --noWSDL -s -d adb -sd -ssi -ss -g -scn ProfilerWebService

$(PROFILER_AAR): $(BUILD_XML) $(SERVICES_XML) $(wildcard src/cis/profiler/web/*.java) $(wildcard src/cis/profiler/client/*.java)
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

deploy: $(PROFILER_AAR) $(PROFILER_INI) $(PROFILER_BACKEND)
	$(SUDO) $(MKDIR) $(TOMCAT_HOME)/webapps/axis2/WEB-INF/conf
	$(SUDO) $(MKDIR) $(TOMCAT_HOME)/webapps/axis2/WEB-INF/services
	$(SUOD) $(CP) $(PROFILER_INI) $(TOMCAT_HOME)/webapps/axis2/WEB-INF/conf
	$(SUDO) $(CP) $(PROFILER_AAR) $(TOMCAT_HOME)/webapps/axis2/WEB-INF/services

backend:
	BACKEND=$(PROFILER_BACKEND) $(MAKE) -C gsm/lexicon backend
include make/test.make
include make/clean.make
