// Homework 3: Recursive Structures
// Name:
// Due: 2/16/24

// This assignment is for practicing
// programming with recursive data structures.
// We'll split this into 3 parts.
// 1. Working with lists
// 2. Working with trees
// 3. Working with ASTs
// Each section will have a few problems in it.
// The first is kind of a warmup problem
// The rest will be more challanging.
//
// The definitions for Tree, List, and AST are in types.rs
//mod types;
use types::{List, List::*, nil, cons};
use types::{Tree, Tree::*, leaf, branch};
use types::{Exp, Exp::*, var,num,add,sub,mul,div,neg};
use std::cmp::{PartialEq};


//////////////////////////////////////////////////////
//
// Part 1: Lists
//
//////////////////////////////////////////////////////

// Throughout this homework I'll use the notation
// a -> b -> c -> *
// to mean the linked list with 3 elements a, b, c.
// A list is either
// Nil : Box<List<T>>
// or 
// Cons(head : T , tail : Box<List<T>>)
// Both Nil and tail need to have Box<> because
// they need to be allocated on the heap.
//
// The list a -> b -> c -> *
// could be created with 
// Box::new(Cons(a,Box::new(b,Box::new(c,Box::new(Nil)))))
// However, this would be a pain.
// Instead, I've provided two helper functions in types.hs
// fn nil() -> Box<List<T>>
// fn cons(T, Box<List<T>>) -> Box<List<T>>
// Now, we can write the list as
// cons(a,cons(b,cons(c,nil())))
// 
//
// You can use println!("{}", list)
// to print out your list with the -> notation.
// Or you can use println!("{:?}", list) to print out the structure
// using the Box(List) notation.


// Question 1: 5 pts
//
// We can represent a string as a linked list of characters.
// This isn't an efficient representation, but it does work.
// Using our List representation, write a function
// that returns the string "Hello World" as a linked list
pub fn hello_world() -> Box<List<char>>
{
    let msg : Box<List<char>> = cons('H', cons('e', cons('l', cons('l',
                                cons('o', cons(' ', cons('W', cons('o',
                                cons('r', cons('l', cons('d', nil())))))))))));
    return msg;
}


// Sidebar: <T : Clone>
// Rust is very picky about copying data.
// We talked in class about how rust won't just let you copy a pointer.
// But there is an alternative to copying a pointer.
// We could clone the entire contents of an object.
// Rust will let you do that, but it makes you be explicit about it.
// Other language (like C++) will happily 
// clone a bunch of data without telling you.
// If we want to make a function to copy a List,
// we can do that, but we have to tell 
// Rust that we are copying each indivitual element.
//
// This is the purpose of the Clone trait.
// If I add the Clone to my type, 
// then I can call the .clone() method to copy it.
// The syntax is just
// fn copy<T : Clone>(l : Box<List<T>>) -> Box<List<T>>
// 
// This is just like a normal template in Rust,
// but I've specifided that anythig of type T can be cloned.
// So I can call the .clone method on any value of type T.



