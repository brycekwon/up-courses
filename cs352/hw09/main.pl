% Homework 9: Recursive Structures
% Name: Bryce Kwon
% Due: 4/19/23

% This assignment is for practicing
% programming with recursive data structures.
% We'll split this into 3 parts.
% 1. Working with lists
% 2. Working with trees
% 3. Working with ASTs
% Each section will have a few problems in it.
% The first is kind of a warmup problem
% The rest will be more challanging.
%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Part 1: Lists
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Throughout this homework I'll use the notation
% a -> b -> c -> *
% to mean the linked list with 3 elements a, b, c.
% A list is either
% []
% or 
% [H|T] where H is an element, and T is a list
%
% Question 1: 5 pts
%
% Using our List representation, write a predicate
% that is true if the list is the characters [h,e,l,l,o]
% For this problem specifically you must use the [|] notation for making a list.
% for example [h,i] is [h|[i|[]]]
hello([h,e,l,l,o|[]]).

% Question 2: 10 pts
% Write a predicate that will concatenate 2 lists
% Example:
% concat([1,2],[3,4],[1,2,3,4]).
% true
%
% This is what the append predicate does.
% You are not allowed to use append for this problem.
concat([], B, B).                                   % base case: if first list empty, then the result is the second list
concat([H|T1], B, [H|T2]) :- concat(T1, B, T2).     % recursive case: recursively check if the head of the first list is the head of the result list

% Question 3: 10 pts
% Write a predicate that is true if R is the reverse of L.
% You cannot use the reverse predicate in prolog
rev([], []).                                        % base case: if input empty then result is empty
rev([H|T], R) :- rev(T, TR), concat(TR, [H], R).    % recursively reverse the tail and append head to reverse the list

% Question 4: 10pts
% Write a predicate that is true if LX is the list L without the element X.
% remove([f,o,o,d],o,[f,d]).
% true
remove([], _, []).                                          % input empty then result is empty
remove([X|T], X, NewList) :- remove(T, X, NewList).                   % check if head is X, skipis it, then continues removing from tha tail
remove([H|T], X, [H|NewList]) :- H \= X, remove(T, X, NewList).       % checks if head is not X, keeps it, and conitnues removing from the tail

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Part 2: Trees
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Trees are built in the same way as lists.
% For this assignment I'll represent trees using LaTeX
% notation
% [.1 [.2 3 4 ] [.5 6 7 ] ]
% representa the tree
%    1
%   / \
%  2   5
% / \ / \
% 3 4 6 7
%
% If you want to render a tree to check if it's correct
% you can go to overleaf.com and put in
%
% \documentclass{article}
% \usepackage{qtree}
% \begin{document}
% \Tree[.1 [.2 3 4 ] [.5 6 7 ] ]
% \end{document}
%
% A Tree consists of either a
% leaf(a)
% or a
% branch(x left, right)
% again leaf and branch are just atoms,
% but we're allowed to nest them
% branch(1, leaf(2), leaf(3)).
% We could even do leaf(branch(1, 2, 3)). if we wanted,
% but that wouldn't really mean anything to us.
% 

