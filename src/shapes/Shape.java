package shapes;

import java.awt.*;
import java.io.Serializable;

public abstract class Shape implements Serializable {
    private final Point p1;
    private final Point p2;
    private final int strokeSize;
    private final Color fillColor;
    private final Color strokeColor;
    private int[] outerPointsX;
    private int[] outerPointsY;
    private int[] innerPointsX;
    private int[] innerPointsY;
    private int nPoints;

    public Shape(Point p1, Point p2, int strokeSize, Color fillColor, Color strokeColor) {
        this.p1 = p1;
        this.p2 = p2;
        this.strokeSize = strokeSize;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    public int getnPoints() {
        return nPoints;
    }

    public void setnPoints(int nPoints) {
        this.nPoints = nPoints;
    }

    public int getStrokeSize() {
        return strokeSize;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public int[] getOuterPointsX() {
        return outerPointsX;
    }

    public void setOuterPointsX(int[] outerPointsX) {
        this.outerPointsX = outerPointsX;
    }

    public int[] getOuterPointsY() {
        return outerPointsY;
    }

    public void setOuterPointsY(int[] outerPointsY) {
        this.outerPointsY = outerPointsY;
    }

    public int[] getInnerPointsX() {
        return innerPointsX;
    }

    public void setInnerPointsX(int[] innerPointsX) {
        this.innerPointsX = innerPointsX;
    }

    public int[] getInnerPointsY() {
        return innerPointsY;
    }

    public void setInnerPointsY(int[] innerPointsY) {
        this.innerPointsY = innerPointsY;
    }

    public abstract void draw(Graphics g);
}
