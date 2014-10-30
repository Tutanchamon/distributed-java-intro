package exercise2.equipment;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Brushes {
    
    private BlockingQueue queue = null;
    public String takeBrush() throws InterruptedException {
        return (String) queue.take();
    }
    
    public Brushes() {
        queue = new ArrayBlockingQueue(5);
        queue.add("Rectangular");
        queue.add("Triangular");
        queue.add("spectacular");
        queue.add("angular");
    }

    public void returnBrush(String brush) {
        queue.offer(brush);
    }
}
