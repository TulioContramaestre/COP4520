import java.util.*;

public class Assignment1 
{
    public static void main(String args[])
    {
        long startTime = System.nanoTime();
        int threadCount = 8;
        MyRunnables runnable1 = new MyRunnables();
        Thread thread1 = new Thread(runnable1); 
        // ExecutorService threads = Executors.newFixedThreadPool(threadCount);

        ArrayList<Integer> primes = new ArrayList<>();
        long sumAllPrimes = 0;
        double max = Math.pow(10,8);

        for (int i = 2; i < max; i++)
        {
            if (isPrime(i))
            {
                primes.add(i);
                sumAllPrimes += i;
            }
        }
        int length = primes.size();
        System.out.println("Top ten maximum primes: ");
        for (int i = length - 10; i < length; i++)
        {
            System.out.println(primes.get(i));
        }

        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime);
        double totalTimeSec  = (double)totalTime / 1000000000.0;
        // System.out.println(primes);
        System.out.println("Execution time: " + (totalTimeSec) + " seconds");
        System.out.println("total number of primes: " + primes.size());
        System.out.println("sum of all primes found: " + sumAllPrimes);

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
            Counter count = new Counter(1);
            int counter = count.getAndIncrement();
            while (counter < max)
            {
                if (isPrime(counter))
                {
                    primes.add(counter);
                    sumAllPrimes += i;
                }
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
    }
}

