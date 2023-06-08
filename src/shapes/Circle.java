package shapes;

import java.awt.*;

public class Circle extends Shape {
    private final int radius;

    public Circle(Point p1, Point p2, int strokeSize, Color fillColor, Color strokeColor) {
        super(p1, p2, strokeSize, fillColor, strokeColor);
        radius = (int) (Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2)));
    }

    @Override
    public void draw(Graphics g) {
        //Outer Bigger Circle For Stroke
        g.setColor(getStrokeColor());
        g.fillOval(getP1().getX() - radius / 2, getP1().getY() - radius / 2, radius, radius);

        //Inner Smaller Circle For Fill
        g.setColor(getFillColor());
        g.fillOval(getP1().getX() - radius / 2 + getStrokeSize(), getP1().getY() - radius / 2 + getStrokeSize(), radius - getStrokeSize() * 2, radius - getStrokeSize() * 2);
    }
}
