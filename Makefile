#
# Makefile
#

# PREFIX ?=
AXIS2_HOME ?= $(HOME)/bin
WSDL2JAVA ?= wsdl2java.sh
ANT ?= ant
MKDIR ?= mkdir -p

#%.wsdl:
mkdir-%:
	$(MKDIR) $*

default: build.xml

build.xml: xml-resources/axis2/META-INF/ProfilerWebService.wsdl
	$(WSDL2JAVA) -uri $<

ant:
	$(ANT)

libs/apache.jar: | mkdir-libs
	wget
.PHONY: clean
clean:
	$(RM) build.xml
