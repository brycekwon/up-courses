#include "hw5.h"
#include<iostream>

using namespace std;

int main()
{
    List<int>* l1 = new Cons<int>(1, new Nil<int>());
    List<int>* l2 = new Cons<int>(2, new Nil<int>());
    cout << "hello world: " << *hello_world()                << endl;
    cout << "concat: "      << *l1->concat(l2)               << endl;
    cout << "reverse: "     << *(l1->concat(l2))->reverse()  << endl;
    cout << "remove: "      << *(l1->concat(l2))->remove(2)  << endl;
    cout << "exmple tree: " << *example_tree()               << endl;
    cout << "flip: "        << *example_tree()->flip()       << endl;
    cout << "post order: "  << *example_tree()->post_order() << endl;
    cout << "example exp: " << *make_exp()                   << endl;
    cout << "vars: "        << *make_exp()->vars()           << endl;
    cout << "simplify: "    << *make_exp()->simplify()       << endl;
    return 0;
}
