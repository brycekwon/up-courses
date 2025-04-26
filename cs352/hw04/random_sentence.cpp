
#include <iostream>
#include <time.h>
#include "grammar_reader.h"
#include "symbol.h"
#include "parse_exception.h"

using namespace std;

int main()
{
    try
    {
        // throw new parse_exception("something went wrong");
        grammar_reader reader;
        symbol* start_symbol = reader.read_grammar();
        srand(time(NULL));
        start_symbol->generate();
        cout << endl;
    }
    catch(parse_exception* e)
    {
        cerr << e->what() << endl;
        return 1;
    }
    return 0;
}
