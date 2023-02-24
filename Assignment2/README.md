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

## Problem 2

### To compile and run
1. Download file
2. Navigate to the directory where Assignment1.java is located
3. Compile the program through the terminal using "javac Problem2.java"
4. To run the program do "java Problem2"

### Strategy

Chosen strategy: Strategy 3

Strategy 1: This strategy seems to be good at allowing multiple people and access, but it could cause major crowding since there are so many guest trying to view the showroom. It wouldn't be efficient and it would slow down since there are a lot of guest in line waiting to get in. This method wouldn't guarantee an efficient and safe manner of viewing the vase, since their is no guarantee that the current guest or guest viewiing the vase would leave or allow someone else to go inside. 

Strategy 2: This strategy might be a good strategy since it prevents a large quantity of guest from linning up. But this method doesn't fix the problem of the sign never changing, it could stay at busy for a really long time and worse the runtime of the program/never allow other guests to enter. However this method would allow the guest to roam around and do other task/ things. Since if it is busy they know there is no reason to wait.

Strategy 3: This is the strategy that I chose to implement since it seems to be the safest and most efficient method. This method would allow every guest to view the vace atleast once and prevent a single guest from viewing it multiple times back to back. However this method would prevent the guest from viewing anything else in the castle since they would have to stay in the que until it is their turn if not they would not view it.

### Implementation

As stated previously I used Strategy 3 to manage all the guest and their viewwing of the vase. To setup the que I used a ConcurrentLinkedQue to be able to implement a thread safe FIFO que. ontop of all the guest being in the que and being able to view the vase, there is also a 15% chance that after viewing the vase they want to view it again if that is the case they would go to the back of the queue and wait until everyone else has viewed it before they can view once more. The program ends after all guest have viewed the vase and the que is empty. 

### Efficiency
 
After performing this and using the ConcurrentLinkedQue along with a reentrant lock I was able to get a runtime of aroun 1.8 seconds this includes a artificial wait time of around 50ms per visit to the vase. After performing these operations I still believe that Strategy 3 is the fastest out of all the methods since it guarantees quick and safe access to the room by all threads. Ontop of having the ability of threads/guest being able to view it again without iterfering with other guest since they have to go to back of the queue if they want to see it again.
