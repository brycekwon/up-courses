#include <stdio.h>
#include <stdlib.h>

#include "airport.h"
#include "llist.h"
#include "process.h"

/* function prototypes */
void run_tests();
void test_process_input();
void test_process_file(char* filename);
void test_llist_save(char* filename);
void test_llist_find();
void test_llist();
void test_printf_airport();
void test_print_airport();
void test_make_airport();

/* main
 * purpose: the main function of the program.
 * parameters: argc - the number of command line arguments
 *             argv - the command line arguments
 * returns: 0 if the program exits successfully, 1 otherwise
 */
int main(int argc, char* argv[]) {
   node* head = NULL;

   if (argc > 1) {
       head = process_file(argv[1]);
       process_input(head);
   } else {
       process_input(head);
   }


    // test function (uncomment)
    //  run_tests();

    return EXIT_SUCCESS;
}

void run_tests() {
    test_process_file("AirportsListShort.csv");
    test_llist_save("llist_test.txt");
    test_llist_find();
    test_llist();
    test_printf_airport();
    test_print_airport();
    test_make_airport();
}

/* test_process_input
 * purpose: tests the process input function.
 * parameters: none
 * returns: nothing
 */
void test_process_input() {
    // create a linked list head
    node *head = NULL;

    // process the input
    process_input(head);
}

/* test_process_file
 * purpose: tests the process file function.
 * parameters: filename - the name of the file to process
 * returns: nothing
 */
void test_process_file(char* filename) {
    // process the file
    node* head = process_file(filename);

    // print the linked list
    print_list(head);

    // free the memory
    delete_list(head);
}

/* test_llist_save
 * purpose: tests the linked list save function.
 * parameters: none
 * returns: nothing
 */
void test_llist_save(char* filename) {
    // create a linked list head
    node *head = NULL;

    // create some airports
    airport* iao = make_airport("IAO", "Siargao-Sayak Airport", "Surigaodel Norte", "Phillipines");
    airport* aml = make_airport("AML", "Puerto Armuellas Airport", "Puerto Armuellas", "Panama");
    airport* azg = make_airport("AZG", "Pablo L. Sidar Airport", "Apatzingán, Michoacán", "Mexico");
    airport* bkc = make_airport("BKC", "Buckland Airport", "Buckland, Alaska", "United States");

    // add the airports to the linked list
    head = insert(head, iao);
    head = insert(head, aml);
    head = insert(head, azg);
    head = insert(head, bkc);

    // print the empty linked list
    print_list(head);

    // save the linked list to a file
    save(head, filename);

    // free the linked list
    delete_list(head);
}

/* test_llist_find
 * purpose: tests the linked list find function.
 * parameters: none
 * returns: nothing
 */
void test_llist_find() {
    // create a linked list
    node* head = NULL;

    // create some valid airports
    airport *iao = make_airport("IAO", "Siargao-Sayak Airport", "Surigaodel Norte", "Phillipines");
    airport *aml = make_airport("AML", "Puerto Armuellas Airport", "Puerto Armuellas", "Panama");
    airport *azg = make_airport("AZG", "Pablo L. Sidar Airport", "Apatzingán, Michoacán", "Mexico");
    airport *bkc = make_airport("BKC", "Buckland Airport", "Buckland, Alaska", "United States");

    // insert the airports into the list
    head = insert(head, iao);
    head = insert(head, aml);
    head = insert(head, azg);
    head = insert(head, bkc);

    // print the list
    print_list(head);

    // find the airports
    airport* find_iao = find_airport(head, "IAO");
    airport* find_dll = find_airport(head, "BKC");
    print_airport(find_iao);
    print_airport(find_dll);

    // find airports not in list
    airport* find_abc = find_airport(head, "ABC");  // find non-existent tag
    airport* find_xyz = find_airport(head, "XYZ");  // find non-existent tag
    airport* find_invalid1 = find_airport(head, NULL);  // find invalid tag
    airport* find_invalid2 = find_airport(NULL, "IAO"); // find invalid head
    airport* find_invalid3 = find_airport(NULL, NULL);  // find invalid head and tag

    // print the found airports
    print_airport(find_abc);
    print_airport(find_xyz);
    print_airport(find_invalid1);
    print_airport(find_invalid2);
    print_airport(find_invalid3);

    // free the memory
    delete_list(head);
}

/* test_llist
 * purpose: tests the linked list functions.
 * parameters: none
 * returns: nothing
 */
