/*
 * Author: Bryce Kwon
 * dijkstra.c
 * Dijkstra function implementations, based on adjacency list
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "main.h"
#include "graph.h"

/* 
 * dijkstra
 * runs dijkstra's algorithm on the graph of airports to find the shortest
 * path to each airport from the source.
 */
void dijkstra(Graph* g, char* source) {

    // find the source airport in the graph
    int sourceIndex = -1;   // source does not exist
    for (int i = 0; i < g->V; i++) {
        if (strcmp(g->array[i].label, source) == 0) {
            sourceIndex = i;
            break;
        }
    }

    // set source distance to 0
    g->array[sourceIndex].dValue = 0;

    // conduct dijkstra's algorithm on the adjacency list
    while (isEmpty(g) == 0) {

        // get the next smallest vertex not explored
        int u = getMin(g);
        // invalid minimum
        if (u < 0) {
            break;
        }

        // get all neighbors for the vertex being explored
        AdjListNode* llnode = g->array[u].head;

        // check for travel distance on each edge
        while (llnode != NULL) {           
            // calculate new travel cost 
            int travelCost = (g->array[u].dValue + llnode->cost);

            // if the new travel cost is less than the current cost to get to
            // the vertex, update the cost and set the predecessor to the
            // current vertext being explored. Otherwise, do nothing.
            if (g->array[llnode->dest].dValue > travelCost) {
                g->array[llnode->dest].dValue = travelCost;
                g->array[llnode->dest].pred = u;
            }
            
            // traverse to the next edge
            llnode = llnode->next;
        }
        // set the node to black to indicate it has been explored
        g->array[u].color = BLACK;
    }
}

/* 
 * isEmpty
 * checks if the graph has any verticies that have not been explored yet
 * by dijkstra's algorithm. WHITE = unexplored, BLACK = explored
 */
int isEmpty(Graph* g) {
    // the graph does not exist
    if (g == NULL) {
        return 1;
    }

    // check for any unexplored vertexes
    for (int i = 0; i < g->V; i++) {
        if (g->array[i].color == WHITE) {
            return 0;
        }
    }

    // no unexplored vertecies found
    return 1;
}

/* 
 * getMin
 * gets the shortest distance vertex that has not been explored.
 */
int getMin(Graph* g) {
    // the graph does not exist
    if (g == NULL) {
        return -1;
    }

    // initialize minimums
    int minIdx = -1;    // initial minimum does not exist
    int minVal = INF;   // smallest current value found is infinity

    // find the smallest unexplored vertex
    for (int i = 0; i < g->V; i++) {
        // check if it has been explored
        if (g->array[i].color == WHITE) {
            // check if this vertex is smaller than the current minimum
            if (g->array[i].dValue < minVal) {
                // update the new minimum found
                minVal = g->array[i].dValue;
                minIdx = i;
            }
        }
    }

    return minIdx;
}