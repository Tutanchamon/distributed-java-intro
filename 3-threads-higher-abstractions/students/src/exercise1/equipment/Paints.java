package exercise1.equipment;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Paints {
    private int available = 3;
    private final Lock lock = new ReentrantLock();
    final Condition notEmpty = lock.newCondition(); 
    

    public void takePaint() throws InterruptedException {
        lock.lock();
        try {    
            while (available == 0) {
                notEmpty.await();
            }
            available -= 1;
            // notEmpty.signal();
        } finally {
            lock.unlock();
        }
        
    }

    public void returnPaint() {
        lock.lock();
        try {
            available += 1;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
}
