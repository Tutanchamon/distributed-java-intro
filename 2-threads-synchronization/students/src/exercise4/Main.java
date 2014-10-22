package exercise4;

import common.Counter;
import static common.CountingRunner.numberOfIterations;
import static common.CountingRunner.numberOfThreads;
import exercise1.SynchronizedCounter;
import exercise2.LockingCounter;
import exercise3.AtomicCounter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static ReentrantLock rl1 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Counter1");

        Counter counter = new SynchronizedCounter(); // TODO: Provide counter implementation
        execute(counter);

        System.out.println("Counter2");

        Counter counter2 = new LockingCounter();
        execute(counter2);

        System.out.println("Counter3");
        Counter counter3 = new AtomicCounter();
        execute(counter3);
    }

    private static void execute(Counter counter) throws InterruptedException {
        ExecutorService executors = Executors.newCachedThreadPool();
        for (int i = 0; i < numberOfThreads; ++i) {
            executors.execute(new EvenCheckingTask(counter, numberOfIterations));
        }
        executors.shutdown();
        executors.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println("Actual: " + counter.getValue() + ", Expected: " + (2 * numberOfThreads * numberOfIterations));
    }
}
