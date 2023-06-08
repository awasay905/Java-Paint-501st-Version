package swingComponents;

import javax.swing.*;
import java.awt.*;

public class SwingTimerEx extends JFrame {

    public SwingTimerEx() {
        initUI();
    }

    public static void main(String[] args) {
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
