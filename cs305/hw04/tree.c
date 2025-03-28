/* tree.c
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

#include "tree.h"
#include "cart.h"
#include "retail_item.h"


/* make_node
 * Purpose: this function creates a new tree node
 * Arguments:   item - the retail item to add
 * Returns: the new tree node, null otherwise
 */
tNode* make_node(retail_item* item) {
    // check if the retail item exists
    if (item == NULL) {
        fprintf(stderr, "Error: could not find item to add to tree.\n");
        return NULL;
    }

    // allocate memory for a new tree node
    tNode* tree_node = (tNode*) malloc(sizeof(tNode));
    if (tree_node == NULL) {
        fprintf(stderr, "Error: could not allocate memory for a new tree node.\n");
        return NULL;
    }

    // add values to the new tree node
    tree_node->value = item;
    tree_node->right = NULL;
    tree_node->left = NULL;

    // return a pointer to the new tree node
    return tree_node;
}

/* compare_value
 * Purpose: this function compares a specific value of a node
 * Arguments:   tree_node - the tree node to compare
 *              mode - the value to compare (0 for number, 1 for name)
 * Returns: -1 for less than, 0 for equal, 1 for greater than, -2 otherwise
 */
int compare_node(tNode* tree_node, int mode) {
}

/* t_create_number
 * Purpose: this function creates a binary search tree sorted by number
 * Arguments:   cart_node - the cart node to build from
 * Returns: the tree root node
 */
tNode* t_create_number(llnode* cart_node) {
    // cart is empty, tree is empty
    if (cart_node == NULL) {
        return NULL;
    }

    // create the tree root
    tNode* root = make_node(cart_node->value);
    cart_node = cart_node->next;

    // iterate through the list until the end is reached
    while (cart_node != NULL) {
        // insert the retail item into the tree sorted by number
        t_insert_number(&root, cart_node->value);
        cart_node = cart_node->next;
    }

    // return the root of the tree
    return root;
}

/* t_create_name
 * Purpose: this function creates a binary search tree sorted by name
 * Arguments:   cart_node - the cart node to build from
 * Returns: the tree root node
 */
tNode* t_create_name(llnode* cart_node) {
    // cart is empty, tree is empty
    if (cart_node == NULL) {
        return NULL;
    }

    // create the tree root
    tNode* root = make_node(cart_node->value);
    cart_node = cart_node->next;

    // iterate through the list until the end is reached
    while (cart_node != NULL) {
        // insert the retail item into the tree sorted by number
        t_insert_name(&root, cart_node->value);
        cart_node = cart_node->next;
    }

    // return the root of the tree
    return root;
}

/* t_search_number
 * Purpose: this function searches the tree for a retail item by number
 * Arguments:   tree_node - the tree node to search
 *              num_id - the retail item number to find
 * Returns: the number of comparisons before found, -1 if not found
 */
int t_search_number(tNode* tree_node, int num_id) {
    // track the number of comparisons
    int comparisons = 0;

    // iterate through the list until the end is reached
    while (tree_node != NULL) {
        comparisons++;

        // check if the current node has the desired value
        if (num_id == tree_node->value->number) {
            print_item(tree_node->value);
            return comparisons;
        }

        // traverse right if the number to find is larger than the current node
        else if (num_id > tree_node->value->number) {
            tree_node = tree_node->right;
        }

        // traverse left if the number to find is smaller than the current node
        else if (num_id < tree_node->value->number) {
            tree_node = tree_node->left;
        }
    }

    // end of the tree has been reached, item not found
    printf("Item not found in tree.\n");
    return -1;
}

/* t_search_name
 * Purpose: this function searches the tree for a retail item by name
 * Arguments:   tree_node - the tree node to search
 *              name_id - the retail item name to find
 * Returns: the number of comparisons before found, -1 if not found
 */
int t_search_name(tNode* tree_node, char* name_id) {
    // track the number of comparisons
    int comparisons = 0;

    // iterate through the list until the end is reached
    while (tree_node != NULL) {
        comparisons++;

        // check if the current node has the desired value
        if (strcmp(name_id, tree_node->value->name) == 0) {
            print_item(tree_node->value);
            return comparisons;
        }

        // traverse right if the name to find is lower in the alphabet
        else if (strcmp(name_id, tree_node->value->name) > 0) {
            tree_node = tree_node->right;
        }

        // traverse left if the name to find is higher in the alphabet
        else if (strcmp(name_id, tree_node->value->name) < 0) {
            tree_node = tree_node->left;
        }
    }

    // end of the tree has been reached, item not found
    printf("Item not found in tree.\n");
    return -1;
}

