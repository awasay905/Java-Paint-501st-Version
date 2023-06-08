package shapes;

import java.awt.*;

public class RightTriangle extends Shape {

    public RightTriangle(Point p1, Point p2, int strokeSize, Color fillColor, Color strokeColor) {
        super(p1, p2, strokeSize, fillColor, strokeColor);
        calculatePoints();
    }

    private void calculatePoints() {
        setnPoints(3);
        setOuterPointsX(new int[getnPoints()]);
        setOuterPointsY(new int[getnPoints()]);

        int[] outerPointsX = getOuterPointsX();
        int[] outerPointsY = getOuterPointsY();

        outerPointsX[0] = getP2().getX();
        outerPointsY[0] = getP2().getY();

        outerPointsX[1] = getP1().getX();
        outerPointsY[1] = getP2().getY();

        outerPointsX[2] = getP1().getX();
        outerPointsY[2] = getP1().getY();

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
