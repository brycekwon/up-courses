/* llist.c
 * ----------
 * This file contains the implementation of the llist module. The llist module
 * is responsible for managing the linked list instances.
 * 
 * CS 305, HW 3 (Spring 2023)
 * Author: Bryce Kwon
 * Version: March 19, 2023
 * ----------
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "airport.h"
#include "llist.h"

/* insert
 * Purpose: this function inserts a new node to the linked list in sorted order.
 * Parameters: head - a pointer to the head of the linked list
 *             apt - a pointer to the airport to be inserted
 * Returns: a pointer to the head of the linked list
 */
node* insert(node* head, airport* apt) {
    if (apt == NULL) {
        printf("Error: invalid airport.\n");
        return head;
    }
    
    // check if the list is empty
    if (head == NULL) {
        // allocate memory for a node
        node* new_node = (node*) malloc(sizeof(node));
        if (new_node == NULL) {
            printf("Error [insert]: could not allocate memory for a node.\n");
            return head;
        }
        
        // insert the first node in the list
        new_node->apt = apt;
        new_node->next = NULL;
        
        // return the new head
        return new_node;
    }

    // insert the node in its sorted position
    if (strcmp(head->apt->tag, apt->tag) > 0) {
        // allocate memory for a node
        node* new_node = (node*) malloc(sizeof(node));
        if (new_node == NULL) {
            printf("Error [insert]: could not allocate memory for a node.\n");
            return head;
        }
        
        // insert the node in the list
        new_node->apt = apt;
        new_node->next = head;
        
        // return the new head
        return new_node;
    } else {
        // continue down the list if the node is not in its sorted position
        head->next = insert(head->next, apt);
        
        // return the head
        return head;
    }
}

/* delete_airport
 * Purpose: this function deletes an airport from the linked list.
 * Parameters: head - a pointer to the head of the linked list
 *             tag - the tag of the airport to be deleted
 * Returns: a pointer to the head of the linked list
 */
node* delete_airport(node* head, char* tag) {
    if (head == NULL) {
        return NULL;
    } else if (tag == NULL || strlen(tag) != 3) {
        printf("Error: invalid airport tag.\n");
        return head;
    }

    if (strcmp(head -> apt -> tag, tag) == 0) {
        node* temp = head -> next;
        free(head -> apt -> name);
        free(head -> apt);
        free(head);
        return temp;
    } else {
        head -> next = delete_airport(head -> next, tag);
        return head;
    }
}

/* find_airport
 * Purpose: this function finds an airport in the linked list.
 * Parameters: head - a pointer to the head of the linked list
 *             tag - the tag of the airport to be found
 * Returns: a pointer to the airport if found, NULL otherwise
 */
airport* find_airport(node* head, char* tag) {
    if (head == NULL) {
        // return NULL if the airport is not found
        return NULL;
    } else if (tag == NULL || strlen(tag) != 3) {
        printf("Error: invalid airport tag.\n");
        return NULL;
    }
    
    // check if the airport is found
    if (strcmp(head->apt->tag, tag) == 0) {
        // return the found airport
        return head->apt;
    } else {
        // continue down the list if the airport is not found
        return find_airport(head->next, tag);
    }
}

/* save
 * Purpose: this function saves the linked list to a file.
 * Parameters: head - a pointer to the head of the linked list
 *             filename - the name of the file to be saved
 * Returns: 0 for success, 1 for failure
 */
int save(node* head, char* filename) {
    // open the file
    FILE* fptr = fopen(filename, "w");
    if (fptr == NULL) {
        printf("Error [save]: could not open the file.\n");
        return 1;
    }
    
    // write the linked list to the file
    node* curr = head;
    while (curr != NULL) {
        // write the airport to the file
        printf_airport(curr->apt, fptr);
        curr = curr->next;
    }

    // close the file
    fclose(fptr);

    // return success
    return 0;
}

/* print_list
 * Purpose: this function prints the linked list.
 * Parameters: head - a pointer to the head of the linked list
 * Returns: nothing
 */
void print_list(node* head) {
    if (head == NULL) {
        return;
    }
    
    // print the airport
    print_airport(head->apt);
    print_list(head->next);
}

/* delete_list
 * Purpose: this function deletes the linked list and frees allocated memory.
 * Parameters: head - a pointer to the head of the linked list
 * Returns: nothing
 */
void delete_list(node* head) {
    if (head == NULL) {
        return;
    }
    
    // delete the node
    delete_list(head->next);
    
    // free allocated memory
    free_airport(head->apt);
    free(head);
}
