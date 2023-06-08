package shapes;

import java.awt.*;

public class Pentagon extends Shape {
    private Point center;
    private int radius;

    public Pentagon(int radius, Point center, int strokeSize, Color fillColor, Color strokeColor) {
        super(center, center, strokeSize, fillColor, strokeColor);
        this.radius = radius;
        this.center = center;
        calculatePoints();
    }

    private void calculatePoints() {
        setnPoints(5);
        setOuterPointsX(new int[getnPoints()]);
        setOuterPointsY(new int[getnPoints()]);

        int[] outerPointsX = getOuterPointsX();
        int[] outerPointsY = getOuterPointsY();

        int angle = 360 / getnPoints();

        for (int i = 0; i < getnPoints(); i++) {
            outerPointsX[i] = (center.getX() + (int) (radius * Math.cos(Math.toRadians(18 + (i + 1) * angle))));
            outerPointsY[i] = (center.getY() + (int) (radius * Math.sin(Math.toRadians(18 + (i + 1) * angle))));
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
