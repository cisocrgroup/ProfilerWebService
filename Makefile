#
# Makefile
#

# PREFIX ?=
AXIS2_HOME ?= $(HOME)/bin
WSDL2JAVA ?= wsdl2java.sh
ANT ?= ant

#%.wsdl:
default: build.xml

build.xml: xml-resources/axis2/META-INF/ProfilerWebService.wsdl
	$(WSDL2JAVA) -uri $<

ant:
	$(ANT)
.PHONY: clean
clean:
	$(RM) build.xml
