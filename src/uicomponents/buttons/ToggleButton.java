package uicomponents.buttons;

import uicomponents.*;
import uicomponents.Rectangle;

import java.awt.*;
import java.io.Serializable;

public class ToggleButton extends Rectangle implements HoverActions, Serializable {
    private transient Image onPress;
    private transient Image dePress;
    private final String text;
    private int textSize;
    private final int shortcutKey;
    private boolean isPressed;
    private transient Image current;
    private Color textColor;
    private MouseListener mouseListener;
    private String helpText;
    private int tooltipX = 0;
    private int tooltipY = 0;

    public ToggleButton(int x, int y, int width, int height, Image onPress, Image dePress, String text, int shortcutKey, MouseListener mouseListener) {
        super(x, y, width, height);
        this.onPress = onPress.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        this.dePress = dePress.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        this.current = this.dePress;
        this.text = text;
        this.textColor = Color.black;
        this.shortcutKey = shortcutKey;
        this.mouseListener = mouseListener;
        helpText = "";
        textSize = -1;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
        if (pressed) {
            current = onPress;
            textColor = Color.white;
        } else {
            current = dePress;
            textColor = Color.black;
        }
    }

    @Override
    public void onClick(int x, int y) {
        if (isClicked(x, y)) {
            isPressed = !isPressed;
            setPressed(isPressed);
            //if (isPressed) mouseListener.onClick();
            //temporarily removing this
            mouseListener.onClick();
        }
    }

    @Override
    public void draw(Graphics g) {
        //First Draw img
        g.drawImage(current, getX(), getY(), null);

        if (isHover()) {
            g.setColor(new Color(72, 66, 66, 94));
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }

        //Now Draw Text on Center
        if (textSize == -1) textSize = calculateFontSize(g);
        Font font = new Font("Arial", Font.PLAIN, textSize);
        g.setFont(font);
        g.setColor(textColor);
        FontMetrics m = g.getFontMetrics();
        int s_width = m.stringWidth(text);
        int s_height = m.getAscent() - m.getDescent();
        g.drawString(text, getX() + getWidth() / 2 - s_width / 2, getY() + getHeight() / 2 + s_height / 2);

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

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }


    public boolean shortcutKeyPressed(int keyCode) {
        if (keyCode == shortcutKey) {
            onClick(getX(), getY());
            return true;
        }
        return false;
    }

    public void setTooltipCoordinates(int x, int y) {
        Tooltip.getInstance().setCoordinates(x, y);
    }

    @Override
    public void drawTooltip(Graphics g) {
        if (isHover()) {
            Tooltip.getInstance().setHelpText(helpText);
            Tooltip.getInstance().draw(g);
        }
    }

    public Image getCurrent() {
        return current;
    }

    public void setOnPress(Image onPress) {
        this.onPress = onPress;
    }
    public void setDePress(Image dePress) {
        this.dePress = dePress;
    }
}
