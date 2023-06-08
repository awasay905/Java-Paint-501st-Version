package uicomponents.buttons;

import uicomponents.MouseListener;

import javax.swing.*;
import java.awt.*;

public class ActiveButton extends ToggleButton {
    public ActiveButton(int x, int y, int width, int height, Image onPress, Image dePress, String text, int shortcutKey, MouseListener mouseListener) {
        super(x, y, width, height, onPress, dePress, text, shortcutKey, mouseListener);
    }

    @Override
    public void onClick(int x, int y) {
        super.onClick(x, y);
        //After clicking, the button is set to false
        //with 50ms delay so user can see button getting clicked
        Timer clickResetter = new Timer(50, e -> setPressed(false));
        clickResetter.setRepeats(false);
        clickResetter.start();
    }
}
