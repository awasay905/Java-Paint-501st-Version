package uicomponents;

import java.awt.*;
import java.io.Serializable;

public abstract class Rectangle implements Serializable {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color backgroundColor;
    private boolean isHover;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.backgroundColor = Color.white;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isClicked(int x, int y) {
        return ((x >= this.x) && (x <= this.x + width) && (y >= this.y) && (y <= this.y + height));
    }

    public abstract void onClick(int x, int y);

    public boolean isHover() {
        return isHover;
    }

    public void setHover(boolean isHover) {
        this.isHover = isHover;
    }

    public void onHover(int x, int y) {
        isHover = isClicked(x, y);
    }

    public abstract void draw(Graphics g);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
