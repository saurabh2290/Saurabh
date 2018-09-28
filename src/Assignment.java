import java.util.concurrent.CountDownLatch;

public class Assignment {

    public static void main(String args[])
        throws InterruptedException {
        TaskFirst one = new TaskFirst("firstTask");
        //here we are using 4 we can use n threads among which we want parallelism
        CountDownLatch latch = new CountDownLatch(4);
        Tasks second = new Tasks(10, latch,
            "secondTask");
        Tasks third = new Tasks(30, latch,
            "thirdTask");
        Tasks fourth = new Tasks(50, latch,
            "fourthTask");
        Tasks fifth = new Tasks(20, latch,
            "fifthTask");

        //here by adjusting the delay time we can get any order of threads in the latch
        //If we want fix order we have to write join individually for each thread
        one.start();
        one.join();
        second.start();
        third.start();
        fourth.start();
        fifth.start();

        // The main task here is the nth task waits for above threads
        latch.await();

        // Main thread has started
        System.out.println(Thread.currentThread().getName() +
            " has finished");
    }
}

class Tasks extends Thread {
    private CountDownLatch latch;
    private int delay;

    public Tasks(int delay, CountDownLatch latch,
        String name) {
        super(name);
        this.delay = delay;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delay);
            latch.countDown();
            System.out.println(Thread.currentThread().getName()
                + " finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class TaskFirst extends Thread {

    public TaskFirst(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
            System.out.println(Thread.currentThread().getName()
                + " finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
