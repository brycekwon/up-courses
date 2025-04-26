#include <stdio.h>
#include <stdlib.h>

#include "resize.h"

void usage();

int main(int argc, char* argv[]) {

    if (argc < 2) {
        printf("usage: ./driver.c <starting size>\n");
        exit(EXIT_FAILURE);
    }

    int starting_size = atoi(argv[1]);
    
    point** poly = malloc(starting_size * sizeof(point*));
    if (poly == NULL) {
        printf("Error: Could not allocate memory\n");
        exit(EXIT_FAILURE);
    }
    
    for (int i = 0; i < starting_size; i++) {
	    poly[i] = malloc(sizeof(point));
        if (poly[i] == NULL) {
            printf("Error: Could not allocate memory\n");
            exit(EXIT_FAILURE);
        }

        poly[i]->x_cor = starting_size - i;
        poly[i]->y_cor = i;
        poly[i]->color = 0;
    }
	
    for (int i = 0; i < starting_size; i++) {
	    printf("Orig Pts: x:%d, y:%d, c:%d\n", poly[i]->x_cor, poly[i]->y_cor, poly[i]->color);
    }

    resize(&poly, &starting_size);
    for (int j = 0; j < starting_size; j++) {
        printf("New Pts: x:%d, y:%d, c:%d\n", poly[j]->x_cor, poly[j]->y_cor, poly[j]->color);
    }

    for (int k = 0; k < starting_size; k++) {
        free(poly[k]);
    }
    free(poly);

    return 0;
}

