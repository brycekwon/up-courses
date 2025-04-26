	.text

	################################################################
	# function to value the polynomial f(x); the coefficients of
	# f are double-precision floating-point values
	# - parameters:
	#   $a0 = pointer to a linked list of coefficients for f. These
	#         ordered from highest-order to lowest. For example, the
	#         linked list for the polynomial f(x) = 2x^2 - 4.5x + 8
	#         would be the list [2.0, -4.5, 8.0]
	#   $f12-13 = the value of x
	# - returns:
	#   $f0-1 = the evaluated polynomial, f(x)
	#
	# The gist of the algorithm is:
	#   double evalPoly(DoubleList f, double x) {
	#     if (f == null) return 0.0;
	#     return f.val + x * evalPoly(f.next, x);
	#   }
	################################################################
evalPoly:
	# dummied up--for not, just return the argument
	mov.d $f0,$f12
	
	################################################################
	# THIS FUNCTION SHOULD BE IMPLEMENTED RECURSIVELY, AS SHOWN ABOVE
	################################################################

	# return
	jr $ra

	################################################################
	# CS 333 Students should not modify below this line
	################################################################

main:	
	# prompt the user for the coefficients
	li $v0,4
	la $a0,pleaseEnterCoeffs
	syscall
	
	# read the list
	jal readDoubleList
	move $s0,$v0		# this line was missing (bug in the starter code)

	# prompt the user for the value
	li $v0,4
	la $a0,pleaseEnterVal
	syscall
	
	# read the argument
	li $a0,1
	jal readDouble

	# evaluate the polynomial
	mov.d $f12,$f0
	move $a0,$s0
	jal evalPoly

	# print result, newline
	la $a0,resultIs
	li $v0,4
	syscall
	mov.d $f12,$f0
	li $v0,3
	syscall
	li $a0,'\n'
	li $v0,11
	syscall
	
	# exit
	li $v0,10
	syscall

	################################################################
	# read a list of double from the keyboard:
	# $s0 = new list
	################################################################
readDoubleList:	

	# save registers
	subu $sp,8
	sw $ra,($sp)
	sw $s0,4($sp)

	# initialize our linked list to be NULL
	li $s0,0

	# enter loop
	b inLoop1
	
loop1:	
	# the just-read data is in $f0-1

	# allocate memory (8 bytes) for our list node
	li $a0, 12
	jal malloc

	# store our data in offset 0, list pointer in offset 8
	s.d $f0,($v0)
	sw $s0,8($v0)

	# update our list-pointer to be to the new node
	move $s0,$v0

inLoop1:	
	# read a double from the keyboard
	li $a0,0
	jal readDouble

	# if no more elements
	bnez $v0, loop1
	
	# set return value
	move $v0,$s0

	# restore registers and return
	lw $ra,($sp)
	lw $s0,4($sp)
	addu $sp,8
	jr $ra

	################################################################
	# function to print a ilst
	#   $a0 = list to print
	################################################################
printDoubleList:
	
	# skip print-loop if list is empty
	beq $a0, $zero, donePrint
	move $t0,$a0

loop2:	
	# print the current element of the loop
	l.d $f12,($t0)
	li $v0,3
	syscall

	# print a newline character
	li $v0,11
	li $a0, '\n'
	syscall

	# move to next element
	lw $t0,8($t0)

	# if not null, branch back
	bne $t0,$zero,loop2
	
donePrint:
	jr $ra

	########### don't touch the code below--it handles heap allocation ##########
	.data
	
pleaseEnterVal:	
	.asciiz "Value to evaluate at: "
pleaseEnterCoeffs:	
	.asciiz "Coefficient values high-to-low degree, separated by spaces: "
resultIs:
	.asciiz "The result is "
dashes:	
	.asciiz "====================\n"
noFlagMsg:
	.ascii "no "
flagMsg:
	.asciiz "termination flag\n"
	
	################################################################
	# data portion
	################################################################

	.data
	.align 2
zeroDouble:	
	.double 0.0
twoDouble:	
	.double 2.0
oneDouble:
	.double 1.0
minusOneDouble:
	.double -1.0
tenDouble:
	.double 10.0
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
	################################################################
	# the heap-allocation function
	################################################################
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
	# readDouble - reads a double from the keyboard
	# parameter:
	# - $a0:
	#   - 0 => do not treat newline characters as whitespace
	#   - 1 => treat newline characters as whitespace
	# returns:
	# - $f0-2 - the double read, if any
	# - $v0 - the return status
	#   - 0 => a double was not read successfully
	#   - 1 => a double was read successfully
	# 
	# This function skips over whitespace: spaces, tabs and, depending
	# on the second parameter, newline and carriage return characters.
	#
	# This function does not check for overflow, so the if the number's
	# magnitude is too large, the result will likely be infinity or
	# minus infinity.
	#
	################################################################
