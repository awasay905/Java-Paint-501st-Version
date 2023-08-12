package uicomponents.toolbars;

import helper.PaintInfo;
import res.ResourceManager;
import shapes.Grid;
import uicomponents.Shortcuts;
import uicomponents.buttons.ActiveButton;
import uicomponents.buttons.ToggleButton;

import java.awt.*;

/**
 * The class represengintg the drawing toolbar.
 */
public class DrawingToolbar extends Toolbar {
    /**
     * The button which enables/disables to draw free stroke line
     */
    private final ToggleButton drawLine;
    /**
     * The button which enables/disables to draw shapes. Useless.
     * TODO: Remove it
     */
    private final ToggleButton drawShape;
    /**
     * The button which enables/disables and changes size for grid
     */
    private final ToggleButton changeGrid;
    /**
     * The button which enables/disables to draw free cubic bezier curves
     * TODO: Somehow make it for Nth Bezier Curve
     */
    private final ToggleButton drawBezierCurve;
    /**
     * Tells if shape button is clicked. Useless as well
     * TODO: Directly use the onPress of drawShape Button
     */
    private boolean shapeSelected;
    /**
     * Tells if free drawing button is clicked. Useless as well
     * TODO: Directly use the onPress of free drawing Button
     */
    private boolean freeformSelected;
    /**
     * Tells if bezier curve button is clicked. Useless as well
     * TODO: Directly use the onPress of bezier Button
     */
    private boolean bezierSelected;

    /**
     * Constructor. It intializes the drawing toolbar and its button.
     * It also sets the tooltip text for the buttons. And links some function
     * to the PaintInfo singelton
     * @param x      the x coordinate of the menubar according to java graphics coordinates
     * @param y      the y coordinate of the menubar according to java graphics coordinates
     * @param width  the width of the menubar
     * @param height the height of the menubar
     */
    public DrawingToolbar(int x, int y, int width, int height) {
        super(x, y, width, height);
        setBackgroundColor(Color.GRAY.brighter());
        drawLine = new ToggleButton(x + 10, y + 5, 50, 70, ResourceManager.freeStrokePressed,ResourceManager.freeStrokeDepressed, "", Shortcuts.selectFreeForm, this::freeformClicked);
        drawBezierCurve = new ToggleButton(x + 70, y + 5, 50, 70, ResourceManager.bezierCurvePressed, ResourceManager.bezierCurveDepressed, "", Shortcuts.selectBezierCurve, this::bezierClicked);
        drawShape = new ToggleButton(x + 130, y + 5, 50, 70, ResourceManager.drawShapesPressed,ResourceManager.drawShapesDepressed, "", Shortcuts.selectShapes, this::shapeClicked);
        changeGrid = new ActiveButton(x + 190, y + 5, 50, 70, ResourceManager.gridEmpty,ResourceManager.gridOff, "", Shortcuts.changeGrid, this::changeGridSize);
        changeGrid.setHelpText("Change Grid Size " + "(" + (char) Shortcuts.changeGrid + ") : " + Grid.getInstance().getGridSize());
        drawLine.setHelpText("Draw Freeform " + "(" + (char) Shortcuts.selectFreeForm + ")");
        drawShape.setHelpText("Draw Shapes " + "(" + (char) Shortcuts.selectShapes + ")");
        drawBezierCurve.setHelpText("Draw Cubic Bezier Curve" + "(" + (char) Shortcuts.selectBezierCurve + ")");
        add(drawLine);
        add(drawShape);
        add(changeGrid);
        add(drawBezierCurve);
        //Below are some SAM I used to help simplify the logic
        PaintInfo.getInstance().setUpdateSelectedButton(() -> {
            drawShape.setPressed(true);
            drawLine.setPressed(false);
            drawBezierCurve.setPressed(false);
            shapeSelected = true;
            bezierSelected = false;
            freeformSelected = false;
            setDetails();
        });
        PaintInfo.getInstance().setDepressShapeButton(this::deselectShapes);
    }

