
#include <iostream>
#include <sstream>
#include <string>
#include <unordered_map>

#include "grammar_reader.h"
#include "parse_exception.h"

using namespace std;


string escape(string word);


symbol* grammar_reader::read_grammar()
{
    std::string line;
    nonterminal* start = nullptr;

    while (std::getline(std::cin, line)) {
        std::istringstream iss(line);
        std::string word;

        if (!(iss >> word)) {
            continue;
        }

        nonterminal* nt = get_nonterminal(word);

        if (start == nullptr) {
            start = nt;
        }

        if (!(iss >> word) || word != "->") {
            continue;
        }

        alternate alt;
        while (iss >> word) {
            if (word == "|") {
                if (!alt.empty()) {
                    nt->add_alt(alt);
                    alt.clear();
                }
            } else if (word != ";") {
                alt.push_back(get_symbol(word));
            }
        }

        if (!alt.empty()) {
            nt->add_alt(alt);
        }
    }

    return start;
}

string escape(string word)
{
    stringstream ss;
    for(int i = 0; i < word.size(); i++)
    {
        if(i < word.size()-1 && word[i] == '\\')
        {
            switch(word[i+1])
            {
                case 'b':    ss << '\b'; i++; break;
                case 'n':    ss << '\n'; i++; break;
                case 'r':    ss << '\r'; i++; break;
                case 't':    ss << '\t'; i++; break;
                case 'a':    ss << '\a'; i++; break;
                case 'f':    ss << '\f'; i++; break;
                case 'v':    ss << '\v'; i++; break;
                case '|':    ss << '|';  i++; break;
                case ';':    ss << ';';  i++; break;
                case '\'': ss << '\''; i++; break;
                case '\"': ss << '\"'; i++; break;
                defualt: ss << '\\';
            }
        }
        else
        {
            ss << word[i];
        }
    }
    return ss.str();
}

//////////////////////////////////////////////////////////////
// These methods are for caching symbols.
// Instead of making a new terminal or new nonterminal,
// use get_nonterminal or get_symbol
//////////////////////////////////////////////////////////////

nonterminal* grammar_reader::get_nonterminal(string token)
{
    if(symbols.find(token) == symbols.end())
    {
        symbols[token] = new nonterminal(token);
    }
    return (nonterminal*)symbols[token];
}

symbol* grammar_reader::get_symbol(string token)
{
    if(symbols.find(token) == symbols.end())
    {
        if(token[0] == '_')
        {
            symbols[token] = new nonterminal(token);
        }
        else
        {
            symbols[token] = new terminal(token);
        }
    }
    return symbols[token];
}


//////////////////////////////////////////////////////////////
// the distructor for grammar reader will go through
// and destroy all of the symbols.
// This needs to happen here,
// because they symbols actually form a graph,
// so destroying the through symbol would be a nightmare.
//////////////////////////////////////////////////////////////

grammar_reader::~grammar_reader()
{
    for(auto& itr : symbols)
    {
        delete itr.second;
    }
}

