#ifndef SYMBOL_H
#define SYMBOL_H

#include<vector>
#include<string>


class symbol
{
public:
    virtual void generate() = 0;
    virtual ~symbol() {}

    //only used for printing
    virtual std::string name() = 0;
    virtual std::ostream& print(std::ostream& out) = 0;
};


using alternate = std::vector<symbol*>;

class terminal : public symbol
{
private:
    std::string _name;

public:
    terminal(std::string);
    void generate();
    ~terminal() {}

    //only used for printing
    std::string name();
    std::ostream& print(std::ostream& out);
};

class nonterminal : public symbol
{
private:
    std::string _name;
    std::vector<alternate> _alts;

public:
    nonterminal(std::string);
    void generate();
    void add_alt(std::vector<symbol*> alt);
    ~nonterminal() {}

    //only used for printing
    std::ostream& print(std::ostream& out);
    std::string name();
};

std::ostream& operator<<(std::ostream& out, symbol& l);

#endif // SYMBOL_H