    /**
     * The onClick Method. It is very simple. It just checks if the buttons are pressed and calls their onClick.
     * For the Grid button, it changes the grid size
     * For the rest, it makes sure that only one button is pressed at a time by declicking others
     * @param x
     * @param y
     */
    @Override
    public void onClick(int x, int y) {
        //if (isClicked(x, y)) {
            if (changeGrid.isClicked(x, y)) {
                changeGrid.onClick(x, y);
                changeGrid.setHelpText("Change Grid Size " + "(" + (char) Shortcuts.changeGrid + ") : " + Grid.getInstance().getGridSize());
                return;
            }

            if (drawLine.isClicked(x, y)) {
                drawLine.onClick(x, y);
                drawShape.setPressed(false);
                drawBezierCurve.setPressed(false);
                PaintInfo.getInstance().resetShapesClick(); //deselects the shapes in shapes toolbar
                return;
            }

            if (drawShape.isClicked(x, y)) {
                drawShape.onClick(x, y);
                if (!drawShape.isPressed()) PaintInfo.getInstance().resetShapesClick(); //deselects the shapes in shapes toolbar if shape button is deselected
                drawLine.setPressed(false);
                drawBezierCurve.setPressed(false);
                return;
            }

            if (drawBezierCurve.isClicked(x, y)) {
                drawBezierCurve.onClick(x, y);
                drawLine.setPressed(false);
                drawShape.setPressed(false);
                PaintInfo.getInstance().resetShapesClick(); //deselects the shapes in shapes toolbar
                return;
            }

//            for (ToggleButton tb : getButtons()) { there was no need to unlick buttons when pressing on empty space
//                tb.setPressed(false);
//            }
       // }
    }

    @Override
    public void shortcutKeyPressed(int keyCode) {

        if (changeGrid.shortcutKeyPressed(keyCode)) {
            changeGrid.setHelpText("Change Grid Size " + "(" + (char) Shortcuts.changeGrid + ") : " + Grid.getInstance().getGridSize());
            return;
        }

        if (drawLine.shortcutKeyPressed(keyCode)) {
            drawShape.setPressed(false);
            drawBezierCurve.setPressed(false);
            return;
        }

        if (drawShape.shortcutKeyPressed(keyCode)) {
            drawLine.setPressed(false);
            drawBezierCurve.setPressed(false);
            return;
        }

        if (drawBezierCurve.shortcutKeyPressed(keyCode)) {
            drawLine.setPressed(false);
            drawShape.setPressed(false);
            return;
        }


//        //maybe bug, booleans not getting false
//        if (drawLine.isPressed()) {
//            drawShape.setPressed(false);
//            drawShape.shortcutKeyPressed(keyCode);
//        }
//
//        if (drawShape.isPressed()) {
//            drawLine.setPressed(false);
//        }

    }

    /**
     * The function which is called when the free drawing button is clicked
     * it declicks the other buttons, sets the boolean, and
     * also updates the details in the paintInfo singelton
     */
    private void freeformClicked() {
        freeformSelected = !freeformSelected;
        shapeSelected = false;
        bezierSelected = false;
        setDetails();
    }
    /**
     * The function which is called when the shapes button is clicked
     * it declicks the other buttons, sets the boolean, and
     * also updates the details in the paintInfo singelton
     */
    private void shapeClicked() {
        shapeSelected = !shapeSelected;
        freeformSelected = false;
        bezierSelected = false;
        setDetails();
    }
    /**
     * The function which is called when the bezeir curve button is clicked
     * it declicks the other buttons, sets the boolean, and
     * also updates the details in the paintInfo singelton
     */
    private void bezierClicked() {
        bezierSelected = !bezierSelected;
        shapeSelected = false;
        freeformSelected = false;
        setDetails();
    }

    /**
     * A function which depresses the shape drawing button.
     * It is passed to singleTon paintINfo as a SAI,
     * so that when we depress indiviidaul shape button from shapes toolbar, this button
     * also gets depressed
     */
    private void deselectShapes(){
        shapeSelected = false;
        drawShape.setPressed(false);
        setDetails();
    }

    /**
     * A function which sets the details in the paintInfo singelton
     */
    private void setDetails() {
        PaintInfo.getInstance().setLineDrawing(freeformSelected);
        PaintInfo.getInstance().setShapeDrawing(shapeSelected);
        PaintInfo.getInstance().setBezierDrawing(bezierSelected);
    }

    /**
     * This function changes the grid size, while also updating the picture shown on the button
     */
    private void changeGridSize() {
        Grid.getInstance().increaseGridSize();
        if (Grid.getInstance().getGridSize() == 0) getButtons().get(2).setDePress(ResourceManager.gridOff);
        if (Grid.getInstance().getGridSize() == 2) getButtons().get(2).setDePress(ResourceManager.grid2x2);
        if (Grid.getInstance().getGridSize() == 4) getButtons().get(2).setDePress(ResourceManager.grid4x4);
        if (Grid.getInstance().getGridSize() == 8) getButtons().get(2).setDePress(ResourceManager.grid8x8);
        if (Grid.getInstance().getGridSize() == 16) getButtons().get(2).setDePress(ResourceManager.grid16x16);
        if (Grid.getInstance().getGridSize() == 32) getButtons().get(2).setDePress(ResourceManager.grid32x32);
        if (Grid.getInstance().getGridSize() == 64) getButtons().get(2).setDePress(ResourceManager.grid64x64);
    }


}
