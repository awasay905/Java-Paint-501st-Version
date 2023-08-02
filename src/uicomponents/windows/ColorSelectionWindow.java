package uicomponents.windows;

import res.ResourceManager;
import uicomponents.ColorGradient;
import uicomponents.LuminosityGradient;
import uicomponents.MouseListener;
import uicomponents.Textbox;
import uicomponents.buttons.ActiveButton;

import javax.swing.*;
import java.awt.*;

public class ColorSelectionWindow extends Window {
    private static ColorSelectionWindow instance;
    private final Textbox c1, c2, c3;  //Color textboxes
    private final Textbox l1, l2, l3; //Label Textboxes
    private final ActiveButton setColor;
    private Color currentColor;
    private Color selectedColor;

    private ColorSelectionWindow(int x, int y, int width, int height, String name) {
        super(x, y, width, height, name);
        selectedColor = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));

        l1 = new Textbox(x + 20 + 50 + 20, y + 256 + 20 + getTitlebarHeight() + 20, 40, 25, "R", Color.BLACK, true);
        l2 = new Textbox(x + 20 + 50 + 20 + 40, y + 256 + 20 + getTitlebarHeight() + 20, 40, 25, "G", Color.BLACK, true);
        l3 = new Textbox(x + 20 + 50 + 20 + 40 + 40, y + 256 + 20 + getTitlebarHeight() + 20, 40, 25, "B", Color.BLACK, true);

        c1 = new Textbox(x + 20 + 50 + 20, y + 256 + 20 + getTitlebarHeight() + 20 + 25, 40, 25, selectedColor.getRed() + "", Color.BLACK, true);
        c2 = new Textbox(x + 20 + 50 + 20 + 40, y + 256 + 20 + getTitlebarHeight() + 20 + 25, 40, 25, selectedColor.getGreen() + "", Color.BLACK, true);
        c3 = new Textbox(x + 20 + 50 + 20 + 40 + 40, y + 256 + 20 + getTitlebarHeight() + 20 + 25, 40, 25, selectedColor.getBlue() + "", Color.BLACK, true);

        c1.setTextSize(20);
        c2.setTextSize(20);
        c3.setTextSize(20);
        l1.setTextSize(20);
        l2.setTextSize(20);
        l3.setTextSize(20);

        ColorGradient.getInstance().setX(x + 20);
        ColorGradient.getInstance().setY(y + getTitlebarHeight() + 20);

        LuminosityGradient.getInstance().setX(ColorGradient.getInstance().getX() + 256 + 15);
        LuminosityGradient.getInstance().setY(ColorGradient.getInstance().getY());

        ColorGradient.getInstance().generateColor(ColorGradient.getInstance().getX(), ColorGradient.getInstance().getY(), 0.8f);
        LuminosityGradient.getInstance().generateLuminosity(LuminosityGradient.getInstance().getX(), LuminosityGradient.getInstance().getY());

        setColor = new ActiveButton(x + 20 + 256 + 15 + 10 - 40 - 40 + 5 - 5, y + 256 + 20 + getTitlebarHeight() + 20, 40 + 40 + 6, 50, ResourceManager.empty, ResourceManager.empty, "SET", -1, new MouseListener() {
            @Override
            public void onClick() {
                currentColor = selectedColor;
            }
        });
        setColor.setHelpText("Set Current Color into box");
    }


    @Override
    public void draw(Graphics g) {
        if (isOpen()) {
            super.draw(g);
            ColorGradient.getInstance().drawGradient(g);
            LuminosityGradient.getInstance().drawLuminosity(g);

            g.setColor(selectedColor);
            g.fillRect(getX() + 20, getY() + 256 + getTitlebarHeight() + 20 + 20, 50, 50);
            g.setColor(Color.BLACK);
            g.drawRect(getX() + 20, getY() + 256 + getTitlebarHeight() + 20 + 20, 50, 50);

            l1.draw(g);
            l2.draw(g);
            l3.draw(g);

            c1.draw(g);
            c2.draw(g);
            c3.draw(g);

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(getX() + 20 + 256 + 15 + 5 + 10 - 40 - 40 - 5, getY() + 256 + 20 + getTitlebarHeight() + 20, 40 + 40 + 5, 50);
            g.setColor(Color.BLACK);
            g.drawRect(getX() + 20 + 256 + 15 + 5 + 10 - 40 - 40 - 5, getY() + 256 + 20 + getTitlebarHeight() + 20, 40 + 40 + 5, 50);
            setColor.draw(g);

        }
    }


    @Override
    public void onClick(int x, int y) {
        if (isOpen()) {
            super.onClick(x, y);
            setColor.onClick(x, y);

            if (setColor.isClicked(x, y)) {
                setColor.setBackgroundColor(currentColor);
            }

            if (ColorGradient.getInstance().gradientIsClicked(x, y)) {
                selectedColor = ColorGradient.getInstance().getColorButtons()[x - ColorGradient.getInstance().getX()][y - ColorGradient.getInstance().getY()].getBackgroundColor();

                c1.setText(selectedColor.getRed() + "");
                c2.setText(selectedColor.getGreen() + "");
                c3.setText(selectedColor.getBlue() + "");
            }

            if (LuminosityGradient.getInstance().luminosityIsClicked(x, y)) {
                ColorGradient.getInstance().generateColor(ColorGradient.getInstance().getX(), ColorGradient.getInstance().getY(), LuminosityGradient.getInstance().getColorButtons()[y - LuminosityGradient.getInstance().getY()].getBackgroundColor().getBlue() / 256.0f);
            }
        }
    }

    public ActiveButton set() {
        return setColor;
    }

    public Color getSelectedColor() {
        return currentColor;
    }

    @Override
    public void onHover(int x, int y) {
        super.onHover(x, y);
        setColor.onHover(x, y);
        LuminosityGradient.getInstance().onHover(x, y);
    }

    @Override
    public void drawToolTips(Graphics g) {
        super.drawToolTips(g);
        setColor.drawTooltip(g);
        LuminosityGradient.getInstance().drawTooltip(g);
    }

    @Override
    public void setTooltipsCoordinates(int x, int y) {
        super.setTooltipsCoordinates(x, y);
        setColor.setTooltipCoordinates(x, y);
        LuminosityGradient.getInstance().setTooltipCoordinates(x, y);
    }

    public static ColorSelectionWindow getInstance(int x, int y, int width, int height, String name){
        if (instance == null) instance = new ColorSelectionWindow(x,y,width,height,name);
        return instance;
    }
}
