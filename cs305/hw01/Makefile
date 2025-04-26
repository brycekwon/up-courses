COMPILE = gcc
OFILES = video.o playlist.o main.o

main: $(OFILES)
	$(COMPILE) -o $@ $(OFILES)

%.o: %.c %.h
	$(COMPILE) -c $<

clean:
	/bin/rm -f *.o main