package uicomponents.toolbars;

import helper.PaintInfo;
import res.ResourceManager;
import uicomponents.Shortcuts;
import uicomponents.buttons.ActiveButton;
import uicomponents.buttons.MenuButton;
import uicomponents.buttons.ToggleButton;
import uicomponents.windows.FileSelectionWindow;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The class representing the top bar of the app. It contains the buttons for
 * file menu, edit menu, as well as undo,redo shortcut.
 * It also contains the file selection window object which opens to let user
 * select which saved file to open from
 */
public class Menubar extends Toolbar {
    /**
     * The file selection window. It shows the list of files saved. User can open saved
     * file by clicking on the file name box.
     */
    private final FileSelectionWindow fileSelectionWindow = new FileSelectionWindow(335, 160, 300, 500, "Saved Files");
    /**
     * The layerToolbar. It is used for the sake of saving and opening files
     */
    private LayerToolbar layerToolbar;

    /**
     * Creates the menu bar based on the given parameters. It adds file and edit menu button. File menu button
     * contains the save, open, and new file button. Edit menu button contains the undo,redo button.
     * There are also quick buttons for undo and redo
     *
     * @param x      the x coordinate of the menubar according to java graphics coordinates
     * @param y      the y coordinate of the menubar according to java graphics coordinates
     * @param width  the width of the menubar
     * @param height the height of the menubar
     */
    public Menubar(int x, int y, int width, int height) {
        super(x, y, width, height);

        MenuButton fileMenu = new MenuButton(x, y + 2, 100, 36, ResourceManager.menuMenuButtonPressed, ResourceManager.menuMenuButtonDepressed, "", Shortcuts.openFileMenu, () -> {});
        fileMenu.setHelpText("Save,Open,New " + "(" + (char) Shortcuts.openFileMenu + ")");

        ActiveButton saveFile = new ActiveButton(x, y + 40, 100, 40, ResourceManager.saveMenuButtonPressed, ResourceManager.saveMenuButtonDepressed, "", Shortcuts.save, this::saveFile);
        ActiveButton openFile = new ActiveButton(x, y + 80, 100, 40, ResourceManager.openMenuButtonPressed, ResourceManager.openMenuButtonDepressed, "", Shortcuts.open, this::openFile);
        ActiveButton newFile = new ActiveButton(x, y + 120, 100, 40, ResourceManager.newMenuButtonPressed, ResourceManager.newMenuButtonDepressed, "", Shortcuts.newFile, this::newFile);

        saveFile.setHelpText("Save the File " + "(" + (char) Shortcuts.save + ")");
        openFile.setHelpText("Open the File " + "(" + (char) Shortcuts.open + ")");
        newFile.setHelpText("New File " + "(" + (char) Shortcuts.newFile + ")");
        fileMenu.add(saveFile);
        fileMenu.add(newFile);
        fileMenu.add(openFile);

        MenuButton editMenu = new MenuButton(x + 102, y + 2, 100, 36, ResourceManager.editMenuButtonPressed, ResourceManager.editMenuButtonDepressed, "", Shortcuts.openEditMenu, () -> {});
        editMenu.setHelpText("Edit the drawing " + "(" + (char) Shortcuts.openEditMenu + ")");
        ActiveButton undo = new ActiveButton(x + 102, y + 40, 100, 40, ResourceManager.undoMenuButtonPressed, ResourceManager.undoMenuButtonDepressed, "", Shortcuts.undo, this::undo);
        ActiveButton redo = new ActiveButton(x + 102, y + 80, 100, 40, ResourceManager.redoMenuButtonPressed, ResourceManager.redoMenuButtonDepressed, "", Shortcuts.redo, this::redo);
        ActiveButton undoShortcut = new ActiveButton(x + 204, y + 2, 36, 36, ResourceManager.undoPressed, ResourceManager.undoDepressed, "", -1, this::undo);
        ActiveButton redoShortcut = new ActiveButton(x + 242, y + 2, 36, 36, ResourceManager.redoPressed, ResourceManager.redoDepressed, "", -1, this::redo);

        undo.setHelpText("Undo Changes " + "(" + (char) Shortcuts.undo + ")");
        undoShortcut.setHelpText("Undo Changes " + "(" + (char) Shortcuts.undo + ")");
        redo.setHelpText("Redo Changes " + "(" + (char) Shortcuts.redo + ")");
        redoShortcut.setHelpText("Redo Changes " + "(" + (char) Shortcuts.redo + ")");

        editMenu.add(undo);
        editMenu.add(redo);

        add(fileMenu);
        add(editMenu);
        add(undoShortcut);
        add(redoShortcut);
    }

