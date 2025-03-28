// Homework 5: Recursive Structures
// Name: Bryce Kwon
// Due: 3/15/24

#include<iostream>

//////////////////////////////////////////////////////////////
// section: Data Structures
//
// Since we are using templated classes,
// we can't define them in a seperate .h file.
// So, we need to have all of the definitions in a single file.
//
// This is going to make one huge file, but I'll try to split it up.
//
// The homework starts at the section: HOMEWORK
//
//////////////////////////////////////////////////////////////


template<typename T>
class List
{
public:
    virtual List<T>* concat(List<T>* rhs) = 0;
    virtual List<T>* reverse() = 0;
    virtual List<T>* remove(T item) = 0;

    virtual std::ostream& print(std::ostream& out) = 0;

    virtual bool equals(List<T>* that) = 0;
    virtual bool equalsNil() {return false;}
    virtual bool equalsCons(T& head, List<T>* tail) {return false;}
};

template<typename T>
class Nil : public List<T>
{
public:
    Nil() {}
    List<T>* concat(List<T>* rhs);
    List<T>* reverse();
    List<T>* remove(T item);

    std::ostream& print(std::ostream& out);

    bool equals(List<T>* that) {return that->equalsNil();}
    bool equalsNil() {return true;}
    bool equalsCons(T& head, List<T>* tail) {return false;}
};

template<typename T>
class Cons : public List<T>
{
private:
    T _head;
    List<T>* _tail;

public:
    Cons(T head, List<T>* tail)
    {
        _head = head;
        _tail = tail;
    }

    List<T>* concat(List<T>* rhs);
    List<T>* reverse();
    List<T>* remove(T item);

    std::ostream& print(std::ostream& out);

    bool equals(List<T>* that) {return that->equalsCons(_head,_tail);}
    bool equalsNil() {return false;}
    bool equalsCons(T& head, List<T>* tail)
    {
        return _head == head && _tail->equals(tail);
    }
};


template<typename T>
class Tree
{
public:
    virtual Tree<T>* flip() = 0;
    virtual List<T>* post_order() = 0;

    virtual std::ostream& print(std::ostream& out) = 0;

    virtual bool equals(Tree<T>* that) = 0;
    virtual bool equalsLeaf(T& x) {return false;}
    virtual bool equalsBranch(T& x, Tree<T>* lhs, Tree<T>* rhs) {return false;}
};

template<typename T>
class Leaf : public Tree<T>
{
private:
    T _x;

public:
    Leaf(T x)
    {
        _x = x;
    }
    Tree<T>* flip();
    List<T>* post_order();

    std::ostream& print(std::ostream& out);

    bool equals(Tree<T>* that) {return that->equalsLeaf(_x);}
    bool equalsLeaf(T& x) {return x == _x;}
};

template<typename T>
class Branch : public Tree<T>
{
private:
    T _x;
    Tree<T>* _left;
    Tree<T>* _right;

public:
    Branch(T x, Tree<T>* left, Tree<T>* right)
    {
        _x = x;
        _left = left;
        _right = right;
    }

    Tree<T>* flip();
    List<T>* post_order();

    std::ostream& print(std::ostream& out);

    bool equals(Tree<T>* that)
    {
        return that->equalsBranch(_x, _left, _right);
    }
    bool equalsBranch(T& x, Tree<T>* left, Tree<T>* right)
    {
        return x == _x && _left->equals(left) && _right->equals(right);
    }
};

class Exp
{
public:
    virtual List<char>* vars() = 0;
    virtual Exp* simplify() = 0;

    virtual std::ostream& print(std::ostream& out) = 0;

    // Equals using double dispatch
    virtual bool equals(Exp* that) = 0;
    virtual bool equalsVar(char c)             {return false;}
    virtual bool equalsNum(int c)              {return false;}
    virtual bool equalsAdd(Exp* lhs, Exp* rhs) {return false;}
    virtual bool equalsSub(Exp* lhs, Exp* rhs) {return false;}
    virtual bool equalsMul(Exp* lhs, Exp* rhs) {return false;}
    virtual bool equalsDiv(Exp* lhs, Exp* rhs) {return false;}
    virtual bool equalsNeg(Exp* e)             {return false;}

    // Methods for checking expression types
    virtual bool isVar() {return false;}
    virtual bool isNum() {return false;}
    virtual bool isAdd() {return false;}
    virtual bool isSub() {return false;}
    virtual bool isMul() {return false;}
    virtual bool isDiv() {return false;}
    virtual bool isNeg() {return false;}
    
