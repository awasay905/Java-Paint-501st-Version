package shapes;

import java.awt.*;

public class Hexagon extends Shape {
    private final Point center;
    private final int radius;

    public Hexagon(Point p1, Point p2, int strokeSize, Color fillColor, Color strokeColor) {
        super(p1, p2, strokeSize, fillColor, strokeColor);

        radius = (int) (Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2)) * 0.7);
        center = new Point(p1.getX() + radius / 2, p1.getY() + radius / 2);
        calculatePoints();
    }

    private void calculatePoints() {
        setnPoints(6);
        setOuterPointsX(new int[getnPoints()]);
        setOuterPointsY(new int[getnPoints()]);

        int[] outerPointsX = getOuterPointsX();
        int[] outerPointsY = getOuterPointsY();

        for (int i = 0; i < 6; i++) {
            outerPointsX[i] = (center.getX() + (int) (radius * Math.cos(Math.toRadians(i * 60)))) - radius / 2;
            outerPointsY[i] = (center.getY() + (int) (radius * Math.sin(Math.toRadians(i * 60)))) - radius / 2;
        }

        setOuterPointsX(outerPointsX);
        setOuterPointsY(outerPointsY);
    }

    @Override
    public void draw(Graphics g) {
        //Fill Inside First
        g.setColor(getFillColor());
        g.fillPolygon(getOuterPointsX(), getOuterPointsY(), getnPoints());

        //Now Fill Border/Stroke
        if (getStrokeSize() == 0) return;
        g.setColor(getStrokeColor());
        Stroke old = ((Graphics2D) g).getStroke();
        ((Graphics2D) g).setStroke(new BasicStroke(getStrokeSize()));
        g.drawPolygon(getOuterPointsX(), getOuterPointsY(), getnPoints());
        ((Graphics2D) g).setStroke(old);
    }
}
