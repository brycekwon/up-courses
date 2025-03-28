	.text
	
	################################################################
	# beginning of main program
	################################################################
main:	
	# print prompt
	li $v0,4
	la $a0,prompt1
	syscall

	# read string into buffer and wipe out newline character
	li $v0,8
	la $a0,buffer
	li $a1,1000
	syscall
	jal killNLChar

	# call the 'hash' function
	la $a0,buffer
	jal hash

	# save result in $t0
	move $t0,$v0

	# print the result
	li $v0,4
	la $a0,resultIs
	syscall
	li $v0,1
	move $a0,$t0
	syscall

	# print newline
	li $v0,4
	la $a0,newline
	syscall
	
	# exit
	li $v0,10
	syscall
	################################################################
	# end of main program
	################################################################
	
		
	################################################################
	# beginning of killNLChar - function to kill newline character
	################################################################
killNLChar:	
	# put pointer into $t0; enter loop
	move $t0,$a0
	b knlcEnter
	
	#### beginning of loop
knlcLoop:
	# move to next byte
	addu $t0,$t0,1	
knlcEnter:	
	# load new byte into $t1
	lb $t1,($t0)
	# break out of loop is byte is 0
	beqz $t1,knlcEnd
	# subtract newline value from $t1; loop back if unequal (result != 0)
	subu $t1,'\n'
	bnez $t1,knlcLoop
	
	#### end of loop

	# we found a newline character: zero out that spot
	sb $zero,($t0)
	
knlcEnd:	
	# return to caller
	jr $ra
	################################################################
	# end of killNLChar - function to kill newline character
	################################################################
	
	
	################################################################
	# beginning of hash function, which you should modify
	################################################################
hash:	
	# dummied up to always return the value 1234
    li $v0,11653

loop:
    lb $t1,0($a0)
    beq $t1,$zero,return
    
    mul $v0,$v0,13
    addu $v0,$v0,$t1
    
    addu $a0,$a0,1
    j loop
	
return:
	# return from function
	jr $ra	
	################################################################
	# end of hash function
	################################################################

	.data
buffer:
	.space 10000

prompt1:	
	.asciiz "Please type in a string: "
newline:
	.asciiz "\n"
resultIs:
	.asciiz "The result is "
