	.data
abc:
	.word 30,-3,37,2876,28,7,-4,-73,-72,76
	.word 4,65,898,225,773,67437,5523,53,2256
	.word -44,2,77,2,774,2254,5287,222687,3235
abcEnd:
	
newline:
	.asciiz "\n"
	
	.text
main:
	li $v0,10 # exit
	syscall
	
	.data
