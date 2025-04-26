	.text

# ================ listLength ================
	################################################################
	# determine the length of a list
	# $a0: (possibly null) pointer to the list's first element
	# upon return:
	#   $v0 contains the length of the list
	################################################################
listLength:
	li $v0, 0

loop:
	beq $a0, $zero, done
	addi $v0, $v0, 1
	lw $a0, 4($a0)

done:
	# return
	jr $ra

	###########################################################
	# CS 333 students should not modify any of the code below #
	###########################################################
	
# ================ main program ================
main:	
	
	# ask for random number seed ; initialize seed
	la $a0,pleaseSeed
	jal printString
		.data
		pleaseSeed:
		.asciiz "Please type the random number seed: "
		.text
	jal readInt
	move $a0,$v0
	jal ranInit

	# ask user how long list should be
	la $a0,pleaseLength
	jal printString
		.data
		pleaseLength:
		.asciiz "List length: "
		.text
	jal readInt

	# create random list of the appropriate length
	move $a0,$v0
	li $a1,-1000
	li $a2,1000
	jal makeRandomList
	move $s0,$v0 # save the list
		
	# compute the length
	move $a0,$s0
	jal listLength
	move $s1,$v0 # save the length
	
	# print the full list, and dashes
	li $a0,listIs
		.data
		listIs:
		.asciiz "-------- the list is --------\n"
		.text
	jal printString
	move $a0,$s0
	jal printIntList
	la $a0,dashesLineString
	jal printString
	
	# print the length
	li $a0,theLengthIs
		.data
		theLengthIs:
		.asciiz "The list length is "
		.text
	jal printString
	move $a0,$s1
	jal printInt
	jal printNewline
	
	# exit
	jal exit
	
# ================ ranInit ================
	# initialize random-number seed
	# parameter 1: seed
ranInit:	
	subu $sp,4
	sw $ra,($sp)
	sw $a0,randomSeed
	jal churnSeed
	lw $ra,($sp)
	addu $sp,4
	jr $ra
	
# ================ churnSeed ================
	# churn the seed
	# (no parameters)	
churnSeed:	
	lw $t0,randomSeed
	lw $t1,randomSeed+4
	
	# top byte, first word
	srl $t2,$t0,24
	sll $t2,$t2,3
	lw $t3,hashArray($t2)
	lw $t4,hashArray+4($t2)
	xor $t0,$t3
	xor $t1,$t4
	
	# top byte, second word
	srl $t2,$t1,24
	sll $t2,$t2,3
	lw $t3,hashArray($t2)
	lw $t4,hashArray+4($t2)
	xor $t0,$t3
	xor $t1,$t4
	
	# second-to-top byte, first word
	sll $t2,$t0,8
	srl $t2,$t2,24
	sll $t2,$t2,3
	lw $t3,hashArray($t2)
	lw $t4,hashArray+4($t2)
	xor $t0,$t3
	xor $t1,$t4
	
	# second-to-top byte, second word
	sll $t2,$t1,8
	srl $t2,$t2,24
	sll $t2,$t2,3
	lw $t3,hashArray($t2)
	lw $t4,hashArray+4($t2)
	xor $t0,$t3
	xor $t1,$t4
	
	# third-to-top byte, first word
	sll $t2,$t0,16
	srl $t2,$t2,24
	sll $t2,$t2,3
	lw $t3,hashArray($t2)
	lw $t4,hashArray+4($t2)
	xor $t0,$t3
	xor $t1,$t4
	
	# third-to-top byte, second word
	sll $t2,$t1,16
	srl $t2,$t2,24
	sll $t2,$t2,3
	lw $t3,hashArray($t2)
	lw $t4,hashArray+4($t2)
	xor $t0,$t3
	xor $t1,$t4
	
	# low byte, first word
	and $t2,$t0,0xff
	sll $t2,$t2,3
	lw $t3,hashArray($t2)
	lw $t4,hashArray+4($t2)
	xor $t0,$t3
	xor $t1,$t4
	
	# low byte, second word
	and $t2,$t1,0xff
	sll $t2,$t2,3
	lw $t3,hashArray($t2)
	lw $t4,hashArray+4($t2)
	xor $t0,$t3
	xor $t1,$t4
	
	sw $t0,randomSeed
	sw $t1,randomSeed+4
	
	jr $ra
	
