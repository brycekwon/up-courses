#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>

int main(void) {
  pid_t pid;
  int temp;
  
  if((pid = fork())<0){
    perror("Fork Error");
  }
  if(pid == 0){
    for (int i=0; i<10; i++){
      printf("Child: ready to read!\n");
      scanf("%d", &temp);
      printf("Child: read temp value: %d\n", temp);
    }
  }else{
    for (int i=0; i<10; i++){
      printf("Parent: wrote temp value: %d\n", i);
      sleep(3);
    }
  }
  scanf("%d", &temp);
  return EXIT_SUCCESS;
} 
