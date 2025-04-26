#include <stdio.h>
#include <stdlib.h>

#include "load.h"
#include "hexcell.h"
#include "hexboard.h"

/* main
 * Hexboard path finder logic
 * CS305 HW2 solution
 * Author: Martin Cenek
 * Version: 1 (2023-02-01)
*/
int main(int argv, char* argc[]) {
    if (argv != 2) {
        fprintf(stderr, "ERROR: Board file name missing. Usage: find_path <board>\n");
        return EXIT_FAILURE;
    }

    int ret = find_path(argc[1]);
    if (ret == 0) {
        printf("Could not find a path. Board could not be solved\n");
    } else {
        printf("Success: Found the path!\n");
        printf("Thank you for playing!\n");
    }

    return EXIT_SUCCESS;
}