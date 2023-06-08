package uicomponents;

import java.awt.*;

public class Tooltip{
    private static Tooltip instance;
    private int x;
    private int y;
    private String helpText;

    private Tooltip(){
        x=0;
        y=0;
        helpText="";
    }

    public static Tooltip getInstance(){
        if (instance==null) instance = new Tooltip();
        return instance;
    }

    public void setCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setHelpText(String helpText){
        this.helpText = helpText;
    }
    public void draw(Graphics g){
        int cornerX = x + 10;
        int cornerY = y + 10;

        g.setFont(new Font(null, Font.PLAIN, 12));
        FontMetrics fm = g.getFontMetrics();
        int stringWidth = fm.stringWidth(helpText);
        if (cornerX + stringWidth > 1080) cornerX = 1080 - stringWidth - 2;

        g.setColor(new Color(161, 151, 12, 220));
        g.fillRect(cornerX, cornerY, stringWidth + 2, fm.getHeight() + 2);
        g.setColor(new Color(152, 255, 255, 220));
        g.fillRect(cornerX + 1, cornerY + 1, stringWidth, fm.getHeight());
        g.setColor(new Color(0, 0, 0, 220));
        g.drawString(helpText, cornerX, cornerY + fm.getHeight() / 2 + fm.getAscent() / 2);
    }

}
