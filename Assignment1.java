package COP4520;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Assignment1 
{
    public static void main(String args[])
    {
        long startTime = System.nanoTime();

        ArrayList<Integer> primes = new ArrayList<>();

        for (int i = 0; i < (Math.pow(10, 5)); i++)
        {
            if (isPrime(i))
            {
                primes.add(i);
            }
        }

        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime);
        double totalTimeSec  = (double)totalTime / 1000000000.0;
        System.out.println(primes);
        System.out.println("Execution time: " + (totalTimeSec) + " seconds");
    }

    public static boolean isPrime(int num)
    {
        if ( num > 2 && num % 2 == 0 ) {
            // System.out.println(num + " is not prime");
            return false;
        }

        for (int i = 3; i < Math.sqrt(num); i++)
        {
            if (i % num == 0)
                return false;
        } 
        // System.out.println(num + "is prime");
        return true;
    }
          
    
}
