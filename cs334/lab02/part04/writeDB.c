/*
* A sample code to illustrate fast file write and the use of fixed record lendth
* using a file descriptor with the following system calls: open, read, write, and close 
* additional library calls include: sprintf to format the fixed len record to be written
*  
* Example produced for University of Portland's 
* CS334 - Introduction to Operating Systems
* Author: Martin Cenek
* Date: 1/03/2021
*		1/01/2022
* Note: the source file is intentionally uncommented.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <fcntl.h>
#include <unistd.h>

int main(const int argc, const char * argv []) {
	int REC_LEN = 25;
	char buff[REC_LEN];
	int len = 0;

	if (argc != 2) {
		printf("Usage: %s <OUTPUT FILE NAME>\n", argv[0]);
		exit(EXIT_FAILURE); 
	}


	const char * fn_out = argv[1];
	int fd_out = open(fn_out, O_WRONLY|O_CREAT|O_TRUNC, 0777);
	int errnum_out = errno;
	if(errnum_out == -1){
		printf("Could not open file: %s \n", argv[1]);
		exit(EXIT_FAILURE);
	}
	char txt[] = "Alf";
  	sprintf(buff, "%s%*u", txt, (REC_LEN - (int)strlen(txt)+2), ' ');
	printf("%.*s", REC_LEN, buff);printf("X\n");
	write(fd_out, buff, REC_LEN);

	char txt2[] = "Cinderella";
	sprintf(buff, "%s%*u",txt2, (REC_LEN - (int)strlen(txt2)+2), ' ');
	printf("%.*s", REC_LEN, buff);printf("X\n");
	write(fd_out, buff, REC_LEN);

	char txt3[] = "Mickey";
	sprintf(buff, "%s%*u",txt3, (REC_LEN -(int)strlen(txt3)+2), ' ');
	printf("%.*s", REC_LEN, buff);printf("X\n");
	write(fd_out, buff, REC_LEN);

	char txt4[] = "Louie";
	sprintf(buff, "%s%*u",txt4, (REC_LEN -(int)strlen(txt4)+2), ' ');
	printf("%.*s", REC_LEN, buff);printf("X\n");
	write(fd_out, buff, REC_LEN);

	char txt5[] = "Dewey";
	sprintf(buff, "%s%*u",txt5, (REC_LEN -(int)strlen(txt5)+2), ' ');
	printf("%.*s", REC_LEN, buff);printf("X\n");
	write(fd_out, buff, REC_LEN);

	char txt6[] = "Huey";
	sprintf(buff, "%s%*u",txt6, (REC_LEN -(int)strlen(txt6)+2), ' ');
	printf("%.*s", REC_LEN, buff);printf("X\n");
	write(fd_out, buff, REC_LEN);

	char txt7[] = "Pluto";
	sprintf(buff, "%s%*u",txt7, (REC_LEN -(int)strlen(txt7)+2), ' ');
	printf("%.*s", REC_LEN, buff);printf("X\n");
	write(fd_out, buff, REC_LEN);

	close(fd_out);

	return 1;
}	
