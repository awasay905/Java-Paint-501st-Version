package shapes;

import java.awt.*;

public class Rectangle extends Shape {
    private final int width;
    private final int height;

    public Rectangle(Point p1, Point p2, int strokeSize, Color fillColor, Color strokeColor) {
        super(p1, p2, strokeSize, fillColor, strokeColor);
        width = Math.abs(p1.getX() - p2.getX());
        height = Math.abs(p1.getY() - p2.getY());
    }

    @Override
    public void draw(Graphics g) {
        int x = getP1().getX();
        int y = getP1().getY();

        if (x > getP2().getX()) x = getP2().getX();
        if (y > getP2().getY()) y = getP2().getY();

        //Drawing outer rectangle
        g.setColor(getStrokeColor());
        g.fillRect(x, y, width, height);

        //Drawing Inner rectangle
        g.setColor(getFillColor());
        g.fillRect(x + getStrokeSize(), y + getStrokeSize(), width - getStrokeSize() * 2, height - getStrokeSize() * 2);
    }
}
