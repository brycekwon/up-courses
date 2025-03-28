#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>

int g_variable = 2; /*  A global variable*/
  
int main(void){ 
  pid_t pid;
  int l_variable = 7; 
  int* ptr_array = (int*) malloc(sizeof(int)*2); 

  *ptr_array    = 8;   //init with some value
  *(ptr_array++)= 10;
  if ((pid = fork())< 0)// check fork()
    perror("fork");

  if (pid == 0){        // child 
    printf("Ch: l_variable: %d, g_variable: %d\n", l_variable, g_variable); 
    ++l_variable; 
    ++g_variable; 

    printf("Ch: l_variable: %d, g_variable: %d\n", l_variable, g_variable); 
    printf("Ch: int array malloc-ed ptr: %p val: %d\n", ptr_array, *ptr_array); 
    
    *ptr_array += 100; 
    printf("Ch: mem value changed to 100\n"); 
    printf("Ch: int array malloc-ed ptr: %p val: %d\n", ptr_array, *ptr_array); 
  }else{              // parent
    printf("P: l_variable: %d, g_variable: %d\n", l_variable, g_variable); 

    l_variable += 10; 
    g_variable += 20; 
    printf("P: l_variable: %d, g_variable: %d\n", l_variable, g_variable); 
    printf("P: int array malloc-ed ptr: %p val: %d\n", ptr_array, *ptr_array); 
    
    *ptr_array += 1000; 
    printf("P: mem value changed to 1000\n"); 
    printf("P: int array malloc-ed ptr: %p val: %d\n", ptr_array, *ptr_array); 

  } 
  return EXIT_SUCCESS; 
} 