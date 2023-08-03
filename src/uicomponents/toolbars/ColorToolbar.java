package uicomponents.toolbars;

import helper.PaintInfo;
import res.ResourceManager;
import uicomponents.Shortcuts;
import uicomponents.buttons.ActiveButton;
import uicomponents.buttons.ColorButton;
import uicomponents.buttons.ToggleButton;
import uicomponents.windows.ColorSelectionWindow;

import java.awt.*;

public class ColorToolbar extends Toolbar {
    private final ColorSelectionWindow cst = ColorSelectionWindow.getInstance(390, 160, 326, 400, "COLOR SELECTION");

    public ColorToolbar(int x, int y, int width, int height) {
        super(x, y, width, height);
        setBackgroundColor(Color.GRAY.brighter());

        Image colorBoxDep = ResourceManager.colorBoxDepressed;
        Image colorBoxPre = ResourceManager.colorBoxPressed;

        ToggleButton color1 = new ColorButton(x+130, y+5, 30, 30, colorBoxPre, colorBoxDep, Color.WHITE, Shortcuts.color0, () -> {});
        ToggleButton color2 = new ColorButton(x+165, y+5, 30, 30, colorBoxPre, colorBoxDep, Color.BLACK, Shortcuts.color1, () -> {});
        ToggleButton color3 = new ColorButton(x+200, y+5, 30, 30, colorBoxPre, colorBoxDep, Color.RED, Shortcuts.color2, () -> {});
        ToggleButton color4 = new ColorButton(x+235, y+5, 30, 30, colorBoxPre, colorBoxDep, Color.GREEN, Shortcuts.color3, () -> {});
        ToggleButton color5 = new ColorButton(x+270, y+5, 30, 30, colorBoxPre, colorBoxDep, Color.BLUE, Shortcuts.color4, () -> {});
        ToggleButton color6 = new ColorButton(x+130, y+45, 30, 30, colorBoxPre, colorBoxDep, Color.YELLOW, Shortcuts.color5, () -> {});
        ToggleButton color7 = new ColorButton(x+165, y+45, 30, 30, colorBoxPre, colorBoxDep, Color.PINK, Shortcuts.color6, () -> {});
        ToggleButton color8 = new ColorButton(x+200, y+45, 30, 30, colorBoxPre, colorBoxDep, Color.ORANGE, Shortcuts.color7, () -> {});
        ToggleButton color9 = new ColorButton(x+235, y+45, 30, 30, colorBoxPre, colorBoxDep, Color.CYAN, Shortcuts.color8, () -> {});
        ToggleButton color10 = new ColorButton(x+270, y+45, 30, 30, colorBoxPre, colorBoxDep, Color.GRAY, Shortcuts.color9, () -> {});

        add(color1);
        add(color2);
        add(color3);
        add(color4);
        add(color5);
        add(color6);
        add(color7);
        add(color8);
        add(color9);
        add(color10);

        ToggleButton colorSelector = new ActiveButton(x + 310, y + 5, 50, 70, ResourceManager.palettePressed, ResourceManager.paletteDepressed, "", Shortcuts.colorGradient, this::colorSelectorCLicked);
        ToggleButton fillColorSelector = new ColorButton(x + 10, y + 5, 50, 70, ResourceManager.fillColorPressed, ResourceManager.fillColorDepressed, Color.WHITE, -1, () -> {});
        ToggleButton strokeColorSelector = new ColorButton(x + 70, y + 5, 50, 70, ResourceManager.strokeColorPressed, ResourceManager.strokeColorDepressed, Color.GRAY, -1, () -> {});

        add(colorSelector);
        add(fillColorSelector);
        add(strokeColorSelector);
        setColorHelpText();
    }

    private void setColorHelpText() {
        for (int i = 0; i < 10; i++) {
            getButtons().get(i).setHelpText("Color: " + "(" + getButtons().get(i).getBackgroundColor().getRed() + ", " + getButtons().get(i).getBackgroundColor().getGreen() + ", " + getButtons().get(i).getBackgroundColor().getBlue() + ")");
        }
        getButtons().get(10).setHelpText("Select More Color");
        getButtons().get(11).setHelpText("Fill Color: " + "(" + getButtons().get(11).getBackgroundColor().getRed() + ", " + getButtons().get(11).getBackgroundColor().getGreen() + ", " + getButtons().get(11).getBackgroundColor().getBlue() + ")");
        getButtons().get(12).setHelpText("Stroke Color: " + "(" + getButtons().get(12).getBackgroundColor().getRed() + ", " + getButtons().get(12).getBackgroundColor().getGreen() + ", " + getButtons().get(12).getBackgroundColor().getBlue() + ")");
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        cst.draw(g);
    }

    @Override
    public void onHover(int x, int y) {
        super.onHover(x, y);
        cst.onHover(x, y);
    }

    @Override
    public void drawToolTips(Graphics g) {
        super.drawToolTips(g);
        if (cst.isOpen()) {
            cst.drawToolTips(g);
        }
    }

    @Override
    public void setTooltipsCoordinates(int x, int y) {
        for (ToggleButton tb : getButtons()) {
            tb.setTooltipCoordinates(x, y);
        }
        setColorHelpText();
        cst.setTooltipsCoordinates(x, y);
    }

    @Override
    public void onClick(int x, int y) {
        cst.onClick(x, y);

        ToggleButton fillButton = getButtons().get(11);
        ToggleButton strokeButton = getButtons().get(12);

        for (int i = 0; i < 10; i++) {
            ToggleButton toggleButton = getButtons().get(i);
            if (toggleButton.isClicked(x, y) ) {
                setColor(toggleButton.getBackgroundColor());
            }
        }

        fillButton.onClick(x, y);
        strokeButton.onClick(x, y);
        //The below code ensures that only one of the button is pressed at a time
        if (fillButton.isClicked(x, y) && fillButton.isPressed() && strokeButton.isPressed()) strokeButton.setPressed(false);
        else if (strokeButton.isClicked(x, y) && strokeButton.isPressed() && fillButton.isPressed()) fillButton.setPressed(false);

        getButtons().get(10).onClick(x, y);

        if (cst.isOpen() && cst.set().isClicked(x, y)) {
            setColor(cst.getSelectedColor());
        }
    }

    private void setColor(Color color) {
        ToggleButton fillButton = getButtons().get(11);
        ToggleButton strokeButton = getButtons().get(12);

        if (fillButton.isPressed()) {
            fillButton.setBackgroundColor(color);
            PaintInfo.getInstance().setFillColor(color); //uses the paintinfo singleton to help transeferring color to another class
            fillButton.setPressed(false);
        } else if (strokeButton.isPressed()) {
            strokeButton.setBackgroundColor(color);
            PaintInfo.getInstance().setStrokeColor(color);  //uses the paintinfo singleton to help transeferring color to another class
            strokeButton.setPressed(false);
        }
    }

    public boolean colorWindowOpen() {
        return cst.isOpen();
    }

    private void colorSelectorCLicked() {
        if (!cst.isOpen()) {
            cst.open();
        } else {
            cst.close();
        }
    }
}
