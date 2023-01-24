import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Assignment1 
{
    private static Counter count = new Counter(1);
    private static Counter sumAllPrimes = new Counter(0);
    private static List<Integer> syncprimes = Collections.synchronizedList(new ArrayList<>());
    public static void main(String args[])
    {
        long startTime = System.nanoTime();
        int threadCount = 8;
        // ArrayList<Integer> primes = new ArrayList<>();
        // long sumAllPrimes = 0;
        double max = Math.pow(10,8);

        MyRunnables runnable1 = new MyRunnables();
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        for (int i = 2; i < max; i++)
        {
            pool.execute(runnable1);
        }
        pool.shutdown();
        try {

            pool.awaitTermination(1, TimeUnit.NANOSECONDS);
        }
        catch(Exception e)
        {
            
        }

        // }

        // for (int i = 2; i < max; i++)
        // {
        //     if (isPrime(i))
        //     {
        //         primes.add(i);
        //         sumAllPrimes += i;
        //     }
        // }
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

        for (int i = 3; i <= (int)Math.sqrt(num); i++ )
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

        public int get() 
        {
            return value;
        }

        public synchronized int getAndAdd(int add)
        {
            value += add;
            return value;
        }
    }
}

