package uicomponents.buttons;

import uicomponents.MouseListener;

import javax.swing.*;
import java.awt.*;

public class ColorButton extends ToggleButton {

    public ColorButton(int x, int y, int width, int height, Image onPress, Image dePress, Color color, int shortcutKey, MouseListener mouseListener) {
        super(x, y, width, height, onPress, dePress, "", shortcutKey, mouseListener);
        setBackgroundColor(color);
    }

    public ColorButton(int x, int y, int width, int height, Color color, MouseListener mouseListener) {
        super(x, y, width, height, new ImageIcon("empty").getImage(), new ImageIcon("empty").getImage(), "", -1, mouseListener);
        setBackgroundColor(color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getBackgroundColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        super.draw(g);
    }
}
