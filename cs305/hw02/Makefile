COMPILE = gcc
OFILES = hexboard.o load.o

find_path: $(OFILES) main.c hexcell.h
	$(COMPILE) -c main.c
	$(COMPILE) -o $@ main.o $(OFILES)

%.o: %.c %.h
	$(COMPILE) -c $<

clean:
	/bin/rm -f *.o find_path