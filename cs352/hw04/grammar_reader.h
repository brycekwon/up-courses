#ifndef GRAMMAR_READER_H
#define GRAMMAR_READER_H

#include<unordered_map>
#include "symbol.h"


class grammar_reader
{
private:
    // cache of the symbols
    std::unordered_map<std::string, symbol*> symbols;

    // methods for looking up symbols in the cache
    symbol* get_symbol(std::string name);
    nonterminal* get_nonterminal(std::string name);

public:
    grammar_reader() = default;
    ~grammar_reader();
    symbol* read_grammar();
};

#endif //GRAMMAR_READER_H
