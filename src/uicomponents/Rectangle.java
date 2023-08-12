package uicomponents;

import java.awt.*;
import java.io.Serializable;

/**
 * The abstract class for all the ui components in the app. The logic i use is that
 * all components are a rectangle, i.e. al have a x and y and some width and height
 */
public abstract class Rectangle implements Serializable {
    /**
     * The x-coordinate of the top-left corner of the rectangle
     */
    private int x;
    /**
     * The y-coordinate of the top-left corner of the rectangle
     */
    private int y;
    /**
     * The width of the rectangle
     */
    private int width;
    /**
     * The height of the rectangle
     */
    private int height;
    /**
     * The background of the rectangle
     */
    private Color backgroundColor;
    /**
     * Tells if mouse is hovering over the component
     */
    private boolean isHover;

    /**
     * Constructs a basic rectangle with white background color
     *
     * @param x      The x-coordinate of the top-left corner of the rectangle
     * @param y      The y-coordinate of the top-left corner of the rectangle
     * @param width  The width of the rectangle
     * @param height The height of the rectangle
     */

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.backgroundColor = Color.white;
    }

    /**
     * The getter for the background color of the rectangle
     *
     * @return the background color of the rectangle
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * The setter for the background color of the rectangle
     *
     * @param backgroundColor the background color of the rectangle
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Checks if the rectangle is clicked by seeing if the mouse cooridnate is
     * in the area of the rectangle
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordiante of the mouse
     * @return true if the rectangle is clicked, else false
     */
    public boolean isClicked(int x, int y) {
        return ((x >= this.x) && (x <= this.x + width) && (y >= this.y) && (y <= this.y + height));
    }

    /**
     * The function which is called when the rectangle is clicked
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordiante of the mouse
     */
    public abstract void onClick(int x, int y);

    /**
     * Checks if the mouse is above the rectangle
     *
     * @return true if the mouse is on the rectangle , else false
     */
    public boolean isHover() {
        return isHover;
    }

    /**
     * Sets the hover boolean to custom value. Useful in case when you
     * want to forcefully hide hover
     *
     * @param isHover the desired boolean value to set for the hover variable
     */
    public void setHover(boolean isHover) {
        this.isHover = isHover;
    }

    /**
     * It sets the isHover boolean value based on the position of the mouse
     * If the mouse is above the component, isHover becomes true
     * Otherwise it is false
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-cooridnate of the mouse
     */
    public void onHover(int x, int y) {
        isHover = isClicked(x, y);
    }

    /**
     * Te function to draw the rectangle
     *
     * @param g graphics objectt
     */
    public abstract void draw(Graphics g);

    /**
     * The getter for the x coordinate of the rectangle
     *
     * @return the x coordinate for the top left corner of the rectangle
     */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * The getter for the y coordinate of the rectangle
     *
     * @return the y  coordinate for the top left corner of the rectangle
     */

    public int getY() {
        return y;
    }

    /**
     * Sets the top-left y coordinate of the rectangle
     * useful when you want to rearrnegt he rectangle during code
     *
     * @param y the new  top left y-coordinate for the rectangle
     */

    public void setY(int y) {
        this.y = y;
    }

    /**
     * The getter for the width of the rectangle
     *
     * @return the width  of the rectangle
     */

    public int getWidth() {
        return width;
    }

    /**
     * The getter for the height of the rectangle
     *
     * @return the height  of the rectangle
     */

    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the rectangle
     * useful when you want to resize the rectangle during code
     *
     * @param height the new height for the rectangle
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
