
% import the word/1 and trie/3 predicates
% You can also use [trie2] and [trie3]
% These are just larger lists of words, but they do contains some words
% that are invalid (especially trie3).
:- [trie].

% elem function is like member, but it won't give you duplicate results.
% Be careful, \= is kind of wierd with free variables.
elem(E,[E|_]).
elem(E,[X|XS]) :- E \= X, elem(E,XS).


% permute function from class.
insert(X,Y,L) :- append(A,B,Y), append(A,[X|B],L).
permute([],[]).
permute([X|XS],L) :- permute(XS,P), insert(X,P,L).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%
% spell(Letters, X, Word).
% Letters is the list of letters that the word can use.
% X is the letter that must be in the word.
% Word is the resulting word we find.
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% part 1
%
% find words by permuting the letters
% and finding a prefix that is a word
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
spell1(Letters, X, Word) :-
    permute(Letters, Perm),     % generate permutations of Letters
    elem(X, Perm),              % ensure X is an element of permutation of Letters
    atom_chars(Word, Perm).     % convert permutation into an atom, representing a word


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% part 2
%
% write a predicate to find words of length N.
% Then, start with 3, and increment N to find every word.
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
spell2(Letters, X, Word) :-
    length(Perm, N),            % initialize permutation of length N
    between(3, N, N),           % ensure length of words between 3 and N
    elem(X, Perm),              % ensure X is an element of permutation
    permute(Letters, Perm),
    atom_chars(Word, Perm).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% part 3
%
% find words by using a trie.
% start with the empty word e0,
% then pick a letter from L and move along that branch
% in the tire.
% repeat until we get to a word.
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
spell3([], [], []).
spell3(Letters, [X|Rest], [X|Word]) :-
    elem(X, Letters),
    trie([X|Rest]),
    spell3(Rest, Rest, Word).

