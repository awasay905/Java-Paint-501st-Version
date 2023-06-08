package shapes;

import java.awt.*;

public class Pentagram extends Shape {
    private final Point center;
    private final int radius;
    private Pentagon centerPentagon;

    public Pentagram(Point p1, Point p2, int strokeSize, Color fillColor, Color strokeColor) {
        super(p1, p2, strokeSize, fillColor, strokeColor);
        radius = (int) (Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2)));
        center = new Point(p1.getX() + radius / 2, p1.getY() + radius / 2);
        calculatePoints();
    }

    private void calculatePoints() {
        setnPoints(10);
        setOuterPointsX(new int[getnPoints()]);
        setOuterPointsY(new int[getnPoints()]);

        int[] outerPointsX = getOuterPointsX();
        int[] outerPointsY = getOuterPointsY();

        for (int i = 0; i < getnPoints(); i++) {
            if (i % 2 == 0) {
                outerPointsX[i] = (center.getX() + (int) (radius * Math.cos(Math.toRadians(18 + (i + 1) * 72))));
                outerPointsY[i] = (center.getY() - (int) (radius * Math.sin(Math.toRadians(18 + (i + 1) * 72))));
            } else {
                outerPointsX[i] = (center.getX() + (int) (((radius) / 2) * Math.cos(Math.toRadians(54 + (i + 1) * 72))));
                outerPointsY[i] = (center.getY() - (int) (((radius) / 2) * Math.sin(Math.toRadians(54 + (i + 1) * 72))));
            }
        }

        setOuterPointsX(outerPointsX);
        setOuterPointsY(outerPointsY);
        centerPentagon = new Pentagon(radius / 2, center, getStrokeSize(), getFillColor(), getStrokeColor());
    }

    @Override
    public void draw(Graphics g) {
        centerPentagon.draw(g);
        //Draws Normally
        g.setColor(getFillColor());
        g.fillPolygon(getOuterPointsX(), getOuterPointsY(), getnPoints());

        //Now draw stroke
        if (getStrokeSize() == 0) return;
        g.setColor(getStrokeColor());
        Stroke old = ((Graphics2D) g).getStroke();
        ((Graphics2D) g).setStroke(new BasicStroke(getStrokeSize()));
        g.drawPolygon(getOuterPointsX(), getOuterPointsY(), getnPoints());
        ((Graphics2D) g).setStroke(old);
    }
}
