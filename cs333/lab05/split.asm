	.text
	#================ split function ================
split:
	# dummied up, simply return the argument as both return values
	# move $v0,$a0
	# move $v1,$a0

	# extract top 16 bits and then sign extend
	li $t0, 0xFFFF0000
	and $v0, $a0, $t0
	sra $v0, $v0, 16

	# extract bottom 16 bits and then sign extend
	li $t0, 0x0000FFFF
	and $v1, $a0, $t0
	sll $v1, $v1, 16
	sra $v1, $v1, 16
	
	# return
	jr $ra

	#================ sum function ================
sum:
	# dummied up, just returns argument unchanged
	move $v0,$a0

	# split the numbers
	jal split

	# add numbers
	add $v0, $v0, $v1

	# return
	jr $ra

	############################ WARNING #########################
	# CS 333 STUDENTS SHOULD NOT MODIFY ANY CODE BELOW THIS POINT#
	##############################################################
	
#================main program================	
main:	
	
	# prompt user
	la $a0,pleaseTypeHex
	jal printString

	# read hex number, save in $s2
	jal readHexNum
	move $s2,$v0

	# split number into two pieces
	move $a0,$v0
	jal split
	
	# save results
	move $s0,$v0
	move $s1,$v1

	# print first number
	la $a0,highHalf
	jal printString
	move $a0,$s0
	jal printInt
	jal printNewline

	# print second number
	la $a0,lowHalf
	jal printString
	move $a0,$s1
	jal printInt
	jal printNewline

	# compute sum, saving result in $s0
	move $a0,$s2
	jal sum
	move $s0,$v0

	# print sum
	la $a0,sumIs
	jal printString
	move $a0,$s0
	jal printInt
	jal printNewline

	# exit
	jal exit
	
#================ exit ================	
exit:
	li $v0,10
	syscall
	jr $ra # should never get here
	
#================ printInt ================
printInt:
	li $v0,1
	syscall
	jr $ra
	
#================ printString ================
printString:	
	li $v0,4
	syscall
	jr $ra
	
#================ printNewline ================
printNewline:	
	li $v0,4
	li $a0,newlineString
	syscall
	jr $ra

#================ readHexNum ================
readHexNum:

	# read a string
	li $v0,8
	la $a0,strBuffer
	la $a1,400
	syscall
	
	# initialize return value
	li $v0,0

	# iterate as long as we have a hex digit
readHexNumLoop:
	lbu $t0,($a0)
	lb $t0,hexDigitMap($t0)
	bltz $t0,outHexNumLoop
	sll $v0,4
	addu $v0,$t0
	addu $a0,1
	b readHexNumLoop
	
outHexNumLoop:	
	jr $ra


	.data
	
	# stringliterals
pleaseTypeHex:
	.asciiz "Please type a hex number: "
lowHalf:
	.asciiz "Low half: "
highHalf:
	.asciiz "High half: "
sumIs:
	.asciiz "The sum is: "
newlineString:
	.asciiz "\n"

	# space for readHexNum's string-buffer
strBuffer:
	.space 400
strBufferEnd:	
	
	# map of ascii characters to hex values
hexDigitMap:
	.byte -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
	.byte -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
	.byte -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
	.byte 0,1,2,3,4,5,6,7,8,9,-1,-1,-1,-1,-1,-1
	.byte -1,10,11,12,13,14,15,-1,-1,-1,-1,-1,-1,-1,-1,-1
	.byte -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
	.byte -1,10,11,12,13,14,15,-1,-1,-1,-1,-1,-1,-1,-1,-1
	.byte -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
	.byte -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
	.byte -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
	.byte -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
	.byte -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
	.byte -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
	.byte -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
	.byte -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
	.byte -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1
