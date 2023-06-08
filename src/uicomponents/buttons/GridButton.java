package uicomponents.buttons;

import uicomponents.MouseListener;

import java.awt.*;

public class GridButton extends ActiveButton{
    Image gridOff;
    Image grid2;
    Image grid4;
    Image grid8;
    Image grid16;
    Image grid32;
    Image grid64;

    public GridButton(int x, int y, int width, int height, String text, int shortcutKey, MouseListener mouseListener) {
        super(x, y, width, height, null, null, text, shortcutKey, mouseListener);
    }
}
