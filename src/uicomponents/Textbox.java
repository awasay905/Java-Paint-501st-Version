package uicomponents;

import java.awt.*;
import java.io.Serializable;

public class Textbox extends Rectangle implements Serializable {
    public static int ALIGN_CENTER = 1;
    public static int ALIGN_LEFT = 2;
    public static int ALIGN_RIGHT = 3;
    private String text;
    private Color textColor;
    private boolean hasBorder;
    private Color borderColor;
    private int textSize;
    private int alignment;

    public Textbox(int x, int y, int width, int height, String text) {
        super(x, y, width, height);
        this.text = text;
        textColor = Color.black;
        hasBorder = false;
        borderColor = Color.white;
        alignment = ALIGN_CENTER;
        textSize = -1;
    }

    public Textbox(int x, int y, int width, int height, String text, Color textColor, boolean hasBorder) {
        super(x, y, width, height);
        this.text = text;
        this.textColor = textColor;
        this.hasBorder = hasBorder;
        borderColor = Color.black;
        alignment = ALIGN_CENTER;
        textSize = -1;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void onClick(int x, int y) {
    }

    @Override
    public void draw(Graphics g) {
        //First calculates font size
        if (textSize == -1) textSize = calculateFontSize(g);
        g.setFont(new Font(Font.SERIF, Font.PLAIN, textSize));
        FontMetrics fm = g.getFontMetrics();

        //Now it draws the border
        if (hasBorder) {
            g.setColor(borderColor);
            g.drawRect(getX(), getY(), getWidth(), getHeight());
        }

        g.setColor(textColor);
        if (alignment == ALIGN_CENTER) {
            g.drawString(text, getX() + getWidth() / 2 - fm.stringWidth(text) / 2, getY() + getHeight() / 2 + fm.getAscent() - fm.getHeight() / 2);
        } else if (alignment == ALIGN_LEFT) {
            g.drawString(text, getX() + (int) (fm.stringWidth(text) * 0.10), getY() + getHeight() / 2 + fm.getAscent() - fm.getHeight() / 2);
        } else {
            g.drawString(text, getX() + getWidth() - (int) (fm.stringWidth(text) * 1.10), getY() + getHeight() / 2 + fm.getAscent() - fm.getHeight() / 2);

        }
    }

    private int calculateFontSize(Graphics g) {
        int tempFontSize = getHeight();
        Font tempFont = new Font("Arial", Font.PLAIN, tempFontSize);
        FontMetrics tempFontMetric = g.getFontMetrics(tempFont);
        int textWidth = tempFontMetric.stringWidth(this.text);
        int textHeight = tempFontMetric.getHeight();

        while (textWidth >= 0.76 * getWidth() || textHeight >= 0.85 * getHeight()) {
            tempFontSize--;
            tempFont = new Font(Font.SERIF, Font.PLAIN, tempFontSize);
            tempFontMetric = g.getFontMetrics(tempFont);
            textWidth = tempFontMetric.stringWidth(this.text);
            textHeight = tempFontMetric.getHeight();
        }

        return tempFontSize;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }


    public void setHasBorder(boolean hasBorder) {
        this.hasBorder = hasBorder;
    }


    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }


    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
}
