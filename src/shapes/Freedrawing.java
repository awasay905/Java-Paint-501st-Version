package shapes;

import java.awt.*;
import java.util.ArrayList;

public class Freedrawing extends Shape {
    private final ArrayList<Circle> points;

    public Freedrawing(Point p1, Point p2, int strokeSize, Color fillColor, Color strokeColor) {
        super(p1, p2, strokeSize, fillColor, strokeColor);
        points = new ArrayList<>();
        points.add(new Circle(p1, new Point(p1.getX() + strokeSize, p1.getY() + strokeSize), strokeSize, strokeColor, strokeColor));
    }

    public void add(Point p1) {
        points.add(new Circle(p1, new Point(p1.getX() + getStrokeSize(), p1.getY() + getStrokeSize()), getStrokeSize(), getStrokeColor(), getStrokeColor()));
    }

    @Override
    public void draw(Graphics g) {
        for (Circle c : points) {
            c.draw(g);
        }
    }
}
