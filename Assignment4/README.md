# Store the DNA information(A, T, G, C) in AVL tree DB.

## Structure

There are 64 avl trees. and the indexes are 1~64.

Each sequence get the ID. ID means the order that the sequence was added to the DB.

## About each command

I: Add new sequence of DNA information in the matched avl tree ( something in 1~64 index).

P: Type an index from 1 to 64 avl trees, and get the values in order of alphabet.

S: Type some pattern longer than 6 symbols(A, T, G, C), and get all the information of sequences (sequence ID, starting point in the sequence  ) including the pattern,

## How to command

Input command
    I ACTTTTGGCC
Print command
    P 14
Search command
    S ACTTTT