/* t_insert_number
 * Purpose: this function inserts a retail item into the tree sorted by number
 * Arguments:   tree_ptr - a pointer to the tree node to add
 *              item - the retail item to add
 * Returns: nothing
 */
void t_insert_number(tNode** tree_ptr, retail_item* item) {
    // end of the list has been reached, add node here
    if (*tree_ptr == NULL) {
        *tree_ptr = make_node(item);
    }

    // check if the item is a duplicate, do not add to list
    else if (item->number == (*tree_ptr)->value->number) {
        printf("Insert Number: Failed, cannot add duplicate items into the tree.\n");
        return;
    }

    // traverse right if the number to add is larger than the current node
    else if (item->number > (*tree_ptr)->value->number) {
        t_insert_number(&(*tree_ptr)->right, item);
    }

    // traverse left if the number to add is smaller than the current node
    else if (item->number < (*tree_ptr)->value->number) {
        t_insert_number(&(*tree_ptr)->left, item);
    }

    // error, invalid nodes found
    else {
        fprintf(stderr, "Error: Parent has invalid nodes.\n");
        return;
    }
}

/* t_insert_name
 * Purpose: this function inserts a retail item into the tree sorted by name
 * Arguments:   tree_ptr - a pointer to the tree node to add
 *              item - the retail item to add
 * Returns: nothing
 */
void t_insert_name(tNode** tree_ptr, retail_item* item) {
    // end of the list has been reached, add node here
    if (*tree_ptr == NULL) {
        *tree_ptr = make_node(item);
    }

    // check if the item is a duplicate, do not add to list
    else if (strcmp(item->name, (*tree_ptr)->value->name) == 0) {
        printf("Insert Number: Failed, cannot add duplicate items into the tree.\n");
        return;
    }

    // traverse right if the name to add is lower in the alphabet
    else if (strcmp(item->name, (*tree_ptr)->value->name) > 0) {
        t_insert_name(&(*tree_ptr)->right, item);
    }

    // traverse left if the name to add is higher in the alphabet
    else if (strcmp(item->name, (*tree_ptr)->value->name) < 0) {
        t_insert_name(&(*tree_ptr)->left, item);
    }

    // error, invalid nodes found
    else {
        fprintf(stderr, "Error: Parent has invalid nodes.\n");
        return;
    }
}

/* t_delete_number
 * Purpose: this function deletes a retail item from the tree by number
 * Arguments:   tree_ptr - a pointer to the tree node to search
 *              num_id - the retail item number to delete
 * Returns: the retail item deleted, null otherwise
 */
retail_item* t_delete_number(tNode** tree_ptr, int num_id) {
    // end of the tree has been reached, item not found
    if (*tree_ptr == NULL) {
        return NULL;
    }

    // check if the current node has the desired value
    else if (num_id == (*tree_ptr)->value->number) {
        // save the retail item temporarily
        retail_item* item = (*tree_ptr)->value;

        // check if the current node has no children
        if ((*tree_ptr)->right == NULL && (*tree_ptr)->left == NULL) {
            // delete the node and set pointer to null
            free(*tree_ptr);
            *tree_ptr = NULL;

            // return the pointer to the retail item
            return item;
        }

        // check if the current node has one child on the left
        else if ((*tree_ptr)->right == NULL) {
            // delete the node and set pointer to the next smallest
            tNode* tmp_node = *tree_ptr;
            *tree_ptr = (*tree_ptr)->left;
            free(tmp_node);

            // return the pointer to the retail item
            return item;
        }

        // check if the current node has one child on right
        else if ((*tree_ptr)->left == NULL) {
            // delete the node and set pointer to the next largest
            tNode* tmp_node = *tree_ptr;
            *tree_ptr = (*tree_ptr)->right;
            free(tmp_node);

            // return the pointer to the retail item
            return item;
        }

        // check if the current node has two children
        else {
            // find the value closest to the found
            tNode* tmp_node = (*tree_ptr)->right;
            while (tmp_node->left != NULL) {
                tmp_node = tmp_node->left;
            }

            // swap the values of the current node with the next closest
            retail_item* swap_item = (*tree_ptr)->value;
            (*tree_ptr)->value = tmp_node->value;
            tmp_node->value = swap_item;

            // delete the successor
            return t_delete_number(&(*tree_ptr)->right, num_id);
        }
    }

    // traverse right if the number to delete is bigger than the current node
    else if (num_id > (*tree_ptr)->value->number) {
        return t_delete_number(&(*tree_ptr)->right, num_id);
    }

    // traverse left if the number to delete is smaller than the current node
    else if (num_id < (*tree_ptr)->value->number) {
        return t_delete_number(&(*tree_ptr)->left, num_id);
    }

    // error, invalid nodes found
    else {
        fprintf(stderr, "Error: Parent has invalid nodes.\n");
        return NULL;
    }
}

