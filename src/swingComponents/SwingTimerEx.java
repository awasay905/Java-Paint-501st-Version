package swingComponents;

import res.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

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
