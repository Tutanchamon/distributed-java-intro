package exercise4;

import common.Counter;
import java.util.concurrent.locks.ReentrantLock;

public class EvenCheckingTask implements Runnable {

    private final Counter counter;
    private final int numberOfIterations;
    private static final ReentrantLock rl = new ReentrantLock();

    public EvenCheckingTask(Counter counter, int numberOfIterations) {
        this.counter = counter;
        this.numberOfIterations = numberOfIterations;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfIterations; ++i) {
            try {
                rl.lock();
                counter.increment();
                counter.increment();
                if (counter.getValue() % 2 != 0) {
                    System.out.println("Value is not even!");
                    break;
                }
            } finally {
                rl.unlock();
            }
        }
    }
}
