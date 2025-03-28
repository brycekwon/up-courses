/* retail_item.c
 * ---------------
 * [[DESCRIPTION]]
 * 
 * CS 305, HW 4 (Spring 2023)
 * Author: Bryce Kwon
 * Version: March 31, 2023
 * ---------------
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "retail_item.h"


/* create_item
 * Purpose: this function creates a new retail item
 * Arguments:   number - the retail item id
 *              name - the retail item name
 *              price - the retail item price
 * Returns: a retail item, null otherwise
 */
retail_item* create_item(int number, char* name, int price) {
    // allocate memory for a new retail item
    retail_item* new_item = (retail_item*) malloc(sizeof(retail_item));
    if (new_item == NULL) {
        fprintf(stderr, "Error: could not allocate memory for a retail item.\n");
        return NULL;
    }

    // add values to the new retail item
    new_item->number = number > 0 ? number : 0;
    new_item->price = price > 0 ? price : 0;
    new_item->name = (char*) malloc(strlen(name) + 1);
    if (new_item->name == NULL) {
        fprintf(stderr, "Error: could not allocate memory for a retail item name.\n");
        free(new_item);
        return NULL;
    }
    strcpy(new_item->name, name);

    // return a pointer to the new retail item
    return new_item;
}

/* print_item
 * Purpose: this function prints information about a retail item
 * Arguments:   item - the retail item to print
 * Returns: nothing
 */
void print_item(retail_item* item) {
    // check if the retail item exists
    if (item == NULL) {
        fprintf(stderr, "Error: could not find the retail item to print.\n");
        return;
    }

    // calculate the dollar value from price
    int price_dollars = item->price / 100;
    int price_cents = item->price % 100;

    // print the retail item to the stdout
    printf("Item %d: %s\t$%d.%02d\n", item->number, item->name, price_dollars, price_cents);
}

/* free_item
 * Purpose: this function frees memory allocated for a retail item
 * Arguments:   item - the retail item to free
 * Returns: nothing
 */
void free_item(retail_item* item) {
    // check if the retail item exists
    if (item == NULL) {
        fprintf(stderr, "Error: could not find the retail item to free.\n");
        return;
    }

    // free the retail item from memory
    free(item->name);
    free(item);
}
