package uicomponents.toolbars;

import uicomponents.Textbox;
import uicomponents.buttons.ColorButton;
import uicomponents.buttons.ToggleButton;

import javax.swing.*;
import java.awt.*;

public class Titlebar extends Toolbar {
    private final Textbox title;

    public Titlebar(int x, int y, int width, int height, String title) {
        super(x, y, width, height);
        this.title = new Textbox(x, y, width, height, title, Color.BLACK, true);
        this.title.setAlignment(Textbox.ALIGN_LEFT);

        int y1 = y + (height - (height * 75 / 100)) / 2;
        int side = height * 75 / 100;

        add(new ColorButton(x + width - height * 3, y1, side, side, new ImageIcon("empty").getImage(), new ImageIcon("empty").getImage(), Color.RED, -1, null));
        add(new ColorButton(x + width - height * 2, y1, side, side, new ImageIcon("empty").getImage(), new ImageIcon("empty").getImage(), Color.GREEN, -1, null));
        add(new ColorButton(x + width - height, y1, side, side, new ImageIcon("empty").getImage(), new ImageIcon("empty").getImage(), Color.BLUE, -1, null));

        for (ToggleButton tb : getButtons()) {
            tb.setHelpText("Close Window");
        }
    }

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
