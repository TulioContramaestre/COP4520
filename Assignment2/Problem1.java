import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class Problem1
{
    public static AtomicInteger counter =  new AtomicInteger(0);
    public static AtomicBoolean cupcake = new AtomicBoolean(true);
    public static AtomicBoolean finished = new AtomicBoolean(false);
    public static int numbGuest = 100;
    public static int randomGuest = (int)(Math.random() * numbGuest);
    public static Lock lock = new ReentrantLock();
    
    public static void main(String args[])
    {
        long startTime = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(numbGuest);

        for (int i = 0; i < numbGuest; i++)
        {
            pool.execute(new labyrinth());
        }
        pool.shutdown();

        try 
        {
            pool.awaitTermination(50, TimeUnit.SECONDS);
        }
        catch(Exception e)
        {
            
        }

        System.out.println("All guests have visited");

        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime);
        double totalTimeSec  = (double)totalTime / 1000000000.0;

        System.out.println(totalTimeSec + " s");

    }
    
    public static class labyrinth implements Runnable
    {
        @Override
        public void run()
        {
            // setting the guy that is going to count to index 0
            int current = counter.getAndIncrement();
            boolean leader = false;
            int count  = 1;
            boolean ate = false;

            if (current == 0)
            {
                leader = true;
            }

            while (!finished.get())
            {
                lock.lock();

                try
                {
                    if (randomGuest == current && leader)
                    {
                        if(!cupcake.get())
                        {
                            count++;
                            cupcake.set(true);
    
                            if (count == numbGuest)
                            {
                                finished.set(true);
                            }
                        }
                    }
                    
                    if (randomGuest == current && !leader)
                    {
                        if (cupcake.get() && ate == false)
                        {
                            cupcake.set(false);
                            ate = true;
                            // System.out.println("ate");
                            // try{
            
                            //     Thread.sleep(1);
                            // }
                            // catch (Exception e)
                            // {
            
                            // }
    
                            // Thread.yield();
                        }
                    }
                    
                    randomGuest = (int)(Math.random() * numbGuest);

                }finally
                {
                    lock.unlock();

                }

                
            }


        }
    }
}


