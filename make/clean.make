# make/clean.make

.PHONY: clean
clean:
	$(RM) $(BUILD_XML) $(SERVICES_XML) $(PWS_INTERFACE) $(PWS_STUB) $(PWS_MRIO)
	$(RM) -r src/de
	$(RM) -r src/org

.PHONY: distclean
distclean: clean
	$(RM) -r var
	$(RM) -r build
