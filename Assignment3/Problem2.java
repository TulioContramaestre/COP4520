import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;
import java.util.Random;

public class Problem2 
{
    public static AtomicInteger minutes =  new AtomicInteger(0);
    public static AtomicInteger index = new AtomicInteger(0);
    public static AtomicBoolean finished = new AtomicBoolean(false);
    public static int[][] data = new int[60][8];
    public static int numSensors = 8;
    public static sensorController[] sensors;
    public static Random rand = new Random();
    public static void main(String args[])
    {
        int hours = 2;

        long startTime = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(numSensors);

        for (int i = 0; i < numSensors; i++)
        {
            sensors[i] = new sensorController(i);
        }

        for (int i = 0; i < numSensors; i++)
        {
            pool.execute(sensors[i]);
        }

        for (int time = 0; time < hours; time++)
        {

            while (minutes.get() < 60)
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (Exception e)
                {
                }

                minutes.incrementAndGet();
            }

            minutes.set(0);
        }

        maxmin(hours);

        pool.shutdown();

        try 
        {
            pool.awaitTermination(50, TimeUnit.SECONDS);
        }
        catch(Exception e)
        {
            
        }

        System.out.println("Complete");

        System.out.print("Data for " + hours);


        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime);
        double totalTimeSec  = (double)totalTime / 1000000000.0;

        System.out.println(totalTimeSec + " s");
    }

    public static void maxmin(int hour)
    {
        int[] top5= new int[5];
        int[] tempTop = new int[60];
        int[] tempBot = new int[60];
        int[] bot5 = new int[5];
        int tempMin = Integer.MAX_VALUE;
        int tempMax = Integer.MIN_VALUE;


        for (int i = 0; i < 60; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                tempMin = Math.min(tempMin, data[i][j]);
                tempMax = Math.max(tempMin, data[i][j]);
            }

            tempTop[i] = tempMax;
            tempBot[i] = tempMin;
        }

        Arrays.sort(tempTop);
        Arrays.sort(tempBot);
        
        for (int i = 0; i < 5; i++)
        {
            top5[i] = tempTop[i];
            bot5[i] = tempBot[60 - i];
        }

        System.out.println("top5 values: " + Arrays.toString(top5) + " bot5 values: " + Arrays.toString(bot5));
    }

    public static class sensorController implements Runnable
    {
        int sensor;
        int reading = 0;

        public sensorController(int sensor)
        {
            this.sensor = sensor;
        }

        @Override
        public void run()
        {
            while (!finished.get())
            {
                reading = rand.nextInt(171) - 100;
                data[minutes.get()][sensor] = reading;
            }
        }
    }
}


