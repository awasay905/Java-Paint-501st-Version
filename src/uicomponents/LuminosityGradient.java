package uicomponents;

import uicomponents.buttons.ColorButton;

import java.awt.*;

public class LuminosityGradient {
    private static LuminosityGradient instance;
    private int x;
    private int y;
    private final ColorButton[] luminosity;

    private LuminosityGradient() {
        luminosity = new ColorButton[256];
    }

    public static LuminosityGradient getInstance() {
        if (instance == null) instance = new LuminosityGradient();
        return instance;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void generateLuminosity(int x, int y) {
        for (int i = 0; i < 256; i++) {
            luminosity[i] = new ColorButton(x, y + i, 15, 1, new Color(i, i, i), (MouseListener) () -> {
            });
            luminosity[i].setHelpText("Brightness: " + String.format("%.0f", i / 256.0 * 100));
        }
    }

    public void drawLuminosity(Graphics g) {
        for (int i = 0; i < 256; i++) {
            luminosity[i].draw(g);
        }
    }

    public void onHover(int x, int y) {
        for (int i = 0; i < 256; i++) {
            luminosity[i].onHover(x, y);
        }
    }

    public void setTooltipCoordinates(int x, int y) {
        for (int i = 0; i < 256; i++) {
            luminosity[i].setTooltipCoordinates(x, y);
        }
    }

    public void drawTooltip(Graphics g) {
        for (int i = 0; i < 256; i += 2) {
            luminosity[i].drawTooltip(g);
        }
    }

    public boolean luminosityIsClicked(int x, int y) {
        return x >= this.x && x < this.x + 15 && y >= this.y && y < this.y + 256;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ColorButton[] getColorButtons() {
        return luminosity;
    }
}
