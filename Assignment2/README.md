# COP4520 HW 1
## Problem 1
running the program on i7-11700k

### To compile and run
1. Download file
2. Navigate to the directory where Assignment1.java is located
3. Compile the program through the terminal using "javac Problem1.java"
4. To run the program do "java Problem1"

### Implementation
For this problem I used a basic lock and unlock method using a reentrant lock. I also setup the randomness of the minotaur letting a guest in a time using a random function before executiong the thread operation. The method of solving this was done through having one person or thread in this case designated as the leader. The leader would keep track of himself and everyother guest. If a gues entered and there was a cupcake, they would eat it. If another guest entered and there was no cupcake they would do nothing. However if the leader entered and there was no cupcake he would add a new cupcake and increase the cound by one. This would continue until all guest have eaten one cupcake and the count matches the number of guests.

### Efficiency
First starting this method out I was getting a runtime of around 14 seconds. This was not as optimal as I wanted so I kept optimizing one optimization that I did that drastically improved runtime was the reentrant lock. The lock allowed for the threads to share the task fairly and brought the runtime thought to around .5 secs with some cases being 1 secs.