/* process.c
 * ----------
 * This file contains the implementation of the process module. The process
 * module is responsible for processing the input file and associated commands.
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
#include "process.h"

/* process_file
 * Purpose: this function creates a linked list from the input file.
 * Parameters: filename - the name of the input file
 * Returns: a pointer to the head of the linked list
 */
node* process_file(char* filename) {
    // open the input file
    FILE* fptr = fopen(filename, "r");
    if (fptr == NULL) {
        printf("Error [process_file]: could not open the input file.\n");
        return NULL;
    }
    
    // create a linked list from the input file
    node* head = NULL;
    
    // read the input file line by line
    char line[256];
    while (fgets(line, 256, fptr) != NULL) {
        // parse data from the line
        char* tag = strtok(line, " ");
        char* name = strtok(NULL, " ");
        char* city = strtok(NULL, " ");
        char* country = strtok(NULL, "\n,");
        
        // create an airport
        airport* apt = make_airport(tag, name, city, country);

        head = insert(head, apt);
    }
    
    // close the input file
    fclose(fptr);
    
    // return the head of the linked list
    return head;
}

/* process_input
 * Purpose: this function processes user input from the command line.
 * Parameters: head - a pointer to the head of the linked list
 * Returns: nothing
 */
void process_input(node* head) {
    // read user input from the command line
    char line[256];
    while (fgets(line, 256, stdin) != NULL) {
        // parse data from the line
        char* command = strtok(line, "\n ");
        char* arg1 = strtok(NULL, "\n ");
        char* arg2 = strtok(NULL, " ");
        char* arg3 = strtok(NULL, " ");
        char* arg4 = strtok(NULL, "\n ");
        
        // process the command
        if (strcmp(command, "a") == 0) {
            // create an airport
            airport* apt = make_airport(arg1, arg2, arg3, arg4);
            
            // insert the airport into the linked list
            head = insert(head, apt);
        } else if (strcmp(command, "d") == 0) {
            // delete the airport from the linked list
            head = delete_airport(head, arg1);
        } else if (strcmp(command, "f") == 0) {
            // find the airport in the linked list
            airport* apt = find_airport(head, arg1);
            
            // print the airport
            if (apt != NULL) {
                print_airport(apt);
            }
        } else if (strcmp(command, "s") == 0) {
            // save the linked list to a file
            save(head, arg1);
        } else if (strcmp(command, "p") == 0) {
            // print the linked list
            print_list(head);
        } else if (strcmp(command, "q") == 0) {
            // quit the program
            delete_list(head);
            break;
        } else {
            // print the usage message
            print_usage();
        }
    }
}

/* print_usage
 * Purpose: print the usage message for the program.
 * Parameters: none
 * Returns: none
 */
void print_usage() {
    printf("a <tag> <name> <city> <country>\t\t#add the airport, to the list of airports no duplicates\n");
    printf("d <tag>\t\t\t\t\t#deletes the airport with given <tag>\n");
    printf("f <tag>\t\t\t\t\t#finds the airport with given <tag>\n");
    printf("s <filename>\t\t\t\t#saves the current content of the LList into a new file\n");
    printf("p\t\t\t\t\t#print the list of airports\n");
    printf("q\t\t\t\t\t#quit the airport tracker and de-allocates all memory\n");
}