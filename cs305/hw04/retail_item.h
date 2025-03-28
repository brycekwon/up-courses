/* retail_item.h
 * ---------------
 * [[DESCRIPTION]]
 *
 * CS 305, HW 4 (Spring 2023)
 * Author: Bryce Kwon
 * Version: March 31, 2023
 * ---------------
 */
#ifndef RETAIL_ITEM_H
#define RETAIL_ITEM_H


/* retail item struct */
typedef struct retail_item {
    int number;     // retail item number
    char* name;     // retail item name
    int price;      // retail item price (in cents)
} retail_item;

/* function prototypes */
retail_item* create_item(int, char*, int);
void print_item(retail_item*);
void free_item(retail_item*);


#endif
