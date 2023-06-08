package uicomponents.windows;

import uicomponents.Rectangle;
import uicomponents.buttons.ToggleButton;
import uicomponents.toolbars.Titlebar;
import uicomponents.toolbars.Toolbar;

import java.awt.*;
import java.util.ArrayList;

public class Window extends Rectangle {
    private final Titlebar titlebar;
    private final ArrayList<Toolbar> toolbars;
    private boolean isOpen;

    public Window(int x, int y, int width, int height, String name) {
        super(x, y, width, height);
        titlebar = new Titlebar(x, y, width, 30, name);
        toolbars = new ArrayList<>();
    }

    public void drawToolTips(Graphics g) {

        for (ToggleButton b : titlebar.getButtons()) {
            b.drawTooltip(g);
        }

    }

    public void setTooltipsCoordinates(int x, int y) {
        for (ToggleButton tb : titlebar.getButtons()) {
            tb.setTooltipCoordinates(x, y);
        }
    }

    public void addToolbar(Toolbar tb) {
        toolbars.add(tb);
    }

    @Override
    public void onClick(int x, int y) {
        if (isOpen) {

            for (ToggleButton b : titlebar.getButtons()) {
                if (b.isClicked(x, y)) {
                    isOpen = false;
                    return;
                }
            }

            for (Toolbar tb : toolbars) {
                tb.onClick(x, y);
            }
        }
    }

    @Override
    public void onHover(int x, int y) {
        for (ToggleButton b : titlebar.getButtons()) {
            b.onHover(x, y);
        }
    }

    @Override
    public void draw(Graphics g) {
        if (isOpen) {
            g.setColor(Color.black);
            g.drawRect(getX(), getY(), getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.fillRect(getX() + 1, getY(), getWidth() - 1, getHeight());
            titlebar.draw(g);
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        isOpen = true;
    }

    public void close() {
        isOpen = false;
    }

    public int getTitlebarHeight() {
        return this.titlebar.getHeight();
    }

}
