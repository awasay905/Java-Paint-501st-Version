package swingComponents;

import res.ResourceManager;
import uicomponents.ColorGradient;
import uicomponents.windows.ColorSelectionWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

public class SwingTimerEx extends JFrame {

    public SwingTimerEx() {
        initUI();
    }

    public static void main(String[] args) {
        ResourceManager.init();
        EventQueue.invokeLater(() -> {
            SwingTimerEx ex = new SwingTimerEx();
            ex.setVisible(true);
        });
        ColorSelectionWindow.getInstance(390, 160, 326, 400, "COLOR SELECTION");
    }

    private void initUI() {
        add(new Board());
        setResizable(false);
        pack();

        setTitle("Java Paint 501st Version");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
