	.text
main:		
	# prompt the user
	li $v0,4 # print string
	la $a0,prompt
	syscall

	# read the string into the 'myString' buffer
	li $v0,8 # read string
	la $a0,myString
	la $a1,9999
	syscall

	################################################################
	# YOUR CODE GOES HERE
	################################################################

	# exit the program
	li $v0,10 # exit code
	syscall

	.data
	# space for the string that we're downcasing
myString:
	.space 10000
prompt:
	.asciiz "Please type some text: "
newline:
	.asciiz "\n"

