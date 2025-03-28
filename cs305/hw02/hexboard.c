#include <stdio.h>
#include <stdlib.h>

#include "load.h"
#include "hexcell.h"
#include "hexboard.h"

/* print_board
 * args: pointer to the current board structure
 * return: void
 * 
 * prints the hexboard board:
 *     if hexcell color is path, print 'P'
 *     if hexcell has an obstacle, print 'X'
 *     otherwise, print '0'
*/
void print_board(board* board) {
    printf("Board: %drX%dc, start: %drX%dc, destination: %drX%dc\n", board->max_row, 
        board->max_col, board->start_row, board->start_col, board->end_row, 
        board->end_col);
    
    for (int j=0; j<board->max_col;j++) {
        printf("/ \\ ");
    }
    printf("\n");

    for (int i = 0; i < board -> max_row; i++) {
        for (int j = 0; j < board -> max_col; j++) {
            if (i % 2 && j == 0) {
                printf("  ");
            }
            printf("|%c| ", (board -> hexcells[i][j].color == BLACK ? 'P' :
                (board -> hexcells[i][j].obstacle == YES ? 'X' : '0')));
        }   
        printf("\n");
    
        for (int j = 0; j < board -> max_col; j++) {
            if(j == 0 && i % 2) {
                printf("/ \\ / ");
            } else {
                printf("\\ / ");
            }
        }
        printf("\n");
    }
    printf("\n");
    return;
}

/* find_path
 * args: unchecked filename with board configuration
 * return: int return board status 1 if path found 0 otherwise
 * 
 * creates the board instance, prints the board, calls and saves
 * the results of the start_search function on the loaded board
 * prints the board, frees the board and returns the path search result
*/
int find_path(char* f_name) {
    // create a board from the configuration file
    board* board = file_load(f_name);
    if (board == NULL) {
        return 0;
    }
    print_board(board);

    // search for a valid path
    int status = start_search(board);
    print_board(board);

    // free the board
    free_board(board);
    return status;
}

/* search
 * args: pointer to the current board structure and the current row and column
 * return: int return board status 1 if path found 0 otherwise
 *
 * recursively searches for a path from the start to the end
 * returns 1 if a path is found, 0 otherwise
*/
int search(board* board, int row, int col) {
    // define the recursion base case
    if (row < 0 || row >= board -> max_row) {    // row index out of bounds
        return 0;
    } else if (col < 0 || col >= board -> max_col) {     // col index out of bounds
        return 0;
    } else if (board -> hexcells[row][col].color == GRAY) {    // path is repeated
        return 0;
    } else if (board -> hexcells[row][col].obstacle == YES) {   // path is an obstacle
        return 0;
    } else if (row == board -> end_row && col == board -> end_col) {    // path found
        return 1;
    } else {
        // mark current index as path
        board -> hexcells[row][col].color = GRAY;

        // recursively call in all 6 directions
        if (row % 2 == 0) { // directions for even rows
            if (search(board, row-1, col-1) || search(board, row, col-1) ||
                    search(board, row+1, col-1) || search(board, row+1, col) ||
                    search(board, row, col+1) || search(board, row+1, col)) {
                        board -> hexcells[row][col].color = BLACK;
                        return 1;
            } else {
                // unmark current index as path
                board -> hexcells[row][col].color = WHITE;
                return 0;
            }
        } else {    // directions for odd rows
            if (search(board, row-1, col) || search(board, row, col-1) ||
                    search(board, row+1, col) || search(board, row+1, col+1) ||
                    search(board, row, col+1) || search(board, row+1, col+1)) {
                        board -> hexcells[row][col].color = BLACK;
                        return 1;
            } else {
                // unmark current index as path
                board -> hexcells[row][col].color = WHITE;
                return 0;
            }
        }
    }
}

/* start_search
 * args: pointer to the current board structure
 * return: int return board status 1 if path found 0 otherwise
 *
 * this is the wrapper function for the search function
 * it calls the search function on the start index
*/
int start_search(board* board) {
    if (board -> hexcells[board -> start_row][board -> start_col].obstacle == YES) {
        return 0;
    } else if (board -> hexcells[board -> end_row][board -> end_col].obstacle == YES) {
        return 0;
    }

    return search(board, board -> start_row, board -> start_col);
}

/* free_board
 * args: pointer to the current board structure
 * return: void
 *
 * frees the board structure
*/
void free_board(board* board) {
    if (board == NULL) {
        return;
    }
    
    for (int i = 0; i < board -> max_row; i++) {
        free(board -> hexcells[i]);
    }
    free(board -> hexcells);
    free(board);
    return;
}