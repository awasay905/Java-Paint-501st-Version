package uicomponents.toolbars;

import res.ResourceManager;
import shapes.Shape;
import uicomponents.Shortcuts;
import uicomponents.buttons.ActiveButton;
import uicomponents.buttons.LayerButton;
import uicomponents.buttons.MenuButton;
import uicomponents.buttons.ToggleButton;
import uicomponents.windows.FileSelectionWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

public class Menubar extends Toolbar {
    private final FileSelectionWindow fileSelectionWindow = new FileSelectionWindow(335, 160, 300, 500, "Saved Files");
    private LayerToolbar lt;
    private ArrayList<Shape> shapes;
    private Stack<Shape> undoStack;

    public Menubar(int x, int y, int width, int height) {
        super(x, y, width, height);

        MenuButton fileMenu = new MenuButton(x, y+2, 100, 36, ResourceManager.menuMenuButtonPressed,ResourceManager.menuMenuButtonDepressed, "", Shortcuts.openFileMenu, () -> {});
        fileMenu.setHelpText("Save,Open,New " + "(" + (char) Shortcuts.openFileMenu + ")");

        ActiveButton saveFile = new ActiveButton(x, y+40, 100, 40,ResourceManager.saveMenuButtonPressed,ResourceManager.saveMenuButtonDepressed, "", Shortcuts.save, this::saveFile);
        ActiveButton openFile = new ActiveButton(x, y+80, 100, 40, ResourceManager.openMenuButtonPressed, ResourceManager.openMenuButtonDepressed, "", Shortcuts.open, this::openFile);
        ActiveButton newFile = new ActiveButton(x, y+120, 100, 40, ResourceManager.newMenuButtonPressed, ResourceManager.newMenuButtonDepressed, "", Shortcuts.newFile, this::newFile);

        saveFile.setHelpText("Save the File " + "(" + (char) Shortcuts.save + ")");
        openFile.setHelpText("Open the File " + "(" + (char) Shortcuts.open + ")");
        newFile.setHelpText("New File " + "(" + (char) Shortcuts.newFile + ")");
        fileMenu.add(saveFile);
        fileMenu.add(newFile);
        fileMenu.add(openFile);

        MenuButton editMenu = new MenuButton(x+102, y+2, 100, 36, ResourceManager.editMenuButtonPressed, ResourceManager.editMenuButtonDepressed, "", Shortcuts.openEditMenu, () -> {});
        editMenu.setHelpText("Edit the drawing " + "(" + (char) Shortcuts.openEditMenu + ")");
        ActiveButton undo = new ActiveButton(x+102, y+40, 100, 40, ResourceManager.undoMenuButtonPressed, ResourceManager.undoMenuButtonDepressed, "", Shortcuts.undo, this::undo);
        ActiveButton redo = new ActiveButton(x+102, y+80, 100, 40, ResourceManager.redoMenuButtonPressed, ResourceManager.redoMenuButtonDepressed, "", Shortcuts.redo, this::redo);
        ActiveButton undoShortcut = new ActiveButton(x+204, y+2, 36, 36, ResourceManager.undoPressed, ResourceManager.undoDepressed, "", -1, this::undo);
        ActiveButton redoShortcut = new ActiveButton(x+242, y+2, 36, 36, ResourceManager.redoPressed, ResourceManager.redoDepressed, "", -1, this::redo);

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

    @Override
    public void onClick(int x, int y) {
        super.onClick(x, y);
        fileSelectionWindow.onClick(x, y);

        MenuButton file = (MenuButton) getButtons().get(0);
        MenuButton edit = (MenuButton) getButtons().get(1);

        if (file.isClicked(x, y) && file.isOpen()) {
            edit.setOpen(false);
            edit.setPressed(false);
        }

        if (edit.isClicked(x, y) && edit.isOpen()) {
            file.setOpen(false);
            file.setPressed(false);
        }

    }

    @Override
    public void onHover(int x, int y) {
        super.onHover(x, y);
        fileSelectionWindow.onHover(x, y);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        fileSelectionWindow.draw(g);
    }

    @Override
    public void drawToolTips(Graphics g) {
        super.drawToolTips(g);

        if (isOpen()) {
            for (int i = 0; i < 2; i++) {
                ToggleButton tb = getButtons().get(i);
                MenuButton tb1 = (MenuButton) tb;
                for (ToggleButton t : tb1.getMenuToggleButtons()) {
                    t.drawTooltip(g);
                }
            }
        }

        if (fileSelectionWindow.isOpen()) {
            fileSelectionWindow.drawToolTips(g);
        }
    }

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

    private void saveFile() {
        try {
            File f = new File("src/saveFiles/" + getCurrentDateTime());

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(lt);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileSelectionWindow.updateFileList();
    }

    private String getCurrentDateTime() {
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy--hh-mm-ss");
        String date = dateFormat.format(currentDate);

        return date;
    }

    private void openFile() {
        fileSelectionWindow.setLt(lt);

        if (!fileSelectionWindow.isOpen()) fileSelectionWindow.open();
        else fileSelectionWindow.close();
    }

    private void newFile() {
        lt.reset();
        lt.setCounter(0);
        lt.addLayer();
        //Added on last day, note
        lt.setSelectedLayer((LayerButton) lt.getButtons().get(0));
        ((LayerButton) lt.getButtons().get(0)).setPressed(true);
    }

    public void setLt(LayerToolbar lt) {
        this.lt = lt;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        if (!shapes.equals(this.shapes)) {
            undoStack = new Stack<>();
        }
        this.shapes = shapes;
    }

    private void undo() {
        if (shapes != null) {
            if (undoStack == null) undoStack = new Stack<>();
            if (shapes.size() >= 1) undoStack.add(shapes.remove(shapes.size() - 1));
        }
    }

    private void redo() {
        if (undoStack != null) {
            if (undoStack.size() >= 1) {
                if (shapes != null) {
                    shapes.add(undoStack.pop());
                }
            }
        }
    }

    public boolean fileSelectionWindowOpen(){
        return fileSelectionWindow.isOpen();
    }
}