void test_llist() {
    // create a linked list
    node* head = NULL;

    // create some valid airports
    airport *iao = make_airport("IAO", "Siargao-Sayak Airport", "Surigaodel Norte", "Phillipines");
    airport *aml = make_airport("AML", "Puerto Armuellas Airport", "Puerto Armuellas", "Panama");
    airport *azg = make_airport("AZG", "Pablo L. Sidar Airport", "Apatzingán, Michoacán", "Mexico");
    airport *bkc = make_airport("BKC", "Buckland Airport", "Buckland, Alaska", "United States");

    // insert the airports into the list
    head = insert(head, iao);
    head = insert(head, aml);
    head = insert(head, azg);
    head = insert(head, bkc);

    // print the list
    print_list(head);

    // free the memory
    delete_list(head);
}

/* test_printf_airport
 * purpose: tests the printf_airport function.
 * parameters: none
 * returns: nothing
 */
void test_printf_airport() {
    // create a file to write to
    FILE *fptr = fopen("airports_test.txt", "w");
    if (fptr == NULL) {
        printf("Error [test_printf_airport]: could not open file");
        return;
    }

    // create some valid airports
    airport *iao = make_airport("IAO", "Siargao-Sayak Airport", "Surigaodel Norte", "Phillipines");
    airport *aml = make_airport("AML", "Puerto Armuellas Airport", "Puerto Armuellas", "Panama");
    airport *azg = make_airport("AZG", "Pablo L. Sidar Airport", "Apatzingán, Michoacán", "Mexico");
    airport *bkc = make_airport("BKC", "Buckland Airport", "Buckland, Alaska", "United States");

    // print the airports
    printf_airport(iao, fptr);
    printf_airport(aml, fptr);
    printf_airport(azg, fptr);
    printf_airport(bkc, fptr);

    // free the memory
    free_airport(iao);
    free_airport(aml);
    free_airport(azg);
    free_airport(bkc);
}

/* test_print_airport
 * purpose: tests the print_airport function.
 * parameters: none
 * returns: nothing
 */
void test_print_airport() {
    // create some valid airports
    airport* iao = make_airport("IAO", "Siargao-Sayak Airport", "Surigaodel Norte", "Phillipines");
    airport* aml = make_airport("AML", "Puerto Armuellas Airport", "Puerto Armuellas", "Panama");
    airport* azg = make_airport("AZG", "Pablo L. Sidar Airport", "Apatzingán, Michoacán", "Mexico");
    airport* bkc = make_airport("BKC", "Buckland Airport", "Buckland, Alaska", "United States");

    // print the airports
    print_airport(iao);
    print_airport(aml);
    print_airport(azg);
    print_airport(bkc);

    // free the memory
    free_airport(iao);
    free_airport(aml);
    free_airport(azg);
    free_airport(bkc);
}

/* test_make_airport
 * purpose: tests the make_airport function.
 * parameters: none
 * returns: nothing
 */
void test_make_airport() {
    // create some valid airports
    airport* iao = make_airport("IAO", "Siargao-Sayak Airport", "Surigaodel Norte", "Phillipines");
    airport* aml = make_airport("AML", "Puerto Armuellas Airport", "Puerto Armuellas", "Panama");
    airport* azg = make_airport("AZG", "Pablo L. Sidar Airport", "Apatzingán, Michoacán", "Mexico");
    airport* bkc = make_airport("BKC", "Buckland Airport", "Buckland, Alaska", "United States");

    // create some airports with invalid tags
    airport* inv1 = make_airport("ABCD", "Name", "City", "Country");    // invalid tag
    airport* inv2 = make_airport("AB", "Name", "City", "Country");      // invalid tag
    airport* inv3 = make_airport("", "Name", "City", "Country");        // invalid tag

    // create some airports with invalid names
    airport* inv4 = make_airport("ABC", "", "City", "Country");        // invalid name
    airport* inv5 = make_airport("ABC", NULL, "City", "Country");      // invalid name

    // create some airports with invalid cities
    airport* inv6 = make_airport("ABC", "Name", "", "Country");        // invalid city
    airport* inv7 = make_airport("ABC", "Name", NULL, "Country");      // invalid city

    // create some airports with invalid countries
    airport* inv8 = make_airport("ABC", "Name", "City", "");           // invalid country
    airport* inv9 = make_airport("ABC", "Name", "City", NULL);         // invalid country

    // free the memory
    free_airport(iao);
    free_airport(aml);
    free_airport(azg);
    free_airport(bkc);
}