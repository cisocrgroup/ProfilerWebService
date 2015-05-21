# make/test.make

.PHONY: test
test: test-wsdl test-getConfigurations test-getProfilingStatus

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
test-getProfilingStatus:
	curl -d userid=10 -# $(PWS_URL)/getProfilingStatus 2> /dev/null | grep 'not profiling' > /dev/null
