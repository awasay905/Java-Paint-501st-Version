package shapes;

import java.awt.*;

public class ShapeFactory {

    public Shape getShape(Shapes shapeType, Point p1, Point p2, int strokeSize, Color fillColor, Color strokeColor) {
        if (shapeType == null) return null;
        switch (shapeType) {
            case CIRCLE:
                return new Circle(p1, p2, strokeSize, fillColor, strokeColor);
            case RECTANGLE:
                return new Rectangle(p1, p2, strokeSize, fillColor, strokeColor);
            case RIGHT_TRIANGLE:
                return new RightTriangle(p1, p2, strokeSize, fillColor, strokeColor);
            case HEXAGON:
                return new Hexagon(p1, p2, strokeSize, fillColor, strokeColor);
            case PENTAGRAM:
                return new Pentagram(p1, p2, strokeSize, fillColor, strokeColor);
            case EQUILATERAL_TRIANGLE:
                return new EquilateralTriangle(p1, p2, strokeSize, fillColor, strokeColor);
        }

        return null;
    }
}
