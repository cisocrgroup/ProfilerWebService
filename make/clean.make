# make/clean.make

.PHONY: clean
clean:
	$(RM) $(BUILD_XML) $(SERVICES_XML) $(PWS_INTERFACE) $(PWS_STUB)
	$(RM) -r build

.PHONY: distclean
distclean: clean
	$(RM) -r var
	$(RM) -r src/de
	$(RM) -r src/org
