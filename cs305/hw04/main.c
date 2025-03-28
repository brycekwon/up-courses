/* main.c
 * ---------------
 * [[DESCRIPTION]]
 *
 * CS 305, HW 4 (Spring 2023)
 * Author: Bryce Kwon
 * Version: March 30, 2023
 * ---------------
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "tree.h"
#include "cart.h"
#include "retail_item.h"

/* function prototypes */
void delete(llnode**, tNode**, tNode**, int);
void run_runtime_test(char*);
int run_main_test(char*);
int run_module_tests(char*);
int run_test_tree(char*);
int run_test_cart(char*);
int run_test_item();


/* main
 * Purpose: this function is the main program wrapper
 * Arguments:   argc - the number of command line arguments
 *              argv - the list of command line arguments
 * Returns: EXIT_SUCCESS for success, EXIT_FAILURE for failure
 */
int main(int argc, char* argv[]) {
    // check for correct usage
    if (argc != 2) {
        fprintf(stderr, "Usage: ./fastLookup <input file>\n");
        return EXIT_FAILURE;
    }

    run_runtime_test(argv[1]);

    // run_test_cart(argv[1]);
    // run_test_tree(argv[1]);
    return EXIT_SUCCESS;

    // run main tests
    // return run_main_test(argv[1]);

    // run module tests
    // return run_module_tests(argv[1]);
}

/* delete
 * Purpose: this function is a user level function that deletes a retail
 *          item from all 3 data structures
 * Arguments:   cart - the cart head
 *              t_number - the tree root sorted by number
 *              t_name - the tree root sorted by name
 *              num_id - the retail item number to delete
 * Returns: nothing
 */
void delete(llnode** cart, tNode** t_number, tNode** t_name, int num_id) {
    // attempt to delete from number tree
    retail_item* item_found = t_delete_number(t_number, num_id);
    if (item_found == NULL) {
        // if no item was found, do nothing
        return;
    }

    // delete item from name tree
    t_delete_name(t_name, item_found->name);

    // delete item from cart
    delete_item(cart, item_found->number);
}

/* run_main_test
 * Purpose: this function gets a runtime comparison
 * Arguments:   filename - name of the input file
 * Returns: nothing
 */
void run_runtime_test(char* filename) {
    // open input file for reading
    FILE* fptr = fopen(filename, "r");
    if (fptr == NULL) {
        fprintf(stderr, "Error: could not open input file for reading.\n");
        return;
    }

    // create a new cart instance
    llnode* cart = create_cart();

    // fill cart with items from input file
    int number, price;
    char name[256];
    while (fscanf(fptr, "%d %s %d \n", &number, name, &price) != EOF) {
        // printf("Adding: %d %s %d\n", number, name, price);
        add_item(&cart, create_item(number, name, price));
    }

    // creating a binary search tree sorted by name
    tNode* t_number = t_create_number(cart);

    // creating a binary search tree sorted by name
    tNode* t_name = t_create_name(cart);

    
    // iterate through cart searching for comparions
    llnode* curr = cart;
    int l_search_num_total = 0;
    int l_search_name_total = 0;
    int t_search_num_total = 0;
    int t_search_name_total = 0;
    while (curr != NULL) {
        l_search_num_total += llist_search_number(cart, curr->value->number);
        l_search_name_total += llist_search_name(cart, curr->value->name);
        t_search_num_total += t_search_number(t_number, curr->value->number);
        t_search_name_total += t_search_name(t_name, curr->value->name);

        curr = curr->next;
    }

    printf("\n\n");
    printf("llsearch_num: %d\nllsearch_name: %d\n", l_search_num_total, l_search_name_total);
    printf("tsearch_num: %d\ntsearch_name: %d\n", t_search_num_total, t_search_name_total);

    free_tree(t_number);
    free_tree(t_name);
    free_cart(cart);

    fclose(fptr);
}

/* run_main_test
 * Purpose: this function tests all modules and functions
 * Arguments:   filename - name of the input file
 * Returns: 0 for success, 1 for failure
 */
