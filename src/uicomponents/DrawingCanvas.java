package uicomponents;

import helper.PaintInfo;
import shapes.Point;
import shapes.Shape;
import shapes.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DrawingCanvas extends Rectangle {
    BezierCurve bc;
    private Shapes shapeType;
    private Color fillColor;
    private Color strokeColor;
    private int strokeSize;
    private Shape temp;
    private Freedrawing tempLine;
    private int x1, y1;
    private boolean isShapeDrawing;
    private boolean isLineDrawing;
    private boolean isBezierDrawing;

    public DrawingCanvas(int x, int y, int width, int height) {
        super(x, y, width, height);
        Grid.getInstance().setCoordinates(new Point(x, y), new Point(x + width, y + height));
    }


    public void setShapeDetails() {
        //All info is now taken from paintinfo
        this.shapeType = PaintInfo.getInstance().getSelectedShape();
        this.fillColor = PaintInfo.getInstance().getFillColor();
        this.strokeColor = PaintInfo.getInstance().getStrokeColor();
        this.strokeSize = PaintInfo.getInstance().getStrokeWidth();
        this.isLineDrawing = PaintInfo.getInstance().isLineDrawing();
        this.isShapeDrawing = PaintInfo.getInstance().isShapeDrawing();
        this.isBezierDrawing = PaintInfo.getInstance().isBezierDrawing();
    }

    @Override
    public void onClick(int x, int y) {
        if (isClicked(x, y)) {
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 12));
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        if (isShapeDrawing) if (temp != null) temp.draw(g);
        if (isLineDrawing) if (tempLine != null) tempLine.draw(g);
        if (isBezierDrawing) if (bc != null) bc.draw(g);
    }

    public void mouseClicked(MouseEvent e) {
        if (isClicked(e.getX(), e.getY())) {
            if (isBezierDrawing) {
                if (bc == null) {
                    bc = new BezierCurve(strokeSize, strokeColor);
                }
                bc.setTemp(new Point(e.getX(), e.getY()));
                bc.addPoint(new Point(e.getX(), e.getY()));
                if (bc.isComplete()) {
                    PaintInfo.getInstance().getShapes().add(bc);
                    PaintInfo.getInstance().updateThumbnail();
                    bc = null;
                }
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (isClicked(e.getX(), e.getY())) {
            if (isBezierDrawing && bc != null) bc.setTemp(new Point(e.getX(), e.getY()));
        }
    }

    public boolean isBezierDrawing() {
        if (bc == null) return false;
        return !bc.isComplete();
    }

    public void mousePressed(MouseEvent e) {
        if (isClicked(e.getX(), e.getY())) {
            setShapeDetails();
            x1 = e.getX();
            y1 = e.getY();
            if (isShapeDrawing)
                temp = new ShapeFactory().getShape(shapeType, new Point(x1, y1), new Point(x1, y1), strokeSize, fillColor, strokeColor);
            if (isLineDrawing)
                tempLine = new Freedrawing(new Point(x1, y1), new Point(x1, y1), strokeSize, fillColor, strokeColor);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (temp != null) {
            addToShapes(temp);
            temp = null;
        }
        if (tempLine != null) {
            addToShapes(tempLine);
            tempLine = null;
        }
    }


    public void mouseDragged(MouseEvent e) {
        if (isShapeDrawing) {
            if (isClicked(e.getX(), e.getY())) {
                temp = new ShapeFactory().getShape(shapeType, new Point(x1, y1), new Point(e.getX(), e.getY()), strokeSize, fillColor, strokeColor);
            } else temp = null;
        } else if (isLineDrawing) {
            if (isClicked(e.getX(), e.getY())) {
                if (tempLine != null) tempLine.add(new Point(e.getX(), e.getY()));
            } else tempLine = null;
        }
    }

    private void addToShapes(Shape s) {
        PaintInfo.getInstance().getShapes().add(s);
        PaintInfo.getInstance().updateThumbnail();
    }

}
