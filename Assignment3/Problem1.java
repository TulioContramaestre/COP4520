import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;
import java.util.Random;


public class Problem1 
{
    public static AtomicInteger cardsComplete =  new AtomicInteger(0);
    public static AtomicInteger bagNum = new AtomicInteger(0);
    public static AtomicBoolean finished = new AtomicBoolean(false);
    public static LockfreeList chain = new LockfreeList();
    public static int threadCount = 4;
    public static MyRunnables[] workers = new MyRunnables[4];
    public static Random random = new Random();
    public static void main(String args[])
    {
        int numGifts = 500000;
        ArrayList<Integer> bag = new ArrayList<>();

        for (int i = 1; i <= numGifts; i++)
        {
            bag.add(i);
        }

        Collections.shuffle(bag);

        long startTime = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++)
        {
            workers[i] = new MyRunnables(bag);
        }

        for (int i = 0; i < threadCount; i++)
        {
            pool.execute(workers[i]);
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
        ArrayList<Integer> bag;

        public MyRunnables(ArrayList<Integer> bag)
        {
            this.bag = bag;
        }

        @Override
        public void run()
        {
            int present = 0;
            int card = 0;
            int task = 0;
            int randPresent = 0;

            while(cardsComplete.get() < bag.size())
            {
                task = random.nextInt(3) + 1;

                if (task == 1)
                {
                    try
                    {
                        present = bag.get(bagNum.getAndIncrement());
                        // System.out.println("present added to the chain: " + present);
                        chain.add(present);
                    }
                    catch (Exception e)
                    {

                    }
                }
                else if(task == 2 && bagNum.get() > 0)
                {
                    try
                    {
                        card = bag.get(cardsComplete.getAndIncrement());
                        // System.out.println("present removed to the chain: " + present);
                        chain.remove(card);
                    }
                    catch (Exception e)
                    {

                    }
                }
                else
                {
                    randPresent = (int)(Math.random() * 500000 + 1);
                    try 
                    {
                        // System.out.println(chain.contains(randPresent));
                        chain.contains(randPresent);
                    }
                    catch (Exception e)
                    {
                    }
                }
            }

        }
    }

    // take from "The art of Multiprocessor programming"
    public static class Node
    {
        public int value;
        public AtomicMarkableReference<Node> next;

        public Node(int value)
        {
            this.value = value;
            this.next = new AtomicMarkableReference<Node>(null, false);
        }
    }

    // take from "The art of Multiprocessor programming"
    public static class LockfreeList
    {
        Node head = new Node(Integer.MAX_VALUE);
        
        public LockfreeList()
        {
            this.head.next = new AtomicMarkableReference<Node>(new Node(-1), false);
        }

        public boolean add(int value)
        {
            while (true)
            {
                Window window = find(head, value);
                Node pred = window.pred;
                Node curr = window.curr;
                
                if (curr.value == value)
                {
                    return false;
                }
                else
                {
                    Node node = new Node(value);
                    node.next = new AtomicMarkableReference<Node>(curr, false);
                    if (pred.next.compareAndSet(curr, node, false, false))
                    {
                        return true;
                    }
                }
                
            }
        }

        public boolean remove(int value)
        {
            boolean snip;

            while (true)
            {
                Window window = find(head, value);
                Node pred = window.pred;
                Node curr = window.curr;

                if (curr.value != value)
                {
                    return false;
                }
                else
                {
                    Node succ = curr.next.getReference();
                    snip = curr.next.attemptMark(succ, true);

                    if (!snip)
                    {
                        continue;
                    }
                    pred.next.compareAndSet(curr, succ, false, false);
                    return true;
                }
            }
        }

        public boolean contains(int value)
        {
            boolean[] marked = {false};
            Node curr = head;

            while (curr.value < value)
            {
                curr = curr.next.getReference();
                Node succ = curr.next.get(marked);
            }

            return (curr.value == value && !marked[0]);
        }

        public Window find(Node head, int key)
        {
            Node pred = null;
            Node curr = null;
            Node succ = null;
            boolean[] marked = {false};
            Boolean snip;
        
            retry: while(true)
            {
                pred = head;
                curr = pred.next.getReference();
        
                while(true)
                {
                    succ = curr.next.get(marked);
                    
                    while(marked[0])
                    {
                        snip = pred.next.compareAndSet(curr, succ, false, false);
                        if (!snip)
                        {
                            continue retry;
                        }
                        curr = succ;
                        succ = curr.next.get(marked);
                    }
        
                    if (curr.value >= key)
                    {
                        return new Window(pred, curr);
                    }
                    pred = curr;
                    curr = succ;
                }
            }
        
        }
    }
    
    // taken from "the art of Multiprocessor programming"
    public static class Window
    {
        public Node pred;
        public Node curr;
    
        Window(Node pred, Node curr)
        {
            this.pred = pred;
            this.curr = curr;
        }
    
    }
    
}
