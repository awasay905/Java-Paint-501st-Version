package shapes;

import java.awt.*;

public class BezierCurve extends Shape {
    private Point p1;
    private Point p2;
    private Point p3;
    private Point p4;
    private final int[] xCoordinates;
    private final int[] yCoordinates;
    private final int factor;
    private int counter;

    public BezierCurve(int strokeSize, Color strokeColor) {
        super(new Point(0, 0), new Point(0, 0), strokeSize, strokeColor, strokeColor);
        factor = 100;  //for smoothness. for a 1080p wide screen i think 100 is fine
        counter = 0;
        xCoordinates = new int[factor];
        yCoordinates = new int[factor];
    }

    public void addPoint(Point p) {
        if (counter == 0) {
            p1 = p2 = p3 = p4 = p;
            counter++;
            calculateCoordinates();
            return;
        }
        if (counter == 1) {
            p4 = p;
            counter++;
            calculateCoordinates();
            return;
        }
        if (counter == 2) {
            p2 = p3 = p;
            counter++;
            calculateCoordinates();
            return;
        }
        if (counter == 3) {
            p3 = p;
            counter++;
            calculateCoordinates();
        }
    }

    public boolean isComplete() {
        return counter == 4;
    }

    public void setTemp(Point temp) {
        if (counter == 1) p4 = temp;
        if (counter == 2) p2 = p3 = temp;
        if (counter == 3) p3 = temp;
        calculateCoordinates();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getStrokeColor());
        Stroke old = ((Graphics2D) g).getStroke();
        ((Graphics2D) g).setStroke(new BasicStroke(getStrokeSize()));
        drawCurve(g);
        ((Graphics2D) g).setStroke(old);
    }

    private void drawCurve(Graphics g) {
        if (counter == 0) return;
        g.drawPolyline(xCoordinates, yCoordinates, xCoordinates.length);
    }

    private void calculateCoordinates() {
        if (counter == 0) return;
        for (int i = 0; i < factor; i++) {
            double t = i / (double) factor;
            xCoordinates[i] = (int) ((1 - t) * (1 - t) * (1 - t) * p1.getX() + 3 * (1 - t) * (1 - t) * t * p2.getX() + 3 * (1 - t) * t * t * p3.getX() + t * t * t * p4.getX());
            yCoordinates[i] = (int) ((1 - t) * (1 - t) * (1 - t) * p1.getY() + 3 * (1 - t) * (1 - t) * t * p2.getY() + 3 * (1 - t) * t * t * p3.getY() + t * t * t * p4.getY());
        }
    }

}
