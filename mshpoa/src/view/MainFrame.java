package view;

import controller.MainFrameController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel mainPanel;

    public JButton getFile() {
        return file;
    }

    public void setFile(JButton file) {
        this.file = file;
    }

    public JTextPane getDisplay() {
        return display;
    }

    public void setDisplay(JTextPane display) {
        this.display = display;
    }

    private JButton file;
    private JTextPane display;
    public static final int WIDHT = 450;
    public static final int HEIGHT = 450;



    public MainFrame() {

        setSize(WIDHT, HEIGHT);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
    }



}
