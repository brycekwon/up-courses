#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>

/////////////////////
// Problem 1
/////////////////////

bool plusOrder()
{
    // answer goes here
    
    return (x=(4*5)) + (y=(6/2)) == 23
}



/////////////////////
// Problem 2
/////////////////////

void printDouble()
{
    int c = 1;
    for(int i = 0; i < 100; i++)
    {
        printf("c = %d\n", c);
        c += c + 1;
    }
}
// Why do you think this function eventually starts printing -1 and doesn't stop?
// What behavior is undefined here?
//
// Due to the standardized size of an "int" in C (8 bytes), it can only hold up to a certain
// value (~2 billion signed) before overflowing, thus the -1 results. Put it simply, the value
// of an "int" can't count any higher and defaults to -1. This behavior however, is undefined as
// different systems and compilers may handle the error in different ways. There is no standard
// for how this needs to be handled.

/////////////////////
// Problem 3
/////////////////////

void scope()
{
    {
        int x = 2;
        printf("%p %d\n", &x, x);
    }
    {
        int y;
        printf("%p %d\n", &y, y);
    }
}
// output:
// 0x7ffcdb6b5424 2
// 0x7ffcdb6b5424 2 
// Why do you think x and y
// have the same memory address and value 
// even thought they're different variables?
//
// Functions and variables are stored on the "stack", or a dynamic region of memory that grows and
// shrinks automatically as functions/variables are "pushed" and "popped". In the code, x is pushed
// onto the stack and initialized to 2, giving the memory address and value displayed. Then, it is
// popped as the scope of its block has concluded, and the memory where it once existed has been freed.
// Then, y is pushed onto the stack at the same location of x (since it is free memory), displaying the
// same address. Since y has not been initialized to any value, it uses the current value of its memory
// location (x=2).

/////////////////////
// Problem 4
/////////////////////
void f(char* dest, char* src)
{
    while(*dest++ = *src++);
} 

// What function is f(char*,char*) implementing?\\
//
// strcpy()

// How does this code work?\\
//
// a pointer to a block of memory and source to copy is provided. "*src++" grabs the value at the memory
// location pointed to by "src" and increments the pointer. The value "*dest" is pointing to is then
// initialized the src value and the pointer is incremented. This continues in the "while" loop until the
// "\0" null terminating character is found.

/////////////////////
// Problem 5
/////////////////////

typedef struct
{
    int* data;
    int size;
    int capacity;
} ArrayList;

// create a new ArrayList that can hold 4 elements
ArrayList* newArrayList()
{
    ArrayList* this = (ArrayList*)malloc(sizeof(ArrayList));
    this->data = malloc(sizeof(int)*4);
    this->size = 0;
    this->capacity = 4;
    return this;
}

// add an element to an ArrayList.
// if the ArrayList is full,
// then allocate new memory for it that's twice as big.
// Then add the element to the end.
void add(ArrayList* this, int elem)
{
    if(this->size <= this->capacity)
    {
        this->data[this->size] = elem;
        this->size++;
    }
    else
    {
        //double the capacity for the array
        this->capacity *= 2;

        //copy all the data to a new array
        int* copy = (int*)malloc(sizeof(int) * this->capacity);
        for(int i = 0; i < this->size; i++)
        {
            copy[i] = this->data[i];
        }

        // add the new element
        copy[this->size] = elem;
        this->size++;

        //get rid of the old data, and replace it with the longer copy
        free(this->data);
        this->data = copy;
    }
}


int main()
{
    ArrayList* a = newArrayList();
    // add 3 values to the ArrayList
    // so our ArrayList looks like [1,2,4]
    add(a, 1);
    add(a, 2);
    add(a, 4);

    //for every value in the ArrayList
    //if the value is even,
    //then move it to the end of the array list,
    //and add an odd value in it's place
    //our arrayList should look like [1,3,5,2,4]
    int* end = a->data + a->size;
    for(int* x = a->data; x < end; x++)
    {
        if(*x % 2 == 0)
        {
            add(a, *x);
            *x = *x + 1;
        }
    }

    //print out the values of the array list
    printf("[ ");
    for(int* x = a->data; x < a->data + a->size; x++)
    {
        printf("%d ", *x);
    }
    printf("]\n");

    return 0;
}


// What went wrong with the above code?
//
// The problem was with the add() function, when checking if the size of the array list was
// larger than its current capacity. Based on the outcome, it would either add the element to the list
// (if not full) or allocate a new list of larger size (if full). Originally, the check
// compared if the size was less than (<) the current capacity, which would cause an error when the
// list is full as it will continue to add to the list rather than allocating a new one. The solution
// is to ensure the check of size is less than or equal to (<=) the current capacity.
//
