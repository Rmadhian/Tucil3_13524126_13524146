SRC_DIR := src
BIN_DIR := bin
MAIN_CLASS := Main
SOURCES := $(shell find $(SRC_DIR) -name "*.java")

.PHONY: build run clean

build: clean
	mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) -sourcepath $(SRC_DIR) $(SOURCES)

run: build
	java -cp $(BIN_DIR) $(MAIN_CLASS)

clean:
	find $(BIN_DIR) -name "*.class" -delete
