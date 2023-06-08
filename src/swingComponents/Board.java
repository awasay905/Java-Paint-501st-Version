package swingComponents;

import shapes.Grid;
import uicomponents.DrawingCanvas;
import uicomponents.toolbars.*;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener, MouseInputListener {
    private final int BOARD_WIDTH = 1080;
    private final int BOARD_HEIGHT = 720;
    private final int DELAY = 25;
    Menubar menubar;
    DrawingToolbar drawingToolbar;
    ShapesToolbar shapesToolbar;
    ColorToolbar colorToolbar;
    LayerToolbar layerToolbar;
    Toolbar toolbar;
    ArrayList<Toolbar> toolbars;
    DrawingCanvas drawingCanvas;
    private Timer timer;

    public Board() {
        initBoard();
    }

    private void InitializeAssets() {
        toolbars = new ArrayList<>();
        //------------------------Bavkground--------------------------//
        toolbar = new Toolbar(0, 40, BOARD_WIDTH, 0);
        toolbar.setBackgroundColor(Color.LIGHT_GRAY);

        //menubar
        menubar = new Menubar(0, 0, BOARD_WIDTH, 40);
        menubar.setBackgroundColor(Color.GRAY);
        //-----------------------------Drawing Toolbar-----------------------------------------------------//
        drawingToolbar = new DrawingToolbar(0, 40, 250, 80);
        toolbars.add(drawingToolbar);

        Toolbar firstDivider = new Toolbar(250, 40, 2, 80);
        firstDivider.setBackgroundColor(Color.GRAY.darker());
        toolbars.add(firstDivider);

        //------------------------------SHAPES TOOLBAR--------------------------------------\\
        shapesToolbar = new ShapesToolbar(132 + 120, 40, 180, 80);
        toolbars.add(shapesToolbar);

        Toolbar secondDivider = new Toolbar(312 + 120, 40, 2, 80);
        secondDivider.setBackgroundColor(Color.GRAY.darker());
        toolbars.add(secondDivider);

        //-------------------------Color Toolbar ------------------------------------//
        colorToolbar = new ColorToolbar(314 + 120, 40, 370, 80);
        toolbars.add(colorToolbar);

        Toolbar thirdDivider = new Toolbar(684 + 120, 40, 2, 80);
        thirdDivider.setBackgroundColor(Color.GRAY.darker());
        toolbars.add(thirdDivider);

        //Empty
        Toolbar emptyToolbar = new Toolbar(686 + 120, 40, 394, 80);
        emptyToolbar.setBackgroundColor(Color.GRAY.brighter());
        toolbars.add(emptyToolbar);

        //------------------------------LAYER TOOLBAR-----------------------------------------//
        layerToolbar = new LayerToolbar(880, 120, 200, 600);
        layerToolbar.setBackgroundColor(Color.LIGHT_GRAY.darker());
        toolbars.add(layerToolbar);

        //Canvas
        drawingCanvas = new DrawingCanvas(0, 120, BOARD_WIDTH - 200, BOARD_HEIGHT - 120);
    }

    private void initBoard() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(new TAdapter());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setFocusable(true);

        InitializeAssets();

        timer = new Timer(DELAY, this);
        timer.start();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawingCanvas.draw(g);
        layerToolbar.drawShapes(g);
        Grid.getInstance().draw(g);
        toolbar.draw(g);

        for (Toolbar tb : toolbars) {
            tb.draw(g);
        }

        menubar.draw(g);

        menubar.drawToolTips(g);
        if (!menubar.isOpen()) {
            for (Toolbar tb : toolbars) {
                tb.drawToolTips(g);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Toolkit.getDefaultToolkit().sync();
        repaint();
        menubar.setLt(layerToolbar);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Checks if color selection window is open
        if (((ColorToolbar) toolbars.get(4)).colorWindowOpen()) {
            toolbars.get(4).onClick(e.getX(), e.getY());
            return;
        }

        if (!menubar.isOpen()) {
            for (Toolbar tb : toolbars) {
                tb.onClick(e.getX(), e.getY());
            }
        }

        menubar.onClick(e.getX(), e.getY());
        if (!menubar.fileSelectionWindowOpen()) {
            drawingCanvas.mouseClicked(e);
        }
        //drawingCanvas.mouseClicked(e);
        layerToolbar.updateThumbnail();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        menubar.setShapes(layerToolbar.getList());
        drawingCanvas.setShapes(layerToolbar.getList());
        drawingCanvas.setShapeDetails(shapesToolbar.getSelectedShape(), colorToolbar.getFillColor(), colorToolbar.getStrokeColor(), shapesToolbar.getStrokeSize());
        drawingCanvas.setLineDrawing(drawingToolbar.isFreeformSelected());
        drawingCanvas.setShapeDrawing(drawingToolbar.isShapeSelected());
        drawingCanvas.setBezierDrawing(drawingToolbar.isBezierSelected());
        //Checks if color selection widnow is open
        if (((ColorToolbar) toolbars.get(4)).colorWindowOpen()) {
            return;
        }

        if (menubar.isOpen()) {
            return;
        }
        if (menubar.fileSelectionWindowOpen()) {
            return;
        }
        drawingCanvas.mousePressed(e);
        layerToolbar.updateThumbnail();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Checks if color selection widnow is open
        if (((ColorToolbar) toolbars.get(4)).colorWindowOpen()) {
            return;
        }
        if (menubar.isOpen()) {
            return;
        }
        if (menubar.fileSelectionWindowOpen()) {
            return;
        }
        drawingCanvas.mouseReleased(e);
        layerToolbar.updateThumbnail();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (((ColorToolbar) toolbars.get(4)).colorWindowOpen()) {
            toolbars.get(4).onClick(e.getX(), e.getY());
            return;
        }
        if (menubar.isOpen()) {
            return;
        }
        if (menubar.fileSelectionWindowOpen()) {
            return;
        } else drawingCanvas.mouseDragged(e);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        drawingCanvas.mouseMoved(e);
        menubar.onHover(e.getX(), e.getY());
        for (Toolbar tb : toolbars) {
            tb.onHover(e.getX(), e.getY());
        }

        for (Toolbar tb : toolbars) {
            tb.setTooltipsCoordinates(e.getX(), e.getY());
        }
        menubar.setTooltipsCoordinates(e.getX(), e.getY());
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            menubar.shortcutKeyPressed(e.getKeyCode());

            for (Toolbar tb : toolbars) {
                tb.shortcutKeyPressed(e.getKeyCode());
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }
    }

}