    // Methods for deconstructing expressions.
    // This isn't great design, but OOP doesn't
    // give us a clean way to look inside of objects.
    virtual Exp* lhs() {return nullptr;}
    virtual Exp* rhs() {return nullptr;}
};

class Var : public Exp
{
private:
    char _v;

public:
    Var(char v) { _v = v; }
    List<char>* vars();
    Exp* simplify();
    std::ostream& print(std::ostream& out);

    bool equals(Exp* that) {return that->equalsVar(_v);}
    bool equalsVar(char v) {return _v == v;}
    bool isVar()           {return true;}
};

class Num : public Exp
{
private:
    int _n;

public:
    Num(int n) { _n = n; }
    List<char>* vars();
    Exp* simplify();
    std::ostream& print(std::ostream& out);

    bool equals(Exp* that) {return that->equalsNum(_n);}
    bool equalsNum(int n)  {return _n == n;}
    bool isNum()           {return true;}
};

class Neg : public Exp
{
private:
    Exp* _e;

public:
    Neg(Exp* e) { _e = e; }
    List<char>* vars();
    Exp* simplify();
    std::ostream& print(std::ostream& out);

    bool equals(Exp* that) {return that->equalsNeg(_e);}
    bool equalsNeg(Exp* e) {return _e->equals(e);}
    bool isNeg()           {return true;}
    Exp* lhs()             {return _e;}
};

class Add : public Exp
{
private:
    Exp* _lhs;
    Exp* _rhs;

public:
    Add(Exp* lhs, Exp* rhs)
    {
        _lhs = lhs;
        _rhs = rhs;
    }
    List<char>* vars();
    Exp* simplify();
    std::ostream& print(std::ostream& out);

    bool equals(Exp* that) {return that->equalsAdd(_lhs,_rhs);}
    bool equalsAdd(Exp* lhs, Exp* rhs)
    {
        return _lhs->equals(lhs) && _rhs->equals(rhs);
    }
    bool isAdd() {return true;}
    Exp* lhs()   {return _lhs;}
    Exp* rhs()   {return _rhs;}
};

class Sub : public Exp
{
private:
    Exp* _lhs;
    Exp* _rhs;

public:
    Sub(Exp* lhs, Exp* rhs)
    {
        _lhs = lhs;
        _rhs = rhs;
    }
    List<char>* vars();
    Exp* simplify();
    std::ostream& print(std::ostream& out);

    bool equals(Exp* that) {return that->equalsSub(_lhs,_rhs);}
    bool equalsSub(Exp* lhs, Exp* rhs)
    {
        return _lhs->equals(lhs) && _rhs->equals(rhs);
    }
    bool isSub() {return true;}
    Exp* lhs()   {return _lhs;}
    Exp* rhs()   {return _rhs;}
};

class Mul : public Exp
{
private:
    Exp* _lhs;
    Exp* _rhs;

public:
    Mul(Exp* lhs, Exp* rhs)
    {
        _lhs = lhs;
        _rhs = rhs;
    }
    List<char>* vars();
    Exp* simplify();
    std::ostream& print(std::ostream& out);

    bool equals(Exp* that) {return that->equalsMul(_lhs,_rhs);}
    bool equalsMul(Exp* lhs, Exp* rhs)
    {
        return _lhs->equals(lhs) && _rhs->equals(rhs);
    }
    bool isMul() {return true;}
    Exp* lhs()   {return _lhs;}
    Exp* rhs()   {return _rhs;}
};

class Div : public Exp
{
private:
    Exp* _lhs;
    Exp* _rhs;

public:
    Div(Exp* lhs, Exp* rhs)
    {
        _lhs = lhs;
        _rhs = rhs;
    }
    List<char>* vars();
    Exp* simplify();
    std::ostream& print(std::ostream& out);

    bool equals(Exp* that) {return that->equalsDiv(_lhs,_rhs);}
    bool equalsDiv(Exp* lhs, Exp* rhs)
    {
        return _lhs->equals(lhs) && _rhs->equals(rhs);
    }
    bool isDiv() {return true;}
    Exp* lhs()   {return _lhs;}
    Exp* rhs()   {return _rhs;}
};


template<typename T>
std::ostream& operator<<(std::ostream& out, List<T>& l);
template<typename T>
std::ostream& operator<<(std::ostream& out, Tree<T>& t);
std::ostream& operator<<(std::ostream& out, Exp& e);


//////////////////////////////////////////////////////////////
// section: HOMEWORK
//
// Part 1: Lists
//
//////////////////////////////////////////////////////

