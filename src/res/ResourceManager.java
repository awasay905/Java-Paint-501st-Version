package res;

import javax.swing.*;
import java.awt.*;

/**
 * A class which preloads all the images used in the app for its components like buttons,
 * toolbars, shapes etc. It uses ImageIcon's getImage() function to get the images.
 * I tried ImageIO, but it was giving "unable to create ImageInputStream" error
 */
public class ResourceManager {

    public static final Image empty = new ImageIcon("empty").getImage();
    public static final Image addLayerPressed = new ImageIcon("src/res/pressed/add-layer.png").getImage();
    public static final Image bezierCurvePressed = new ImageIcon("src/res/pressed/bezier-curve.png").getImage();
    public static final Image circleBorderPressed = new ImageIcon("src/res/pressed/circle-border.png").getImage();
    public static final Image colorBoxPressed = new ImageIcon("src/res/pressed/color-box.png").getImage();
    public static final Image drawShapesPressed = new ImageIcon("src/res/pressed/draw-shapes.png").getImage();
    public static final Image editMenuButtonPressed = new ImageIcon("src/res/pressed/edit-menu-button.png").getImage();
    public static final Image equilateralTrianglePressed = new ImageIcon("src/res/pressed/equilateral-triangle.png").getImage();
    public static final Image fileTextBoxPressed = new ImageIcon("src/res/pressed/fileTextBox.png").getImage();
    public static final Image fillColorPressed = new ImageIcon("src/res/pressed/fill-color.png").getImage();
    public static final Image freeStrokePressed = new ImageIcon("src/res/pressed/free-stroke.png").getImage();
    public static final Image hexagonPressed = new ImageIcon("src/res/pressed/hexagon.png").getImage();
    public static final Image layerDownPressed = new ImageIcon("src/res/pressed/layer-down.png").getImage();
    public static final Image layerUpPressed = new ImageIcon("src/res/pressed/layer-up.png").getImage();
    public static final Image menuMenuButtonPressed = new ImageIcon("src/res/pressed/menu-menu-button.png").getImage();
    public static final Image newMenuButtonPressed = new ImageIcon("src/res/pressed/new-menu-button.png").getImage();
    public static final Image notVisiblePressed = new ImageIcon("src/res/pressed/not-visible.png").getImage();
    public static final Image openMenuButtonPressed = new ImageIcon("src/res/pressed/open-menu-button.png").getImage();
    public static final Image palettePressed = new ImageIcon("src/res/pressed/palette.png").getImage();
    public static final Image pentagramPressed = new ImageIcon("src/res/pressed/pentagram.png").getImage();
    public static final Image rectanglePressed = new ImageIcon("src/res/pressed/rectangle.png").getImage();
    public static final Image redoMenuButtonPressed = new ImageIcon("src/res/pressed/redo-menu-button.png").getImage();
    public static final Image redoPressed = new ImageIcon("src/res/pressed/redo.png").getImage();
    public static final Image removeLayerPressed = new ImageIcon("src/res/pressed/remove-layer.png").getImage();
    public static final Image rightAngleTrianglePressed = new ImageIcon("src/res/pressed/right-angle-triangle.png").getImage();
    public static final Image saveMenuButtonPressed = new ImageIcon("src/res/pressed/save-menu-button.png").getImage();
    public static final Image strokeColorPressed = new ImageIcon("src/res/pressed/stroke-color.png").getImage();
    public static final Image strokeWidthPressed = new ImageIcon("src/res/pressed/stroke-width.png").getImage();
    public static final Image undoMenuButtonPressed = new ImageIcon("src/res/pressed/undo-menu-button.png").getImage();
    public static final Image undoPressed = new ImageIcon("src/res/pressed/undo.png").getImage();
    public static final Image addLayerDepressed = new ImageIcon("src/res/depressed/add-layer.png").getImage();
    public static final Image bezierCurveDepressed = new ImageIcon("src/res/depressed/bezier-curve.png").getImage();
    public static final Image circleBorderDepressed = new ImageIcon("src/res/depressed/circle-border.png").getImage();
    public static final Image colorBoxDepressed = new ImageIcon("src/res/depressed/color-box.png").getImage();
    public static final Image drawShapesDepressed = new ImageIcon("src/res/depressed/draw-shapes.png").getImage();
    public static final Image editMenuButtonDepressed = new ImageIcon("src/res/depressed/edit-menu-button.png").getImage();
    public static final Image equilateralTriangleDepressed = new ImageIcon("src/res/depressed/equilateral-triangle.png").getImage();
    public static final Image fileTextBoxDepressed = new ImageIcon("src/res/depressed/fileTextBox.png").getImage();
    public static final Image fillColorDepressed = new ImageIcon("src/res/depressed/fill-color.png").getImage();
    public static final Image freeStrokeDepressed = new ImageIcon("src/res/depressed/free-stroke.png").getImage();
    public static final Image hexagonDepressed = new ImageIcon("src/res/depressed/hexagon.png").getImage();
    public static final Image layerDownDepressed = new ImageIcon("src/res/depressed/layer-down.png").getImage();
    public static final Image layerUpDepressed = new ImageIcon("src/res/depressed/layer-up.png").getImage();
    public static final Image menuMenuButtonDepressed = new ImageIcon("src/res/depressed/menu-menu-button.png").getImage();
    public static final Image newMenuButtonDepressed = new ImageIcon("src/res/depressed/new-menu-button.png").getImage();
    public static final Image openMenuButtonDepressed = new ImageIcon("src/res/depressed/open-menu-button.png").getImage();
    public static final Image paletteDepressed = new ImageIcon("src/res/depressed/palette.png").getImage();
    public static final Image pentagramDepressed = new ImageIcon("src/res/depressed/pentagram.png").getImage();
    public static final Image rectangleDepressed = new ImageIcon("src/res/depressed/rectangle.png").getImage();
    public static final Image redoMenuButtonDepressed = new ImageIcon("src/res/depressed/redo-menu-button.png").getImage();
    public static final Image redoDepressed = new ImageIcon("src/res/depressed/redo.png").getImage();
    public static final Image removeLayerDepressed = new ImageIcon("src/res/depressed/remove-layer.png").getImage();
    public static final Image rightAngleTriangleDepressed = new ImageIcon("src/res/depressed/right-angle-triangle.png").getImage();
    public static final Image saveMenuButtonDepressed = new ImageIcon("src/res/depressed/save-menu-button.png").getImage();
    public static final Image strokeColorDepressed = new ImageIcon("src/res/depressed/stroke-color.png").getImage();
    public static final Image strokeWidthDepressed = new ImageIcon("src/res/depressed/stroke-width.png").getImage();
    public static final Image undoMenuButtonDepressed = new ImageIcon("src/res/depressed/undo-menu-button.png").getImage();
    public static final Image undoDepressed = new ImageIcon("src/res/depressed/undo.png").getImage();
    public static final Image visibleDepressed = new ImageIcon("src/res/depressed/visible.png").getImage();
    public static final Image gridEmpty = new ImageIcon("src/res/depressed/grid/empty.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
    public static final Image grid16x16 = new ImageIcon("src/res/depressed/grid/grid16x16.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
    public static final Image grid2x2 = new ImageIcon("src/res/depressed/grid/grid2x2.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
    public static final Image grid32x32 = new ImageIcon("src/res/depressed/grid/grid32x32.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
    public static final Image grid4x4 = new ImageIcon("src/res/depressed/grid/grid4x4.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
    public static final Image grid64x64 = new ImageIcon("src/res/depressed/grid/grid64x64.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
    public static final Image grid8x8 = new ImageIcon("src/res/depressed/grid/grid8x8.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
    public static final Image gridOff = new ImageIcon("src/res/depressed/grid/gridOff.png").getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);

    /**
     * Constructor is private to avoid instantiation. The variables are public static and hence can
     * be used directly
     */
    private ResourceManager() {
    }

    /**
     * I made this function to make sure all the images are first loaded before the app launches
     */
    public static void init() {
        new ResourceManager();
    }
}
