package uicomponents.buttons;

import uicomponents.MouseListener;

import java.awt.*;
import java.util.ArrayList;

public class MenuButton extends ToggleButton {
    private final ArrayList<ToggleButton> menuToggleButtons;
    private boolean isOpen;

    public MenuButton(int x, int y, int width, int height, Image onPress, Image dePress, String text, int shortcutKey, MouseListener mouseListener) {
        super(x, y, width, height, onPress, dePress, text, shortcutKey, mouseListener);
        isOpen = false;
        setPressed(false);
        menuToggleButtons = new ArrayList<>();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void add(ToggleButton b) {
        menuToggleButtons.add(b);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        if (isOpen) {
            g.setColor(new Color(0, 0, 0, 95));
            g.fillRect(0, 0, 10000, 10000);
            for (ToggleButton b : menuToggleButtons) {
                b.draw(g);
            }
        }
    }

    @Override
    public void onClick(int x, int y) {
        if (isClicked(x, y)) {
            isOpen = !isOpen;
            setPressed(isOpen);
            return;
        }

        if (isOpen) {
            for (ToggleButton b : menuToggleButtons) {
                if (b.isClicked(x, y)) {
                    b.onClick(x, y);
                    isOpen = false;
                    setPressed(false);
                    return;
                }
            }
        }
    }

    @Override
    public void onHover(int x, int y) {
        super.onHover(x, y);

        for (ToggleButton b : menuToggleButtons) {
            b.onHover(x, y);
        }
    }

    @Override
    public boolean shortcutKeyPressed(int keyCode) {
        if (super.shortcutKeyPressed(keyCode)) return true;
        for (ToggleButton b : menuToggleButtons) {
            if (b.shortcutKeyPressed(keyCode)) return true;
        }
        return false;
    }

    public ArrayList<ToggleButton> getMenuToggleButtons() {
        return menuToggleButtons;
    }
}
