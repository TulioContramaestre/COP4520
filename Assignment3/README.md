# COP4520 HW 3
## Problem 1
running the program on i7-11700k

### To compile and run
1. Download file
2. Navigate to the directory where Assignment1.java is located
3. Compile the program through the terminal using "javac Problem1.java"
4. To run the program do "java Problem1"

### Implementation
For this problem I used a lock free method provided by the book "The Art of Multiprocessor Programming". I used the books LockFreeList Algorithm to be able to parse, add and remove the presents from the chain as they are being manipulated by the threads. I first started with using the LazyList also provided by the book but after more optimization and changes I ended up using the LockFreeList since removing the need for locks helped slightly improve runtime and efficiency among threads. I also use a random generator to help randomize the tasks that are performed by each worker/thread since there isn't one set task that needs to be done first.

### Efficiency
The first method I tried the LazyList wasn't as efficient as the current one I am using to solve this problem. I was able to average a runtime of about .3 secs or around 280- 340 miliseconds. It was able to go through and add all the gifts to the chain and remove them after they have been added to send the thank you cards very efficiently. Following mutual exclusion without the need of locks.

## Problem 2

### To compile and run
1. Download file
2. Navigate to the directory where Assignment1.java is located
3. Compile the program through the terminal using "javac Problem2.java"
4. To run the program do "java Problem2"

To change the amount of hours to collect the data, change the integer value named hours on line 20.

### Implementation



### Efficiency



## Citations
Herlihy, M., Shavit, N. (2008). The Art of Multiprocessor Programming. Morgan Kaufmann. ISBN: 978-0-12-370591-4
