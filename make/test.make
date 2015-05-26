# make/test.make

.PHONY: test
test: deploy test-wsdl test-getConfigurations test-getProfilingStatus test-startSession test-getProfile

.PHONY: test-wsdl
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
test-startSession:
	curl -# $(PWS_URL)/startSession 2> /dev/null | grep 'returncode>0<' > /dev/null
test-getProfile:
	java -cp "build/classes:$(AXIS2_HOME)/lib/*" cis.profiler.client.Main $(PWS_URL)/getProfile testFiles/bsb00000424_00221.txt german
#	curl -d userid=foo --data-binary doc_in="$(shell base64 testFiles/bsb00000424_00221.txt)" -d configuration=german -d doc_in_type=TXT -d doc_in_size=10 $(PWS_URL)/getProfile
