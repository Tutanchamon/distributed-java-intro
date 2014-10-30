package exercise2.equipment;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Paints {
    
    
    private BlockingQueue queue = null;
    public String takePaint() throws InterruptedException {
        return (String) queue.take();
    }
    
    public Paints() {
        queue = new ArrayBlockingQueue(5);
        queue.add("Red");
        queue.add("Blue");
        queue.add("Yellow");
        queue.add("Green");
        queue.add("Orange");
    }

    public void returnPaint(String paint) {
        queue.offer(paint);
    }
}
