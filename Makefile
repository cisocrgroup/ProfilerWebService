#
# Makefile
#

APACHE ?= $(HOME)/uni/profiler/tomcat/apache-tomcat-8.0.0-RC5

AXIS2 = axis2-1.6.2
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

# TOOLS
ANT ?= ant
MKDIR ?= mkdir -p
JAR ?= jar
CP ?= cp

# DEFAULT
default: $(PROFILER_AAR)

# -ss: server-side
# -sd: service-description
# -or: override
# -ssi service-interface
build.xml: $(WSDL) $(WSDL2JAVA)
	$(WSDL2JAVA) -uri $< -p cis.profiler.web -s -d adb -sd -ss -ssi #-scn ProfilerWebService

$(PROFILER_AAR): build.xml $(wildcard src/cis/profiler/web/*.java)
	ANT_OPTS=$(ANT_OPTS) AXIS2_HOME=$(AXIS2_HOME) $(ANT)

var/$(AXIS2).zip:
	@$(MKDIR) -p var
	wget -O$@ http://mirror.nexcess.net/apache/axis/axis2/java/core/1.6.2/$(AXIS2)-bin.zip

$(WSDL2JAVA): var/$(AXIS2).zip
	unzip -d var $<
	touch $@

$(PROFILER_INI): scripts/generate_profiler_ini.sh
	@$(MKDIR) $(PROFILER_CONF_DIR)
	$< $@
$(PROFILER_SKELETON): build.xml
	scripts/generate_profiler_skeleton.sh $@

deploy: do-deploy restart-apache

do-deploy: $(PROFILER_AAR) $(PROFILER_INI)
	@$(MKDIR) $(APACHE)/webapps/axis2/WEB-INF/conf
	@$(MKDIR) $(APACHE)/webapps/axis2/WEB-INF/services
	$(CP) $(PROFILER_INI) $(APACHE)/webapps/axis2/WEB-INF/conf
	$(CP) $(PROFILER_AAR) $(APACHE)/webapps/axis2/WEB-INF/services

restart-apache: do-deploy
	$(APACHE)/bin/shutdown.sh
	$(APACHE)/bin/startup.sh

# HELPER
mkdir-%: dir = $(subst -,/,$*)
mkdir-%:
	@$(MKDIR) $(dir)

.PHONY: clean
clean:
	$(RM) build.xml #$(PROFILER_SKELETON)
	$(RM) -r build

.PHONY: distclean
distclean: clean
	$(RM) -r var
	$(RM) -r src/de
	$(RM) -r src/org
