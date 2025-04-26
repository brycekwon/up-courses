#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>


void print_header() {
  for (int i = 0; i < 80; i++) 
    printf("#");
  printf("\n");
}

int main(const int argc, char * const argv []) {
  if (argc > 1) {
    const char * filename = argv[1];
    printf("Try exec: %s with argument\n", filename);
    for (int i = 2; i < argc; ++i) {
      printf("arg1: %s\n", argv[i]);
    }
    print_header();
    if (execvp(filename, argv + 1) == -1){
      perror(argv[0]);
    }
    /* uncommment the following.
    *  recompile and run the code
    *  does the header show after the command executes?
    *  Why?
    */
    //printf("Executed successfully!");
    //print_header();

  }
  return EXIT_FAILURE;
}

