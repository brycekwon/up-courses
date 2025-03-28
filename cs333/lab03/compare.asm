	.text
	
	################################################################
	# beginning of main program
	################################################################
main:	
	# print first prompt
	li $v0,4
	la $a0,prompt1
	syscall

	# read first string into buffer and wipe out newline character
	li $v0,8
	la $a0,buffer1
	li $a1,1000
	syscall
	jal killNLChar
	
	# print secondprompt
	li $v0,4
	la $a0,prompt2
	syscall

	# read second string into buffer and wipe out newline character
	li $v0,8
	la $a0,buffer2
	li $a1,1000
	syscall
	jal killNLChar

	# call the 'strCmp' function
	la $a0, buffer1
	la $a1, buffer2
	jal strCmp

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
	# beginning of strCmp function, which you should modify
	################################################################
strCmp:	
	# dummied up to always return 1
	#li $v0,1

    move $v0,$zero

loop:
    lb $t1,0($a0)
    lb $t2,0($a1)

    beq $t1,$zero,return
    beq $t2,$zero,return
    
    addu $a0,$a0,1
    addu $a1,$a1,1

    bgt $t1,$t2,greater
    bgt $t2,$t1,less

    j loop

less:
    li $v0,-1
    j return

greater:
    li $v0,1
    j return


	# return from function
return:	
	jr $ra	
	################################################################
	# end of strCmp function
	################################################################

	.data
buffer1:
	.space 10000
buffer2:
	.space 10000

prompt1:	
	.asciiz "Please type in a string: "
prompt2:	
	.asciiz "Please type in another string: "
newline:
	.asciiz "\n"
resultIs:
	.asciiz "The result is "
