package uicomponents.toolbars;

import helper.PaintInfo;
import res.ResourceManager;
import uicomponents.Shortcuts;
import uicomponents.Textbox;
import uicomponents.buttons.ActiveButton;
import uicomponents.buttons.LayerButton;
import uicomponents.buttons.ToggleButton;

import java.awt.*;
import java.util.ArrayList;

/**
 * The class representing the layers toolbar. It has a list of layers,
 * with minimum of one layer and maximum of 11 layers. There is no scrolling.
 * It contains buttons to add,remove, and move layers
 * The layer is represented by a LayerButton. It contains a thumbnail and another
 * button to select the visibility of the layer.
 * Each layer shows a small thumbnail of what is contains. The final image
 * is drawn from bottom to top, meaning that top layer will be drawn on top of bottom.
 */
public class LayerToolbar extends Toolbar {
    /**
     * A variable to hold the reference to the currently selected layer to help in
     * adding, deleting and moving layers
     */
    private LayerButton selectedLayer;
    /**
     * A toolbar which contains the buttons to manipulate layers
     */
    private final Toolbar layerActionButtons;
    /**
     * A textbox which shows the word Layers on the top of layers toolbar
     */
    private final Textbox title;
    /**
     * A int to hold count of number of layers.
     * TODO: Change it to byte because no one will make more the 256 layers
     */
    private int counter;

    /**
     * The function to use in opening file from saved file.
     * Basically through serialization the entire layerToolbar is saved,
     * and when user open it, just set the current layerToolbar's counter,
     * selected layer, and layerButton(as toggleButton) arraylist to the one loaded from deserialization
     * @param layerToolbar the layerToolbar object from the opened file
     */
    public void open(LayerToolbar layerToolbar){
        this.selectedLayer = layerToolbar.selectedLayer; //sets the selected layer to the one of opened file
        this.selectedLayer.setActive(true);
        PaintInfo.getInstance().setShapes(selectedLayer.getShapes());
        this.counter = layerToolbar.counter;
        this.setToggleButtons(layerToolbar.getButtons());  //sets the layerbuttons to openend file
        PaintInfo.getInstance().updateThumbnail(); //updates the thumbnail when opening file
    }

