package uicomponents.toolbars;

import uicomponents.Rectangle;
import uicomponents.buttons.ToggleButton;

import java.awt.*;
import java.util.ArrayList;

/**
 * The basic class for toolbars. It can be used to group together buttons.
 */

public class Toolbar extends Rectangle {
    /**
     * the arraylist containg the buttons of the toolbar
     */
    private ArrayList<ToggleButton> toggleButtons;

    /**
     *A basic constructor which initializes the toolbar
     * @param x      the x coordinate of the toolbar according to java graphics coordinates
     * @param y      the y coordinate of the toolbar according to java graphics coordinates
     * @param width  the width of the toolbar
     * @param height the height of the toolbar
     */
    public Toolbar(int x, int y, int width, int height) {
        super(x, y, width, height);
        toggleButtons = new ArrayList<>();
    }

    /**
     * The get the arraylist of the button stored in the toolbar
     * @return returns the arraylist of buttons
     */
    public ArrayList<ToggleButton> getButtons() {
        return toggleButtons;
    }

    /**
     * Used by subclasses to add more buttons to the arrayList of buttons
     * @param toggleButtons button to add to arrayList of buttons
     */
    public void setToggleButtons(ArrayList<ToggleButton> toggleButtons) {
        this.toggleButtons = toggleButtons;
    }

    public void add(ToggleButton b) {
        toggleButtons.add(b);
    }

    /**
     * The function which is called when the toolbar is clicked.
     * First it checks if the toolbar area is clicked, if not, it returns.
     * It checks if any button is clicked, if it is, then it
     * calls the button onClick and returns so that it don't need to check every button
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     */
    @Override
    public void onClick(int x, int y) {
        if (!isClicked(x,y)) return; //should not cause problem
        for (ToggleButton toggleButton : toggleButtons) {
            //toggleButton.onClick(x, y); why was this called before?
            if (toggleButton.isClicked(x, y)) {
                toggleButton.onClick(x,y);
                return;
            }
        }
    }

    /**
     * Checks if toolbar or it's buttons are clicked or not.
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     * @return true if the toolbar's covered area or its button is clicked, else false
     */
    @Override
    public boolean isClicked(int x, int y) {
        if (super.isClicked(x, y)) return true;
        for (ToggleButton toggleButton : toggleButtons) {
           // toggleButton.onClick(x, y); No need to call onClick of button as we jsut need to check if button it pressed
            if (toggleButton.isClicked(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * It sets the isHover boolean value based on the position of the mouse
     * If the mouse is above the components, isHover becomes true
     * Otherwise it is false.
     * This override calls the onHover of all the buttons in the toolbar
     * @param x the x-coordinate of the mouse
     * @param y the y-cooridnate of the mouse
     */
    @Override
    public void onHover(int x, int y) {
        for (ToggleButton b : toggleButtons) {
            b.onHover(x, y);
        }
    }

    /**
     * Draws the toolbar. First it draws a rectangle matching the color of the toolbar,
     * then it draws all the buttons
     * @param g graphics object
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(getBackgroundColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        for (ToggleButton b : toggleButtons) {
            b.draw(g);
        }

    }

    /**
     * Draws the tooltip on the passed graphics object
     * @param g the graphics object
     */
    public void drawToolTips(Graphics g) {
        for (ToggleButton b : toggleButtons) {
            b.drawTooltip(g);
        }
    }

    /**
     * Sets the coodinates for the tooltip
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     */
    public void setTooltipsCoordinates(int x, int y) {
        for (ToggleButton tb : toggleButtons) {
            tb.setTooltipCoordinates(x, y);
        }
    }

    /**
     * Processes the shortcuts
     * @param keyCode the ascii code of the key pressed
     */
    public void shortcutKeyPressed(int keyCode) {
        for (ToggleButton b : toggleButtons) {
            b.shortcutKeyPressed(keyCode);
        }
    }
}
