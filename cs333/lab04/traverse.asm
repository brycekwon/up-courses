	.text
main:	

	# prompt user and read list number
	la $a0,indexPlease
	li $v0,4
	syscall
	li $v0,5
	syscall

	# check for out-of-bounds index
	blt $v0,$zero,badIndex
	li $t0,10
	bge $v0,$t0,badIndex
	
	# get list header from memory array
	sll $t0,$v0,2
	lw $t0,headers($t0)
	
	# if the pointer is null, go exit
	beq $t0,$zero,done
	
	# print data from first list element
	#lw $a0,($t0)
	#li $v0,1
	#syscall
	#la $a0,newline
	#li $v0,4
	#syscall

loop:
	beq $t0,$zero,done

	lw $a0,($t0)
	li $v0,1
	syscall
	la $a0,newline
	li $v0,4
	syscall
	
	addu $t0, $t0, 4
	lw $t0, ($t0)

	b loop
	
	# exit
done:	
	li $v0,10
	syscall

badIndex:
	la $a0,badIndexMsg
	li $v0,4
	syscall
	b done

	.data
	
	# statically-allocated linked list
	.align 2
node9:	.word 27,node10
node1:	.word -5,node2
node3:	.word 44,node4
node6:	.word -76,node7
node4:	.word 6,node5
node8:	.word 24,node9
node12:	.word 512,0
node10:	.word 100,node11
node0:	.word -9,node1
node2:	.word 17,node3
node5:	.word -21,node6
node11:	.word 187,node12
node7:	.word 7,node8

	# array of linked-list headers
headers:
	.word node0
	.word node2
	.word node12
	.word node8
	.word node6
	.word node5
	.word 0
	.word node11
	.word node1
	.word node3

indexPlease:
	.asciiz "Please type the list # (0-9): "
badIndexMsg:
	.ascii "The index is not the range 0-9."
newline:
	.asciiz "\n"
