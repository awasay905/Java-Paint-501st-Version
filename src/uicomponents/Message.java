package uicomponents;

import swingComponents.Board;

import javax.swing.*;
import java.awt.*;

public class Message extends Rectangle{
    private static Message instance;
    private boolean showMessage;
    private String message;

    public static void showMessage(String message, int durationInSec){
        if (instance == null) instance = new Message();
        instance.message = message;
        instance.showMessage = true;

        new Timer(durationInSec*1000, (j)->{instance.showMessage = false;
            instance.message = "";}).start();
    }
    public Message() {
        super(0, Board.BOARD_HEIGHT-40, 0, 30);
    }

    @Override
    public void onClick(int x, int y) {
    }

    @Override
    public void draw(Graphics g) {
    }

    public static void drawMessage(Graphics g){
        if (instance != null && instance.showMessage){
            Font f = new Font(Font.DIALOG, Font.ITALIC, 15);
            FontMetrics fontMetrics = g.getFontMetrics(f);
            int stringWidth = fontMetrics.stringWidth(instance.message);

            g.setColor(new Color(37, 66, 77, 94));
            g.fillRoundRect(10, instance.getY(), stringWidth + 15, 30, 10,10);
            g.setColor(new Color(0, 0, 0, 129));
            g.drawRoundRect(10, instance.getY(), stringWidth + 15, 30, 10,10);
            g.setFont(f);
            g.drawString(instance.message, 15, instance.getY()+15+ fontMetrics.getAscent() - fontMetrics.getHeight() / 2);
        }
    }
}
