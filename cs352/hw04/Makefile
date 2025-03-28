

all: symbol.o grammar_reader.o random_sentence.cpp
	g++ random_sentence.cpp grammar_reader.o symbol.o -o random_sentence

symbol.o: symbol.cpp symbol.h
	g++ -c symbol.cpp

grammar_reader.o: grammar_reader.cpp grammar_reader.h parse_exception.h
	g++ -c grammar_reader.cpp

clean:
	rm random_sentence *.o
