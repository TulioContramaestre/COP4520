import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;

public class Assignment1 
{
    private static Counter count = new Counter(2);
    // private static AtomicInteger count = new AtomicInteger(2);
    private static AtomicLong sumAllPrimes = new AtomicLong(0);
    private static List<Integer> syncprimes = Collections.synchronizedList(new ArrayList<>());
    public static void main(String args[])
    {
        long startTime = System.nanoTime();
        int threadCount = 8;

        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++)
        {
            pool.execute(new MyRunnables());
        }
        pool.shutdown();

        while(!pool.isTerminated())
        {

        }
        // try 
        // {

        //     pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        // }
        // catch(Exception e)
        // {
            
        // }

        Collections.sort(syncprimes);
        int length = syncprimes.size();
        System.out.println("Top ten maximum primes: ");
        for (int i = length - 10; i < length; i++)
        {
    
            System.out.println(syncprimes.get(i));
            
        }

        

        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime);
        double totalTimeSec  = (double)totalTime / 1000000000.0;
        // System.out.println(primes);
        System.out.println("Execution time: " + (totalTimeSec) + " seconds");
        System.out.println("total number of primes: " + syncprimes.size());
        System.out.println("sum of all primes found: " + sumAllPrimes.get());

    }

    public static boolean isPrime(int num)
    {
        if ( num > 2 && num % 2 == 0 ) {
            return false;
        }

        for (int i = 3; i <= (int)Math.sqrt(num); i++)
        {
            if (num % i == 0)
                return false;
        }
        return true;
    }
          
    public static class MyRunnables implements Runnable
    {
        // AtomicInteger count = new AtomicInteger(2);
        double max = Math.pow(10,8);
        @Override
        public void run()
        {
            // int counter = 2;
            int counter = count.getAndIncrement();
            while (counter < max)
            { 
                if (isPrime(counter))
                {

                    syncprimes.add(counter);
            
                    sumAllPrimes.getAndAdd(counter);
                }
                counter = count.getAndIncrement();
            }
        }
    }

    public static class Counter 
    {
        // private AtomicInteger Counter = new AtomicInteger(2);
        private int value;
        public Counter(int i)
        {
            this.value = i;
        }

        public synchronized int getAndIncrement()
        {
            int temp = value;
            value += 1;
            return temp;
        }

        public synchronized int get() 
        {
            return value;
        }

        public synchronized long getAndAdd(long add)
        {
            value += add;
            return value;
        }
    }
}

