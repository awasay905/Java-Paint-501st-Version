package uicomponents;

import uicomponents.buttons.ColorButton;

import java.awt.*;

public class ColorGradient implements Runnable{
    private static ColorGradient instance;
    private final ColorButton[][] gradient;
    private int x;
    private int y;

    private ColorGradient() {
        gradient = new ColorButton[256][256];
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                gradient[i][j] = new ColorButton(i + x, j + y, 1, 1, Color.getHSBColor((256 - i) / 256.0f, j / 256.0f, 0.8f), (MouseListener) () -> {
                });
            }
        }
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
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                gradient[i][j].setX(i + x);
                gradient[i][j].setY(j + y);

            }
        }
    }

    public void generateColor(float lum) {
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                gradient[i][j].setBackgroundColor(Color.getHSBColor((256 - i) / 256.0f, j / 256.0f, lum));
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

        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                gradient[i][j].setX(i + x);
                gradient[i][j].setY(j + y);
            }
        }
    }

    public ColorButton[][] getColorButtons() {
        return gradient;
    }

    @Override
    public void run() {

    }
}
