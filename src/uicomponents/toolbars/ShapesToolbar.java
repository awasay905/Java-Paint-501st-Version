package uicomponents.toolbars;

import helper.PaintInfo;
import res.ResourceManager;
import shapes.Shapes;
import uicomponents.Shortcuts;
import uicomponents.buttons.ActiveButton;
import uicomponents.buttons.ToggleButton;

import java.awt.*;

/**
 * THe toolbar responsible for showing the shapes button and the stroke size selector button
 */
public class ShapesToolbar extends Toolbar {
    /**
     * The size of the stroke
     * TODO: remove it, as it is available thorught paintInfo Singleton
     */
    private int strokeSize;

    /**
     *  initializes the shapes selecting button, the stroke selection button,
     *  the stroke size to 5, and passes a function to paintInfo
     * @param x      the x coordinate of the toolbar according to java graphics coordinates
     * @param y      the y coordinate of the toolbar according to java graphics coordinates
     * @param width  the width of the toolbar
     * @param height the height of the toolbar
     */

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
        PaintInfo.getInstance().setResetShapesClick(()-> resetShapesClicks());
    }

    /**
     * A simple onClick, first it checks if the toolbar is clicked,
     * then it check if the stroke button is clicked, if it is, then it returns.
     * Then it checks the shapes button,
     * if no button was clicked previously, and a button is clicked this time,
     * then the button becomes "pressed" and using paintInfo, the drawShape
     * button of the shapesToolbar becomes pressed as well. Then it returns.
     * In case the toolbar is clicked but no shape button is clicked,
     * all shapes gets deselected, and the drawShapes button of the shapesToolbar also gets depresses.
     * In case a new shape is clicked when a previous one was still active, if depresses the old button
     * and preses the new one
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     */
    @Override
    public void onClick(int x, int y) {

        if (isClicked(x, y)) { //checks if the toolbar is clicked
            //Checks the stroke size button
            if (getButtons().get(6).isClicked(x, y)) {
                getButtons().get(6).onClick(x, y);
                return;
            }

            for (ToggleButton shapeButton : getButtons()) { //checks all shapes button
                if (shapeButton.isClicked(x, y)) {  // if a button is clicked
                    shapeButton.onClick(x, y);  //calls the button onClick, usually it will set the shapes Enum
                    if (shapeButton.isPressed()) {  //if after clicking the shape button, it becomes active, so deselct all others
                        //but deselecting all others will deselct this buutton too,
                        //so reselect this button
                        resetShapesClicks(); //deselect all shapes
                        shapeButton.onClick(x, y); //reselect this chape
                        return;
                    } else {
                        //if a shape button is depressed, the drawshape button also depresses
                        PaintInfo.getInstance().depressShapeButton();
                    }
                }
            }

            resetShapesClicks(); //in case no button is pressed, it depresses all shape buttons
        }

    }

    /**
     * Resets all shapes, for helpin shotcut keys
     */
    private void resetClick() {
        resetShapesClicks();
        getButtons().get(6).setPressed(false);
    }

    /**
     * Sets all shapes to false, and also depressed the drawShapes button in drawing tolbar
     */
    private void resetShapesClicks() {
        PaintInfo.getInstance().setSelectedShape(null);
        for (int i = 0; i < 6; i++) {
            getButtons().get(i).setPressed(false);
        }
        PaintInfo.getInstance().depressShapeButton(); //after all shape button is depressed, the draeshapes also depresses
    }

    /**
     * Processes the shortcuts
     * @param keyCode the ascii code of the key pressed
     */
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

    /**
     * Sets the stated shape in the paintInfo singelton
     */
    private void circleClicked() {
        PaintInfo.getInstance().setSelectedShape(Shapes.CIRCLE);
    }
    /**
     * Sets the stated shape in the paintInfo singelton
     */
    private void rectangleClicked() {
        PaintInfo.getInstance().setSelectedShape(Shapes.RECTANGLE);
    }
    /**
     * Sets the stated shape in the paintInfo singelton
     */
    private void rightAngleTriangleClicked() {
        PaintInfo.getInstance().setSelectedShape(Shapes.RIGHT_TRIANGLE);
    }
    /**
     * Sets the stated shape in the paintInfo singelton
     */
    private void hexagonClicked() {
        PaintInfo.getInstance().setSelectedShape(Shapes.HEXAGON);
    }
    /**
     * Sets the stated shape in the paintInfo singelton
     */
    private void pentagramClicked() {
        PaintInfo.getInstance().setSelectedShape(Shapes.PENTAGRAM);
    }
    /**
     * Sets the stated shape in the paintInfo singelton
     */
    private void equilateralTriangleClicked() {
        PaintInfo.getInstance().setSelectedShape(Shapes.EQUILATERAL_TRIANGLE);
    }

    /**
     * The onClick for stroke button. It increases stroke width by 5, and resets it to 0 after 35.
     * It also updates the corresponding value in paintInfo
     */
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