// Throughout this homework I'll use the notation
// a -> b -> c -> *
// to mean the linked list with 3 elements a, b, c.
// A list is either
// Nil<T>
// or 
// Cons<T>(T head, List<T>* tail)
//
// The list a -> b -> c -> *
// could be created with the constructors for the Nil and Cons class.
//
// cout can use
// cout << *list;
// to print out a list with the -> notation.

// Question 1: 5 pts
//
// We can represent a string as a linked list of characters.
// This isn't an efficient representation, but it does work.
// Using our List representation, write a function
// that returns the string "Hello World" as a linked list
List<char>* hello_world()
{
    std::string str = "Hello World";

    List<char>* list = new Nil<char>();
    for (int i = str.length() - 1; i >= 0; i--) {
        list = new Cons<char>(str[i], list);
    }

    return list;
}


// Question 2: 10 pts
// Write a function that will concatenate 2 lists
// Example:
// l1 = 1 -> 2 -> *;
// l2 = 3 -> 4 -> *;
// l1->concat(l2) should return
// 1 -> 2 -> 3 -> 4 -> *
//
template<typename T>
List<T>* Nil<T>::concat(List<T>* rhs)
{
    return rhs;
}

template<typename T>
List<T>* Cons<T>::concat(List<T>* rhs)
{
    return new Cons<T>(_head, _tail->concat(rhs));
}



// Question 3: 10 pts
// Write a function to reverse a list.
// While we can just change the pointers in C++,
// you're going to destroy the origonal list.
// Instead we want a copy of the origonal list that is reversed.
template<typename T>
List<T>* Nil<T>::reverse()
{
    return new Nil<T>();
}
template<typename T>
List<T>* Cons<T>::reverse()
{
    List<T>* reversedTail = _tail->reverse();
    return reversedTail->concat(new Cons<T>(_head, new Nil<T>()));
}

// Question 4: 10pts
// Write a function to remove an element form a list
// example:
// l = f -> o -> o -> d -> *;
// l->remove(o)
// should return 
// f -> d -> *
//
// While we can just move the pointers in C++,
// You're going to have an easier time if you build a noew list.
template<typename T>
List<T>* Nil<T>::remove(T elem)
{
    return new Nil<T>();
}
template<typename T>
List<T>* Cons<T>::remove(T elem)
{
    if (_head == elem) {
        return _tail->remove(elem);
    }
    else {
        return new Cons<T>(_head, _tail->remove(elem));
    }
}


//////////////////////////////////////////////////////
//
// Part 2: Trees
//
//////////////////////////////////////////////////////

// Trees are built in the same way as lists.
// For this assignment I'll represent trees using LaTeX
// notation
// [.1 [.2 3 4 ] [.5 6 7 ] ]
// representa the tree
//    1
//   / \
//  2   5
// / \ / \
// 3 4 6 7
//
// If you want to render a tree to check if it's correct
// you can go to overleaf.com and put in
//
// \documentclass{article}
// \usepackage{qtree}
// \begin{document}
// \Tree[.1 [.2 3 4 ] [.5 6 7 ] ]
// \end{document}
//
// A Tree consists of either a
// class Leaf(T x)
// or a
// class Node(T x, Tree<T>* lhs, Tree<T>* rhs)

// Question 5: 5pts
// Write a function to create the example tree
//    1
//   / \
//  2   5
// / \ / \
// 3 4 6 7
Tree<int>* example_tree()
{
    Tree<int>* leaf1 = new Leaf<int>(3);
    Tree<int>* leaf2 = new Leaf<int>(4);
    Tree<int>* leaf3 = new Leaf<int>(6);
    Tree<int>* leaf4 = new Leaf<int>(7);

    Tree<int>* branchRight = new Branch<int>(5, leaf3, leaf4);
    Tree<int>* branchLeft = new Branch<int>(2, leaf1, leaf2);

    Tree<int>* root = new Branch<int>(1, branchLeft, branchRight);

    return root;
}

// Question 6: 10pts
// Write a function to flip a tree around it's root.
// example:
// t = [.1 [.2 3 4 ] [.5 6 7 ] ]
// t->flip() should return 
// [.1 [.5 7 6 ] [.2 4 3 ] ]
// Or graphically
//    1             1
//   / \    =>     / \
//  2   5  flip   5   2
// / \ / \       / \ / \
// 3 4 6 7       7 6 4 3
template<typename T>
Tree<T>* Leaf<T>::flip()
{
    return this;
}
template<typename T>
Tree<T>* Branch<T>::flip()
{
    Tree<T>* tmp = this->_left;
    this->_left = this->_right;
    this->_right = tmp;

    if (this->_left != nullptr) {
        this->_left = this->_left->flip();
    }

    if (this->_right != nullptr) {
        this->_right = this->_right->flip();
    }

    return this;
}

