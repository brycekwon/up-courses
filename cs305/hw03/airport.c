/* airport.c
 * ----------
 * This file contains the implementation of the airport module. The airport
 * module is responsible for managing the airport instances.
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

/* make_airport
 * Purpose: this function creates a new airport instance.
 * Parameters: tag - the airport's tag
 *             name - the airport's name
 *             city - the airport's city
 *             country - the airport's country
 * Returns: a pointer to the new airport instance
 */
airport* make_airport(char* tag, char* name, char* city, char* country) {
    // allocate memory for an airport
    airport* apt = (airport*) malloc(sizeof(airport));
    if (apt == NULL) {
        printf("Error [make_airport]: could not allocate memory for an airport.\n");
        return NULL;
    }

    // check for invalid parameters
    if (tag == NULL || strlen(tag) != 3) {
        printf("Error [make_airport]: invalid airport tag provided.\n");
        free(apt);
        return NULL;
    } else if (name == NULL || strlen(name) < 1) {
        printf("Error [make_airport]: invalid airport name provided.\n");
        free(apt);
        return NULL;
    } else if (city == NULL || strlen(city) < 1) {
        printf("Error [make_airport]: invalid airport city provided.\n");
        free(apt);
        return NULL;
    } else if (country == NULL || strlen(country) < 1) {
        printf("Error [make_airport]: invalid airport country provided.\n");
        free(apt);
        return NULL;
    }
    
    // allocate memory for the name, city, and country
    apt->name = (char*) malloc(sizeof(char) * (strlen(name) + 1));
    if (apt->name == NULL) {
        printf("Error [make_airport]: could not allocate memory for the airport name.\n");
        free(apt);
        return NULL;
    }
    apt->city = (char*) malloc(sizeof(char) * (strlen(city) + 1));
    if (apt->city == NULL) {
        printf("Error [make_airport]: could not allocate memory for the airport city.\n");
        free(apt->name);
        free(apt);
        return NULL;
    }
    apt->country = (char*) malloc(sizeof(char) * (strlen(country) + 1));
    if (apt->country == NULL) {
        printf("Error [make_airport]: could not allocate memory for the airport country.\n");
        free(apt->name);
        free(apt->city);
        free(apt);
        return NULL;
    }
    
    // copy the tag, name, city, and country
    strcpy(apt->tag, tag);
    strcpy(apt->name, name);
    strcpy(apt->city, city);
    strcpy(apt->country, country);
    
    // return the new airport
    return apt;
}

/* print_airport
 * Purpose: this function prints the information of an airport instance.
 * Parameters: apt - the airport to print
 * Returns: nothing
 */
void print_airport(airport* apt) {
    if (apt == NULL) {
        printf("Error [print_airport]: invalid airport provided.\n");
        return;
    }
    
    // print the airport's tag, name, city, and country
    printf("<%s> <%s> <%s> <%s>\n", apt->tag, apt->name, apt->city, apt->country);
}

/* printf_airport
 * Purpose: this function prints the information of an airport instance to a file.
 * Parameters: apt - the airport to print
 *             fptr - the file to print to
 * Returns: nothing
 */
void printf_airport(airport* apt, FILE* fptr) {
    if (apt == NULL) {
        printf("Error [printf_airport]: invalid airport provided.\n");
        return;
    } else if (fptr == NULL) {
        printf("Error [printf_airport]: invalid file provided.\n");
        return;
    }

    // print the airport's tag, name, city, and country to the file
    fprintf(fptr, "<%s> <%s> <%s> <%s>\n", apt->tag, apt->name, apt->city, apt->country);
}

/* free_airport
 * Purpose: this function frees memory allocated for an airport instance.
 * Parameters: apt - the airport to free
 * Returns: nothing
 */
void free_airport(airport* apt) {
    if (apt == NULL) {
        printf("Error [free_airport]: invalid airport provided.\n");
        return;
    }

    // free the name, city, country and airport
    free(apt->name);
    free(apt->city);
    free(apt->country);
    free(apt);
}
