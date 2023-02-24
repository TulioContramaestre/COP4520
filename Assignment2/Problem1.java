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
    public static Random random = new Random();
    public static int randomGuest;
    public static Lock lock = new ReentrantLock();
    public static labyrinth[] guests = new labyrinth[numbGuest];    
    public static void main(String args[])
    {
        long startTime = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(numbGuest);

        for (int i = 0; i < numbGuest; i++)
        {
            guests[i] = new labyrinth();
        }

        while(!finished.get())
        {
            randomGuest = random.nextInt(numbGuest);
            pool.execute(guests[randomGuest]);

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
        private int current;
        private boolean leader =false;
        private int count  = 1;
        private boolean ate = false;


        public labyrinth()
        {
            this.current = counter.getAndIncrement();
            if (current == 0)
            {
                this.leader = true;
            }
        }

        @Override
        public void run()
        {
            lock.lock();
            
            try
            {
                if (randomGuest == current && leader)
                {
                    if(!cupcake.get())
                    {
                        count++;
                        // System.out.println(count);
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
                    }
                }

            }
            finally
            {
                lock.unlock();
            }
        }
    }
}


