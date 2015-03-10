#
# Makefile
#

# PREFIX ?=
AXIS2 = axis2-1.6.2
AXIS2_HOME = var/$(AXIS2)
WSDL2JAVA = $(AXIS2_HOME)/bin/wsdl2java.sh

# TOOLS
ANT ?= ant
MKDIR ?= mkdir -p

# DEFAULT
default: build.xml

# -ss: server-side
# -sd: service-description
# -or: override
build.xml: ProfilerWebService.wsdl $(WSDL2JAVA)
	$(WSDL2JAVA) -uri $< -ss -sd -or

ant: build.xml
	$(ANT)

var/$(AXIS2).zip: | mkdir-var
	wget -O$@ http://mirror.nexcess.net/apache//axis/axis2/java/core/1.6.2/$(AXIS2)-bin.zip

$(WSDL2JAVA): var/$(AXIS2).zip
	unzip -d var $<
	touch $@

# HELPER
mkdir-%:
	$(MKDIR) $*
.PHONY: clean
clean:
	$(RM) build.xml
distclean: clean
	$(RM) -r var
