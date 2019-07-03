package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainWindow extends JFrame{

    private Map map = new Map(500, 500);
    private Command commands = new Command();

    public MainWindow() {
        super("Balloon journey!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setResizable(false);
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        this.add(map);
        this.add(commands);
    }
}
