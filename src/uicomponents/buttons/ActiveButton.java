package uicomponents.buttons;

import uicomponents.MouseListener;

import javax.swing.*;
import java.awt.*;

/**
 * The button which does not remain pressed.
 */
public class ActiveButton extends ToggleButton {
    /**
     * Constructor to make the button. same as super constructor
     * @param x the x-coordinate of the top-left point of the button
     * @param y the y-coordinate of the top-left point of the button
     * @param width the width of the button
     * @param height the height of the button
     * @param onPress the image to show when the button is pressed. it is scaled to button dimensions
     * @param dePress the image to show when the button is depressed. it is scaled to button dimensions
     * @param text the text to show on top of image
     * @param shortcutKey the shortcut key for the button
     * @param mouseListener the function to be called when the button is clicked
     */
    public ActiveButton(int x, int y, int width, int height, Image onPress, Image dePress, String text, int shortcutKey, MouseListener mouseListener) {
        super(x, y, width, height, onPress, dePress, text, shortcutKey, mouseListener);
    }

    /**
     * The function which is called when the button is clicked.
     * It calls the super onClick which in turn calls the mouseListener.
     * The super onClick causes the button to become 'pressed'.
     * Then a timer is started which depresses the button after 50ms.
     * @param x the x-xoordinate of the mouse
     * @param y the y-coordiate of the mouse
     */
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