// Question 7: 10pts
// Write a function to do an post-order traversal on a tree,
// and construct a List from that traversal
// example:
//    1
//   / \
//  2   5
// / \ / \
// 3 4 6 7
// should return
// 3 -> 4 -> 2 -> 6 -> 7 -> 5 -> 1 -> *
template<typename T>
List<T>* Leaf<T>::post_order()
{
    return new Cons<T>(this->_x, new Nil<T>());
}
template<typename T>
List<T>* Branch<T>::post_order()
{
    List<T>* leftList = (this->_left != nullptr) ? this->_left->post_order() : new Nil<T>();
    List<T>* rightList = (this->_right != nullptr) ? this->_right->post_order() : new Nil<T>();

    return leftList->concat(rightList)->concat(new Cons<T>(this->_x, new Nil<T>()));
}


//////////////////////////////////////////////////////
//
// Part 3: ASTs
//
//////////////////////////////////////////////////////

// An abstract syntax tree is just a tree with
// more types of branches.
// In our AST will represent and expression and has 8 cases
//
//  Var(char)
//  Num(int)
//  Add(Exp*,Exp*)
//  Sub(Exp*,Exp*)
//  Mul(Exp*,Exp*)
//  Div(Exp*,Exp*)
//  Neg(Exp*)
//
// I'll use normal expression notation to represent ASTs
// (1 + x) * (1 - x)
// represents the Exp
//     *
//    / \
//   +   -
//  / \ / \
//  1 x 1 x
//

// problem 8: 5pts
// Write a function to construct the expression
// (x + 1) * (x - 1)
Exp* make_exp()
{
    Exp* x = new Var('x');
    Exp* one = new Num(1);

    Exp* addition = new Add(one, x);
    Exp* subtraction = new Sub(one, x);

    Exp* result = new Mul(addition, subtraction);

    return result;
}

// problem 9: 10pts
// Write a function to find all of the variables in an expression.
// example:
// e = a*x*x + b * x + c
// e->vars()
// should return
// a -> x -> x -> b -> x -> c -> *
// 
// You may have repeating variables in your answer.
List<char>* Var::vars() { return new Cons<char>(this->_v, new Nil<char>()); }
List<char>* Num::vars() { return new Nil<char>(); }
List<char>* Add::vars() { return this->_lhs->vars()->concat(this->_rhs->vars()); }
List<char>* Sub::vars() { return this->_lhs->vars()->concat(this->_rhs->vars()); }
List<char>* Mul::vars() { return this->_lhs->vars()->concat(this->_rhs->vars()); }
List<char>* Div::vars() { return this->_lhs->vars()->concat(this->_rhs->vars()); }
List<char>* Neg::vars() { return this->_e->vars(); }

