	.text
	
	################################################################
	# prints a list of list of integer
	#   $a0 = pointer to list
	################################################################
printIntListOfList:	

	beqz $a0, done

	subu $sp, $sp, 4
	sw $a0, ($sp)

	lw $a0, ($a0)
	
	jal printIntList

	li $v0, 11
	li $a0, '\n'
	syscall

	lw $a0 ($sp)
	addu $sp, $sp, 4

	addu $a0, $a0, 4
	lw $a0, ($a0)

	b printIntListOfList
	
	# return
	jr $ra
	
	################################################################
	# function to print a list of integer
	#   $a0 = pointer to list
	################################################################
printIntList:

	subu $sp, $sp, 4
	sw $a0, ($sp)

	lw $a0, ($a0)
	li $v0, 1
	syscall

	li $v0, 11
	li $a0, ' '
	syscall

	lw $a0 ($sp)
	addu $sp, $sp, 4

	addu $a0, $a0, 4

	lw $a0, ($a0)

	bnez $a0, printIntList
	
	# return
	jr $ra

main:	
	# prompt the user
	li $v0,4
	la $a0,pleaseEnter
	syscall
	
	# initialize the list of lists
	li $s0,0

	jal readIntListOfList

	move $a0,$v0
	jal printIntListOfList
	
done:	
	################################################################
	# exit the program
	################################################################
	# exit
	li $v0,10
	syscall
	
	################################################################
	# reads a list of list of integer
	################################################################
readIntListOfList:	
	# save registers
	subu $sp,12
	sw $ra,($sp)
	sw $s0,4($sp)
	sw $s1,8($sp)

	# initialize list
	li $s0,0
	
	# jump into list-reading loop
	b enterRilol
	
loopRilol:	
	move $s1,$v0

	li $a0,8
	jal malloc

	sw $s1,($v0)
	sw $s0,4($v0)
	move $s0,$v0

	# skip the presumed newline character
	jal readChar

enterRilol:	
	jal readIntList
	and $t0,$v0,1
	beqz $t0,loopRilol
	
	move $a0,$s0
	jal reverseList

	# restore registers and return
	lw $ra,($sp)
	lw $s0,4($sp)
	lw $s1,8($sp)
	subu $sp,12
	jr $ra

	################################################################
	# function to read a list of integer, terminating with a newline
	# or non-whitespace character
	#
	# returns:
	# - $v0 = the list read, in order of reading, or -1 if no list
	################################################################
readIntList:
	
	# save registers
	subu $sp,12
	sw $ra,($sp)
	sw $s0,4($sp)
	sw $s1,8($sp)
	
	# skip any whitespace
	li $a0,0
	jal skipWhite

	# peek at the next character, returning -1 if '.'
	jal peekChar
	li $t0,'.'
	move $t1,$v0
	li $v0,-1 # in case we see '.'
	beq $t0,$t1,doneRil
	
mainRil:	
	# initialize return value to be empty list
	li $s0,0

	b enterLoopRil

loopRil:	
	# allocate list node
	move $s1,$v0 # save integer
	li $a0,8
	jal malloc

	# store data in list node; prepend to list
	sw $s1,($v0)
	sw $s0,4($v0)
	move $s0,$v0

enterLoopRil:	
	# attempt to read an integer
	li $a0,0
	jal readInteger

	# if integer was read, loop back, add to list and read another
	bnez $v1,loopRil

	# reverse the list
	move $a0,$s0
	jal reverseList

doneRil:	
	# restore registers
	lw $ra,($sp)
	lw $s0,4($sp)
	lw $s1,8($sp)
	addu $sp,12

	# return
	jr $ra

	################################################################
	# function to reverse a list (destructively)
	# parameter:
	# - $a0 = pointer to list to reverse
	# returns:
	# - $v0 = reversed list
	################################################################
reverseList:
	# initialize the return-list to be the empty list
	li $v0,0 # new list
	
	# finish if the list is empty
	beqz $a0,doneRl
	
loopRl:	
	move $t0,$v0
	move $v0,$a0
	lw $a0,4($v0)
	sw $t0,4($v0)
	bnez $a0,loopRl

doneRl:	
	jr $ra

	########### don't touch the code below--it handles heap allocation ##########
	.data
	
pleaseEnter:	
	.ascii "Please enter lists of integers (separated by spaces),\n"
	.asciiz "one per line. Terminate with a '.' character:\n"
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
