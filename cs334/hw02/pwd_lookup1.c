#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <openssl/sha.h>

void bin_to_hex(unsigned char[], char[]);


int main(int argc, char* argv[]) {
	
	// check for correct arguments
	if (argc != 3) {
		fprintf(stderr, "Usage: %s <input hash file> <password>\n", argv[0]);
		exit(EXIT_FAILURE);
	}
	printf("Searching for password \"%s\":\n", argv[2]);

	// program performance measuring
	clock_t start_time;
	clock_t end_time;
	long double total_time;

	// storing hash function values
	unsigned char bin_hash[SHA_DIGEST_LENGTH];	// contains the binary value of hash
	char hex_hash[(SHA_DIGEST_LENGTH * 2) + 1];	// contains the hexadecimal value of hash
	
	// searching for matching hash values
	int counter = 1;	// line number being compared
	char entry[41];		// buffer for each hash entry + NUL

	// derive SHA1 hash from provided input password
	SHA1((unsigned char*) argv[2], strlen(argv[2]), bin_hash);
	bin_to_hex(bin_hash, hex_hash);
	printf("SHA hash: %s\n", hex_hash);

	// open database of pwned passwords with user-level commands
	FILE* pwned_passwords = fopen(argv[1], "r");
	if (pwned_passwords == NULL) {
		fprintf(stderr, "Could not open file %s\n", argv[1]);
		exit(EXIT_FAILURE);
	}
	printf("Searching in file \"%s\"\n", argv[1]);

	// check database for matching password hash
	start_time = clock();
	while (fscanf(pwned_passwords, "%40s", entry) != EOF) {
		if (strcmp(hex_hash, entry) == 0) {
			
			// match found
			end_time = clock();
			total_time = (long double) (end_time - start_time) / CLOCKS_PER_SEC;
			printf("Found, line: %d\n", counter);
			printf("Time searching: %Lfs\n", total_time);

			// close file and exit
			fclose(pwned_passwords);
			exit(EXIT_SUCCESS);
		}
		counter++;
	}

	// no matches found
	end_time = clock();
	total_time = (long double) (end_time - start_time) / CLOCKS_PER_SEC;
	printf("Not found\n");
	printf("Time searching: %Lfs\n", total_time);

	// close file and exit
	fclose(pwned_passwords);
	exit(EXIT_SUCCESS);
}


void bin_to_hex(unsigned char hash[SHA_DIGEST_LENGTH], char buf[(SHA_DIGEST_LENGTH * 2) + 1]) {
	// store all hexadecimal representation into allocated buffer
	for (int i = 0; i < SHA_DIGEST_LENGTH; i++) {
		sprintf((char*) &buf[i*2], "%02X", hash[i]);
	}
}


