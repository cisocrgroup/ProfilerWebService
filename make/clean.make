# make/clean.make

.PHONY: clean
clean:
	$(RM) build.xml resources/services.xml
	$(RM) -r build

.PHONY: distclean
distclean: clean
	$(RM) -r var
	$(RM) -r src/de
	$(RM) -r src/org
