	################################################################
	# this demostrates pushing 3 elements onto the stack, and then
	# looping to pop and print them
	################################################################

	.text
main:	
	# note: the stack is, by default, initialized to be the address
	# just below the bottom of the stack.

	# save the current stack-pointer value so that we know when
	# we've popped all our elements back off the stack
	move $s0,$sp

	################################################################
	# push 3 elements onto the stack
	################################################################

	# push a 10 onto the stack
	li $t0,10
	subu $sp,4
	sw $t0,($sp)

	# push a 20 onto the stack
	li $t0,20
	subu $sp,4
	sw $t0,($sp)

	# push a 30 onto the stack
	li $t0,30
	subu $sp,4
	sw $t0,($sp)

	################################################################
	# loop through the elements, pop/printing them
	#
	# note: $s0 contains the address of the stack-bottom
	################################################################
	
	# if stack is empty, go exit
	beq $sp,$s0,done
	
loop:
	# pop an element off the stack
	lw $a0,($sp)
	addu $sp,4
	
	# print the element, followed by a newline
	li $v0,1 # print integer
	syscall
	li $v0,11 # print character
	li $a0, '\n'
	syscall

	# if there are more elements, loop back
	bne $sp,$s0,loop
	
	################################################################
	# exit the program
	################################################################
done:	
	li $v0,10
	syscall