readDouble:
	
	# prolog
	subu $sp,32
	sw $ra,($sp)
	sw $s0,4($sp)
	sw $s3,8($sp)
	sw $s4,12($sp)
	sw $s5,16($sp)
	sw $s6,20($sp)
	s.d $f20,24($sp)

	# skip whitespace
	# (newline-action parameter is already in $a0)
	jal skipWhite
	
	# read the first character
	jal readChar

	# registers will be used as follows:
	# - $f0-1 = the accumlated value as we are reading the digits
	# - $v0 = the most recent character read
	# - $s3 = 0 if a minus sign was seen; non-zero otherwise

	# if the current character is a '-', gobble it up, and mark
	# the result as being negative.
	subu $s3,$v0,'-'
	bnez $s3 doneWithMinus
	jal readChar
	
	################################################################
	# after the reading loop:
	# - $s4 will be the $sp address before any numbers were pushed
	# - $s0 will be the $sp address just after the '.' is seen
	#   - if there was not a binary point, it will be the $sp
	# This means that
	# - the digit values to the left of the decimal point will be on
	#   the stack from the $s0 location down to (but not including) the
	#   $s4 location
	# - the digit values to the right of the decimal point will be on
	#   the stack from the $s4 location down to (but not including) the
	#   $s0 location
	################################################################

doneWithMinus:	
	# initialize $s0 to be the current $sp value
	move $s0,$sp

	# initialize $s4 to be 0, indicating that a decimal point has
	# not yet been seen
	li $s4,0

	# initialize some helper-constants
	li $s5,'.'
	li $s6,9

rdLoop1:	
	
	# test the character for being a '.'
	bne $v0,$s5,notDot

	# we have a '.', so:
	# - if we've already seen a '.', break out and go to the next
	#   step, which is popping the values off the stack and creating
	#   the result
	# - if we have not already seen a '.', update $s4 with the current
	#   $sp value
	bnez $s4,doneRdLoop1
	move $s4,$sp
	b rdReadChar
	
notDot:	
	# test the character for being a digit; drop out of loop if not
	subu $t2,$v0,'0'
	bgtu $t2,$s6,doneRdLoop1

	# we have a digit, so push its value on the stack
	subu $sp,4
	sw $t2,($sp)

rdReadChar:	
	
	# read the next character and loop back
	jal readChar
	b rdLoop1

doneRdLoop1:	
	# push the last character back into the input queue
	move $a0,$v0
	jal pushBackChar

	# If $s0 is equal to $sp, it means that we did not read
	# any digits--in other words, we did not read a legal number.
	# If this is the case, go return an error status
	beq $s0,$sp,finishRdNoGo

	# if $s4 is still 0, set it to the $sp value to indicate no
	# digits after the decimal point
	bnez $s4,skipRd1
	move $s4,$sp
skipRd1:	

	################################################################
	# At this point, we've run out of characters to read, and:
	# - $v0 contains the last character that was read
	# - $s4 contains the address of the topmost element of the stack
	#   with a digit value before the decimal point
	# - $s0 contains the address of the topmost element of the stack
	#   with a digit value after the decimal point
	################################################################

	################################################################
	# loop to compute the fractional part of the result
	################################################################
	
	# initialize result to be 0.0, $f8 to be 10.0
	l.d $f0,zeroDouble
	l.d $f8,tenDouble

	beq $s4,$sp,doneSmallRd
	
rdLoop2:	
	l.s $f4,($sp)	
	addu $sp,4
	cvt.d.w $f4,$f4
	add.d $f0,$f0,$f4
	div.d $f0,$f0,$f8

	bne $s4,$sp,rdLoop2

doneSmallRd:	

	# initialize our multliplier to 1.0
	l.d $f6,oneDouble

	beq $s0,$sp,doneLargeRd
rdLoop3:	
	l.d $f4,($sp)
	addu $sp,4
	cvt.d.w $f4,$f4
	mul.d $f4,$f4,$f6
	mul.d $f6,$f6,$f8
	add.d $f0,$f0,$f4

	bne $s0,$sp,rdLoop3
	
doneLargeRd:

	# multiply by possible negation factor, if needed
	bnez $s3,finishRd
	l.d $f2,minusOneDouble
	mul.d $f0,$f0,$f2

finishRdGo:	
	# mark that double was read successfully
	li $v0,1

finishRd:	
	# epilog
	lw $ra,($sp)
	lw $s0,4($sp)
	lw $s3,8($sp)
	lw $s4,12($sp)
	lw $s5,16($sp)
	lw $s6,20($sp)
	l.d $f20,24($sp)
	addu $sp,32

	# return
	jr $ra

	# mark that we did not successfully read the double
finishRdNoGo:	
	li $v0,0
	b finishRd

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
	
