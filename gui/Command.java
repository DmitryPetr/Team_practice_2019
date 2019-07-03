package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Command extends JPanel{

    private JButton button1 = new JButton("Button1");
    private JButton button2 = new JButton("Button2");
    private JButton button3 = new JButton("Button3");
    private JLabel label1 = new JLabel("Current date:");
    private JLabel label2 = new JLabel("26.06.2019");

    public Command() {
        super();
        this.setLayout(new BoxLayout( this, BoxLayout.Y_AXIS));
        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(label1);
        this.add(label2);
    }
}
