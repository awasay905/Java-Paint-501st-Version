package uicomponents.toolbars;

import res.ResourceManager;
import uicomponents.Textbox;
import uicomponents.buttons.ColorButton;
import uicomponents.buttons.ToggleButton;

import javax.swing.*;
import java.awt.*;

/**
 * A toolbar used in window object. it is a toolbar which displays a name and three buttons, for decoration purpose they are 3 but they perform same function
 */
public class Titlebar extends Toolbar {
    /**
     * the title of the window
     */
    private final Textbox title;

    /** Constructor
     * Initializes the titlebar. It makes the title draw on the left. It initializes the three buttons which shows text hint of closing window
     * @param x      the x coordinate of the menubar according to java graphics coordinates
     * @param y      the y coordinate of the menubar according to java graphics coordinates
     * @param width  the width of the menubar
     * @param height the height of the menubar
     * @param title  the title of the windows
     */
    public Titlebar(int x, int y, int width, int height, String title) {
        super(x, y, width, height);
        this.title = new Textbox(x, y, width, height, title, Color.BLACK, true);
        this.title.setAlignment(Textbox.ALIGN_LEFT);

        int y1 = y + (height - (height * 75 / 100)) / 2;
        int side = height * 75 / 100;

        add(new ColorButton(x + width - height * 3, y1, side, side, ResourceManager.empty, ResourceManager.empty, Color.RED, -1, null));
        add(new ColorButton(x + width - height * 2, y1, side, side, ResourceManager.empty, ResourceManager.empty, Color.GREEN, -1, null));
        add(new ColorButton(x + width - height, y1, side, side, ResourceManager.empty, ResourceManager.empty, Color.BLUE, -1, null));

        for (ToggleButton tb : getButtons()) {
            tb.setHelpText("Close Window");
        }
    }

    /**
     * Draws the titlebar. First it draws a rectagnle, then the title, then the buttons
     * @param g graphics object
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(getX(), getY(), getWidth(), getHeight());

        g.setColor(Color.GRAY);
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        title.setBorderColor(Color.BLACK);
        title.setTextColor(Color.WHITE);
        title.draw(g);

        for (ToggleButton b : getButtons()) {
            b.draw(g);
        }
    }


}
