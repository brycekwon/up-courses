#ifndef HEXBOARD_H
#define HEXBOARD_H

#include <stdio.h>
#include <stdlib.h>

#include "hexcell.h"

// hexboard struct
typedef struct board {
    int max_row;
    int max_col;
    int start_row;
    int start_col;
    int end_row;
    int end_col;
    hexcell** hexcells;
} board;

/* function prototypes */
void print_board(board*);
int find_path(char*);
int search(board*, int, int);
int start_search(board*);
int check_board(board*);    // unknown function prototype
void free_board(board*);

#endif