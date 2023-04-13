import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;

public class Problem2 
{
    public static AtomicInteger cardsComplete =  new AtomicInteger(0);
    public static AtomicInteger bagNum = new AtomicInteger(0);
    public static AtomicBoolean finished = new AtomicBoolean(false);
    public static int threadCount = 8;
    public static void main(String args[])
    {
        long startTime = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++)
        {
            pool.execute(new MyRunnables());
        }

        pool.shutdown();

        try 
        {
            pool.awaitTermination(50, TimeUnit.SECONDS);
        }
        catch(Exception e)
        {
            
        }

        System.out.println("All threads have finished");

        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime);
        double totalTimeSec  = (double)totalTime / 1000000000.0;

        System.out.println(totalTimeSec + " s");
    }

        public static class MyRunnables implements Runnable
        {
    
    
            @Override
            public void run()
            {

            }
        }
}