# ================ ran ================
	# random 32-bit number
	# $a0: number to mod result by (or no mod, if zero)
ran:
	subu $sp,8
	sw $ra,($sp)
	sw $a0,4($sp)
	jal churnSeed
	lw $v0,randomSeed
	lw $t0,randomSeed+4
	xor $v0,$t0
	lw $t0,4($sp)
	beqz $t0,ranDone
	divu $v0,$t0
	mfhi $v0
ranDone:	
	lw $ra,($sp)
	addu $sp,8
	jr $ra	
	
	.text
	
# ================ makeRandomList ================
	# makes a list of random integers
	# $a0 - length of list to generate
	# $a1 - minimum number to generate
	# $a2 - maximum number to generate
makeRandomList:	

	# prolog
	subu $sp,20
	sw $ra,($sp)
	sw $s0,4($sp) # list pointer
	sw $s1,8($sp) # size of random number range
	sw $s2,12($sp) # first random number in range
	sw $s3,16($sp) # count

	# range of random numbers
	move $s1,$a2
	subu $s1,$a1
	addu $s1,1
	
	# first random number in range
	move $s2,$a1

	# count
	move $s3,$a0

	# current list-pointer (initially null)
	li $s0,0
	
	b inLoop1
loop1:
	# generate next random number (range -1000 to 1000)
	move $a0,$s1
	jal ran
	addu $a0,$v0,$s2 # argument for newListNode
	
	# allocate and link in list node
	move $a1,$s0 # current head is next next-pointer
	jal newListNode
	move $s0,$v0
	
	# decrement counter, and loop back unless down to zero
	subu $s3,1
inLoop1:	
	bgtz $s3,loop1

	# return value
	move $v0,$s0
	
	# epilog
	lw $s3,16($sp) # count
	lw $s2,12($sp) # first random number in range
	lw $s1,8($sp) # size of random number range
	lw $s0,4($sp) # list pointer
	lw $ra,($sp)
	addu $sp,20
	jr $ra

	.text

#================ newListNode ================	
	# create a new list node on the heap
	# $a0: data, with which to initialize first slot
	# $a1: next-pointer, with which to intialize second slot
newListNode:	
	
	# allocate space for list-node
	subu $sp,4
	sw $a0,($sp)
	li $a0,8 # number of bytes to allocate
	la $v0,malloc
	jalr $v0,$v0
	lw $a0,($sp)
	addu $sp,4
	
	# insert node at front of list
	sw $a0,($v0) # store data into data-slot
	sw $a1,4($v0) # store list-pointer into next-slot
	
	# return
	jr $ra

#================ exit ================	
	# exits the program
exit:
	li $v0,10
	syscall
	jr $ra # should never get here
	
#================ printInt ================
	# prints an integers
	# $a0 - the integer to print
printInt:
	li $v0,1
	syscall
	jr $ra
	
#================ printIntList ================
	# prints a list of integer, one per line
	# $a0 - pointer to first node in list	
printIntList:
	# prolog
	subu $sp,8
	sw $ra,4($sp)
	sw $s2,($sp)
	
	# do nothing if the list is empty
	beqz $a0,donePrintIntList

	# put head-pointer into $s2
	move $s2,$a0

printIntListLoop:	
	# print current element
	lw $a0,($s2)
	jal printInt
	jal printNewline
	
	# go to next element, and loop back if not null
	lw $s2,4($s2)
inPrintIntListLoop2:	
	bnez $s2,printIntListLoop

	# epilog
donePrintIntList:	
	lw $s2,($sp)
	lw $ra,4($sp)
	addu $sp,8
	jr $ra
	
#================ readInt ================
	# reads an integer from the keyboard
readInt:
	li $v0,5
	syscall
	jr $ra
	
#================ printString ================
	# prints a string
	# $s0 - pointer to the string
printString:	
	li $v0,4
	syscall
	jr $ra
	
#================ printNewline ================
	# prints a newline character
printNewline:	
	li $v0,4
	li $a0,newlineString
	syscall
	jr $ra

	.data
	
	# ensure that we're word-aligned
	.align 2	
	
	# word to keep track of next available heap location
currentHeapPointer:
	.word heapStart
	
	# the heap space
heapStart:
	.space 100000
