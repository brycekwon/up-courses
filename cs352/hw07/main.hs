module Hw7 where

-- Homework 7: Recursive Structures
-- Name: Bryce Kwon
-- Due: 4/5/23

-- This assignment is for practicing
-- programming with recursive data structures.
-- We'll split this into 3 parts.
-- 1. Working with lists
-- 2. Working with trees
-- 3. Working with ASTs
-- Each section will have a few problems in it.
-- The first is kind of a warmup problem
-- The rest will be more challanging.
--
-- The definitions for Tree, List, and AST are in types.rs
import Types
import Prelude hiding (flip, reverse)

------------------------------------------------------
--
-- Part 1: Lists
--
------------------------------------------------------

-- Throughout this homework I'll use the notation
-- a -> b -> c -> *
-- to mean the linked list with 3 elements a, b, c.
-- A list is either
-- [] : [a]
-- or 
-- x : xs where x : a and xs : [a]
--
-- Question 1: 5 pts
--
-- In haskell a String is a list of Char's.
-- It's literally defined as
-- type String = [Char]
-- Using our List representation, write a function
-- that returns the string "Hello World" as a linked list,
-- without using the string notation (you have to build the list manually)
helloWorld :: String
helloWorld = 'H' : 'e' : 'l' : 'l' : 'o' : ' ' : 'W' : 'o' : 'r' : 'l' : 'd' : []



-- Question 2: 10 pts
-- Write a function that will concatenate 2 lists
-- Example:
-- append (1:2:[]) (3:4:[]) should return
-- 1 : 2 : 3 : 4 : []
--
-- This is what the ++ function does.
append :: [a] -> [a] -> [a]
append [] list2 = list2
append (item:list1) list2 = item : append list1 list2

-- Question 3: 10 pts
-- Write a function to reverse a list.
-- notice that I've hidden the Haskell definition for reverse,
-- so you can't just call that.
reverse :: [a] -> [a]
reverse [] = []
reverse (item:list) = reverse list ++ [item]

-- Question 4: 10pts
-- Write a function to remove an element form a list
-- example:
-- remove (f -> o -> o -> d -> *, o)
-- should return 
-- f -> d -> *
--
-- NO USING FILTER!
--
-- note: Eq a just means that we can use the == function for type a.
-- It's like an interface in Java.
remove :: (Eq a) => [a] -> a -> [a]
remove [] ys = []
remove (item:list1) list2 = if item == list2 then list1 else item : remove list1 list2


------------------------------------------------------
--
-- Part 2: Trees
--
------------------------------------------------------

-- Trees are built in the same way as lists.
-- For this assignment I'll represent trees using LaTeX
-- notation
-- [.1 [.2 3 4 ] [.5 6 7 ] ]
-- representa the tree
--    1
--   / \
--  2   5
-- / \ / \
-- 3 4 6 7
--
-- If you want to render a tree to check if it's correct
-- you can go to overleaf.com and put in
--
-- \documentclass{article}
-- \usepackage{qtree}
-- \begin{document}
-- \Tree[.1 [.2 3 4 ] [.5 6 7 ] ]
-- \end{document}
--
-- A Tree consists of either a
-- Leaf a
-- or a
-- Branch a (Tree a) (Tree a)
--

-- Question 5: 5pts
-- Write a function to create the example tree
--    1
--   / \
--  2   5
-- / \ / \
-- 3 4 6 7
exampleTree :: Tree Int
exampleTree = Branch 1
        (Branch 2
            (Leaf 3)
            (Leaf 4))
        (Branch 5
            (Leaf 6)
            (Leaf 7))

-- Question 6: 10pts
-- Write a function to flip a tree around it's root.
-- example:
-- flip([.1 [.2 3 4 ] [.5 6 7 ] ]) should return 
-- [.1 [.5 7 6 ] [.2 4 3 ] ]
-- Or graphically
--    1             1
--   / \    =>     / \
--  2   5  flip   5   2
-- / \ / \       / \ / \
-- 3 4 6 7       7 6 4 3
flip :: Tree a -> Tree a
flip (Leaf x) = Leaf x
flip (Branch x left right) = Branch x (flip right) (flip left)

-- Question 7: 10pts
-- Write a function to do an post-order traversal on a tree,
-- and construct a List from that traversal
-- example:
--    1
--   / \
--  2   5
-- / \ / \
-- 3 4 6 7
-- should return
-- 3 -> 4 -> 2 -> 6 -> 7 -> 5 -> 1 -> *
postOrder :: Tree a -> [a]
postOrder (Leaf x) = [x]
postOrder (Branch x left right) = postOrder left ++ postOrder right ++ [x]


