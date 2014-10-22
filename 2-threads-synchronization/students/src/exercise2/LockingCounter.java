package exercise2;

import common.Counter;
import java.util.concurrent.locks.ReentrantLock;

public class LockingCounter implements Counter {

    private final ReentrantLock reentrantLock = new ReentrantLock();
    private long counter;
    @Override
    public void increment() {
        //reentrantLock.lock();
        try {
            counter++;
        } finally {
            //reentrantLock.unlock();
        }
    }

    @Override
    public long getValue() {
        return counter;
    }
}
