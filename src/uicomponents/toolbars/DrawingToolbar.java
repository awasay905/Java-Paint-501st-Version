package uicomponents.toolbars;

import helper.PaintInfo;
import res.ResourceManager;
import shapes.Grid;
import uicomponents.Shortcuts;
import uicomponents.buttons.ActiveButton;
import uicomponents.buttons.ToggleButton;

import java.awt.*;

public class DrawingToolbar extends Toolbar {
    private final ToggleButton drawLine;
    private final ToggleButton drawShape;
    private final ToggleButton changeGrid;
    private final ToggleButton drawBezierCurve;
    private boolean shapeSelected;
    private boolean freeformSelected;
    private boolean bezierSelected;

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
        //Below are some SAM i used to help simplify the logic
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

    @Override
    public void onClick(int x, int y) {
        if (isClicked(x, y)) {
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
        }
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


    private void freeformClicked() {
        freeformSelected = !freeformSelected;
        shapeSelected = false;
        bezierSelected = false;
        setDetails();
    }

    private void shapeClicked() {
        shapeSelected = !shapeSelected;
        freeformSelected = false;
        bezierSelected = false;
        setDetails();
    }

    private void bezierClicked() {
        bezierSelected = !bezierSelected;
        shapeSelected = false;
        freeformSelected = false;
        setDetails();
    }

    private void deselectShapes(){
        shapeSelected = false;
        drawShape.setPressed(false);
        setDetails();
    }

    private void setDetails() {
        PaintInfo.getInstance().setLineDrawing(freeformSelected);
        PaintInfo.getInstance().setShapeDrawing(shapeSelected);
        PaintInfo.getInstance().setBezierDrawing(bezierSelected);
    }

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
