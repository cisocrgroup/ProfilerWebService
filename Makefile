#
# Makefile
#

# PREFIX ?=
AXIS_HOME ?= $(HOME)/Downloads/axis2-1.6.2

# TOOLS
WSDL2JAVA ?= $(AXIS_HOME)/bin/wsdl2java.sh
ANT ?= ant
MKDIR ?= mkdir -p

#%.wsdl:
mkdir-%:
	$(MKDIR) $*

default: ant

build.xml: xml-resources/axis2/META-INF/ProfilerWebService.wsdl
	CLASSPATH=$(CLASSPATH):$(AXIS_HOME)/lib/* $(WSDL2JAVA) -uri $< -d adb -s

ant: build.xml
	$(ANT)

libs/apache.jar: | mkdir-libs
	wget
.PHONY: clean
clean:
	$(RM) build.xml
