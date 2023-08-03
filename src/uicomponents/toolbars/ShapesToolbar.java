package uicomponents.toolbars;

import helper.PaintInfo;
import res.ResourceManager;
import shapes.Shapes;
import uicomponents.Shortcuts;
import uicomponents.buttons.ActiveButton;
import uicomponents.buttons.ToggleButton;

import javax.swing.*;
import java.awt.*;

public class ShapesToolbar extends Toolbar {
    private int strokeSize;

    public ShapesToolbar(int x, int y, int width, int height) {
        super(x, y, width, height);
        PaintInfo.getInstance().setSelectedShape(null);
        strokeSize = 5;
        setBackgroundColor(Color.GRAY.brighter());

        ToggleButton circle = new ToggleButton(x+10, y+5, 30, 30, ResourceManager.circleBorderPressed, ResourceManager.circleBorderDepressed, "", Shortcuts.circle, this::circleClicked);
        ToggleButton rectangle = new ToggleButton(x+45, y+5, 30, 30, ResourceManager.rectanglePressed, ResourceManager.rectangleDepressed, "", Shortcuts.rectangle, this::rectangleClicked);
        ToggleButton rightAngleTriangle = new ToggleButton(x+80, y+5, 30, 30, ResourceManager.rightAngleTrianglePressed, ResourceManager.rightAngleTriangleDepressed, "", Shortcuts.triangle, this::rightAngleTriangleClicked);
        ToggleButton hexagon = new ToggleButton(x+10, y + 45, 30, 30, ResourceManager.hexagonPressed, ResourceManager.hexagonDepressed, "", Shortcuts.hexagon, this::hexagonClicked);
        ToggleButton pentagram = new ToggleButton(x+45, y + 45, 30, 30, ResourceManager.pentagramPressed,ResourceManager.pentagramDepressed, "", Shortcuts.pentagram, this::pentagramClicked);
        ToggleButton equilateralTriangle = new ToggleButton(x+80, y + 45, 30, 30, ResourceManager.equilateralTrianglePressed, ResourceManager.equilateralTriangleDepressed, "", Shortcuts.equilateralTriangle, this::equilateralTriangleClicked);

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

        ToggleButton stokeWidthSelector = new ActiveButton(x+120, y+5, 50, 70,ResourceManager.strokeWidthPressed, ResourceManager.strokeWidthDepressed, "", Shortcuts.changeStroke, this::strokeWidthClicked);
        add(stokeWidthSelector);

        stokeWidthSelector.setHelpText("Stroke size" + "(" + (char) Shortcuts.changeStroke + ")" + ": " + strokeSize);
        PaintInfo.getInstance().setResetShapesClick(()-> resetShapesClicks(null));
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
                    } else {
                        //if a shape button is depressed, the drawshape button also depresses
                        PaintInfo.getInstance().depressShapeButton();
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
        PaintInfo.getInstance().setSelectedShape(null);
        for (int i = 0; i < 6; i++) {
            if (getButtons().get(i).equals(tb)) continue;
            getButtons().get(i).setPressed(false);
        }
        PaintInfo.getInstance().depressShapeButton(); //after all shape button is depressed, the draeshapes also depresses
    }

    public void shortcutKeyPressed(int keyCode) {
        if (getButtons().get(6).shortcutKeyPressed(keyCode)){
            //When stroke size button is clicked
            getButtons().get(6).setHelpText("Stroke size" + "(" + (char) Shortcuts.changeStroke + ")" + ": " + strokeSize);
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
        PaintInfo.getInstance().setSelectedShape(Shapes.CIRCLE);
    }

    private void rectangleClicked() {
        PaintInfo.getInstance().setSelectedShape(Shapes.RECTANGLE);
    }

    private void rightAngleTriangleClicked() {
        PaintInfo.getInstance().setSelectedShape(Shapes.RIGHT_TRIANGLE);
    }

    private void hexagonClicked() {
        PaintInfo.getInstance().setSelectedShape(Shapes.HEXAGON);
    }

    private void pentagramClicked() {
        PaintInfo.getInstance().setSelectedShape(Shapes.PENTAGRAM);
    }

    private void equilateralTriangleClicked() {
        PaintInfo.getInstance().setSelectedShape(Shapes.EQUILATERAL_TRIANGLE);
    }

    private void strokeWidthClicked() {
        if (strokeSize == 0) {
            strokeSize = 5;
            PaintInfo.getInstance().setStrokeWidth(strokeSize);
            getButtons().get(6).setHelpText("Stroke size" + "(" + (char) Shortcuts.changeStroke + ")" + ": " + strokeSize);
            return;
        }
        strokeSize += 5;
        strokeSize %= 40;
        PaintInfo.getInstance().setStrokeWidth(strokeSize);
        getButtons().get(6).setHelpText("Stroke size" + "(" + (char) Shortcuts.changeStroke + ")" + ": " + strokeSize);
    }


}