// Sidebar 2: The &** notation
// When we write a function, we would like to take a Box<List<T>>
// as a parameter.
// Unfortunately this is going to lead to problems.
// If we take call the function on any actual list, then
// the function will take ownership of the list.
// example: 
// suppose I have 
// copy<T : Clone>(l : Box<List<T>>) -> Box<List<T>> { ... }
// let l1 = cons(1,cons(2,nil()));
// let l2 = copy(l1);
// Now, copy has taken ownership of the list in l1.
// If I try to use l1 again, then I'll get a compiler error.
// It really defeats the purpose of a copy function
// if I lose access to the origonal.
// 
// Instead, I should borrow l1
// copy<T : Clone>(l : &Box<List<T>>) -> Box<List<T>>
// let l1 = cons(1,cons(2,nil()));
// let l2 = copy(&l1);
//
// This works much better, but now we need to implement copy.
// We just need to copy a list node by node.
// copy<T : Clone>(l : &Box<List<T>>) -> Box<List<T>>
// {
//     match l
//     {
//         Nil => Nil,
//         Cons(head,tail) => Cons(head.clone(), copy(tail))
//     }
// }
// This logically should work, but if we try to compile it we get
// an error about matching on struct Box.
// To solve this problem, we can just dereference the box with *
// (remember, Box<T> is just a pointer to T).
//
// copy<T : Clone>(l : &Box<List<T>>) -> Box<List<T>>
// {
//     match *l
//     {
//         Nil => Nil,
//         Cons(head,tail) => Cons(head.clone(),copy(tail))
//     }
// }
// Now, when I compile it, I still get the same error.
// What gives? we dereferenced the pointer, right?
//
// It turns out no.  Because, there are two pointers.
// Whenever we borrow something in rust, we're taking the address of it.
// So, the &Box<List<T>> actually is behind 2 pointers.
// One for the & and one for the Box.
//
// Ok, easy enough, we'll just dereference it twice.
// copy<T : Clone>(l : &Box<List<T>>) -> Box<List<T>>
// {
//     match **l
//     {
//         Nil => Nil,
//         Cons(head,tail) => Cons(head.clone(),copy(tail))
//     }
// }
// Now, we've drilled down to the orignal list, but when we compile
// we get an error about moving out of barrowed list l.
// Since we dereferenced all the way to the List,
// the match statement itself takes ownersip of the contents of l.
// To solve this problem we actually need to 
// barrow from the value we just dereferenced.
//
// This leads to the final (working) implementation of copy
// copy<T : Clone>(l : &Box<List<T>>) -> Box<List<T>>
// {
//     match &**l
//     {
//         Nil => Nil,
//         Cons(head,tail) => Cons(head.clone(),copy(tail))
//     }
// }
//
// If you don't like the &** syntax, you can also convert the box
// to a reference using the .as_ref() method.
// This converts a pointer to a reference.
// Since calling a method automatically dereferences a pointer in rust,
// this dereferences, converts to a reference, 
// and barrows the reference all at once.
// copy<T : Clone>(l : &Box<List<T>>) -> Box<List<T>>
// {
//     match l.as_ref()
//     {
//         Nil => Nil,
//         Cons(head,tail) => Cons(head.clone(),copy(tail))
//     }
// }
fn copy<T : Clone>(lhs : &Box<List<T>>) -> Box<List<T>>
{
    match &**lhs
    {
        Nil => nil(),
        Cons(h,t) => cons(h.clone(), copy(t))
    }
}


// Question 2: 10 pts
// Write a function that will concatenate 2 lists
// Example:
// concat(1 -> 2 -> *, 3 -> 4 -> *) should return
// 1 -> 2 -> 3 -> 4 -> *
//
// Think carefully about ownership here.
pub fn concat<T : Clone>(lhs : &Box<List<T>>, 
                         rhs : &Box<List<T>>) -> Box<List<T>>
{
    match &**lhs {
        Nil => copy(rhs),
        Cons(h, t) => cons(h.clone(), concat(t, rhs))
    }
}



// Question 3: 10 pts
// Write a function to reverse a list.
// Remember, we're barrowing the list, so we can't just change the
// pointers around.
// We need to construct a new list that is the reverse of the old one.
pub fn reverse<T : Clone>(list : &Box<List<T>>) -> Box<List<T>>
{
    let mut result = nil();
    let mut current = list;
    while let Cons(h, t) = &**current {
        result = cons(h.clone(), result);
        current = t;
    }
    result
}

