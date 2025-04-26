/* process.h
 * ----------
 * This file contains the function prototypes for the process module. The
 * process module is responsible for processing the input file and associated
 * commands.
 * 
 * CS 305, HW 3 (Spring 2023)
 * Author: Bryce Kwon
 * Version: March 19, 2023
 * ----------
 */
#ifndef PROCESS_H
#define PROCESS_H

#include <stdio.h>
#include <stdlib.h>

#include "airport.h"
#include "llist.h"

/* function prototypes */
node* process_file(char* filename);
void process_input(node* head);
void print_usage();

#endif