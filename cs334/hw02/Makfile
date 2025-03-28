all: pwd_lookup1 pwd_lookup2

pwd_lookup1: pwd_lookup1.c
	gcc -o pwd_lookup1 pwd_lookup1.c -lcrypto


pwd_lookup2: pwd_lookup2.c
	gcc -o pwd_lookup2 pwd_lookup2.c -lcrypto

clean:
	rm -rf pwd_lookup1 pwd_lookup2
