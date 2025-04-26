	.text
main:		

	# prompt the user for a number
	la $a0, prompt1
	li $v0,4
	syscall
	
	# read the number from the keyboard
	li $v0,5
	syscall
	move $t0,$v0

	# print the value in binary, followed by a newline
	li $v0,4
	la $a0,valInBin
	syscall
	move $a0,$t0
	jal binPrint
	li $v0,11
	li $a0,'\n'
	syscall
	
	################################################################
	# YOUR CODE SHOULD GO HERE
	################################################################

	li $v0,4
	la $a0,valInHex
	syscall

	li $t1,0xF0000000

	li $t5,1
	li $t6,9

loop:
	beq $t5,$t6,exit

	and $t3,$t1,$t0
	lb $t4,hexTable($t3)

	li $v0,11
	move $a0,$t4
	syscall

	srl $t1,$t1,4
	addu $t5,$t5,1
	j loop

exit:
	li $v0,4
	la $a0 newline
	syscall

	# exit the program
	li $v0,10 # syscall-opcode for "exit program"
	syscall

	
	################################################################
	# function binPrint - print an integer in binary to the terminal
	# - parameter $a0: the integer to print
	# This function preserves all registers except for $ra
	################################################################
binPrint:
	
	# save registers on stack
	subu $sp,16
	sw $s0,($sp)
	sw $s1,4($sp)
	sw $a0,8($sp)
	sw $v0,12($sp)
	
	# set up counter for 32 bits
	li $s0,32
	
	# move value into $s1 to free up $a0 for syscalls
	move $s1,$a0

binPrintLoop:
	
	# load the digit '0' into $a0 for syscall-printing
	li $a0,'0'
	
	# if top bit of value is 1, bump $a0 so that it has the digit '1'
	bgez $s1,skip
	addu $a0,1
skip:	
	
	# print the bit as a '0' or a '1'
	li $v0,11
	syscall
	
	# shift our value left so that we have the next bit in the
	# top position; decrement the counter
	sll $s1,1
	subu $s0,1
	
	# if our counter is divisible by 4, print a space character so
	# that our bits are displayed in chunks of 4
	and $v0,$s0,0x3
	bnez $v0,skip2
	li $v0,11
	li $a0,' '
	syscall
skip2:	

	# if the counter has not counted down to 0, loop back
	bnez $s0,binPrintLoop

	# restore registers by popping them off the stack, and return
	lw $s0,($sp)
	lw $a0,4($sp)
	lw $a0,8($sp)
	lw $v0,12($sp)
	addu $sp,16
	jr $ra

	.data
prompt1:	
	.asciiz "Please type a number: "
valInBin:
	.asciiz "The value in binary is: "
newline:
	.asciiz "\n"
hexTable:
	.ascii "0123456789ABCDEF"
valInHex:
	.asciiz "The value in hex is: "
