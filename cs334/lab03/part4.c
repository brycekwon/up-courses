#include <stdio.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>


#define BUFF_SIZE 10

int main(int argc, char* argv[]) {

  pid_t pid1, pid2;
  if(argc!=2){
    printf("Usage: ./<program name> <file name>");
    exit(EXIT_FAILURE); 
  }
  int fd = open(argv[1], O_RDONLY);

  printf("Before\n");

  if ((pid1 = fork()) < 0) // could not fork
    perror("FORK ERROR");
  
  if (pid1 == 0) {    // child 
    char buff[BUFF_SIZE];
    int n;
    printf("CH1: %d\n",getpid());
    while(1){
    /************************************************
    * logic for suspending yourself goes here
    ************************************************/
      n=read(fd, buff, BUFF_SIZE);
      if(n>0){
        printf("\nCh1:");
        write(STDOUT_FILENO, buff, n);
      }else
        break;
      sleep(.1);
    }
    close(fd);
    exit(EXIT_SUCCESS);
  }else{
    pid2 = fork();
    if (pid2 == 0) {    // child2 
      char buff[BUFF_SIZE];
      int n;
      printf("CH2: %d\n",getpid());
      while(1){
    /************************************************
    * logic for suspending yourself goes here
    ************************************************/
        n=read(fd, buff, BUFF_SIZE);
        if(n>0){
          printf("\nCh2:");
          write(STDOUT_FILENO, buff, n);
        }else
          break;
        sleep(.1);
      }
      close(fd);
      exit(EXIT_SUCCESS);   
    }else{              //parent
      printf("P:%d ch1:%d ch2:%d\n", getpid(), pid1, pid2);
      int rc;
      pid_t wpid;
     while (1){
        wpid = waitpid(-1, &rc, WNOHANG | WUNTRACED | WCONTINUED);
    /************************************************
    * check if EXIT flag was caught, close FD and exit with SUCCESS
    * otherwise check if STOP flag was caught
    *     if in child 2 then send CONTINUE flag to child 1
    *     if in child 1 then send CONTINUE flag to child 2
    *************************************************/ 
        sleep(.1);
      }
    }
  }
  return EXIT_SUCCESS;
}