import java.sql.Time;
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
    public static sensorController[] sensors= new sensorController[8];
    public static Random rand = new Random();
    public static void main(String args[])
    {
        int hours = 5;

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

        System.out.println("Data for " + hours + " hours: ");
        for (int time = 0; time < hours; time++)
        {

            while (minutes.get() < 60)
            {
                try
                {
                    // each data collection stage takes 20ms plus calculation times
                    Thread.sleep(2);
                }
                catch (Exception e)
                {
                }

                if (minutes.get() == 59)
                {
                    break;
                }

                minutes.incrementAndGet();

            }

            minutes.set(0);
            System.out.println("Hour: " + time);
            maxmin(hours);
        }

        finished.set(true);
        pool.shutdown();

        try 
        {
            pool.awaitTermination(50, TimeUnit.SECONDS);
        }
        catch(Exception e)
        {
            
        }

        System.out.println("Complete");


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
        int[] temp1 = new int[10];
        int[] temp2 = new int[10];
        int time = 0;

        int distanceDiff = 0;


        for (int i = 0; i < 60; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                tempMin = Math.min(tempMin, data[i][j]);
                tempMax = Math.max(tempMax, data[i][j]);
            }

            tempTop[i] = tempMax;
            tempBot[i] = tempMin;
        }

        Arrays.sort(tempTop);
        Arrays.sort(tempBot);
        
        for (int i = 0; i < 5; i++)
        {
            top5[i] = tempTop[i];
            bot5[i] = tempBot[60 - (i + 1)];
        }

        tempMax = Integer.MIN_VALUE;
        tempMin = Integer.MAX_VALUE;
        
        for (int i = 0; i < 50; i += 10)
        {
            
            temp1 = Arrays.copyOfRange(tempTop, i, i + 10);
            temp2 = Arrays.copyOfRange(tempBot, i, i + 10);
            
            for (int j = 0; j < 10; j++)
            {
                if (temp1[j] >= tempMax)
                {
                    tempMax = temp1[j];
                }
                
                if (temp2[j] <= tempMin);
                {
                    tempMin = temp2[j];
                }
                
            }
            
            if (tempMax - tempMin >= distanceDiff)
            {
                
                distanceDiff = tempMax - tempMin;
                time = i;
                
            }
        }
        
        System.out.println("top5 values: " + Arrays.toString(top5) + " bot5 values: " + Arrays.toString(bot5));
        System.out.println("Largest temperature difference: " + distanceDiff + " between time " + time + " " + (time + 10));
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
                // System.out.println(reading);
                data[minutes.get()][sensor] = reading;
                // System.out.println(data[minutes.get()][sensor]);
            }
        }
    }
}