    /** Constructor
     * Initializes the layerToolbar, the buttons for manipulating the layers,
     * Initializes the layerActionButtons toolbar and adds the button to them,
     * and passes the arrayList of shapes of the selected layer to the paintInfo
     * and passes the updateFunction SAI to paintInfo to improve thumbnail loading
     * @param x      the x coordinate of the menubar according to java graphics coordinates
     * @param y      the y coordinate of the menubar according to java graphics coordinates
     * @param width  the width of the menubar
     * @param height the height of the menubar
     */

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
        selectedLayer.setActive(true);
        PaintInfo.getInstance().setShapes(selectedLayer.getShapes());  //initializes the layer
        PaintInfo.getInstance().setUpdateThumbnail(this::updateThumbnail);  //set the thumbnail function
    }

    /**
     * This function is called when user opens new file.
     * It resets the counter, clears the arrayList of layerbuttons,
     * and passed new shapes arraylist from the first layer of new layerButtons
     */
    public void reset() {
        setToggleButtons(new ArrayList<>()); //new layerButtons
        this.counter = 0;
        addLayer(); //there should be atleast one layer
        selectedLayer = (LayerButton) getButtons().get(0); //the new layer is selected
        selectedLayer.setActive(true);
        PaintInfo.getInstance().setShapes(selectedLayer.getShapes()); //the arraylist of shapes from the new layer is passed to paintInfo
    }

    /**
     * Overridden from toolbar class to call the onhovr of layerActionButtons as well
     * @param x the x-coordinate of the mouse
     * @param y the y-cooridnate of the mouse
     */
    @Override
    public void onHover(int x, int y) {
        super.onHover(x, y);
        layerActionButtons.onHover(x, y);
    }

    /**
     * Adds a new layer to the bottom, and give it a number from counter, and make sure that no more than 11 layers can be added
     */
    public void addLayer() {
        if (getButtons().size() >= 10) return;
        counter++;
        super.add(new LayerButton(getX(), getY() + layerActionButtons.getHeight() + (50 + 2) * (getButtons().size()), getWidth(), 50, "Layer " + counter));
    }

    /**
     * Helps to rearrange the layers when a layer is deleted/moved
     */
    private void updateY() {
        int i = 0;
        for (ToggleButton b : getButtons()) {
            LayerButton lb = (LayerButton) b;
            lb.updateY(getY() + layerActionButtons.getHeight() + (50 + 2) * (i));
            i++;
        }
    }

    /**
     * Moves the selected layer up. It doesn't work if there is no layer selected, or if the top most layer is selected
     */
    public void moveUp() {
        if (selectedLayer == null) return;
        LayerButton tempLayer = selectedLayer;
        int oldIndex = getButtons().indexOf(tempLayer);
        if (oldIndex >= 1) { //makes sure the topMost layer is not selected
            getButtons().remove(tempLayer);
            getButtons().add(oldIndex - 1, tempLayer);
            updateY();
        }
    }

    /**
     * Moves the selected layer down. it doesn;t work if there are no selected layer, or if the bottom most layer is selected
     */
    public void moveDown() {
        if (selectedLayer == null) return;
        LayerButton tempLayer = selectedLayer;
        int oldIndex = getButtons().indexOf(tempLayer);
        if (oldIndex < getButtons().size() - 1) { //makes sure the bottom most layer is not selected
            getButtons().remove(tempLayer);
            getButtons().add(oldIndex + 1, tempLayer);
            updateY();
        }
    }

    /**
     * Removes the selected layer. since there should be atleast one layer, it doesn't work if there is only one layer
     * It also doesnt work if no layer selected
     */
    public void removeLayer() {
        if (selectedLayer == null) return;
        if (getButtons().size() == 1) return;
        if (selectedLayer.isActive()) {
            getButtons().remove(selectedLayer);
            selectedLayer = (LayerButton) getButtons().get(0);
            selectedLayer.setActive(true);
            PaintInfo.getInstance().setShapes(selectedLayer.getShapes()); //when removing a layer, the top most becomes selected
            updateY();
        }
    }

    /**
     * Draws the layer toolbar. The selected layer is drawn darker
     * @param g graphics object
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        layerActionButtons.draw(g);
        title.draw(g);
        //if (selectedLayer != null ) selectedLayer.setActive(true); this is causing flashes probably. update,
        //the flashes was because the selected layer was getting unactive and active in the onClick method,
        //now fix
    }

    /**
     * Draws all the shapes in the layers on the canvas
     * TODO: Improve it's working
     * @param g graphics object
     */
    public void drawShapes(Graphics g) {
        for (int i = getButtons().size() -1; i >= 0; i--) {
            LayerButton temp = (LayerButton) getButtons().get(i);
            temp.drawShapes(g);
        }
    }

    /**
     * Draws the tooltips
     * @param g the graphics object
     */
    @Override
    public void drawToolTips(Graphics g) {
        super.drawToolTips(g);

        for (ToggleButton tb : layerActionButtons.getButtons()) {
            tb.drawTooltip(g);
        }
    }

    /**
     * Sets tooltops coordinates. This one calls the super class function and then draws the tooltip of
     * layerActionButtons
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     */
    @Override
    public void setTooltipsCoordinates(int x, int y) {
        super.setTooltipsCoordinates(x, y);
        for (ToggleButton tb : layerActionButtons.getButtons()) {
            tb.setTooltipCoordinates(x, y);
        }
    }

    /**
     * The onClick method which is called when mouse is clicked
     * First it calls the onClick of the layerActionButtons. Then it calls the onClick of
     * each layer button individually.
     * If a layer button is clicked, it is set as the selected layer, and is made active
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     */
    @Override
    public void onClick(int x, int y) {
        if (isClicked(x,y)) { //makes sure that the functions are called only when the toolbar is clicked
            layerActionButtons.onClick(x, y);
            for (ToggleButton b : getButtons()) {
                if (b.isClicked(x, y)) { //If a layer is clicked, it makes it active and sets it as selected layer
                    deactivateAllLayers();
                    b.onClick(x, y);
                    selectedLayer = (LayerButton) b;
                    PaintInfo.getInstance().setShapes(selectedLayer.getShapes());  //makes the clicked layer selected
                    selectedLayer.setActive(true);
                }

            }
        }
    }

    /**
     * A helper function to set all layers as deactive.
     */
    private void deactivateAllLayers(){
        for (ToggleButton b : getButtons()) {
            b.setPressed(false);
                ((LayerButton) b).setActive(false);
            }
    }

    /**
     * process shrtcut key press
     * @param keyCode the ascii code of the key pressed
     */
    @Override
    public void shortcutKeyPressed(int keyCode) {
        super.shortcutKeyPressed(keyCode);
        layerActionButtons.shortcutKeyPressed(keyCode);
    }

    /**
     * For all layers, it updates their thumbnail
     */
    private void updateThumbnail(){
        for (int i = getButtons().size() -1; i >= 0; i--) {
            LayerButton temp = (LayerButton) getButtons().get(i);
            temp.updateThumbnail();
        }
    }

}