    /**
     * To check if a menu button is open
     *
     * @return the status of menu button opening
     */
    public boolean isOpen() {
        for (ToggleButton b : getButtons()) {
            if (b instanceof MenuButton) {
                if (((MenuButton) b).isOpen()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * The function which runs when the menu bar is clicked.
     * First it checks if fileSelection window is open, if it is, the onClick on fileSelection window is called,
     * and the function returns.
     * Otherwise, the super method is called. It calls the onClick of all the buttons.
     * In this case, those are the file menu button, edit menu button, and the undo redo shortcut
     * The onClick of menu buttons further checks which menu is open and then calls the onClick of those menus.
     * In case a menu is open, and it's respective button is clicked, the menu closes.
     * It also makes sure that only one menu opens at a time.
     * The undo/redo shortcut buttons will close any menu if it is open. This is implemented in the function passed to them,below
     *
     * @param x The x-coordinate of the mouse
     * @param y The y-coordinate of the mouse
     */
    @Override
    public void onClick(int x, int y) {
        if (fileSelectionWindow.isOpen()) {
            fileSelectionWindow.onClick(x, y);
            return;
        }
        super.onClick(x, y);
        MenuButton fileMenuButton = (MenuButton) getButtons().get(0);
        MenuButton editMenuButton = (MenuButton) getButtons().get(1);

        //The code below closes one menu if you try to open two at a time
        if (fileMenuButton.isClicked(x, y) && fileMenuButton.isOpen()) {
            editMenuButton.setOpen(false);
            editMenuButton.setPressed(false);
        }

        if (editMenuButton.isClicked(x, y) && editMenuButton.isOpen()) {
            fileMenuButton.setOpen(false);
            fileMenuButton.setPressed(false);
        }

       //TODO:The menu should close when clicked outside it

    }

    /**
     * It closes the file and edit menu if it is open. This is called from undo,redo buttons
     */
    private void closeAllMenus() {
        MenuButton fileMenuButton = (MenuButton) getButtons().get(0);
        MenuButton editMenuButton = (MenuButton) getButtons().get(1);
        editMenuButton.setOpen(false);
        editMenuButton.setPressed(false);
        fileMenuButton.setOpen(false);
        fileMenuButton.setPressed(false);
    }

    /**
     * It sets the isHover boolean value based on the position of the mouse
     * If the mouse is above the components, isHover becomes true
     * Otherwise it is false
     * If the fileSelectionWindow is open, then only its onHover is called
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     */
    @Override
    public void onHover(int x, int y) {
        if (fileSelectionWindow.isOpen()) fileSelectionWindow.onHover(x, y);
        else super.onHover(x, y);
    }

    /**
     * It draws the menubar on the canvas, which includes the buttons.
     * If fileSelectionWindow is open, then it is also drawn
     *
     * @param g the graphics object
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (fileSelectionWindow.isOpen()) fileSelectionWindow.draw(g);
    }

    /**
     * Draws the tooltip. If the fileSelectionWindow is opened,
     * then only its tooltips are drawn. Otherwise, only the menuButtons tooltips are shown
     *
     * @param g the graphics object
     */
    @Override
    public void drawToolTips(Graphics g) {
        if (fileSelectionWindow.isOpen()) {
            fileSelectionWindow.drawToolTips(g);
            return;
        }
        //draws the tooltips on the menubar buttons
        super.drawToolTips(g);

        //this draws the tooltips on the menu buttons if the menu is open
        if (isOpen()) {
            for (int i = 0; i < 2; i++) {
                ToggleButton tb = getButtons().get(i);
                MenuButton tb1 = (MenuButton) tb;
                if (tb1.isOpen()) {
                    for (ToggleButton t : tb1.getMenuToggleButtons()) {
                        t.drawTooltip(g);
                    }
                }
            }
        }

    }

    /**
     * Sets the coordinates of the tooltip.
     *
     * @param x the x-coordinate of the mouse
     * @param y the y-coordinate of the mouse
     */
    @Override
    public void setTooltipsCoordinates(int x, int y) {
        super.setTooltipsCoordinates(x, y);
        for (int i = 0; i < 2; i++) {
            ToggleButton tb = getButtons().get(i);
            MenuButton tb1 = (MenuButton) tb;
            for (ToggleButton t : tb1.getMenuToggleButtons()) {
                t.setTooltipCoordinates(x, y);
            }
        }
        fileSelectionWindow.setTooltipsCoordinates(x, y);
    }

    /**
     * This function is called when the file is saved.
     * It saves the current state of the program which is the shapes drawn in the layers
     * using the current date and time as file name.
     * The files are saved in the saveFiles folder inside src folder
     * TODO: Add option to save pic as PNG
     */
    private void saveFile() {
        try {
            File f = new File("src/saveFiles/" + getCurrentDateTime());

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(layerToolbar);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileSelectionWindow.updateFileList();
    }

    /**
     * Returns the current date and time as a string in the format dd-MM-yy--hh-mm-ss
     *
     * @return the current date and time as a string in the format dd-MM-yy--hh-mm-ss
     */
    private String getCurrentDateTime() {
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy--hh-mm-ss");

        return dateFormat.format(currentDate);
    }

    /**
     * This functions launches the fileSelectionWindow and makes user open
     * a file from the list of saved files
     */
    private void openFile() {
        fileSelectionWindow.setLt(layerToolbar);

        if (!fileSelectionWindow.isOpen()) fileSelectionWindow.open();
        else fileSelectionWindow.close();
    }

    /**
     * It simulates the behaviour of opening a new file
     * by emptying the current layer list
     */
    private void newFile() {
        layerToolbar.reset();
    }

    /**
     * To save the layers list from toolbar, and also to open and renew it,
     * this class needed access to the toolbar. This functions gets the toolbar from
     * the board class
     *
     * @param layerToolbar the layerToolbar used in board.java
     */
    public void setLayerToolbar(LayerToolbar layerToolbar) {
        this.layerToolbar = layerToolbar;
    }

    /**
     * Undoes the shapes drawn on the current selected layer and puts it into undo stack
     * Both the current layer and shape stack is taken from the paintInfo class
     * This also closes any menu which was open before
     */
    private void undo() {
        //Undo is now handled by paintInfo
        if (PaintInfo.getInstance().getShapes() != null && PaintInfo.getInstance().getShapes().size() >= 1) {
            PaintInfo.getInstance().getUndoStack().add(PaintInfo.getInstance().getShapes().remove(PaintInfo.getInstance().getShapes().size() - 1));
            PaintInfo.getInstance().updateThumbnail();
        }
        closeAllMenus();
    }

    /**
     * Redoes the shapes saved in the undo stack and puts it into current selected layer
     * Both the current layer and shape stack is taken from the paintInfo class
     * This also closes any menu which was open before
     */
    private void redo() {
        //Redo is now handled by paintInfo
        if (PaintInfo.getInstance().getUndoStack().size() >= 1) {
            if ((PaintInfo.getInstance().getShapes() != null)) {
                PaintInfo.getInstance().getShapes().add(PaintInfo.getInstance().getUndoStack().pop());
                PaintInfo.getInstance().updateThumbnail();
            }
        }
        closeAllMenus();
    }

    /**
     * Used to help in making click logic
     *
     * @return the status of fileSelectionWindow
     */
    public boolean isFileSelectionWindowOpen() {
        return fileSelectionWindow.isOpen();
    }
}
