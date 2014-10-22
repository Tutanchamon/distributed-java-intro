package exercise6.painters;

import exercise6.equipment.Brush;
import exercise6.equipment.Paint;

public class LeftHandedPainter extends Painter {

    public LeftHandedPainter(Paint paint, Brush brush) {
        super(paint, brush);
    }

    @Override
    public void run() {
        try {
            synchronized (paint) {
                paint.wait();
                String takenPaint = paint.takePaint();
                Thread.sleep(100);
                
                synchronized (brush) {
                    String takenBrush = this.brush.takeBrush();
                    Thread.sleep(100);
                    System.out.printf("Left hand painter painting with %s and %s\n", takenPaint, takenBrush);
                    paint.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
