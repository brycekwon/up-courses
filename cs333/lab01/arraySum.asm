	.data
	#### define the array ####
abc: # label for beginning of array
	# elements in the array
	.word 30,-3,37,2876,28,7,-4,-73,-72,76
	.word 4,65,898,225,773,67437,5523,53,2256
	.word -44,2,77,2,774,2254,5287,222687,3235
abcEnd: # label for end of array
	
newline:
	.asciiz "\n"
	
	.text
main:		
	################################################################
	#  YOUR CODE SHOULD GO HERE
	################################################################

	li $t4,4
	lw $t0,abc

	li $v0,1
	move $a0,$t0
	syscall

	li $v0,4
	la $a0,newline
	syscall

	li $v0,10
	syscall
