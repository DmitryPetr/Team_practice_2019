package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 *
 */
public class MainWindow extends JFrame{

    private Map map = new Map(650, 650);
    private Command commands = new Command();

    public MainWindow() {
        super("Balloon journey!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(930, 710);    //40 пикселей - полоска наверху программы
        this.setResizable(false);
        this.setLayout(null);
        map.setLocation(10, 10);
        map.setSize(651, 651);
        this.add(map);
        commands.setLocation(661, 10);
        commands.setSize(350,651);
        this.add(commands);
    }
}