% Question 5: 5pts
% Write a predicate that succeeds if T is the example tree.
%    1
%   / \
%  2   5
% / \ / \
% 3 4 6 7
exampleTree(branch(1, branch(2, leaf(3), leaf(4)), branch(5, leaf(6), leaf(7))).

% Question 6: 10pts
% Write a predicate flip(T1,T2) that succeeds if T1 is the mirror image of T2.
% example:
% flip([.1 [.2 3 4 ] [.5 6 7 ] ], [.1 [.5 7 6 ] [.2 4 3 ] ]) should succeed
% Or graphically
%    1             1
%   / \    <=>    / \
%  2   5  flip   5   2
% / \ / \       / \ / \
% 3 4 6 7       7 6 4 3
flip(leaf(X), leaf(X)).                                                     %     Base case: A leaf is its own mirrored image
flip(branch(X, L1, R1), branch(X, R2, L2)) :- flip(L1, R2), flip(R1, L2).   % Recursively check if the left and right subtrees are mirror images.

% Question 7: 10pts
% Write a predicate that succeeds if L is a postorder traversal of T.
% example:
% if T is
%    1
%   / \
%  2   5
% / \ / \
% 3 4 6 7
% the L should be
% 3 -> 4 -> 2 -> 6 -> 7 -> 5 -> 1 -> *
postorder(leaf(X), [X|[]]).                     % Base case: For a leaf, the postorder traversal is the leaf itself
postorder(branch(X, L, R), PostorderList) :-               % For a branch, the postorder traversal is the concatenation of the postorder traversals of the left and right subtrees followed by the root
    postorder(L, L1),
    postorder(R, R1),
    concat(L1, R1, ConcatenadedList),
    concat(ConcatenadedList, [X], PostorderList).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% Part 3: ASTs
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% In prolog I can use use +,-,*,/ to make syntax trees
%
% for example the expression (1 + x) * (1 - x)
% is the tree
%      *
%     / \
%    +   -
%   / \ / \
%   1 x 1 x 
%
% You can think of this like
% mul(add(1,x),sub(1,x))
%
% Since there's really nothing to do for making an expression,
% I want to focus on simplifying expressions in prolog!
%
% If I want to match an expression, there are 7 cases
% isExp(N) :- integer(N).
% isExp(V) :- atom(V).
% isExp(E1+E2).
% isExp(E1-E2).
% isExp(E1*E2).
% isExp(E1/E2).
% isExp(- E1).
%
% I'm putting a space in front of the - because prolog can get confused
% thinking it's another operator.
%
% For example prolog will read --3 as an operator -- followed by 3,
% and not - (- 3) which was probably what was intended.

% problem 8: 5pts
% Write a Predicate that succeeds if VS is a list of all of the variables in E.
% example:
% vars(a*x*x + b*x + c, a -> x -> x -> b -> x -> c -> *).
% should succeed
%
% You may have repeating variables in your answer.
vars(N, []) :- integer(N).               % base case: If the expression is an integer, there are no variables
vars(V, [V|[]]) :- atom(V).              % base case: If the expression is an atom, it is a variable
vars(E1+E2, VS) :-                       % for addition, concatenate the variables from both subexpressions
    vars(E1, VS1),
    vars(E2, VS2),
    concat(VS1, VS2, VS).
vars(E1-E2, VS) :-                       % for subtraction, concatenate the variables from both subexpressions
    vars(E1, VS1),
    vars(E2, VS2),
    concat(VS1, VS2, VS).
vars(E1*E2, VS) :-                       % for multiplication, concatenate the variables from both subexpressions
    vars(E1, VS1),
    vars(E2, VS2),
    concat(VS1, VS2, VS).
vars(E1/E2, VS) :-                       % for division, concatenate the variables from both subexpressions
    vars(E1, VS1),
    vars(E2, VS2),
    concat(VS1, VS2, VS).
vars(-E, VS) :- vars(E, VS).             % for negation, the variables are the same as in the expression being negated

% Fortunately it turns out that simplifyign expressions is just
% a bunch of short rules that we repeat over an over again
% As an example 
% (2+(x-(y/y)*x))/2 can be simplified
% (2+(x-(y/y)*x))/2 
% (2+(x-1*x))/2 
% (2+(x-x))/2 
% (2+0)/2 
% 2/2 
% 1
%
% We just repeatedly applied a few simple rules
% 1*e => e
% e+0 => e
% e-e => 0
% e/e => 1
%
% This strategy of simplifying an expression using rules is called rewriting.
% In this part, I'm giving you all of the rules.
% It's your job to make a "Rewriting Engine".
% I want you to make a predicate that succeeds if E1 simplifies to E2.
%
% Problem 9: 20 pts
% I have given you the predicate rule(E1,E2)
% this predicate succeeds if an expression E1 matches any of our rules
% for simplification.
%
% For example:
% rule(3 + 0,3).
% succeeds, % but 
% rule(2 * (3+0),2*3).
% fails, because the 3+0 is burried inside the expression.
%
% An expression E1 "rewrites" to E2 if, and only if,
% there is a subexpression X in E1 
% and a subexpress Y in E2 where
%  1. if I replace X and Y with a free variable, then E1 and E2 unify
%  2. rule(X,Y) is true.
%
% Example:
%
%      E1       E2
%      *        *
%     / \      / \
%    2   +    2   3
%       / \
%      3   0
%
% If I replace 3+0 with X in E1 and 3 with Y in E2
% then E1 and E2 unify (2*X) = (2*Y)
% there is a rule that will succeed with rule(3+0,3).
%
% Write a predicate rewrite(E1,E2) to implement this.
rewrite(X,Y)      :- fail.


% problem 10: 10pts
% Finally we can simplify an expression.
% Start with an expression E.
% If we can rewrite E to a new expression X,
% then continue trying to simplify E1.
% If we can't rewrite E to any new expression,
% then E cannot be simplified anymore, so it simplifies to itself.
simplify(E, E) :- \+ (rule(E, _)).                      % base case: if the expression E cannot be further simplified, return E itself
simplify(E, Result) :-                                  % recursive case: apply the rewriting rules to simplify the expression E to a new expression E1, then continue simplifying E1 until it cannot be further simplified
    rule(E, E1), % apply the rules to simplify E to E1
    simplify(E1, Result). % recursively simplify E1

rule(N,         -M) :- integer(N), N < 0, M is 0-N.
rule(0+E,        E).
rule(E+0,        E).
rule(E+E,        2*E).
rule(E1 + (-E2), E1 - E2).
rule((-E1) + E2, E2 - E1).
rule(E-0,        E).
rule(0-E,        -E).
rule(E-E,        0).
rule(E1 - (-E2), E1 + E2).
rule((-E1) - E2, E1 + E2).
rule(0*_,        0).
rule(_*0,        0).
rule(1*E,        E).
rule(E*1,        E).
rule((-E1) * E2, - (E1 * E2)).
rule(E1 * (-E2), - (E1 * E2)).
rule(0/_,        0).
rule(E/1,        E).
rule(E/E,        1).
rule((-E1) / E2, - (E1 / E2)).
rule(E1 / (-E2), - (E1 / E2)).
rule(- - E,       E).
rule(-(E1+E2),   (-E1) + (-E2)).
rule(-(E1-E2),   (-E1) - (-E2)).
