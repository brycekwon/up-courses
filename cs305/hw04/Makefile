COMPILE_SWITCHES = -g
COMPILE = gcc $(COMPILE_SWITCHES)
OFILES = retail_item.o cart.o tree.o main.o

fastLookup: $(OFILES)
	$(COMPILE) -o fastLookup $(OFILES)

retail_item.o: retail_item.c retail_item.h
	$(COMPILE) -c retail_item.c

cart.o: cart.c cart.h retail_item.h
	$(COMPILE) -c cart.c

tree.o: tree.c tree.h retail_item.h cart.h
	$(COMPILE) -c tree.c

main.o: main.c retail_item.h cart.h tree.h
	$(COMPILE) -c main.c

clean:
	/bin/rm -f *.o fastLookup