/* t_delete_name
 * Purpose: this function deletes a retail item from the tree by name
 * Arguments:   tree_ptr - a pointer to the tree node to search
 *              name_id - the retail item name to delete
 * Returns: the retail item deleted, null otherwise
 */
retail_item* t_delete_name(tNode** tree_ptr, char* name_id) {
    // end of the tree has been reached, item not found
    if (*tree_ptr == NULL) {
        return NULL;
    }

    // check if the current node has the desired value
    else if (strcmp(name_id, (*tree_ptr)->value->name) == 0) {
        // save the retail item temporarily
        retail_item* item = (*tree_ptr)->value;

        // check if the current node has no children
        if ((*tree_ptr)->right == NULL && (*tree_ptr)->left == NULL) {
            // delete the node and set pointer to null
            free(*tree_ptr);
            *tree_ptr = NULL;

            // return the pointer to the retail item
            return item;
        }

        // check if the current node has one child on the left
        else if ((*tree_ptr)->right == NULL) {
            // delete the node and set pointer to the next smallest
            tNode* tmp_node = *tree_ptr;
            *tree_ptr = (*tree_ptr)->left;
            free(tmp_node);

            // return the pointer to the retail item
            return item;
        }

        // check for child on right
        else if ((*tree_ptr)->left == NULL) {
            // delete the child
            tNode* tmp_node = *tree_ptr;
            *tree_ptr = (*tree_ptr)->right;
            free(tmp_node);

            // return the pointer to the retail item
            return item;
        }

        // check if the current node has two children
        else {
            // find the value closest to the found
            tNode* tmp_node = (*tree_ptr)->right;
            while (tmp_node->left != NULL) {
                tmp_node = tmp_node->left;
            }

            // swap the values of the current node with the next closest
            retail_item* swap_item = (*tree_ptr)->value;
            (*tree_ptr)->value = tmp_node->value;
            tmp_node->value = swap_item;

            // delete the successor
            return t_delete_name(&(*tree_ptr)->right, name_id);
        }
    }

    // traverse right if the name to delete is lower in the alphabet
    else if (strcmp(name_id, (*tree_ptr)->value->name) > 0) {
        return t_delete_name(&(*tree_ptr)->right, name_id);
    }

    // traverse left if the name to delete is higher in the alphabet
    else if (strcmp(name_id, (*tree_ptr)->value->name) < 0) {
        return t_delete_name(&(*tree_ptr)->left, name_id);
    }

    // error, invalid nodes found
    else {
        fprintf(stderr, "Error: Parent has invalid nodes.\n");
        return NULL;
    }
}

/* print_tree
 * Purpose: this function prints information about a tree
 * Arguments:   tree_node - the tree node to print
 * Returns: nothing
 */
void print_tree(tNode* tree_node) {
    // end of the tree has been reached
    if (tree_node == NULL) {
        return;
    }

    // print items in ascending order
    print_tree(tree_node->left);
    print_item(tree_node->value);
    print_tree(tree_node->right);
}

/* free_tree
 * Purpose: this function frees memory allocated for a tree
 * Arguments:   tree_node - the tree node to free
 * Returns: nothing
 */
void free_tree(tNode* tree_node) {
    // end of the tree has been reached
    if (tree_node == NULL) {
        return;
    }

    // traverse through the left side of the tree until the end is reached
    free_tree(tree_node->left);

    // traverse through the right side of the tree until the end is reached
    free_tree(tree_node->right);

    // free the tree node from memory
    free(tree_node);
}