int run_main_test(char* filename) {
    // open input file for reading
    FILE* fptr = fopen(filename, "r");
    if (fptr == NULL) {
        fprintf(stderr, "Error: could not open input file for reading.\n");
        return 0;
    }

    // create a new cart instance
    llnode* test_cart = create_cart();

    // fill cart with items from input file
    int number, price;
    char name[256];
    while (fscanf(fptr, "%d %s %d \n", &number, name, &price) != EOF) {
        // printf("Adding: %d %s %d\n", number, name, price);
        add_item(&test_cart, create_item(number, name, price));
    }

    // creating a binary search tree sorted by name
    tNode* t_number = t_create_number(test_cart);

    // creating a binary search tree sorted by name
    tNode* t_name = t_create_name(test_cart);

    printf("\n====================MAIN TESTS====================\n\n");

    // print cart to stdout
    print_cart(test_cart);
    printf("\n");

    // print tree to stdout
    printf("Tree Number:\n");
    print_tree(t_number);
    printf("\n");

    // print tree to stdout
    printf("Tree Name:\n"); 
    print_tree(t_name);
    printf("\n");

    // delete items from all 3 data structures
    delete(&test_cart, &t_number, &t_name, 7354);
    delete(&test_cart, &t_number, &t_name, 6974);
    delete(&test_cart, &t_number, &t_name, 9038);
    delete(&test_cart, &t_number, &t_name, 6184);
    delete(&test_cart, &t_number, &t_name, 2607);

    // print cart to stdout
    print_cart(test_cart);
    printf("\n");

    // print tree to stdout
    printf("Tree Number:\n");
    print_tree(t_number);
    printf("\n");

    // print tree to stdout
    printf("Tree Name:\n"); 
    print_tree(t_name);
    printf("\n");

    printf("\n====================MAIN TESTS====================\n\n");

    // free trees from memory
    free_tree(t_number);
    free_tree(t_name);

    // free cart from memory
    free_cart(test_cart);

    // close file pointer
    fclose(fptr);

    // exit successful process
    return 0;
}

/* run_module_tests
 * Purpose: this function is a wrapper for all module tests
 * Arguments:   filename - the input file name
 * Returns: EXIT_SUCCESS for success, EXIT_FAILURE for failure
 */
int run_module_tests(char* filename) {
    if (run_test_item()) {
        return EXIT_FAILURE;
    } else if (run_test_cart(filename)) {
        return EXIT_FAILURE;
    } else if (run_test_tree(filename)) {
        return EXIT_FAILURE;
    } else {
        return EXIT_SUCCESS;
    }
}

/* run_test_tree
 * Purpose: this function tests the tree module
 * Arguments: none
 * Returns: 0 for success, 1 for failure
 */
int run_test_tree(char* filename) {
    // open input file for reading
    FILE* fptr = fopen(filename, "r");
    if (fptr == NULL) {
        fprintf(stderr, "Error: could not open input file for reading.\n");
        return 1;
    }

    // create new carts
    llnode* test_cart = create_cart();

    // fill cart with items from the input file
    int number, price;
    char name[256];
    while (fscanf(fptr, "%d %s %d \n", &number, name, &price) != EOF) {
        // printf("Adding: %d %s %d\n", number, name, price);
        add_item(&test_cart, create_item(number, name, price));
    }

    printf("\n====================TEST \"TREE NUMBER\" MODULE====================\n\n");

    // create new number trees
    tNode* t_number = t_create_number(test_cart);

    // print tree
    print_tree(t_number);
    printf("\n");

    // search for items in tree by number
    printf("SEARCHING BY NUMBER:\n");
    int search0 = t_search_number(t_number, 7354);      // find value in list
    int search1 = t_search_number(t_number, 6974);      // find value not in list
    int search2 = t_search_number(t_number, 9038);      // find incorrect value not in list
    int search3 = t_search_number(t_number, 6184);      // find root of tree
    int search4 = t_search_number(t_number, 2607);      // find end of tree
    printf("\n");
    printf("%d, %d, %d, %d, %d\n", search0, search1, search2, search3, search4);
    printf("\n");

    // delete items from tree by number
    printf("DELETING BY NUMBER\n");
    print_item(t_delete_number(&t_number, 7354));   // delete value
    print_item(t_delete_number(&t_number, 6974));   // delete value not in list
    print_item(t_delete_number(&t_number, 9038));   // delete negative value not in list
    print_item(t_delete_number(&t_number, 6184));   // delete head of list
    print_item(t_delete_number(&t_number, 2607));   // delete tail of list
    printf("\n");

    // print tree
    print_tree(t_number);
    printf("\n");

    // free trees
    free_tree(t_number);

    printf("\n====================TEST \"TREE NUMBER\" MODULE====================\n\n");

    printf("\n====================TEST \"TREE NAME\" MODULE====================\n\n");

    // create new number trees
    tNode* t_name = t_create_name(test_cart);

    // print tree
    print_tree(t_name);
    printf("\n");

    // search for items in tree by name
    printf("SEARCHING BY NAME:\n");
    int search5 = t_search_name(t_name, "LampShade");   
    int search6 = t_search_name(t_name, "Clothes");    
    int search7 = t_search_name(t_name, "PrimeBeef");      
    int search8 = t_search_name(t_name, "Toothbrush");   
    int search9 = t_search_name(t_name, "ShoeLace");    
    printf("\n");
    printf("%d, %d, %d, %d, %d\n", search5, search6, search7, search8, search9);
    printf("\n");

    // delete items from tree by name
    printf("DELETING BY NAME\n");
    print_item(t_delete_name(&t_name, "LampShade"));     
    print_item(t_delete_name(&t_name, "Clothes"));   
    print_item(t_delete_name(&t_name, "PrimeBeef")); 
    print_item(t_delete_name(&t_name, "Toothbrush"));  
    print_item(t_delete_name(&t_name, "ShoeLace"));      
    printf("\n");

    // print tree
    print_tree(t_name);
    printf("\n");

    // free trees
    free_tree(t_name);

    printf("\n====================TEST \"TREE NAME\" MODULE====================\n\n");

    // free carts from memory
    free_cart(test_cart);

    // close input file
    fclose(fptr);

    // exit successful process
    return 0;
}

