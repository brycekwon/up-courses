/* cart.c
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

#include "cart.h"
#include "retail_item.h"


/* create_cart
 * Purpose: this function creates a new empty cart
 * Arguments: none
 * Returns: the head of the cart
 */
llnode* create_cart() {
    return NULL;
}

/* llist_search_number
 * Purpose: this function searches the cart for a retail item by number
 * Arguments:   cart_node - the cart node to search
 *              num_id - the retail item number to find
 * Returns: the number of comparisons before found, -1 if not found
 */
int llist_search_number(llnode* cart_node, int num_id) {
    // track the number of comparisons
    int comparisons = 0;

    // iterate through the list until the end is reached
    while (cart_node != NULL) {
        comparisons++;

        // check if the current node has the desired value
        if (num_id == cart_node->value->number) {
            print_item(cart_node->value);
            return comparisons;
        }

        // traverse to the next node in the list
        cart_node = cart_node->next;
    }

    // end of the list has been reached, item not found
    printf("Item not found in list.\n");
    return -1;
}

/* llist_search_name
 * Purpose: this function searches the cart for a retail item by name
 * Arguments:   cart_node - the cart node to search
 *              name_id - the retail item name to find
 * Returns: the number of comparisons before found, -1 if not found
 */
int llist_search_name(llnode* cart_node, char* name_id) {
    // track the number of comparisons
    int comparisons = 0;

    // iterate through the list until the end is reached
    while (cart_node != NULL) {
        comparisons++;

        // check if the current node contains the desired value
        if (strcmp(name_id, cart_node->value->name) == 0) {
            print_item(cart_node->value);
            return comparisons;
        }
        
        // traverse to the next node in the list
        cart_node = cart_node->next;
    }

    // end of the list has been reached, item not found
    printf("Item not found.\n");
    return -1;
}

/* add_item
 * Purpose: this function adds a retail item to the end of the cart
 * Arguments:   cart_ptr - a pointer to the cart node to add
 *              item - the retail item to add
 * Returns: nothing
 */
void add_item(llnode** cart_ptr, retail_item* item) {
    // end of the list has been reached, add new node here
    if (*cart_ptr == NULL) {
        // allocate memory for a new linked list node
        llnode* cart_node = (llnode*) malloc(sizeof(llnode));
        if (cart_node == NULL) {
            fprintf(stderr, "Error: could not allocate memory for a new linked list node.\n");
            return;
        }

        // add values to the new linked list node
        cart_node->value = item;
        cart_node->next = NULL;

        // set pointer to the new linked list node
        *cart_ptr = cart_node;
    }

    // check if the item is a duplicate, do not add to list
    else if (item->number == (*cart_ptr)->value->number) {
        printf("Add Item: Failed, cannot add duplicate item to the list.\n");
        free_item(item);
    }

    // traverse through the list until the end is reached
    else {
        add_item(&(*cart_ptr)->next, item);
    }
}

/* delete_item
 * Purpose: this function deletes a retail item from the cart by number
 * Arguments:   cart_ptr - a pointer to the cart node to search
 *              num_id - the retail item number to delete
 * Returns: nothing
 */
void delete_item(llnode** cart_ptr, int num_id) {
    // end of the list has been reached, item not found
    if (*cart_ptr == NULL) {
        return;
    }

    // check if the current node has the desired value
    else if (num_id == (*cart_ptr)->value->number) {
        // delete the found node
        llnode* tmp_node = (*cart_ptr)->next;
        free_item((*cart_ptr)->value);
        free(*cart_ptr);

        // reconnect the list
        *cart_ptr = tmp_node;
    }

    // traverse through the list until the end is reached
    else {
        delete_item(&(*cart_ptr)->next, num_id);
    }
}

/* print_cart
 * Purpose: this function prints information about a cart
 * Arguments:   cart_node - the cart node to print
 * Returns: nothing
 */
void print_cart(llnode* cart_node) {
    // print a title for the cart
    printf("Cart:\n");

    // track the total price of the cart's contents
    int total_price = 0;

    // iterate through the list until the end is reached
    while (cart_node != NULL) {
        total_price += cart_node->value->price;

        // print the item to the stdout
        print_item(cart_node->value);

        // traverse to the next node in the list
        cart_node = cart_node->next;
    }

    // calculate the dollar value from total price
    int total_dollars = total_price / 100;
    int total_cents = total_price % 100;

    // print the total cost of the cart
    printf("Total: $%d.%02d\n", total_dollars, total_cents);
}

/* free_cart
 * Purpose: this function frees memory allocated for a cart
 * Arguments:   cart_ptr - the cart node to free
 * Returns: nothing
 */
void free_cart(llnode* cart_node) {
    // end of the list has been reached
    if (cart_node == NULL) {
        return;
    }

    // traverse through the list until the end is reached
    free_cart(cart_node->next);

    // free the retail item from memory
    free_item(cart_node->value);

    // free the cart node from memory
    free(cart_node);
}
