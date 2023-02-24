import java.sql.Time;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Problem2 
{
    public static AtomicInteger counter = new AtomicInteger(1);
    public static AtomicBoolean available = new AtomicBoolean(true);
    public static AtomicBoolean finished = new AtomicBoolean(false);
    public static int numbGuest = 100;
    public static Random random = new Random();
    public static int randomGuest = (random.nextInt(numbGuest));
    public static Lock lock = new ReentrantLock();
    public static showroom[] showroom = new showroom[numbGuest];
    public static ConcurrentLinkedQueue<Integer> que = new ConcurrentLinkedQueue<Integer>();
  
    
    public static void main(String args[])
    {
        long startTime = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(numbGuest);

        // for (int i = 0; i < numbGuest; i++)
        // {
        //     showroom[i] = new showroom();
        // }

        for (int i = 0; i < numbGuest; i++)
        {
            pool.execute(new showroom());
        }
        pool.shutdown();

        try 
        {
            pool.awaitTermination(50, TimeUnit.SECONDS);
        }
        catch(Exception e)
        {
            
        }

        System.out.println("All guests have seen the vase atleast once and some more than once");

        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime);
        double totalTimeSec  = (double)totalTime / 1000000000.0;

        System.out.println(totalTimeSec + " s");

    }
    
    public static class showroom implements Runnable
    {
        int guestNum;
        boolean inQue;

        // public showroom()
        // {
        //     this.guestNum = counter.getAndIncrement();
        //     que.offer(guestNum);
        //     this.inQue = true;
        // }

        @Override
        public void run()
        {
            AtomicInteger viewer = new AtomicInteger(0);
            guestNum = counter.getAndIncrement();
            que.offer(guestNum);
            inQue = true;

            while(!finished.get())
            {
                lock.lock();
                
                try
                {
                    // System.out.println(Thread.currentThread().getName());
                    if (que.peek() != null && available.get())
                    {
                        viewer.set(que.poll());
                        available.set(false);
                        System.out.println("current viewer is : " + viewer);
                        try
                        {
                            // TimeUnit.MILLISECONDS.sleep(150);
                            // Thread.yield();
                            Thread.sleep(50);
                        }
                        catch (Exception e)
                        {
    
                        }
                        // after spending some time viewing the gues leaves the room and notifies the next person in line that they are able to go inside
                        available.set(true);
    
                        if (random.nextInt(100) < 15)
                        {
                            System.out.println("viewer: " + viewer + " wansts to view again");
                            que.offer(viewer.get());
                        }
                    }
                    else
                    {
                        finished.set(true);
                    } 

                }
                finally
                {
                    lock.unlock();
                }
                

            }
           
        }
    }
}
