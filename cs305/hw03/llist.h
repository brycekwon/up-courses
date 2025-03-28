/* llist.h
 * ----------
 * This file contains the node struct and function prototypes for the
 * llist module. The llist module is responsible for managing the
 * linked list instances.
 * 
 * CS 305, HW 3 (Spring 2023)
 * Author: Bryce Kwon
 * Version: March 19, 2023
 * ----------
 */
#ifndef LLIST_H
#define LLIST_H

#include <stdio.h>
#include <stdlib.h>

#include "airport.h"

/* node struct
 * This struct represents a node in a linked list. It contains a pointer to
 * the next node in the list and a pointer to the data stored in the node.
 */
typedef struct node {
    airport* apt;
    struct node* next;
} node;

/* function prototypes */
node* insert(node* head, airport* apt);
node* delete_airport(node* head, char* tag);
airport* find_airport(node* head, char* tag);
int save(node* head, char* filename);
void print_list(node* head);
void delete_list(node* head);

#endif
