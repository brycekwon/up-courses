
#include<iostream>
#include<string>
#include<vector>
#include<random>
#include "symbol.h"
#include "parse_exception.h"

using namespace std;

//////////////////////////////////////////////////////
//
// terminal symbols
//
//////////////////////////////////////////////////////

terminal::terminal(string n) : _name(n) { }

void terminal::generate()
{
    std::cout << _name << " ";
}

//////////////////////////////////////////////////////
//
// nonterminal symbols
//
//////////////////////////////////////////////////////

nonterminal::nonterminal(string n) : _name(n), _alts() { }

void nonterminal::generate()
{
    // randomly select an alternate
    int index = rand() % _alts.size();
    alternate& alt = _alts[index];

    // generate each symbol in the alternate
    for (symbol* sym : alt) {
        sym->generate();
    }
}

void nonterminal::add_alt(vector<symbol*> alt)
{
    _alts.push_back(alt);
}

//////////////////////////////////////////////////////
//
// code for printing symbols using cout;
//
//////////////////////////////////////////////////////

ostream& operator<<(std::ostream& out, symbol& s)
{
    return s.print(out);
}

ostream& terminal::print(ostream& out)
{
    return out << _name;
}

ostream& nonterminal::print(ostream& out)
{
    out << _name << " ->" << endl;
    for(vector<symbol*> alt : _alts)
    {
        out << "[ ";
        for(symbol* sym : alt)
        {
            out << sym->name() << " ";
        }
        out << "]" << endl;
    }
    return out;
}

string nonterminal::name()
{
    return _name;
}

string terminal::name()
{
    return _name;
}
