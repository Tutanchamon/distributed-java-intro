package exercise1.equipment;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Brushes {
    private int available = 3;
    private final Lock lock = new ReentrantLock();
    final Condition notEmpty = lock.newCondition();

    public void takeBrush() throws InterruptedException {
        lock.lock();
        try {    
            while (available == 0) {
                notEmpty.await();
            }
            available -= 1;
        } finally {
            lock.unlock();
        }
    }

    public void returnBrush() {
        lock.lock();
        try {
            available += 1;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
}
