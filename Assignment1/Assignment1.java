import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;

public class Assignment1 
{
    private static AtomicInteger count = new AtomicInteger(2);
    private static AtomicLong sumAllPrimes = new AtomicLong(0);
    private static List<Integer> syncprimes = Collections.synchronizedList(new ArrayList<>());
    private static double max = Math.pow(10,8);
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

        try 
        {

            pool.awaitTermination(50, TimeUnit.SECONDS);
        }
        catch(Exception e)
        {
            
        }
        
        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime);
        double totalTimeSec  = (double)totalTime / 1000000000.0;

        Collections.sort(syncprimes);
        int length = syncprimes.size();
        List<Integer> primes10 = new ArrayList<>();
        for (int i = length - 10; i < length; i++)
        {
            primes10.add(syncprimes.get(i));
        }
        System.out.println("Execution time: " + (totalTimeSec) + " seconds");
        System.out.println("total number of primes: " + syncprimes.size());
        System.out.println("sum of all primes found: " + sumAllPrimes.get());
        System.out.println("Top10 primes: " + primes10);

        try
        {
            FileWriter file = new FileWriter(new File("primes.txt"));
            file.write("Execution time: " + totalTimeSec + " seconds" +  "\n" + "Total number of primes: " + syncprimes.size());
            file.write("\n" + "Sum of all primes found: " + sumAllPrimes.get() + "\n" + "Top ten maximum primes: " + primes10);
            file.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

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
        @Override
        public void run()
        {
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

