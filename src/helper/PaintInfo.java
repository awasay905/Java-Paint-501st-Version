package helper;

import shapes.Shape;
import shapes.Shapes;
import uicomponents.SingleFuction;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class PaintInfo {
    private static PaintInfo instance;
    private Color strokeColor;
    private Color fillColor;
    private int strokeWidth;
    private ArrayList<Shape> shapes;
    private Stack<Shape> undoStack;
    private Shapes selectedShape;
    private boolean isShapeDrawing;
    private boolean isLineDrawing;
    private boolean isBezierDrawing;
    private SingleFuction updateThumbnail;
    private SingleFuction updateSelectedButton;
    private SingleFuction resetShapesClick;
    private SingleFuction depressShapeButton;
    private PaintInfo(){
        fillColor = new Color(255,255,255);
        strokeColor = new Color(128,128,128);
        strokeWidth = 5;
        shapes = new ArrayList<>();
        selectedShape = null;
        undoStack = new Stack<>();
    }
    public static PaintInfo getInstance(){
        if (instance == null) instance = new PaintInfo();
        return instance;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public Shapes getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(Shapes selectedShape) {
        this.selectedShape = selectedShape;
        if (selectedShape != null) updateSelectedButton();
    }

    public boolean isShapeDrawing() {
        return isShapeDrawing;
    }

    public void setShapeDrawing(boolean shapeDrawing) {
        isShapeDrawing = shapeDrawing;
    }

    public boolean isLineDrawing() {
        return isLineDrawing;
    }

    public void setLineDrawing(boolean lineDrawing) {
        isLineDrawing = lineDrawing;
    }

    public boolean isBezierDrawing() {
        return isBezierDrawing;
    }

    public void setBezierDrawing(boolean bezierDrawing) {
        isBezierDrawing = bezierDrawing;
    }

    public Stack<Shape> getUndoStack() {
        return undoStack;
    }

    public void updateThumbnail() {
         updateThumbnail.function();
    }

    public void setUpdateThumbnail(SingleFuction updateThumbnail) {
        this.updateThumbnail = updateThumbnail;
    }

    public void setUpdateSelectedButton(SingleFuction updateSelectedButton) {
        this.updateSelectedButton = updateSelectedButton;
    }

    private void updateSelectedButton(){
        updateSelectedButton.function();
    }
    public void resetShapesClick(){
        if (resetShapesClick != null) resetShapesClick.function();
    }

    public void setResetShapesClick(SingleFuction resetShapesClick) {
        this.resetShapesClick = resetShapesClick;
    }

    public void depressShapeButton(){
        if (depressShapeButton!= null)depressShapeButton.function();
    }

    public void setDepressShapeButton(SingleFuction depressShapeButton) {
        this.depressShapeButton = depressShapeButton;
    }
}
