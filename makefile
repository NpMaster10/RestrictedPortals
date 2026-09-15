# Define the JAVA_HOME path
JAVA_HOME := /mnt/564ab391-ca02-4c4e-8ed2-85ed4b1e62f3/My\ Source\ Code/1.7.10\ mod\ develpoment/jdk1.8.0_401

# Default target
.PHONY: build
build:
	@echo "Building with JAVA_HOME=$(JAVA_HOME)"
	export JAVA_HOME=$(JAVA_HOME) && ./gradlew build
