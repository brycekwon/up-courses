
use std::fmt;
use self::Exp::*;
use self::List::*;
use self::Tree::*;

// A List data structure
// The derive(PartialEq, Debug, Clone)
// automatically provide our structure with some operations
// PartialEq means that if I have 2 lists l1 and l2,
// then I can compare them with l1 == l2 and l1 != l2
//
// Debug means that I can print the structure out with
// println!("{:?}", l1)
// and It will print out in the format
// Cons(a, Box::new(Cons(b, Box::new(Cons(c, Box::new(Nil))))))
//
// Clone means that I can call l1.clone() and get a copy of the list.
#[derive(PartialEq,Debug,Clone)]
pub enum List<T>
{
    Nil,
    Cons(T,Box<List<T>>)
}

#[derive(PartialEq,Debug,Clone)]
pub enum Tree<T>
{
    Leaf(T),
    Branch(T, Box<Tree<T>>, Box<Tree<T>>)
}

#[derive(PartialEq,Debug,Clone)]
pub enum Exp
{
    Var(char),
    Num(i32),
    Add(Box<Exp>,Box<Exp>),
    Sub(Box<Exp>,Box<Exp>),
    Mul(Box<Exp>,Box<Exp>),
    Div(Box<Exp>,Box<Exp>),
    Neg(Box<Exp>)
}

// Helper function for constructing Lists

pub fn nil<T>() -> Box<List<T>>
{
    Box::new(Nil)
}
pub fn cons<T>(head : T, tail : Box<List<T>>) -> Box<List<T>>
{
    Box::new(Cons(head,tail))
}

// Helper function for constructing Trees

pub fn leaf<T>(x : T) -> Box<Tree<T>>
{
    Box::new(Leaf(x))
}
pub fn branch<T>(x : T, l : Box<Tree<T>>, r : Box<Tree<T>>) -> Box<Tree<T>>
{
    Box::new(Branch(x,l,r))
}

// Helper function for constructing Exps

pub fn var(x : char) -> Box<Exp>
{
    Box::new(Var(x))
}
pub fn num(n : i32) -> Box<Exp>
{
    Box::new(Num(n))
}
pub fn add(e1 : Box<Exp>, e2 : Box<Exp>) -> Box<Exp>
{
    Box::new(Add(e1,e2))
}
pub fn sub(e1 : Box<Exp>, e2 : Box<Exp>) -> Box<Exp>
{
    Box::new(Sub(e1,e2))
}
pub fn mul(e1 : Box<Exp>, e2 : Box<Exp>) -> Box<Exp>
{
    Box::new(Mul(e1,e2))
}
pub fn div(e1 : Box<Exp>, e2 : Box<Exp>) -> Box<Exp>
{
    Box::new(Div(e1,e2))
}
pub fn neg(e : Box<Exp>) -> Box<Exp>
{
    Box::new(Neg(e))
}


///////////////////////////////////////////////////////////
// 
// Code for printing out List, Tree, and Exp
//
// You can use 
// println!("{}", x) to print out x if it's one of those types
// 
// List will print out in the format
// a -> b -> c -> *
//
// Tree will print out in the format
// [.a [.b c d ] [.e f g ] ]
// For the tree
//       a
//      / \
//     b   e
//    / \ / \
//    c d f g
//
// Exp will print out a normal expression with parentheses
// around every subexpression
// ((1) * (2)) + (3)
///////////////////////////////////////////////////////////


impl<T : fmt::Display> fmt::Display for List<T>
{
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result 
    {
        match self 
        {
            Nil             => write!(f, "*"),
            Cons(head,tail) => write!(f, "{} -> {}", head, tail)
        }
    }
}

impl<T : fmt::Display> fmt::Display for Tree<T>
{
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result 
    {
        match self 
        {
            Leaf(n)       => write!(f, "{}", n),
            Branch(v,l,r) => write!(f, "[.{} {} {} ]", v, l, r),
        }
    }
}

impl fmt::Display for Exp
{
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result 
    {
        match self 
        {
            Var(x) => write!(f, "{x}"),
            Num(n) => write!(f, "{n}"),
            Add(e1,e2) => write!(f, "({}) + ({})", e1, e2),
            Sub(e1,e2) => write!(f, "({}) - ({})", e1, e2),
            Mul(e1,e2) => write!(f, "({}) * ({})", e1, e2),
            Div(e1,e2) => write!(f, "({}) / ({})", e1, e2),
            Neg(e)     => write!(f, "-({})", e),
        }
    }
}

