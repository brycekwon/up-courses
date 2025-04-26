	.text
main:		
	
	################################################################
	# YOUR CODE SHOULD GO HERE
	################################################################

	# prompt the user for an integer
	li $v0,4 # print string
	li $a0,pleaseType
	syscall

	# read an integer from the keyboard
	li $v0,5
	syscall

	# the integer read is now in $v0

	#### Your code should multiply $v0 by 19 using shifts and adds,
	#### putting the result into $t0.
	sll $t0,$v0,4
	sll $t1,$v0,1
	addu $t0,$t0,$t1
	addu $t0,$t0,$v0

	# for now, we're putting the value of 22 into $t0
	# li $t0,22

	# print the "The result ..." text
	la $a0,theResultIs
	li $v0,4
	syscall

	# print the result
	move $a0,$t0
	li $v0,1
	syscall

	# print a newline
	la $a0,newline
	li $v0,4
	syscall

	# exit the program
	li $v0,10 # syscall-opcode for "exit program"
	syscall
	
	.data
pleaseType:
	.asciiz "Please type an integer: "
theResultIs:
	.asciiz "The result is "
newline:
	.asciiz "\n"
	
