package uicomponents.buttons;

import shapes.Shape;
import uicomponents.MouseListener;
import uicomponents.Textbox;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LayerButton extends ToggleButton {
    private static final long serialVersionUID = 9022930223438610390L;
    private final ToggleButton visibility;
    private final String layerName;
    private final int imgHeight;
    private boolean isActive;
    private boolean isVisible;
    private transient Image thumbnail;
    private ArrayList<Shape> shapes;
    private int imgWidth;
    private Textbox title;

    public LayerButton(int x, int y, int width, int height, String layerName) {
        super(x, y, width, height, new ImageIcon("empty").getImage(), new ImageIcon("empty").getImage(), "", -1, null);
        isActive = false;
        shapes = new ArrayList<>();
        this.layerName = layerName;

        this.setMouseListener((MouseListener) () -> isActive = !isActive);

        title = new Textbox(x + imgWidth + 30, y, width - imgWidth - 40, height, layerName);
        title.setTextSize(15);
        title.setTextColor(Color.BLACK);
        title.setAlignment(Textbox.ALIGN_RIGHT);

        imgHeight = (int) (height * 0.9);
        imgWidth = (int) (imgHeight * 1.5);
        thumbnail = shapesToImage();

        visibility = new ToggleButton(x + height / 2 - 30 / 2, y + height / 2 - 30 / 2, 30, 30, new ImageIcon("src/res/depressed/visible.png").getImage(), new ImageIcon("src/res/pressed/not-visible.png").getImage(), "", -1, (MouseListener) () -> {
        });
        isVisible = true;
        visibility.setPressed(true);
        visibility.setHelpText("Hide " + layerName);
        setHelpText(layerName);

        //new Timer(500, e -> thumbnail = shapesToImage()).start(); no need
    }

    public void updateY(int y) {
        setY(y);
        visibility.setY(y + getHeight() / 2 - 30 / 2);
        title = new Textbox(getX() + imgWidth + 30, y, getWidth() - imgWidth - 40, getHeight(), layerName);
        title.setTextSize(15);
        title.setTextColor(Color.BLACK);
        title.setAlignment(Textbox.ALIGN_RIGHT);
    }

    @Override
    public void draw(Graphics g) {
        if (visibility.getCurrent() == null){
            visibility.setOnPress(new ImageIcon("src/res/depressed/visible.png").getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
            visibility.setDePress(new ImageIcon("src/res/pressed/not-visible.png").getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
            visibility.setPressed(visibility.isPressed());
        }
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        g.drawRect(getX(), getY(), getWidth(), getHeight());
        super.draw(g);
        visibility.draw(g);
        g.drawImage(thumbnail, getX() + getHeight() / 2 - 30 / 2 + 30 + 30 / 2, getY() + getHeight() * 5 / 100, null);
        title.draw(g);
        if (isActive) {
            g.setColor(new Color(0, 0, 0, 89));
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public void onClick(int x, int y) {
        if (visibility.isClicked(x, y)) {
            if (visibility.isPressed()) {
                visibility.setHelpText("Show " + layerName);
            } else visibility.setHelpText("Hide " + layerName);
            visibility.onClick(x, y);
            isVisible = !isVisible;
            return;
        }
        super.onClick(x, y);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
        visibility.setPressed(visible);
        if (isVisible) visibility.setHelpText("Hide " + layerName);
        else visibility.setHelpText("Show " + layerName);
    }

    @Override
    public void onHover(int x, int y) {
        super.onHover(x, y);
        visibility.onHover(x, y);
        if (visibility.isHover()) {
            super.setHover(false);
        }
    }

    @Override
    public void drawTooltip(Graphics g) {
        super.drawTooltip(g);
        visibility.drawTooltip(g);
    }

    @Override
    public void setTooltipCoordinates(int x, int y) {
        super.setTooltipCoordinates(x, y);
        visibility.setTooltipCoordinates(x, y);
    }

    private Image shapesToImage() {
        BufferedImage bi = new BufferedImage(1080, 720, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1080, 720);

        for (Shape s : shapes) {
            s.draw(g);
        }
        return bi.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
    }

    public void updateThumbnail(){
        thumbnail = shapesToImage();
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public void drawShapes(Graphics g) {
        if (isVisible) {
            for (Shape s : shapes) {
                s.draw(g);
            }
        }
    }

    public String getTitle() {
        return title.getText();
    }

    public void setTitle(String s) {
        title.setText(s);
        if (isVisible) visibility.setHelpText("Hide " + s);
        else visibility.setHelpText("Show " + s);
        setHelpText(s);
    }
}
