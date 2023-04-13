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
    public static void main(String args[])
    {
        long startTime = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(numbGuest);

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
