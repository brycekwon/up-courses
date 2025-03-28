#ifndef HEXCELL_H
#define HEXCELL_H

// board's hexcell obstacle
typedef enum {YES=-1, NO=0} obstacles;

// colors enumerated type
// board's hexcell colors used to keep track of path search progress
// Each board starts colored WHITE
//     when recursion visits the tile - color GRAY
//     when recursion exits the tile or tile belongs to path - color BLACK
typedef enum {WHITE=1, GRAY=2, BLACK=3} colors;

// hexcell struct
//defines the playing board
typedef struct hexcell {
    colors color;
    obstacles obstacle;
} hexcell;

#endif