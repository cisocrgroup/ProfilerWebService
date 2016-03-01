#
# Makefile
#

# edit or set via `export TOMCAT_HOME=...` to point to the tomcat home directory
TOMCAT_HOME ?= $(HOME)/tomcat/apache-tomcat-8.0.26
# edit or set via `export BACKEND_HOME=...` to point to language backend
BACKEND_HOME ?= $(TOMCAT_HOME)/../backend
# edit or set via `export JAVA_HOME=...` to point to your java installation
JAVA_HOME ?= /usr/lib/jvm/default
# edit or set via `export AXIS2_HOME=...` to point to the axis2 binary directory
AXIS2_HOME ?= $(HOME)/axis2/axis2-1.7.1
# edit or set via `export PROFILER_EXE=...` to point to the profiler binary
PROFILER_EXE ?= $(HOME)/local/bin/profiler
# set if you need sudo to deploy the webservice
SUDO ?=

# fixed variables
SERVICES_XML = resources/services.xml
BUILD_XML = build.xml
WSDL = resources/ProfilerWebService.wsdl
PROFILER_AAR = build/lib/ProfilerWebService.aar
ANT_OPTS = -Dbuild.sysclasspath=ignore

# DEFAULT
default: $(PROFILER_AAR)

# -ss: server-side
# -sd: service-description
# -or: override
# -ssi service-interface
$(BUILD_XML) $(SERVICES_XML):
	$(AXIS2_HOME)/bin/wsdl2java.sh -uri $(WSDL) -p cis.profiler.web --noWSDL -s -d adb -sd -ssi -ss -g -scn ProfilerWebService

$(PROFILER_AAR): $(BUILD_XML) $(SERVICES_XML) $(wildcard src/cis/profiler/web/*.java) $(wildcard src/cis/profiler/client/*.java)
	ANT_OPTS=$(ANT_OPTS) AXIS2_HOME=$(AXIS2_HOME) ant

deploy: $(PROFILER_AAR)
	$(SUDO) mkdir -p $(TOMCAT_HOME)/webapps/axis2/WEB-INF/conf
	$(SUDO) mkdir -p $(TOMCAT_HOME)/webapps/axis2/WEB-INF/services
	$(SUDO) cp $(PROFILER_AAR) $(TOMCAT_HOME)/webapps/axis2/WEB-INF/services
	scripts/generate_profiler_ini.sh $(TOMCAT_HOME)/webapps/axis2/WEB-INF/conf/profiler.ini $(BACKEND_HOME) $(PROFILER_EXE)
clean:
	$(RM) $(BUILD_XML)
	$(RM) $(SERVICES_XML)
	$(RM) -r build

.PHONY: deploy clean

# AXIS2 = axis2-1.6.2
# AXIS2_WAR = var/$(AXIS2)-war/axis2.war
# #AXIS2_HOME = var/$(AXIS2)
# WSDL2JAVA = $(AXIS2_HOME)/bin/wsdl2java.sh
# PROFILER_JAR = build/lib/ProfilerWebService.jar
# PROFILER_DIR = build/ProfilerWebService
# WEB_INF_DIR = $(PROFILER_DIR)/WEB-INF
# PROFILER_EXE ?= $(BACKEND_HOME)/bin/profiler
# PROFILER_CONF_DIR = $(WEB_INF_DIR)/conf
# PROFILER_LIB_DIR = $(PROFILER_DIR)/lib
# PROFILER_INI = $(PROFILER_CONF_DIR)/profiler.ini
# PWS_INTERFACE = src/cis/profiler/web/ProfilerWebServiceSkeletonInterface.java
# PWS_STUB = src/cis/profiler/web/ProfilerWebServiceStub.java
# PWS_MRIO = src/cis/profiler/web/ProfilerWebServiceMessageReceiverInOut.java
# PWS_HOST ?= http://localhost
# PWS_PORT ?= 8080
# TEST_HOST ?= $(PWS_HOST):$(PWS_PORT)
# PWS_URL ?= $(TEST_HOST)/axis2/services/ProfilerWebService


# # TOOLS
# ANT ?= ant
# MKDIR ?= mkdir -p
# JAR ?= jar
# CP ?= cp





# var/$(AXIS2).zip:
# 	@$(MKDIR) -p var
# 	wget -O$@ http://mirror.nexcess.net/apache/axis/axis2/java/core/1.6.2/$(AXIS2)-bin.zip

# var/$(AXIS2)-war.zip:
# 	@$(MKDIR) -p var
# 	wget -O$@ http://mirror.nexcess.net/apache/axis/axis2/java/core/1.6.2/$(AXIS2)-war.zip

# $(WSDL2JAVA): var/$(AXIS2).zip
# 	unzip -d var -u $<
# 	touch $@
# $(AXIS2_WAR): var/$(AXIS2)-war.zip
# 	unzip -d $(dir $@) -u $<
# 	touch $@
# deploy-axis2: $(AXIS2_WAR)
# 	$(SUDO) $(CP) $(AXIS2_WAR) $(TOMCAT_HOME)/webapps

# $(PROFILER_INI): scripts/generate_profiler_ini.sh
# 	@$(MKDIR) $(PROFILER_CONF_DIR)
# 	$< $@ $(BACKEND_HOME) $(PROFILER_EXE)

# deploy: $(PROFILER_AAR) $(PROFILER_INI) $(BACKEND_HOME)
# 	$(SUDO) $(MKDIR) $(TOMCAT_HOME)/webapps/axis2/WEB-INF/conf
# 	$(SUDO) $(MKDIR) $(TOMCAT_HOME)/webapps/axis2/WEB-INF/services
# 	$(SUDO) $(CP) $(PROFILER_INI) $(TOMCAT_HOME)/webapps/axis2/WEB-INF/conf
# 	$(SUDO) $(CP) $(PROFILER_AAR) $(TOMCAT_HOME)/webapps/axis2/WEB-INF/services

# $(BACKEND_HOME):
# 	$(SUDO) $(MAKE) BACKEND=$(BACKEND_HOME) -C gsm/lexicon backend
# include make/test.make
# include make/clean.make
