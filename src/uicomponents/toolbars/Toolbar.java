package uicomponents.toolbars;

import uicomponents.Rectangle;
import uicomponents.buttons.ToggleButton;

import java.awt.*;
import java.util.ArrayList;

public class Toolbar extends Rectangle {
    private ArrayList<ToggleButton> toggleButtons;

    public Toolbar(int x, int y, int width, int height) {
        super(x, y, width, height);
        toggleButtons = new ArrayList<>();
    }

    public ArrayList<ToggleButton> getButtons() {
        return toggleButtons;
    }

    public void setToggleButtons(ArrayList<ToggleButton> toggleButtons) {
        this.toggleButtons = toggleButtons;
    }

    public void add(ToggleButton b) {
        toggleButtons.add(b);
    }

    @Override
    public void onClick(int x, int y) {
        for (ToggleButton toggleButton : toggleButtons) {
            toggleButton.onClick(x, y);
            if (toggleButton.isClicked(x, y)) {
                return;
            }
        }
    }

    @Override
    public boolean isClicked(int x, int y) {
        if (super.isClicked(x, y)) return true;
        for (ToggleButton toggleButton : toggleButtons) {
            toggleButton.onClick(x, y);
            if (toggleButton.isClicked(x, y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onHover(int x, int y) {
        for (ToggleButton b : toggleButtons) {
            b.onHover(x, y);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getBackgroundColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        for (ToggleButton b : toggleButtons) {
            b.draw(g);
        }

    }

    public void drawToolTips(Graphics g) {
        for (ToggleButton b : toggleButtons) {
            b.drawTooltip(g);
        }
    }


    public void setTooltipsCoordinates(int x, int y) {
        for (ToggleButton tb : toggleButtons) {
            tb.setTooltipCoordinates(x, y);
        }
    }

    public void shortcutKeyPressed(int keyCode) {
        for (ToggleButton b : toggleButtons) {
            b.shortcutKeyPressed(keyCode);
        }
    }
}
