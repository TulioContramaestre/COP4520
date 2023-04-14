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
For this problem I had a timeController portion that dealt with managing the sleeps to simulate the threads performing operations every minute for the 1 hour interval. I used the Thread.sleep() method within the main thread set to a 20 milisecond timer to simulate this 1 minute wait. The problem was able to parse the data that was randomly generated within the threads. Each thread is one sensor and after every iteration they randomly pick a value between -100 and 70 for the temperature sensor to read out. After this is done the data is stored and parsed to find the top5 values, bottom5 values and the biggest temperature difference.
The amount of data calculated can be adjusted by hour intervals by changing the integer value within the code. The data is also stored in a 2d array that is seperated by thread ID or temperature sensor and minutes so each second has a value for each ID. this guarantees that each ID or thread outputs a value for each minute within the hour


### Efficiency
The programm is able to efficiently parse the data with a runtime of 9s with the simulated wait time for a 5 hour block. Each thread is assigned and ID and that is the only place they will be able to adjust the data in the 2d array that is being used to manipulate the data this guarantees mutual exclusion.


## Citations
Herlihy, M., Shavit, N. (2008). The Art of Multiprocessor Programming. Morgan Kaufmann. ISBN: 978-0-12-370591-4
