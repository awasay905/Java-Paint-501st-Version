package uicomponents.toolbars;

import shapes.Grid;
import uicomponents.Shortcuts;
import uicomponents.buttons.ActiveButton;
import uicomponents.buttons.ToggleButton;

import javax.swing.*;
import java.awt.*;

public class DrawingToolbar extends Toolbar {
    private final ToggleButton drawLine;
    private final ToggleButton drawShape;
    private final ToggleButton changeGrid;
    private final ToggleButton drawBezierCurve;
    private boolean shapeSelected;
    private boolean freeformSelected;
    private boolean bezierSelected;
    private Image gridOff, gird2, grid4, gird8, grid16, grid32, grid64;

    public DrawingToolbar(int x, int y, int width, int height) {
        super(x, y, width, height);
        setBackgroundColor(Color.GRAY.brighter());
        drawLine = new ToggleButton(x + 10, y + 5, 50, 70, new ImageIcon("src/res/pressed/free-stroke.png").getImage(), new ImageIcon("src/res/depressed/free-stroke.png").getImage(), "", Shortcuts.selectFreeForm, this::freeformClicked);
        drawBezierCurve = new ToggleButton(x + 70, y + 5, 50, 70, new ImageIcon("src/res/pressed/bezier-curve.png").getImage(), new ImageIcon("src/res/depressed/bezier-curve.png").getImage(), "", Shortcuts.selectBezierCurve, this::bezierClicked);
        drawShape = new ToggleButton(x + 130, y + 5, 50, 70, new ImageIcon("src/res/pressed/draw-shapes.png").getImage(), new ImageIcon("src/res/depressed/draw-shapes.png").getImage(), "", Shortcuts.selectShapes, this::shapeClicked);
        changeGrid = new ActiveButton(x + 190, y + 5, 50, 70, new ImageIcon("src/res/depressed/grid/empty.png").getImage(), new ImageIcon("src/res/depressed/grid/gridOff.png").getImage(), "", Shortcuts.changeGrid, this::changeGridSize);
        changeGrid.setHelpText("Change Grid Size " + "(" + (char) Shortcuts.changeGrid + ") : " + Grid.getInstance().getGridSize());
        drawLine.setHelpText("Draw Freeform " + "(" + (char) Shortcuts.selectFreeForm + ")");
        drawShape.setHelpText("Draw Shapes " + "(" + (char) Shortcuts.selectShapes + ")");
        drawBezierCurve.setHelpText("Draw Cubic Bezier Curve" + "(" + (char) Shortcuts.selectBezierCurve + ")");
        add(drawLine);
        add(drawShape);
        add(changeGrid);
        add(drawBezierCurve);
        loadImages();
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
                return;
            }

            if (drawShape.isClicked(x, y)) {
                drawShape.onClick(x, y);
                drawLine.setPressed(false);
                drawBezierCurve.setPressed(false);
                return;
            }

            if (drawBezierCurve.isClicked(x, y)) {
                drawBezierCurve.onClick(x, y);
                drawLine.setPressed(false);
                drawShape.setPressed(false);
                return;
            }

            for (ToggleButton tb : getButtons()) {
                tb.setPressed(false);
            }
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

    public boolean isShapeSelected() {
        return shapeSelected;
    }

    public boolean isFreeformSelected() {
        return freeformSelected;
    }

    public boolean isBezierSelected() {
        return bezierSelected;
    }

    private void freeformClicked() {
        freeformSelected = !freeformSelected;
        shapeSelected = false;
        bezierSelected = false;
    }

    private void shapeClicked() {
        shapeSelected = !shapeSelected;
        freeformSelected = false;
        bezierSelected = false;
    }

    private void bezierClicked() {
        bezierSelected = !bezierSelected;
        shapeSelected = false;
        freeformSelected = false;
    }

    private void changeGridSize() {
        Grid.getInstance().increaseGridSize();
        if (Grid.getInstance().getGridSize() == 0) getButtons().get(2).setDePress(gridOff);
        if (Grid.getInstance().getGridSize() == 2) getButtons().get(2).setDePress(gird2);
        if (Grid.getInstance().getGridSize() == 4) getButtons().get(2).setDePress(grid4);
        if (Grid.getInstance().getGridSize() == 8) getButtons().get(2).setDePress(gird8);
        if (Grid.getInstance().getGridSize() == 16) getButtons().get(2).setDePress(grid16);
        if (Grid.getInstance().getGridSize() == 32) getButtons().get(2).setDePress(grid32);
        if (Grid.getInstance().getGridSize() == 64) getButtons().get(2).setDePress(grid64);
    }

    private void loadImages() {
        gridOff = new ImageIcon("src/res/depressed/grid/gridOff.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
        gird2 = new ImageIcon("src/res/depressed/grid/grid2x2.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
        grid4 = new ImageIcon("src/res/depressed/grid/grid4x4.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
        gird8 = new ImageIcon("src/res/depressed/grid/grid8x8.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
        grid16 = new ImageIcon("src/res/depressed/grid/grid16x16.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
        grid32 = new ImageIcon("src/res/depressed/grid/grid32x32.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
        grid64 = new ImageIcon("src/res/depressed/grid/grid64x64.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
    }
}
