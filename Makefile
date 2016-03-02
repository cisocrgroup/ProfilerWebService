#
# Makefile
#

# edit or set via `export TOMCAT_HOME=...` to point to the tomcat home directory
TOMCAT_HOME ?= $(HOME)/tomcat/apache-tomcat-8.0.32
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
PROFILER_INI = profiler.ini
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
#	cd $(TOMCAT_HOME) && bin/shutdown.sh
	mkdir -p build/ProfilerWebService/WEB-INF/conf
	scripts/generate_profiler_ini.sh $(PROFILER_INI) $(BACKEND_HOME) $(PROFILER_EXE)
	jar uf $(PROFILER_AAR) $(PROFILER_INI)
	$(SUDO) mkdir -p $(TOMCAT_HOME)/webapps/axis2/WEB-INF/conf
	$(SUDO) mkdir -p $(TOMCAT_HOME)/webapps/axis2/WEB-INF/services
	$(SUDO) cp $(PROFILER_AAR) $(TOMCAT_HOME)/webapps/axis2/WEB-INF/services
#	cd $(TOMCAT_HOME) && bin/startup.sh

clean:
	$(RM) $(PROFILER_INI)
	$(RM) $(BUILD_XML)
	$(RM) $(SERVICES_XML)
	$(RM) -r build

.PHONY: deploy clean
