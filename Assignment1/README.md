# COP4520 HW 1
check for primes from 1 - 10^8

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
For this assignment I first started with a simple iterative solution without the use of multithreading to see the time that it would take in comparison. With a very generic and simple implementation, I was able to find and perform all operations on the primes in around 60s for 1 - 10^8. This implementation is very innefficient and it can be further improved. This can be done implementing threads, the use of 8 threads improves the the runtime from around 60s to around 17s. This implementation of 8 threads running concurrently helps to significantly improve performance. However this can be further improved. 