// problem 10: 25pts
// Ok, ASTs are important, but the real use is cheating at math homework.
// Specifically, I need help with simplifying expressions.
// I get the right answer, but I still get points marked off.
//
// Fortunately it turns out that simplifyign expressions is just
// a bunch of short rules that we repeat over an over again
// As an example 
// (2+(x-(y/y)*x))/2 can be simplified
// (2+(x-(y/y)*x))/2 
// (2+(x-1*x))/2 
// (2+(x-x))/2 
// (2+0)/2 
// 2/2 
// 1
//
// We just repeatedly applied a few simple rules
// 1*e => e
// e+0 => e
// e-e => 0
// e/e => 1
//
// This strategy of simplifying an expression using rules is called rewriting.
//
// The general strategy is pretty easy
// Variables and numbers can't be simplified,
// for every other expression, we simplify the children,
// then we see if that simplified expression matches one of our rules.
// I've made a simplify function that works for double negation.
// --e => e
// Your job is to expand this simplify function to work for all of the rules.
//
// You may also find the if let syntax helpful
// https://doc.rust-lang.org/rust-by-example/flow_control/if_let.html
//
// Each rule (aside from --e and 0+e) is worth 2 points.
// Any points above 25 will be extra credit.
// You can also add more rules if you want.
// num(-n)    => -num(n)
// 0+e        => e
// e+0        => e
// e+e        => 2*e
// e1 + (-e2) => e1 - e2
// (-e1) + e2 => e2 - e1
// e-0        => e
// 0-e        => -e
// e-e        => 0
// e1 - (-e2) => e1 + e2
// (-e1) - e2 => e1 + e2
// 0*e        => 0
// e*0        => 0
// 1*e        => e
// e*1        => e
// (-e1) * e2 => - (e1 * e2)
// e1 * (-e2) => - (e1 * e2)
// 0/e        => 0
// 1/e        => e
// e/1        => e
// e/e        => 1
// (-e1) / e2 => - (e1 / e2)
// e1 / (-e2) => - (e1 / e2)
// --e        => e
// -(e1+e2)   => (-e1) + (-e2)
// -(e1-e2)   => (-e1) - (-e2)
Exp* Var::simplify()
{ 
    // variables cannot be simplified
    return this;
}
Exp* Num::simplify()
{
    // numbers cannot be simplified
    return this;
}
Exp* Add::simplify()
{
    Exp* lhs = _lhs->simplify();
    Exp* rhs = _rhs->simplify();
    
    if (lhs->isNum() && static_cast<Num*>(lhs)->equalsNum(0)) {
        return lhs;
    }
    else if (rhs->isNum() && static_cast<Num*>(rhs)->equalsNum(0)) {
        return rhs;
    }
    else if (lhs->equals(rhs)) {
        return new Mul(new Num(2), lhs);
    }
    else {
        return new Add(lhs, rhs);
    }
}
Exp* Sub::simplify()
{
    Exp* lhs = _lhs->simplify();
    Exp* rhs = _rhs->simplify();
    
    if (rhs->isNum() && static_cast<Num*>(rhs)->equalsNum(0)) {
        return rhs;
    }
    else if (lhs->equals(rhs)) {
        return new Num(0);
    }
    else {
        return new Sub(lhs, rhs);
    }
}
Exp* Mul::simplify()
{
    Exp* lhs = _lhs->simplify();
    Exp* rhs = _rhs->simplify();
    
    if ((lhs->isNum() && static_cast<Num*>(lhs)->equalsNum(0)) || (rhs->isNum() && static_cast<Num*>(rhs)->equalsNum(0))) {
        return new Num(0);
    }
    else if (lhs->isNum() && static_cast<Num*>(lhs)->equalsNum(1)) {
        return lhs;
    }
    else if (rhs->isNum() && static_cast<Num*>(rhs)->equalsNum(1)) {
        return rhs;
    }
    else {
        return new Mul(lhs, rhs);
    }
}
Exp* Div::simplify()
{
    Exp* lhs = _lhs->simplify();
    Exp* rhs = _rhs->simplify();
    
    if (lhs->isNum() && static_cast<Num*>(lhs)->equalsNum(0)) {
        return new Num(0);
    }
    else if (rhs->isNum() && static_cast<Num*>(rhs)->equalsNum(1)) {
        return lhs;
    }
    else if (rhs->equals(lhs)) {
        return new Num(1);
    }
    else {
        return new Div(lhs, rhs);
    }
}
Exp* Neg::simplify()
{
    Exp* s = _e->simplify();
    if(s->isNeg())
    {
        return s->lhs();
    }
    else
    {
        return new Neg(_e->simplify());
    }
}

////////////////////////////////////////////////////////////////
//
// functions for printing the data structures.
// You don't need to do anything here.
//
////////////////////////////////////////////////////////////////

template<typename T>
std::ostream& operator<<(std::ostream& out, List<T>& l)
{
    return l.print(out);
}
template<typename T>
std::ostream& operator<<(std::ostream& out, Tree<T>& t)
{
    return t.print(out);
}
std::ostream& operator<<(std::ostream& out, Exp& e)
{
    return e.print(out);
}

template<typename T>
std::ostream& Nil<T>::print(std::ostream& out)
{
    return out << "*";
}
template<typename T>
std::ostream& Cons<T>::print(std::ostream& out)
{
    return out << _head << " -> " << *_tail;
}
template<typename T>
std::ostream& Leaf<T>::print(std::ostream& out)
{
    return out << _x;
}
template<typename T>
std::ostream& Branch<T>::print(std::ostream& out)
{ 
    return out << "[." << _x << " " << *_left << " " << *_right << " ]";
}

std::ostream& Var::print(std::ostream& out)
{
    return out << _v;
}
std::ostream& Num::print(std::ostream& out)
{
    return out << _n;
}
std::ostream& Neg::print(std::ostream& out)
{
    return out << "-(" << *_e << ")";
}
std::ostream& Add::print(std::ostream& out)
{
    return out << "(" << *_lhs << ") + (" << *_rhs << ")";
}
std::ostream& Sub::print(std::ostream& out)
{
    return out << "(" << *_lhs << ") - (" << *_rhs << ")";
}
std::ostream& Mul::print(std::ostream& out)
{
    return out << "(" << *_lhs << ") * (" << *_rhs << ")";
}
std::ostream& Div::print(std::ostream& out)
{
    return out << "(" << *_lhs << ") / (" << *_rhs << ")";
}
