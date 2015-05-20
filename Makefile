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
PROFILER_SKELETON = src/cis/profiler/web/ProfilerWebServiceSkeleton.java
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
build.xml: $(WSDL) $(WSDL2JAVA)
	$(WSDL2JAVA) -uri $< -p cis.profiler.web -s -d adb -sd -ss -ssi -g -scn ProfilerWebService

$(PROFILER_AAR): build.xml $(wildcard src/cis/profiler/web/*.java) $(PROFILER_SKELETON)
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

$(PROFILER_SKELETON): build.xml
	scripts/generate_profiler_skeleton.sh $@

deploy: do-deploy restart-apache

do-deploy: $(PROFILER_AAR) $(PROFILER_INI) $(AXIS2_WAR) backend
	@$(MKDIR) $(APACHE_HOME)/webapps/axis2/WEB-INF/conf
	@$(MKDIR) $(APACHE_HOME)/webapps/axis2/WEB-INF/services
	$(CP) $(AXIS2_WAR) $(APACHE_HOME)/webapps/
	$(CP) $(PROFILER_INI) $(APACHE_HOME)/webapps/axis2/WEB-INF/conf
	$(CP) $(PROFILER_AAR) $(APACHE_HOME)/webapps/axis2/WEB-INF/services

restart-apache: do-deploy
	$(APACHE_HOME)/bin/shutdown.sh
	$(APACHE_HOME)/bin/startup.sh

backend:
	BACKEND=$(PROFILER_BACKEND) $(MAKE) -C gsm/lexicon backend

.PHONY: test
test: test-wsdl test-getConfigurations

.PHONY: test-wsdl #test-service-GetConfigurations
test-wsdl: test-wsdl-GetConfigurations test-wsdl-GetProfile test-wsdl-GetProfilingStatus

test-wsdl-%:
	curl -# $(PWS_URL)?wsdl 2> /dev/null | grep $* > /dev/null
test-getConfigurations: \
	test-getConfigurations-latin \
	test-getConfigurations-polish \
	test-getConfigurations-greek \
	test-getConfigurations-german
test-getConfigurations-%:
	curl -# $(PWS_URL)/getConfigurations 2> /dev/null | grep $* > /dev/null
#test-service-GetConfiguration:

.PHONY: clean
clean:
	$(RM) build.xml #$(PROFILER_SKELETON)
	$(RM) -r build

.PHONY: distclean
distclean: clean
	$(RM) -r var
	$(RM) -r src/de
	$(RM) -r src/org