/* run_test_cart
 * Purpose: this function tests the cart module
 * Arguments: none
 * Returns: 0 for success, 1 for failure
 */
int run_test_cart(char* filename) {
    // open input file for reading
    FILE* fptr = fopen(filename, "r");
    if (fptr == NULL) {
        fprintf(stderr, "Error: could not open input file for reading.\n");
        return 1;
    }

    printf("\n====================TEST \"CART\" MODULE====================\n\n");

    // create new carts
    llnode* test_cart = create_cart();

    // fill cart with items from the input file
    int number, price;
    char name[256];
    while (fscanf(fptr, "%d %s %d \n", &number, name, &price) != EOF) {
        // printf("Adding: %d %s %d\n", number, name, price);
        add_item(&test_cart, create_item(number, name, price));
    }

    // print cart
    print_cart(test_cart);
    printf("\n");

    // print cart
    print_cart(test_cart);
    printf("\n");

    // search for items in cart by number
    printf("SEARCHING BY NUMBER\n");
    int search0 = llist_search_number(test_cart, 7354);     // find value in list
    int search1 = llist_search_number(test_cart, 6974);     // find value not in list
    int search2 = llist_search_number(test_cart, 9038);     // find incorrect value not in list
    int search3 = llist_search_number(test_cart, 6184);     // find head of list
    int search4 = llist_search_number(test_cart, 2607);     // find tail of list
    printf("%d, %d, %d, %d, %d\n", search0, search1, search2, search3, search4);
    printf("\n");

    // search for items in cart by number
    printf("SEARCHING BY NAME\n");
    int search5 = llist_search_name(test_cart, "LampShade");    // find value in list
    int search6 = llist_search_name(test_cart, "Clothes");      // find value not in list
    int search7 = llist_search_name(test_cart, "PrimeBeef");    // find incorrect value not in list
    int search8 = llist_search_name(test_cart, "Toothbrush");   // find head of list
    int search9 = llist_search_name(test_cart, "ShoeLace");     // find tail of list
    printf("%d, %d, %d, %d, %d\n", search5, search6, search7, search8, search9);
    printf("\n");

    // delete items from cart
    printf("DELETING BY NUMBER\n");
    delete_item(&test_cart, 7354);  // delete value in list
    delete_item(&test_cart, 6974);  // delete value not in list
    delete_item(&test_cart, 9038);  // delete incorrect value not in list
    delete_item(&test_cart, 6184);  // delete head of list
    delete_item(&test_cart, 2607);  // delete tail of list
    printf("\n");

    // print cart
    print_cart(test_cart);
    printf("\n");

    // free carts from memory
    free_cart(test_cart);

    printf("\n====================TEST \"CART\" MODULE====================\n\n");

    // close input file
    fclose(fptr);

    // exit successful process
    return 0;
}

/* run_test_item
 * Purpose: this function tests the retail_item module
 * Arguments: none
 * Returns: 0 for success, 1 for failure
 */
int run_test_item() {
    printf("\n====================TEST \"RETAIL_ITEM\" MODULE====================\n\n");

    // create new retail items
    retail_item* snickers = create_item(1001, "Snickers", 75);
    retail_item* starbucks = create_item(3033, "Starbucks", 699);
    retail_item* chipsAhoy = create_item(2222, "chipsAhoy", 329);
    retail_item* largeShirt = create_item(9212, "largeShirt", 1599);
    retail_item* DozenEggs = create_item(1234, "DozenEggs", 369);
    retail_item* Clementines = create_item(5545, "Clementines", 799);

    // print retail items
    print_item(snickers);
    print_item(starbucks);
    print_item(chipsAhoy);
    print_item(largeShirt);
    print_item(DozenEggs);
    print_item(Clementines);

    // free retail items
    free_item(snickers);
    free_item(starbucks);
    free_item(chipsAhoy);
    free_item(largeShirt);
    free_item(DozenEggs);
    free_item(Clementines);

    printf("\n====================TEST \"RETAIL_ITEM\" MODULE====================\n\n");

    // exit successful process
    return 0;
}