// Question 4: 10pts
// Write a function to remove an element form a list
// example:
// remove (f -> o -> o -> d -> *, o)
// should return 
// f -> d -> *
//
// Again, you can't just move the pointers.
// Instead you want to construct a new list that has the same elements
// just without the o's.
//
// Note: the PartialEq just allows us to use == and != with elements
// in our list.
pub fn remove<T : Clone + PartialEq>(list : &Box<List<T>>, item : T) 
                      -> Box<List<T>>
{
    match &**list {
        Nil => nil(),
        Cons(h, t) => {
            if *h == item {
                remove(t, item.clone())
            } else {
                cons(h.clone(), remove(t, item.clone()))
            }
        }
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
// Leaf(x : T)
// or a
// Branch(x : T, lhs : Box<Tree<T>>, rhs : Box<Tree<T>>)
//
// Again, I have leaf() and branch() functions 
// to help with constructing trees.
// leaf(x : T) -> Box<Tree<T>>
// branch(x : T, lhs : Box<Tree<T>>, rhs : Box<Tree<T>>) -> Box<Tree<T>>

// Question 5: 5pts
// Write a function to create the example tree
//    1
//   / \
//  2   5
// / \ / \
// 3 4 6 7
pub fn example_tree() -> Box<Tree<i32>>
{ 
    branch(1,
        branch(2,
            leaf(3),
            leaf(4)
        ),
        branch(5,
            leaf(6),
            leaf(7)
        )
    )
}

// Question 6: 10pts
// Write a function to flip a tree around it's root.
// example:
// flip([.1 [.2 3 4 ] [.5 6 7 ] ]) should return 
// [.1 [.5 7 6 ] [.2 4 3 ] ]
// Or graphically
//    1             1
//   / \    =>     / \
//  2   5  flip   5   2
// / \ / \       / \ / \
// 3 4 6 7       7 6 4 3
pub fn flip<T : Clone>(t : &Box<Tree<T>>) -> Box<Tree<T>>
{
    match &**t {
        Leaf(_) => t.clone(),
        Branch(x, lhs, rhs) => branch(x.clone(), flip(rhs), flip(lhs)),
    }
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
pub fn post_order<T : Clone>(t : &Box<Tree<T>>) -> Box<List<T>>
{
    nil()
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
//  Num(i32)
//  Add(Box<Exp>,Box<Exp>)
//  Sub(Box<Exp>,Box<Exp>)
//  Mul(Box<Exp>,Box<Exp>)
//  Div(Box<Exp>,Box<Exp>)
//  Neg(Box<Exp>)
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
pub fn make_exp() -> Box<Exp>
{
    Box::new(Exp::Mul(
        Box::new(Exp::Add(
            Box::new(Exp::Var('x')), Box::new(Exp::Num(1)))
        ),
        Box::new(Exp::Sub(
            Box::new(Exp::Var('x')), Box::new(Exp::Num(1)))
        )
    ))
}

// problem 9: 10pts
// Write a function to find all of the variables in an expression.
// example:
// vars(a*x*x + b * x + c)
// should return
// a -> x -> x -> b -> x -> c -> *
// 
// You may have repeating variables in your answer.
pub fn vars(e : &Box<Exp>) -> Box<List<char>>
{
    nil()
}

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
pub fn simplify(e : &Box<Exp>) -> Box<Exp>
{
    match e.as_ref()
    {
        Var(x) => { var(*x) } // the parameters here are also references
        Num(n) => { num(*n) } // but we need them to be chars and its
        Add(e1,e2) =>
        {
            let s1 = simplify(e1);
            let s2 = simplify(e2);
            if *s1 == Exp::Num(0) 
            {
                return s2;
            }
            add(s1,s2)
        }
        Sub(e1,e2) =>
        {
            let s1 = simplify(e1);
            let s2 = simplify(e2);
            sub(s1,s2)
        }
        Mul(e1,e2) =>
        {
            let s1 = simplify(e1);
            let s2 = simplify(e2);
            mul(s1,s2)
        }
        Div(e1,e2) =>
        {
            let s1 = simplify(e1);
            let s2 = simplify(e2);
            div(s1,s2)
        }
        Neg(e) =>
        {
            let s = simplify(e);
            match *s
            {
                Neg(s1) => s1,
                _       => neg(s)
            }
        }
    }
}


