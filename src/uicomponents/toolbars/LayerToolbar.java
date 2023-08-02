package uicomponents.toolbars;

import res.ResourceManager;
import shapes.Shape;
import uicomponents.Shortcuts;
import uicomponents.Textbox;
import uicomponents.buttons.ActiveButton;
import uicomponents.buttons.LayerButton;
import uicomponents.buttons.ToggleButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LayerToolbar extends Toolbar {
    private LayerButton selectedLayer;
    private final Toolbar layerActionButtons;
    private final Textbox title;
    private int counter;
    public void open(LayerToolbar lt){
        this.selectedLayer = lt.selectedLayer;
        this.counter = lt.counter;
        this.setToggleButtons(lt.getButtons());
    }

    public LayerToolbar(int x, int y, int width, int height) {
        super(x, y, width, height);
        layerActionButtons = new Toolbar(x, y, width, 30);
        setBackgroundColor(Color.BLACK.brighter());
        title = new Textbox(x, y, width, 30, "Layers", Color.BLACK, false);
        counter = 0;

        ActiveButton addLayer = new ActiveButton(x + 10, y + 5, 20, 20, ResourceManager.addLayerPressed,ResourceManager.addLayerDepressed, "", Shortcuts.addLayer, this::addLayer);
        ActiveButton removeLayer = new ActiveButton(x + 10 + 5 + 20, y + 5, 20, 20, ResourceManager.removeLayerPressed, ResourceManager.removeLayerDepressed, "", Shortcuts.removeLayer, this::removeLayer);
        ActiveButton moveUp = new ActiveButton(x + width - 10 - 20, y + 5, 20, 20,ResourceManager.layerUpPressed, ResourceManager.layerUpDepressed, "", Shortcuts.moveLayerUp, this::moveUp);
        ActiveButton moveDown = new ActiveButton(x + width - 10 - 20 - 5 - 20, y + 5, 20, 20, ResourceManager.layerDownPressed, ResourceManager.layerDownDepressed, "", Shortcuts.moveLayerDown, this::moveDown);

        addLayer.setHelpText("Add Layer " + "(" + (char) Shortcuts.addLayer + ")");
        removeLayer.setHelpText("Remove Layer " + "(" + (char) Shortcuts.removeLayer + ")");
        moveUp.setHelpText("Move Layer Up " + "(" + (char) Shortcuts.moveLayerUp + ")");
        moveDown.setHelpText("Move Layer Down " + "(" + (char) Shortcuts.moveLayerDown + ")");
        layerActionButtons.add(addLayer);
        layerActionButtons.add(removeLayer);
        layerActionButtons.add(moveUp);
        layerActionButtons.add(moveDown);
        layerActionButtons.setBackgroundColor(Color.GRAY.darker());

        addLayer();
        selectedLayer = (LayerButton) getButtons().get(0);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void reset() {
        setToggleButtons(new ArrayList<>());
    }

    public void setSelectedLayer(LayerButton selectedLayer) {
        this.selectedLayer = selectedLayer;
    }

    @Override
    public void onHover(int x, int y) {
        super.onHover(x, y);
        layerActionButtons.onHover(x, y);
    }

    public void addLayer() {
        if (getButtons().size() >= 10) return;
        counter++;
        super.add(new LayerButton(getX(), getY() + layerActionButtons.getHeight() + (50 + 2) * (getButtons().size()), getWidth(), 50, "Layer " + counter));
    }

    private void updateY() {
        int i = 0;
        for (ToggleButton b : getButtons()) {
            LayerButton lb = (LayerButton) b;
            lb.updateY(getY() + layerActionButtons.getHeight() + (50 + 2) * (i));
            i++;
        }
    }

    public void moveUp() {
        if (selectedLayer == null) return;
        LayerButton tempLayer = selectedLayer;
        int oldIndex = getButtons().indexOf(tempLayer);
        if (oldIndex >= 1) {
            getButtons().remove(tempLayer);
            getButtons().add(oldIndex - 1, tempLayer);
            updateY();
        }
    }

    public void moveDown() {
        if (selectedLayer == null) return;
        LayerButton tempLayer = selectedLayer;
        int oldIndex = getButtons().indexOf(tempLayer);
        if (oldIndex < getButtons().size() - 1) {
            getButtons().remove(tempLayer);
            getButtons().add(oldIndex + 1, tempLayer);
            updateY();
        }
    }

    public void removeLayer() {
        if (selectedLayer == null) return;
        if (getButtons().size() == 1) return;
        if (selectedLayer.isActive()) {
            getButtons().remove(selectedLayer);
            selectedLayer = (LayerButton) getButtons().get(0);
            updateY();
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (selectedLayer != null) selectedLayer.setActive(true);
        layerActionButtons.draw(g);
        title.draw(g);

    }

    public void drawShapes(Graphics g) {
        for (int i = getButtons().size() -1; i >= 0; i--) {
            LayerButton temp = (LayerButton) getButtons().get(i);
            temp.drawShapes(g);
        }
    }

    @Override
    public void drawToolTips(Graphics g) {
        super.drawToolTips(g);

        for (ToggleButton tb : layerActionButtons.getButtons()) {
            tb.drawTooltip(g);
        }
    }

    @Override
    public void setTooltipsCoordinates(int x, int y) {
        super.setTooltipsCoordinates(x, y);
        for (ToggleButton tb : layerActionButtons.getButtons()) {
            tb.setTooltipCoordinates(x, y);
        }
    }

    @Override
    public void onClick(int x, int y) {
        layerActionButtons.onClick(x, y);
        for (ToggleButton b : getButtons()) {
            if (b.isClicked(x, y)) { //If a layer is clicked, it makes it active and sets it as selected layer
                b.onClick(x, y);
                selectedLayer = (LayerButton) b;
                selectedLayer.setActive(b.isPressed());
               // if (!b.isPressed()) selectedLayer = (LayerButton) getButtons().get(0); //there will be no unselected layer
            } else { //It sets all other layers to false.
                b.setPressed(false);
                ((LayerButton) b).setActive(false);
            }
        }
    }

    @Override
    public void shortcutKeyPressed(int keyCode) {
        super.shortcutKeyPressed(keyCode);
        layerActionButtons.shortcutKeyPressed(keyCode);
    }

    public ArrayList<Shape> getList() {
        return selectedLayer.getShapes();
    }

    public void updateThumbnail(){
        for (int i = getButtons().size() -1; i >= 0; i--) {
            LayerButton temp = (LayerButton) getButtons().get(i);
            temp.updateThumbnail();
        }
    }

}
