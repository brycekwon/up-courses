	.text
main:	
	
	# prompt the user
	li $v0,4
	la $a0,pleaseEnter1
	syscall

	move $t6, $zero
	
loop:	

	# read an integer
	li $a0,0
	jal readInteger

	# if no integer read, drop out of loop; else loop back
	beqz $v1,doneLoop

	# store number into block
	move $t5, $v0
	jal malloc
	sw $t5, ($v0)

	# link blocks together

	# link blocks together
 	move $t7, $v0
 	addu $v0, 4
 	sw $t6, ($v0)
 	move $t6, $t7
	sw $v0, ($t0)

	# loop back
	b loop

	# skip over the (presumed) newline character
doneLoop:	
	jal readChar

print:
	lw $t2,currentHeapPointer
	move $t1, $t2

	lw $t2,($t1)
	beqz $t2, exit

	subu $t3, $t2, 4
	lw $a0, ($t3)
	li $v0, 1
	syscall

	li $v0, 11
	li $a0, '\n'
	syscall

	move $t1, $t2

printLoop:
	lw $t2,($t1)
	beqz $t2, exit

	# subu $t3, $t2, 4
	lw $a0, ($t2)
	li $v0, 1
	syscall

	li $v0, 11
	li $a0, '\n'
	syscall

	addu $t2, $t2, 4
	move $t1, $t2

	b printLoop

exit:
	################################################################
	# exit the program
	################################################################
	# exit
	li $v0,10
	syscall
	########### don't touch the code below--it handles heap allocation ##########
	.data
	
pleaseEnter1:	
	.asciiz "Please enter a list of integers, separated by spaces: "
pleaseEnter2:	
	.asciiz "Please enter an integer: "
dashes:	
	.asciiz "====================\n"

	# ensure that we're word-aligned
	.align 2	

	# word to keep track of next available heap location
currentHeapPointer:
	.word heapStart
	
	# the heap space
heapStart:
	.space 100000
heapEnd:

	.text
	# the heap-allocation function
malloc:
	# load return value into $v0
	lw $v0,currentHeapPointer
	
	# bump heap pointer by byte-size object, rounded up to next
	# multiple of 4
	addu $t0,$v0,$a0
	addu $t0,$t0,3
	and $t0,$t0,0xfffffffc
	
	# store heap pointer for next allocation
	sw $t0,currentHeapPointer
	
	#return
	jr $ra
	
	################################################################
	# readInteger - reads an integer from the keyboard
	# parameter:
	# - $a0:
	#   - 0 => do not treat newline characters as whitespace
	#   - 1 => treat newline characters as whitespace
	# returns:
	# - $v0 - the integer read, if any
	# - $v1 - the return status
	#   - 0 => an integer was not read successfully
	#   - 1 => an integer was read successfully
	# 
	# This function skips over whitespace: spaces, tabs and, depending
	# on the second parameter, newline and carriage return characters.
	#
	# This function does not check for overflow, so the if the number
	# typed in is too large, the result will only be the bottom
	# 32 bits of the true result.
	#
	################################################################
readInteger:	
	
	# prolog
	subu $sp,16
	sw $ra,($sp)
	sw $s0,4($sp)
	sw $s1,8($sp)
	sw $s2,12($sp)

	# save parameter in $s1
	move $s1,$a0

	# skip any whitespace found; then read next character
	jal skipWhite
	jal readChar
	move $t2,$v0

	# registers will be used as follows:
	# - $s0 = -1 if a '-' sign was seen, 1 otherwise
	# - $s1 = saved flag, telling whether to allow newlines as whitespace
	# - $s2 = the accumulated value as we are reading digits
	# - $t2 = the most recent character read

	# if the current character is a '-', gobble it up, and mark
	# the result as being negative.
	li $t3,'-'
	li $s0,1 # multliplication factor is 1, tenatively
	bne $t2,$t3,doneWithMinus
	jal readChar
	move $t2,$v0
	li $s0,-1 # multliplication factor is -1
	
doneWithMinus:	
	
	# test the first character for being a digit, return 0 (failure)
	# if not
	li $v1,0 # tentative return value
	li $s2,0 # start with 0 in building number
	li $t3,'0'
	blt $t2,$t3,failRi
	li $t3,'9'
	bgt $t2,$t3,failRi

	# We have a first digit in $t2. Enter loop to process digits.

