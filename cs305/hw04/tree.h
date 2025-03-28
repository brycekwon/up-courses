/* tree.h
 * ---------------
 * [[DESCRIPTION]]
 *
 * CS 305, HW 4 (Spring 2023)
 * Author: Bryce Kwon
 * Version: March 31, 2023
 * ---------------
 */
#ifndef TREE_H
#define TREE_H

#include "cart.h"
#include "retail_item.h"


/* tree node struct */
typedef struct tNode {
    retail_item* value;     // pointer to a retail item
    struct tNode* right;    // pointer to right node
    struct tNode* left;     // pointer to left node
} tNode;

/* function prototypes */
tNode* t_create_number(llnode*);
tNode* t_create_name(llnode*);
int t_search_number(tNode*, int);
int t_search_name(tNode*, char*);
void t_insert_number(tNode**, retail_item*);
void t_insert_name(tNode**, retail_item*);
retail_item* t_delete_number(tNode**, int);
retail_item* t_delete_name(tNode**, char*);
void print_tree(tNode*);
void free_tree(tNode*);


#endif
