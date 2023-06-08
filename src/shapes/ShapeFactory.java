package shapes;

import java.awt.*;

public class ShapeFactory {

    public Shape getShape(String shapeType, Point p1, Point p2, int strokeSize, Color fillColor, Color strokeColor) {

        switch (shapeType.toUpperCase()) {
            case "CIRCLE":
                return new Circle(p1, p2, strokeSize, fillColor, strokeColor);
            case "RECTANGLE":
                return new Rectangle(p1, p2, strokeSize, fillColor, strokeColor);
            case "RIGHT-TRIANGLE":
                return new RightTriangle(p1, p2, strokeSize, fillColor, strokeColor);
            case "HEXAGON":
                return new Hexagon(p1, p2, strokeSize, fillColor, strokeColor);
            case "PENTAGRAM":
                return new Pentagram(p1, p2, strokeSize, fillColor, strokeColor);
            case "EQUILATERAL-TRIANGLE":
                return new EquilateralTriangle(p1, p2, strokeSize, fillColor, strokeColor);
        }

        return null;
    }
}
