package exercise3;

import common.Counter;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicCounter implements Counter {
    
    private AtomicLong atomicLong = new AtomicLong();
    @Override
    public void increment() {
        atomicLong.incrementAndGet();
    }

    @Override
    public long getValue() {
        return atomicLong.longValue();
    }
}
