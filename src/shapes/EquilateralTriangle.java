package shapes;

import java.awt.*;

public class EquilateralTriangle extends Shape {
    private final Point center;
    private final int radius;

    public EquilateralTriangle(Point p1, Point p2, int strokeSize, Color fillColor, Color strokeColor) {
        super(p1, p2, strokeSize, fillColor, strokeColor);

        radius = (int) (Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2)) * 0.7);
        center = new Point(p1.getX() + radius / 2, p1.getY() + radius / 2);
        calculatePoints();
    }

    private void calculatePoints() {
        setnPoints(3);
        setOuterPointsX(new int[getnPoints()]);
        setOuterPointsY(new int[getnPoints()]);

        int[] outerPointsX = getOuterPointsX();
        int[] outerPointsY = getOuterPointsY();

        int angle = 360 / getnPoints();

        for (int i = 0; i < getnPoints(); i++) {
            outerPointsX[i] = (center.getX() + (int) (radius * Math.cos(Math.toRadians(i * angle + 30)))) - radius / 2;
            outerPointsY[i] = (center.getY() + (int) (radius * Math.sin(Math.toRadians(i * angle + 30)))) - radius / 2;
        }

        setOuterPointsX(outerPointsX);
        setOuterPointsY(outerPointsY);
    }

    @Override
    public void draw(Graphics g) {
        //Filled Triangle first
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
