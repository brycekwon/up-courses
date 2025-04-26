/*
* A sample code to illustrate the open, read, write, and close primitives
* Example produced for University of Portland's 
* CS334 - Introduction to Operating Systems
* Author: Martin Cenek
* Date: 1/03/2021
* Note: the source file is intentionally uncommented.
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <fcntl.h>
#include <unistd.h>

int main(const int argc, const char * argv []) {
  if (argc != 2) {
    printf("Usage: %s <FILE NAME>\n", argv[0]);
    exit(EXIT_FAILURE); 
  }

  const char * filename = argv[1];
  int fd = open(filename, O_WRONLY);
  int errnum = errno;

  if (fd != -1) {
    printf("Opened %s\tfd = %d\terror = %s\n", 
      filename, fd, strerror(errnum));
  } else {
    printf("Failed to open %s\tfd = %d\terror = %s\n", 
      filename, fd, strerror(errnum));
    exit(EXIT_FAILURE); 
  } 
  const int BUFFER_SIZE = 1024;
  char buffer [BUFFER_SIZE];

  while (read(STDIN_FILENO, buffer, BUFFER_SIZE) > 0) {
    if (write(fd, buffer, BUFFER_SIZE) == -1) perror("write");
  }

  close(fd);  
  return EXIT_SUCCESS;
}