heapEnd:

	.data
newlineString:
	.asciiz "\n"
dashesLineString:
	.asciiz "-------------------------------------\n"
	
	.text
	# the heap-allocation function
malloc:
	# open up two words on stack ;  save $v0 and $t0
	subu $sp,8
	sw $v0,4($sp) # return address
	sw $t0,($sp)
	
	# load return value into $v0
	lw $v0,currentHeapPointer
	
	# bump heap pointer by byte-size object, rounded up to next
	# multiple of 4
	addu $t0,$v0,$a0
	addu $t0,$t0,3
	and $t0,$t0,0xfffffffc
	sw $t0,currentHeapPointer
	
	# restore $t0, and return
	lw $t0,($sp)
	lw $at,4($sp) #return address (yes, it's safe to use $at here)
	addu $sp,8
	jr $at


	.data

	.align 2
	
	.data
	# our random number seed (two words)
randomSeed:
	.word 0,0x8c8fa301

# these random numbers were generated by random.org
hashArray:	
	.word 0x68d83cd8
	.word 0xb216bc19
	.word 0x8c050f9d
	.word 0xa502cdd8
	.word 0xb444d106
	.word 0x3aa083d5
	.word 0xd68e938f
	.word 0xcf9ef151
	.word 0x4c1a881b
	.word 0xe9d7a706
	.word 0x8e651c3a
	.word 0x8b141dfa
	.word 0xd087c326
	.word 0xce61852c
	.word 0x4e465ab4
	.word 0x9bed6d91
	.word 0xd4c7b322
	.word 0x4ecab3a9
	.word 0x56df86e0
	.word 0xbcb16622
	.word 0x8d41530f
	.word 0x634a6ca0
	.word 0x4c0abf22
	.word 0x8885926f
	.word 0x66461394
	.word 0x2e99b09b
	.word 0xfc46579d
	.word 0xb6e0ab63
	.word 0x01a51024
	.word 0x71f69d1d
	.word 0x788c8ff9
	.word 0x4ec9db47
	.word 0xe7a647dc
	.word 0xed306b1b
	.word 0x226b5b82
	.word 0xaca82de9
	.word 0x5ef41e2c
	.word 0xe86452bd
	.word 0x20541d7f
	.word 0xb81bd816
	.word 0x63c1db39
	.word 0x884360a8
	.word 0xf09db6f3
	.word 0x8b6d3501
	.word 0x6047c391
	.word 0x65a921d2
	.word 0x6becd04d
	.word 0x917042a1
	.word 0x2f73291b
	.word 0x9c378989
	.word 0x9dd71cb5
	.word 0x73f109ea
	.word 0x792a26e8
	.word 0xf047b241
	.word 0x10b5198f
	.word 0x74ef5c01
	.word 0x5efa40cb
	.word 0x6e3a02ff
	.word 0x6842f271
	.word 0x2b62a578
	.word 0x7856d982
	.word 0xb489cb08
	.word 0x6ec9096b
	.word 0xc98386e6
	.word 0x0fd8b2e5
	.word 0x8a4d7f35
	.word 0x563bb4aa
	.word 0x08e4295c
	.word 0xa0ef9f1b
	.word 0x7698d57b
	.word 0xff02ab89
	.word 0xc6eb3a82
	.word 0xbca07d0d
	.word 0x83704ab7
	.word 0xed515592
	.word 0x22aed340
	.word 0xb7e3a5f6
	.word 0x6fbc615d
	.word 0x44017ffd
	.word 0x44c633c1
	.word 0xa9a4ab37
	.word 0x321468f7
	.word 0x840d6568
	.word 0x0eca3b12
	.word 0xb64a0104
	.word 0xbb9b3fd6
	.word 0x3ecb5844
	.word 0x3fac0c7d
	.word 0x81144dab
	.word 0xd7435b69
	.word 0x784690fc
	.word 0xd254c7bd
	.word 0xa5713183
	.word 0x80ab53bf
	.word 0xe60cac64
	.word 0x9fd3eccd
	.word 0xe93d84ae
	.word 0x5b26dfca
	.word 0x90d5af01
	.word 0x64a7ad13
	.word 0x8d2ee9e4
	.word 0xea154c65
	.word 0xef94a8a8
	.word 0x75d80f46
	.word 0xc3374629
	.word 0x70080dce
	.word 0x43ae88b0
	.word 0x162c1b00
	.word 0x09116540
	.word 0xc2166b0f
	.word 0x0ca971ea
	.word 0x33f63181
	.word 0xf138adac
	.word 0x9aeeb5ce
	.word 0xd25d4a1e
	.word 0xdf813922
	.word 0x1832f063
	.word 0xe8f11c5e
	.word 0x9dca0e55
	.word 0x1302cc61
	.word 0xdc2874a1
	.word 0x6923f1c7
	.word 0x28ef0890
	.word 0xd8d17eb7
	.word 0xa53965f3
	.word 0x77e42f01
	.word 0x4b3a6915
	.word 0x4f0855a7
	.word 0xe262817b
	.word 0xdd363a6a
	.word 0x2ef0c591
	.word 0x230fa259
	.word 0x8ca49cf0
	.word 0x70ce073b
	.word 0x821ff069
	.word 0x153dbadc
	.word 0x3e50beb9
	.word 0x6bf05155
	.word 0x8e6a2948
	.word 0xb86fb877
	.word 0xdb8f0f51
	.word 0x61ea0d5b
	.word 0x06bd4444
	.word 0xe21d5e2e
	.word 0x38101690
	.word 0x542e7eb1
	.word 0x6732f0b0
	.word 0x1fc35e7c
	.word 0xdf71a883
	.word 0x96f03011
	.word 0xb363d07d
	.word 0xb3c7223e
	.word 0x1b57d357
	.word 0xe5625f1c
	.word 0x275d26ce
	.word 0xfd1d7b95
	.word 0x03b693d8
	.word 0xf024f185
	.word 0x2461a0bf
	.word 0x42be42f2
	.word 0x413a144a
	.word 0x43bcf361
	.word 0x638d92c8
	.word 0x54764306
	.word 0x5937c11d
	.word 0x6e76e8c3
	.word 0x8acb0ad4
	.word 0x9285e4ff
	.word 0x8cb0c56a
	.word 0x5556698f
	.word 0xbb164bf8
	.word 0xce6ae588
	.word 0x09553c23
	.word 0xf84ebfe5
	.word 0x15af63a0
	.word 0xed76ab41
	.word 0x7d8f98a7
	.word 0x4c6f9b4b
	.word 0x07ae2e01
	.word 0xc021dfc9
	.word 0x52505b4c
	.word 0x2f54fca2
	.word 0xb3ec80e1
	.word 0x42e04243
	.word 0x55abf4ef
	.word 0x00eaafcb
	.word 0x921e33f6
	.word 0xf6aa8d83
	.word 0x721654e1
	.word 0xb455b90e
	.word 0xe09afd49
	.word 0x0d05ac84
	.word 0x8a9a6332
	.word 0xd244a269
	.word 0x9aafd204
	.word 0x77572940
	.word 0x6d98b066
	.word 0x4e37e596
	.word 0xe8afcefa
	.word 0x08dfef9c
	.word 0xf81b0fec
	.word 0x99b152c8
	.word 0x29d0f451
	.word 0x5c7e51bd
	.word 0x7d8829df
	.word 0x7a68a8d9
	.word 0xd9682fc6
	.word 0xeae98e99
	.word 0xdfe11dac
	.word 0x016f6c5f
	.word 0xf9d88942
	.word 0x2f59e88b
	.word 0x2eb83f8d
	.word 0x67693806
	.word 0x6ebc51e5
	.word 0x0ea3f53d
	.word 0x5ceab202
	.word 0x35d6f9a1
	.word 0xb8007980
	.word 0xcb2eba68
	.word 0x2b3a6651
	.word 0x119159b0
	.word 0x3902572d
	.word 0x0f1eebd6
	.word 0x41d0070f
	.word 0x7a0f5daf
	.word 0x4fbc66e5
	.word 0xa4b4e403
	.word 0xe0aadedc
	.word 0xb08de46a
	.word 0xbedfc37a
	.word 0x9aa4e7bd
	.word 0x2f4cfb27
	.word 0x5130286e
	.word 0xbe3c52bf
	.word 0x30c69ed2
	.word 0x5ad759ce
	.word 0x34567e0f
	.word 0x1fce98b8
	.word 0x408d2b01
	.word 0x7cb84d5c
	.word 0x9c3bb953
	.word 0xf8186bdd
	.word 0xfdd4091a
	.word 0x661930b1
	.word 0xbf22dca9
	.word 0x56f2cc13
	.word 0x619a8ec7
	.word 0xde2b8e9a
	.word 0x560615c5
	.word 0x1513dbe4
	.word 0xbebd1ce7
	.word 0x582fc312
	.word 0xea9919e6
	.word 0xf2481ca2
	.word 0xebd657b0
	.word 0xf7d5ae4f
	.word 0x42b3ae88
	.word 0x0185dd9c
	.word 0x7ee8075f
	.word 0xe90e1c42
	.word 0x5285ac82
	.word 0x63db27f9
	.word 0xaf6c7833
	.word 0x5dc6d212
	.word 0x1ef49445
	.word 0xa89edc27
	.word 0x4c76240c
	.word 0x60510ccb
	.word 0xec8f8794
	.word 0xe9054edc
	.word 0x77c206be
	.word 0xf51a7129
	.word 0x46e4b46d
	.word 0x80afdf5b
	.word 0xcf39519b
	.word 0x2f9bf9c4
	.word 0xa9158259
	.word 0x7a0e028e
	.word 0x33dad7ff
	.word 0x09f7c232
	.word 0xe58a82aa
	.word 0x84b97ed0
	.word 0x162da6e8
	.word 0x8fef8d43
	.word 0x7da6a9ee
	.word 0xd68ea86e
	.word 0x89f41192
	.word 0xb4860ad0
	.word 0x1346c376
	.word 0x38163f1c
	.word 0x502b54fb
	.word 0x2ec0d3f7
	.word 0x5904de99
	.word 0xacd74117
	.word 0x2c13e3ff
	.word 0xe1b7f497
	.word 0x6e007534
	.word 0x6cad431d
	.word 0x97ec267a
	.word 0x5c0053e2
	.word 0xbd5c574b
	.word 0xff166d6f
	.word 0x71477776
	.word 0x56b932d9
	.word 0x8b6f1fe7
	.word 0xc096431a
	.word 0x738e5ca8
	.word 0x19be4e5c
	.word 0x870b1354
	.word 0x9bc7c23f
	.word 0x635aec2e
	.word 0xcb6cfada
	.word 0xa0ded18c
	.word 0x0b89c0c2
	.word 0x0a607e7b
	.word 0xe5198cc3
	.word 0x345c2dc5
	.word 0x7e47e256
	.word 0xa985d7b3
	.word 0x61053bc4
	.word 0x6fb6ec62
	.word 0x4cd1a9a2
	.word 0x643b7ce1
	.word 0x4d70e612
	.word 0x0ca70d45
	.word 0xe4bda2cf
	.word 0x32a229de
	.word 0x9febbd3b
	.word 0x8814f922
	.word 0x3fd87a85
	.word 0x593d69f8
	.word 0x3672e064
	.word 0x6c47ccbb
	.word 0xf970349f
	.word 0x961796dc
	.word 0x1f7361a7
	.word 0x1f765667
	.word 0xc6ac63ec
	.word 0x2c674ed8
	.word 0x85aeab95
	.word 0x10062024
	.word 0x96a0df37
	.word 0x13d01da5
	.word 0xfdf3f412
	.word 0x182532a7
	.word 0x16cc6ee4
	.word 0xf1dcec4a
	.word 0x6850d3eb
	.word 0xfab18f9b
	.word 0xe94935cd
	.word 0xf5208172
	.word 0x7437eabf
	.word 0xa10b4648
	.word 0x752ec5b0
	.word 0x55c90771
	.word 0x45b849ff
	.word 0x349573c9
	.word 0x883d83f8
	.word 0xdada3349
	.word 0x420d933f
	.word 0xe2bfabbb
	.word 0x4a3c7764
	.word 0xc4196cce
	.word 0x72be3d73
	.word 0xd121f6b8
	.word 0xf814b415
	.word 0xfb1150c5
	.word 0xe7d77980
	.word 0xfe1eff7a
	.word 0x049b9dd6
	.word 0x39e07c9b
	.word 0x53d646e6
	.word 0x678113bd
	.word 0x1a709b79
	.word 0x7f1a64a8
	.word 0xb4900210
	.word 0xb6497526
	.word 0xee09fa86
	.word 0x69e2b9eb
	.word 0xf6a50d0d
	.word 0xbe55f13e
	.word 0xea710a1a
	.word 0x19b94c98
	.word 0x9368afb1
	.word 0x69d9b52d
	.word 0x91054e4d
	.word 0x3216e4a3
	.word 0x607af1cf
	.word 0x5c768e81
	.word 0x814845b1
	.word 0xb0e378a3
	.word 0x6a9e0bb3
	.word 0xe90a31e6
	.word 0x4ef53fa7
	.word 0x20049490
	.word 0x5f1c4109
	.word 0x6ff95ec2
	.word 0x6cf83500
	.word 0xd13c1d79
	.word 0x174bb7f5
	.word 0xe058a780
	.word 0x2d519fae
	.word 0xbcf26431
	.word 0x85e532b3
	.word 0x5dc398c1
	.word 0xcec7fa6c
	.word 0x544fae45
	.word 0xbecdf75d
	.word 0x92b55e64
	.word 0x8dc1b13a
	.word 0xa4b53576
	.word 0xdf95acee
	.word 0x54ec9962
	.word 0x12b34356
	.word 0x2ebb3431
	.word 0xc86489f3
	.word 0x0620e2f5
	.word 0x47116a49
	.word 0xd8c48054
	.word 0x3a946863
	.word 0x5020fe6e
	.word 0x13e374a4
	.word 0x13fd0b9c
	.word 0xb0926723
	.word 0xa1e24386
	.word 0x5c038c11
	.word 0xdfbeed20
	.word 0x4f3b0bbc
	.word 0x5a7b3d19
	.word 0x8afdcbd2
	.word 0x40cb518c
	.word 0xfba11e4d
	.word 0x965d2e64
	.word 0x99e34f4b
	.word 0x1cc948de
	.word 0x4ed5ef87
	.word 0x83d2b9a2
	.word 0xbdf6d9e4
	.word 0x98ed165f
	.word 0x9df71ff0
	.word 0x79224f3e
	.word 0xeec03406
	.word 0x46d26b02
	.word 0x30057bfa
	.word 0x37baf62d
	.word 0xd5c0b658
	.word 0x9821d442
	.word 0x7054f2b1
	.word 0x9a163580
	.word 0x8292df53
	.word 0xfb0fda88
	.word 0x29b05e7b
	.word 0x4fd186d0
	.word 0x57f9977b
	.word 0x0dc00a41
	.word 0x7771e3d2
	.word 0x9662c6ca
	.word 0xcedde4c5
	.word 0x6c1eef9b
	.word 0x91d857a5
	.word 0xbe7fac5e
	.word 0xe795f3b7
	.word 0x9ded7262
	.word 0x7262c9d6
	.word 0xc8d1ef69
	.word 0x959ebb48
	.word 0x2f139a3c
	.word 0x86c0cb48
	.word 0x70d98e69
	.word 0xb82c07d1
	.word 0xd47d5659
	.word 0xe34de63f
	.word 0x4525d033
	.word 0x3b8a7641
	.word 0x70a20500
	.word 0xf761056d
	.word 0xb87f42bf
	.word 0xf57c58f1
	.word 0x6b6b32ee
	.word 0xd97d9d48
	.word 0xbd70f78e
	.word 0xea4cd7df
	.word 0x95b2b8b5
	.word 0xcfe1f04e
	.word 0xec09460e
	.word 0x1d788e55
	.word 0x05061ea1
	.word 0x4156b583
	.word 0x2ea69994
	.word 0xa82f1bc1
	.word 0x961ad459
	.word 0xab47b453
	.word 0x66e9c660
	.word 0x26f680c6
	.word 0xf63d006f
	.word 0xf877fa1d
	.word 0x4802bf64
	.word 0x0a12c61c
	.word 0x8ea9e054
	.word 0x9d7a13d9
	.word 0x3360965c
	.word 0x8621dd83
	.word 0xd7e6ec55
	.word 0xb8d666c9
	.word 0xd0886910
	.word 0xb7c59516
	.word 0x873761df
	.word 0xbf15e888
	.word 0x5f1cee12
	.word 0x4b712125
	.word 0x87ed0785
