#include <stdio.h>

#include "load.h"
#include "hexcell.h"
#include "hexboard.h"

/* file_load
 * args: filename with board configuration
 * return: pointer to the board structure
 *
 * loads the board configuration from the file and creates
 * a board structure from the given data. Returns NULL if
 * the file cannot be opened or memory cannot be allocated.
*/
board* file_load(char* f_name) {
    // allocate memory for the hexboard structure
    board* hexboard = (board*) malloc(sizeof(board));
    if (hexboard == NULL) {
        return NULL;
    }

    // open the input file for reading; clean memory if error
    FILE* doc = fopen(f_name, "r");
    if (doc == NULL) {
        free(hexboard);
        return NULL;
    }

    // read board parameters from the input file
    int max_row, max_col, start_row, start_col, end_row, end_col;
    int result = fscanf(doc, "%d %d %d %d %d %d", &max_row, &max_col,
        &start_row, &start_col, &end_row, &end_col);
    if (result == EOF) {
        free(hexboard);
        return NULL;
    }
    
    // error check the board parameters
    if (max_row < 1 || max_col < 1 || start_row < 0 || start_col < 0 ||
            end_row < 0 || end_col < 0 || start_row >= max_row ||
            start_col >= max_col || end_row >= max_row || end_col >= max_col) {
        free(hexboard);
        return NULL;
    }

    // define the hexboard values
    hexboard -> max_row = max_row;
    hexboard -> max_col = max_col;
    hexboard -> start_row = start_row;
    hexboard -> start_col = start_col;
    hexboard -> end_row = end_row;
    hexboard -> end_col = end_col;
    hexboard -> hexcells = (hexcell**) malloc(sizeof(hexcell*) * max_row);
    if (hexboard -> hexcells == NULL) {
        free(hexboard);
        return NULL;
    }

    // create a 2d array of the given parameters
    for (int i = 0; i < max_row; i++) {
        hexboard -> hexcells[i] = (hexcell*) malloc(sizeof(hexcell) * max_col);
        if (hexboard -> hexcells[i] == NULL) {
            free(hexboard -> hexcells);
            free(hexboard);
            return NULL;
        }
    }

    // assign each hexcell's color and obstacle status
    int curr;
    for (int j = 0; j < max_row; j++) {
        for (int k = 0; k < max_col; k++) {
            result = fscanf(doc, "%d", &curr);
            if (result == EOF) {
                free(hexboard -> hexcells);
                free(hexboard);
                return NULL;
            }
            hexboard -> hexcells[j][k].color = WHITE;
            hexboard -> hexcells[j][k].obstacle = curr < 0 ? YES : NO;
        }
    }

    // close the file
    fclose(doc);

    // return the hexboard
    return hexboard;
}