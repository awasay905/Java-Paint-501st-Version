package uicomponents.windows;

import res.ResourceManager;
import uicomponents.MouseListener;
import uicomponents.buttons.LayerButton;
import uicomponents.buttons.ToggleButton;
import uicomponents.toolbars.LayerToolbar;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class FileSelectionWindow extends Window {
    private LayerToolbar lt;
    private ArrayList<ToggleButton> fileNames;

    public FileSelectionWindow(int x, int y, int width, int height, String name) {
        super(x, y, width, height, name);
        fileNames = new ArrayList<>();
        readFileNames();
    }

    public void setLt(LayerToolbar lt) {
        this.lt = lt;
    }

    public void updateFileList() {
        readFileNames();
    }

    @Override
    public void onHover(int x, int y) {
        super.onHover(x, y);

        for (ToggleButton b : fileNames) {
            b.onHover(x, y);
        }
    }

    @Override
    public void onClick(int x, int y) {
        if (isOpen()) {
            super.onClick(x, y);
            if (isClicked(x, y)) {
                resetClick();
                for (ToggleButton b : fileNames) {
                    b.onClick(x, y);
                }
            }
        }
    }

    private void resetClick() {
        for (ToggleButton b : fileNames) {
            b.setPressed(false);
        }
    }


    private void readFileNames() {
        ArrayList<String> as = new ArrayList<>();

        File f = new File("./src/saveFiles");

        //In case folder is deleted, new folder is made
        f.mkdir();

        File[] files = f.listFiles();

        for (File F : files) {
            if (F.isFile()) {
                as.add(F.getName().toUpperCase());
            }
        }

        int textboxHeight = 20;
        //if (as.size() > 0) textboxHeight = (getHeight() - getTitlebarHeight()) / (as.size());
        //if (textboxHeight < 10) textboxHeight = 10;
        //if (textboxHeight > 25) textboxHeight = 25;
        //TODO: WHEN THERE ARE LOTS OF FILE, THEM BUTTONS BE GETTING SMALL,
        //SO MAKE THEN INTO ROWS MAYBE?
        //after every 20, make them into new rows
        //how??
        fileNames = new ArrayList<>();
        int width = getWidth() / 90 * 100;
        int noOfRows = 1;
        int noOfCol = as.size();
        if (noOfCol > 20) noOfRows += noOfCol/20;
        int i = 0;
        int j = 0;
        for (String s : as) {
            if (i == 20) {
                i = 0; j++;
            }
            fileNames.add(new ToggleButton(getX() + j*(width/noOfRows), getY() + getTitlebarHeight() + textboxHeight * i,width/noOfRows , textboxHeight, ResourceManager.fileTextBoxPressed, ResourceManager.fileTextBoxDepressed, s, -1, new MouseListener() {
                @Override
                public void onClick() {
                    openFile(s);
                }
            }));
            i++;

        }

        setHeight(textboxHeight *20 + getTitlebarHeight());
        //if (getHeight() < 100) setHeight(100);
    }

    @Override
    public void draw(Graphics g) {
        if (isOpen()) {
            super.draw(g);
            for (ToggleButton b : fileNames) {
                b.draw(g);
            }
        }

    }

    public void openFile(String fileName) {
        if (isOpen()) {
            try {
                File f = new File("src/saveFiles/" + fileName);

                ObjectInputStream oos = new ObjectInputStream(new FileInputStream(f));
                LayerToolbar tb = (LayerToolbar) oos.readObject();
                lt.open(tb);
                oos.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            for (ToggleButton tb : fileNames) {
                tb.setPressed(false);
            }
            close();
        }

    }

}
