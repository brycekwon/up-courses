#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <fcntl.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>

#include "Lab5Part1.h"

#define KEY ftok("Lab5Part1.c", 65)
#define SIZE sizeof(sh_data)
#define RECORD_LEN 14

int main(void) {
  pid_t pid, cpid;
  int sh_id, fd, fd2, records;
  off_t bytes;
  pid = getpid();
 
  
  if((fd = open("scores.txt", O_RDONLY)) < 0){
    perror("open");
    exit(EXIT_FAILURE);
  }
  if((bytes = lseek(fd, 0, SEEK_END)) < 0){
    perror("lseek");
  }
  records = bytes / RECORD_LEN;
  printf("File size is: %ld\n", bytes);
  lseek(fd, -bytes, SEEK_CUR);
  if((fd2 = open("scores.txt", O_RDONLY)) < 0){
    perror("open");
    exit(EXIT_FAILURE);
  }
  lseek(fd2, (int)records/2 * RECORD_LEN, SEEK_SET);


  if ((sh_id = shmget(KEY, SIZE, 0666 | IPC_CREAT)) == -1) {
	  perror("shmget failure");
	  exit(EXIT_FAILURE);
  }

  sh_data* g_data;

    if ((g_data = (sh_data*) shmat(sh_id, NULL, 0)) == (sh_data*) 1) {
	    perror("shmat failed");
	    exit(EXIT_FAILURE);
    }

  g_data->gpa = 0.0;            //initialize struct with initial values
  strcpy(g_data->name,"none");
  printf("A: Initial value: %s:%.1f\n", g_data->name, g_data->gpa);

  if((cpid = fork()) < 0){ //create and check child
    perror("Fork Error");
  }
  if(cpid == 0){
    printf("C: in child\n");
    sh_data* c_data;            // declare and set local variables
    char buffer[RECORD_LEN+1];
    char name[RECORD_LEN-3];
    int rc = 0;
    double l_gpa = 0;

    if ((g_data = (sh_data*) shmat(sh_id, NULL, 0)) == (sh_data*) 1) {
	    perror("shmat failed");
	    exit(EXIT_FAILURE);
    }
    for(int i = 0; i < records/2+1; i++){
      if((rc = read(fd2, buffer, RECORD_LEN)) < 0){
        perror("c read"); 

	if (shmdt(c_data) == -1) {
		perror("shmdt failed");
		exit(EXIT_FAILURE);
	}

        exit (EXIT_FAILURE);
      }
      sscanf(buffer, "%s %lf", name, &l_gpa);
      sleep(0.1);
      //printf("C: %s read: %s, gpa: %f\n", buffer, name, l_gpa);
      if(c_data->gpa < l_gpa){
		strcpy(c_data->name, name);
		c_data->gpa = l_gpa;
        	printf("C: found higher GPA %s: %.1f\n", c_data->name, c_data->gpa);
      }
    }    

    if (shmdt(c_data) == -1) {
	    perror("shmdt failed");
	    exit(EXIT_FAILURE);
    }
    }else{
    printf("P: in parent\n");
    int rc = 0;                // declare and set local variables
    sh_data* p_data;
    char buffer[RECORD_LEN+1];
    char name[RECORD_LEN-3];
    double l_gpa = 0;

    if ((p_data = (sh_data*) shmat(sh_id, NULL, 0)) == (sh_data*) -1) {
	    perror("shmat failed");
	    exit(EXIT_FAILURE);
    }

    for(int i = 0; i < records/2+1; i++){
      if((rc = read(fd, buffer, RECORD_LEN)) < 0){
        perror("p read");
	
	if (shmdt(p_data) == -1) {
		perror("shmdt failed");
		exit(EXIT_FAILURE);
	}

        exit (EXIT_FAILURE);
      }
      sscanf(buffer, "%s %lf",name, &l_gpa);
      sleep(0.3);
      //printf("P: %s read: %s, gpa: %f\n", buffer, name, l_gpa);
      if(p_data->gpa < l_gpa){
	      strcpy(p_data->name, name);
	      p_data->gpa = l_gpa;
              printf("P: found higher GPA %s: %.1f\n", p_data->name, p_data->gpa);
      }
    }

    if (shmdt(p_data) == -1) {
	    perror("shmdt failed");
	    exit(EXIT_FAILURE);
    }

    }
  if((close(fd) < 0) || (close(fd2) < 0 )){
    perror("close");
  }
  printf("A: Highest gpa found: %s: %lf\n", g_data->name, g_data->gpa);

  if (shmdt(g_data) == -1) {
	  perror("shmdt failed");
	  exit(EXIT_FAILURE);
  }

  if (shmctl(sh_id, IPC_RMID, NULL) == -1) {
	  perror("shmctl failed");
	  exit(EXIT_FAILURE);
  }


  return EXIT_SUCCESS;
}
