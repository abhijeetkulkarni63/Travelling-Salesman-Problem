#Targets:
#  Default target: build project
#  clean:          remove all generated files.
#  submit:         build compress archive all project source files.

PROJECT = 	akulka16_P1

VERBOSE =
TARGET =	jar

SRC_FILES = \
  src \
  build.xml \
  Makefile \
  README


CFLAGS = -g -Wall -std=c11

$(TARGET):  	
		ant -emacs $(VERBOSE) $(TARGET)

clean:		
		ant -emacs $(VERBOSE) clean

submit:
		tar -cvzf $(PROJECT).tar.gz $(SRC_FILES)
