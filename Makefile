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
build.xml: resources/ProfilerWebService.wsdl $(WSDL2JAVA)
	$(WSDL2JAVA) -uri $< -ss -sd -or -ssi

$(PROFILER_AAR): build.xml
	ANT_OPTS=$(ANT_OPTS) AXIS2_HOME=$(AXIS2_HOME) $(ANT)

var/$(AXIS2).zip: | mkdir-var
	wget -O$@ http://mirror.nexcess.net/apache/axis/axis2/java/core/1.6.2/$(AXIS2)-bin.zip

$(WSDL2JAVA): var/$(AXIS2).zip
	unzip -d var $<
	touch $@

$(PROFILER_INI):
	@$(MKDIR) $(PROFILER_CONF_DIR)
	scripts/generate_profiler_ini.sh $@

deploy: $(PROFILER_INI) $(PROFILER_AAR)
	@$(MKDIR) $(APACHE)/webapps/axis2/WEB-INF/conf
	@$(MKDIR) $(APACHE)/webapps/axis2/WEB-INF/services
	$(CP) $(PROFILER_INI) $(APACHE)/webapps/axis2/WEB-INF/conf
	$(CP) $(PROFILER_AAR) $(APACHE)/webapps/axis2/WEB-INF/services

# HELPER
mkdir-%:
	@$(MKDIR) $*
.PHONY: clean
clean:
	$(RM) build.xml
	$(RM) -r build
.PHONY: distclean
distclean: clean
	$(RM) -r var
