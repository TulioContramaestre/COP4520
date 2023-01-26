# COP4520 HW 1
## Overview
The repository contains an Assignment.java that finds all the primes from 1 - 10^8. It performs these operations using 8 threads to improve the runtime. It is also using a slightly modified isPrime() method that checks the current digit to every other digit from 3 to the square root of itself. the Isprime() also skips past all even values since those values will never be prime. The Assignment.java program also calculates the sum of all the primes, this is done through the run operation that is ran through each thread. It is using atomics to keep track of the value in a thread-safe manner.

## To compile and run
1. Download file
2. Navigate to the directory where Assignment1.java is located
3. compile the program through the terminal using "javac Assignment1.java"
4. To run the program do "java Assignment1"
5. The output of the program will be pushed into a .txt file called primes.txt
6. The results and runtime in seconds will be displayed in this primes.txt

## Experimental evaluation
