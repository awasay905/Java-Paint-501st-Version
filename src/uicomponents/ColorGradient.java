package uicomponents;

import uicomponents.buttons.ColorButton;

import java.awt.*;

public class ColorGradient {
    private static ColorGradient instance;
    private int x;
    private int y;
    private final ColorButton[][] gradient;

    private ColorGradient() {
        gradient = new ColorButton[256][256];
    }

    public static ColorGradient getInstance() {
        if (instance == null) instance = new ColorGradient();
        return instance;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void generateColor(int x, int y, float lum) {
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                gradient[i][j] = new ColorButton(i + x, j + y, 1, 1, Color.getHSBColor((256 - i) / 256.0f, j / 256.0f, lum), (MouseListener) () -> {
                });
            }
        }
    }

    public void drawGradient(Graphics g) {
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                gradient[i][j].draw(g);
            }
        }
    }

    public boolean gradientIsClicked(int x, int y) {
        return x >= this.x && x < this.x + 256 && y >= this.y && y < this.y + 256;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ColorButton[][] getColorButtons() {
        return gradient;
    }
}