readLoop:	
	# subtract the ASCII offset for '0' from the character, and add
	# it into the accumulator
	subu $t2,'0'
	addu $s2,$t2

	# read the next character, test for being digit
	jal readChar
	move $t2,$v0 # the character read
	li $v1,1 # tentative return value: status of "integer read"
	move $v0,$s2 # tentative return value: integer value
	li $t3,'0'
	blt $t2,$t3,negateVal
	li $t3,'9'
	bgt $t2,$t3,negateVal
	
	# multiply current value by 10, in anticipation of addition of
	# value for the digit we just read.
	mul $s2,$s2,10

	# loop back
	b readLoop

negateVal:
	# push back unused character; then multiply by negation factor
	move $a0,$t2
	jal pushBackChar
	mul $v0,$s2,$s0 # negate, if needed
	li $v1,1 # return status: found an integer

finishRi:	
	# epilog
	lw $ra,($sp)
	lw $s0,4($sp)
	lw $s1,8($sp)
	lw $s2,12($sp)
	addu $sp,16

	# return
	jr $ra

failRi:
	move $a0,$t2
	jal pushBackChar
	bgez $s0,skipPushBackMinus
	li $a0,'-'
	jal pushBackChar
skipPushBackMinus:	
	li $v0,0
	b finishRi

	################################################################
	# skipWhite -- skips whitespace
	# parameter:
	# - $a0:
	#   - 0 => do not treat newline as whitespace
	#   - 1 => treat newline as whitespace--that is, skip over it
	################################################################
skipWhite:	
	subu $sp,12
	sw $ra,($sp)
	sw $s0,4($sp)
	sw $s1,8($sp)
	
	# save parameter
	move $s1,$a0
	
loopSw:	
	jal readChar
	move $s0,$v0
	move $a0,$v0
	move $a1,$s1
	jal isWhitespace
	bnez $v0,loopSw
	
	move $a0,$s0
	jal pushBackChar
	
	lw $ra,($sp)
	lw $s0,4($sp)
	lw $s1,8($sp)
	addu $sp,12
	jr $ra

	################################################################
	# isWhitespace - tells whether a character represents whitespace
	# parameter:
	# - $a0: the character value. It is expected that the top three
	#   bytes are zero
	# - $a1: whether to consider a newline and carriage return
	#   characters to be whitespace
	# returns:
	# - $v0 = 1 if the character is whitespace, 0 otherwise
	# - $v1 = the character value tested
	#
	# Whitespace is considered to be codes 9 (tab), 10 (newline),
	# 13 (carriage return) and 32 (space).
	################################################################
isWhitespace:	

	# set tentative return values
	li $v0,1
	move $v1,$a0
	
	# if its one of our whitespace characters, to return
	li $t0,' '
	beq $a0,$t0,doneIws
	li $t0,9
	beq $a0,$t0,doneIws
	beqz $a1,noIws
	li $t0,10
	beq $a0,$t0,doneIws
	li $t0,13
	beq $a0,$t0,doneIws

	# not a whitespace character: put a 0 into the return-value
noIws:	
	li $v0,0
	
doneIws:
	# return
	jr $ra

	################################################################
	# readChar -- read a character from input
	# result: $v0 = the character read
	################################################################
readChar:
	# first check first element in the pushback queue
	lh $v0,charFirstOut
	li $t0,-1
	bltz $v0,noFirstRc
	sh $t0,charFirstOut
	jr $ra
	
noFirstRc:	
	# next, check the second element
	lh $v0,charFirstIn
	bltz $v0,noSecond
	sh $t0,charFirstIn
	jr $ra
noSecond:
	
	# queue is empty--need to do a syscall
	li $v0,12
	syscall
	jr $ra

	.data
	.align 1
charFirstOut:
	.half -1
charFirstIn:
	.half -1

	.text
	
	##############################################################
	# pushBackChar -- push a character back into the input queue
	# parameter:
	# - $a0: the character to push back (in low 8 bits)
	# result:
	# - $v0: the character pushed back (with high 24 bits set to 0)
	# The pushback queue has length 2, so if more than 2 characters
	# are pushed back, data will be lost.
	################################################################
pushBackChar:
	# mask of any high bits
	and $v0,$a0,0xff

	# first check the "first in" slot
	lh $t0,charFirstIn
	bgez $t0,noFirstPbc
	sh $v0,charFirstIn
	jr $ra

	# store the byte in "second in" slot
noFirstPbc:	
	sh $v0,charFirstOut
	jr $ra

	################################################################
	# peekChar -- peek at a character from input without consuming it
	#
	# The pushback queue has length 2, so if more than 2 characters
	# are pushed back, data will be lost.
	################################################################
peekChar:
	subu $sp,4
	sw $ra,($sp)
	
	# read character; then push it back
	jal readChar
	move $a0,$v0
	jal pushBackChar

	lw $ra,($sp)
	addu $sp,4
	jr $ra