------------------------------------------------------
--
-- Part 3: ASTs
--
------------------------------------------------------

-- An abstract syntax tree is just a tree with
-- more types of branches.
-- In our AST will represent and expression and has 8 cases
--
--  Var(char)
--  Num(i32)
--  Add(Box<Exp>,Box<Exp>)
--  Sub(Box<Exp>,Box<Exp>)
--  Mul(Box<Exp>,Box<Exp>)
--  Div(Box<Exp>,Box<Exp>)
--  Neg(Box<Exp>)
--
-- I'll use normal expression notation to represent ASTs
-- (1 + x) * (1 - x)
-- represents the Exp
--     *
--    / \
--   +   -
--  / \ / \
--  1 x 1 x
--
--
-- problem 8: 5pts
-- Write a function to construct the expression
-- (x + 1) * (x - 1)
makeExp :: Exp
makeExp = Mul (Add (Var 'x') (Num 1)) (Sub (Var 'x') (Num 1))

-- problem 9: 10pts
-- Write a function to find all of the variables in an expression.
-- example:
-- vars (a*x*x + b*x + c)
-- should return
-- a -> x -> x -> b -> x -> c -> *
-- 
-- You may have repeating variables in your answer.
vars :: Exp -> [Char]
vars (Var v) = [v]
vars (Num _) = []
vars (Add l r) = vars l ++ vars r
vars (Sub l r) = vars l ++ vars r
vars (Mul l r) = vars l ++ vars r
vars (Div l r) = vars l ++ vars r
vars (Neg e) = vars e

-- problem 10: 25pts
-- Ok, ASTs are important, but the real use is cheating at math homework.
-- Specifically, I need help with simplifying expressions.
-- I get the right answer, but I still get points marked off.
--
-- Fortunately it turns out that simplifyign expressions is just
-- a bunch of short rules that we repeat over an over again
-- As an example 
-- (2+(x-(y/y)*x))/2 can be simplified
-- (2+(x-(y/y)*x))/2 
-- (2+(x-1*x))/2 
-- (2+(x-x))/2 
-- (2+0)/2 
-- 2/2 
-- 1
--
-- We just repeatedly applied a few simple rules
-- 1*e => e
-- e+0 => e
-- e-e => 0
-- e/e => 1
--
-- This strategy of simplifying an expression using rules is called rewriting.
--
-- The general strategy is pretty easy
-- Variables and numbers can't be simplified,
-- for every other expression, we simplify the children,
-- then we see if that simplified expression matches one of our rules.
-- I've made a simplify function that works for double negation.
-- --e => e
-- Your job is to expand this simplify function to work for all of the rules.
--
-- You may also find the if let syntax helpful
-- https://doc.rust-lang.org/rust-by-example/flow_control/if_let.html
--
-- Each rule (aside from --e and 0+e) is worth 2 points.
-- Any points above 25 will be extra credit.
-- You can also add more rules if you want.
-- num(-n)    => -num(n)
-- 0+e        => e
-- e+0        => e
-- e+e        => 2*e
-- e1 + (-e2) => e1 - e2
-- (-e1) + e2 => e2 - e1
-- e-0        => e
-- 0-e        => -e
-- e-e        => 0
-- e1 - (-e2) => e1 + e2
-- (-e1) - e2 => e1 + e2
-- 0*e        => 0
-- e*0        => 0
-- 1*e        => e
-- e*1        => e
-- (-e1) * e2 => - (e1 * e2)
-- e1 * (-e2) => - (e1 * e2)
-- 0/e        => 0
-- 1/e        => e
-- e/1        => e
-- e/e        => 1
-- (-e1) / e2 => - (e1 / e2)
-- e1 / (-e2) => - (e1 / e2)
-- --e        => e
-- -(e1+e2)   => (-e1) + (-e2)
-- -(e1-e2)   => (-e1) - (-e2)
simplify :: Exp -> Exp
simplify (Var v)   = Var v
simplify (Num n)   = Num n
simplify (Add l r) = Add (simplify l) (simplify r)
simplify (Sub l r) = Sub (simplify l) (simplify r)
simplify (Mul l r) = Mul (simplify l) (simplify r)
simplify (Div l r) = Div (simplify l) (simplify r)
simplify (Neg e)   = simpNeg (simplify e)
 where simpNeg (Neg s) = s
       simpNeg s       = Neg s

