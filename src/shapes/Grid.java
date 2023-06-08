package shapes;

import java.awt.*;

public class Grid {
    private static Grid instance;
    private Point p1;
    private Point p2;
    private int gridSize;

    private Grid() {
        gridSize = 0;
    }

    public static Grid getInstance() {
        if (instance == null) instance = new Grid();
        return instance;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void increaseGridSize() {
        if (gridSize == 0) {
            gridSize = 2;
            return;
        }
        gridSize += gridSize;
        gridSize %= 128;
    }

    public void setCoordinates(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void draw(Graphics g) {
        if (gridSize == 0) return;
        g.setColor(Color.LIGHT_GRAY);
        for (int i = p1.getX(); i < p2.getX(); i += gridSize) {
            g.drawLine(i, p1.getY(), i, p2.getY());
        }

        for (int i = p1.getY(); i < p2.getY(); i += gridSize) {
            g.drawLine(p1.getX(), i, p2.getX(), i);
        }
    }
}
