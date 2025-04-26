/* airport.h
 * ----------
 * This file contains the airport struct and function prototypes for the
 * airport module. The airport module is responsible for managing the
 * airport instances.
 * 
 * CS 305, HW 3 (Spring 2023)
 * Author: Bryce Kwon
 * Version: March 19, 2023
 * ----------
 */
#ifndef AIRPORT_H
#define AIRPORT_H

#include <stdio.h>
#include <stdlib.h>

/* airport struct
 * This struct represents an airport. It contains the airport's tag, name,
 * city, and country.
 */
typedef struct airport {
    char tag[4];        // 3-letter airport tag + null terminator
    char* name;         // airport name (variable length)
    char* city;         // airport city (variable length)
    char* country;      // airport country (variable length)
} airport;

/* function prototypes */
airport* make_airport(char* tag, char* name, char* city, char* country);
void print_airport(airport* apt);
void printf_airport(airport* apt, FILE* fptr);
void free_airport(airport* apt);

#endif
