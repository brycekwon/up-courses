/* cart.h
 * ---------------
 * [[DESCRIPTION]]
 *
 * CS 305, HW 4 (Spring 2023)
 * Author: Bryce Kwon
 * Version: March 31, 2023
 * ---------------
 */
#ifndef CART_H
#define CART_H

#include "retail_item.h"


/* cart struct (defined as a linked list) */
typedef struct llnode {
    retail_item* value;     // pointer to a retail item
    struct llnode* next;    // pointer to next node
} llnode;

/* function prototypes */
llnode* create_cart();
int llist_search_number(llnode*, int);
int llist_search_name(llnode*, char*);
void add_item(llnode**, retail_item*);
void delete_item(llnode**, int);
void print_cart(llnode*);
void free_cart(llnode*);


#endif
