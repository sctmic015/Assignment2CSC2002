# Makefile: SCTMIC015

JAVAC = /usr/bin/javac
.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
DOCDIR=doc

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES=Terrain.class Water.class WaterEditor.class FlowPanel.class WaterClickListener.class Flow.class

CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)
	
docs:
	javadoc -d $(DOCDIR) $(SRCDIR)/*.java
clean:
	rm $(BINDIR)/*.class
run:
	java -cp bin Terrain
run2: 
	java -cp bin Water
run3:
	java -cp bin WaterEditor
run4:
	java -cp bin Flow
run5:
	java -cp bin FlowPanel
run6:
	java -cp bin WaterClickListener

