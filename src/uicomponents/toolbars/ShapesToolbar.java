package uicomponents.toolbars;

import uicomponents.Shortcuts;
import uicomponents.buttons.ActiveButton;
import uicomponents.buttons.ToggleButton;

import javax.swing.*;
import java.awt.*;

public class ShapesToolbar extends Toolbar {
    private String selectedShape;
    private int strokeSize;

    public ShapesToolbar(int x, int y, int width, int height) {
        super(x, y, width, height);
        selectedShape = "";
        strokeSize = 5;
        setBackgroundColor(Color.GRAY.brighter());

        ToggleButton circle = new ToggleButton(x+10, y+5, 30, 30, new ImageIcon("src/res/pressed/circle-border.png").getImage(), new ImageIcon("src/res/depressed/circle-border.png").getImage(), "", Shortcuts.circle, this::circleClicked);
        ToggleButton rectangle = new ToggleButton(x+45, y+5, 30, 30, new ImageIcon("src/res/pressed/rectangle.png").getImage(), new ImageIcon("src/res/depressed/rectangle.png").getImage(), "", Shortcuts.rectangle, this::rectangleClicked);
        ToggleButton rightAngleTriangle = new ToggleButton(x+80, y+5, 30, 30, new ImageIcon("src/res/pressed/right-angle-triangle.png").getImage(), new ImageIcon("src/res/depressed/right-angle-triangle.png").getImage(), "", Shortcuts.triangle, this::rightAngleTriangleClicked);
        ToggleButton hexagon = new ToggleButton(x+10, y + 45, 30, 30, new ImageIcon("src/res/pressed/hexagon.png").getImage(), new ImageIcon("src/res/depressed/hexagon.png").getImage(), "", Shortcuts.hexagon, this::hexagonClicked);
        ToggleButton pentagram = new ToggleButton(x+45, y + 45, 30, 30, new ImageIcon("src/res/pressed/pentagram.png").getImage(), new ImageIcon("src/res/depressed/pentagram.png").getImage(), "", Shortcuts.pentagram, this::pentagramClicked);
        ToggleButton equilateralTriangle = new ToggleButton(x+80, y + 45, 30, 30, new ImageIcon("src/res/pressed/equilateral-triangle.png").getImage(), new ImageIcon("src/res/depressed/equilateral-triangle.png").getImage(), "", Shortcuts.equilateralTriangle, this::equilateralTriangleClicked);

        add(circle);
        add(rectangle);
        add(rightAngleTriangle);
        add(hexagon);
        add(pentagram);
        add(equilateralTriangle);

        circle.setHelpText("Circle " + "(" + (char) Shortcuts.circle + ")");
        rectangle.setHelpText("Rectangle " + "(" + (char) Shortcuts.rectangle + ")");
        rightAngleTriangle.setHelpText("Right Angle Triangle " + "(" + (char) Shortcuts.triangle + ")");
        hexagon.setHelpText("Hexagon " + "(" + (char) Shortcuts.hexagon + ")");
        pentagram.setHelpText("Pentagram " + "(" + (char) Shortcuts.pentagram + ")");
        equilateralTriangle.setHelpText("Equilateral Triangle " + "(" + (char) Shortcuts.equilateralTriangle + ")");

        ToggleButton stokeWidthSelector = new ActiveButton(x+120, y+5, 50, 70, new ImageIcon("src/res/pressed/stroke-width.png").getImage(), new ImageIcon("src/res/depressed/stroke-width.png").getImage(), "", Shortcuts.changeStroke, this::strokeWidthClicked);
        add(stokeWidthSelector);

        stokeWidthSelector.setHelpText("Stroke size" + "(" + (char) Shortcuts.changeStroke + ")" + ": " + getStrokeSize());
    }

    @Override
    public void onClick(int x, int y) {

        if (isClicked(x, y)) {
            if (getButtons().get(6).isClicked(x, y)) {
                getButtons().get(6).onClick(x, y);
                return;
            }

            for (ToggleButton shapeButton : getButtons()) {
                if (shapeButton.isClicked(x, y)) {
                    shapeButton.onClick(x, y);
                    if (shapeButton.isPressed()) {
                        resetShapesClicks(null);
                        shapeButton.onClick(x, y);
                        return;
                    }
                }
            }

            resetShapesClicks(null);
        }

    }

    private void resetClick() {
        resetShapesClicks(null);
        getButtons().get(6).setPressed(false);
    }

    private void resetShapesClicks(ToggleButton tb) {
        selectedShape = "";
        for (int i = 0; i < 6; i++) {
            if (getButtons().get(i).equals(tb)) continue;
            getButtons().get(i).setPressed(false);
        }
    }

    public void shortcutKeyPressed(int keyCode) {
        if (getButtons().get(6).shortcutKeyPressed(keyCode)){
            //When stroke size button is clicked
            getButtons().get(6).setHelpText("Stroke size" + "(" + (char) Shortcuts.changeStroke + ")" + ": " + getStrokeSize());
            return;
        }
        for (ToggleButton b : getButtons()) {
            b.shortcutKeyPressed(keyCode);
            if (b.isPressed()) {
                resetClick();
            }
            b.shortcutKeyPressed(keyCode);
        }
    }

    private void circleClicked() {
        selectedShape = "CIRCLE";
    }

    private void rectangleClicked() {
        selectedShape = "RECTANGLE";
    }

    private void rightAngleTriangleClicked() {
        selectedShape = "RIGHT-TRIANGLE";
    }

    private void hexagonClicked() {
        selectedShape = "HEXAGON";
    }

    private void pentagramClicked() {
        selectedShape = "PENTAGRAM";
    }

    private void equilateralTriangleClicked() {
        selectedShape = "EQUILATERAL-TRIANGLE";
    }

    private void strokeWidthClicked() {
        if (strokeSize == 0) {
            strokeSize = 5;
            getButtons().get(6).setHelpText("Stroke size" + "(" + (char) Shortcuts.changeStroke + ")" + ": " + getStrokeSize());
            return;
        }
        strokeSize += 5;
        strokeSize %= 40;
        getButtons().get(6).setHelpText("Stroke size" + "(" + (char) Shortcuts.changeStroke + ")" + ": " + getStrokeSize());
    }

    public String getSelectedShape() {
        return selectedShape;
    }

    public int getStrokeSize() {
        return strokeSize;
    }

}
