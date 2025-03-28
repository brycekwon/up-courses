COMPILER = gcc
OFILES = airport.o llist.o process.o main.o

airportTracker: $(OFILES)
	$(COMPILER) -o $@ $(OFILES)

airport.o: airport.c airport.h
	$(COMPILER) -c $<

llist.o: llist.c llist.h
	$(COMPILER) -c $<

process.o: process.c process.h
	$(COMPILER) -c $<

main.o: main.c
	$(COMPILER) -c $<

clean:
	/bin/rm -f *.o airportTracker