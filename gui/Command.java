package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Command extends JPanel{

    private JLabel labelRegion = new JLabel("Выберите регион:");
    private JComboBox region = new JComboBox(new String[] {"Австралия", "Регион2", "Регион3"});
    private JLabel labelMode = new JLabel("Выберите режим:");
    private JComboBox mode = new JComboBox(new String[] {"По времени", "По точке"});
    private JToggleButton start = new JToggleButton("Выбрать стартовую точку");
    private JLabel date = new JLabel("Выберите дату отправления:");
    private JLabel labelMonth = new JLabel("Месяц:");
    private JComboBox month = new JComboBox(new String[] {"Май", "Июнь", "Июль"});
    private String[] days = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private JLabel labelDays = new JLabel("Число:");
    private JComboBox day = new JComboBox(days);
    private JButton begin = new JButton("Отправиться");

    public Command() {
        super();
        this.setLayout(null);
        labelRegion.setSize(110, 40);
        labelRegion.setLocation(15, 0);
        this.add(labelRegion);
        region.setSize(110, 20);
        region.setLocation(125, 10);
        this.add(region);
        labelMode.setSize(110, 40);
        labelMode.setLocation(15, 40);
        this.add(labelMode);
        mode.setSize(110, 20);
        mode.setLocation(125, 50);
        this.add(mode);
        start.setSize(185, 20);
        start.setLocation(33, 90);
        this.add(start);
        date.setSize(200, 20);
        date.setLocation(37, 130);
        this.add(date);
        labelMonth.setSize(75, 20);
        labelMonth.setLocation(27, 170);
        this.add(labelMonth);
        month.setSize(60, 20);
        month.setLocation(77, 170);
        this.add(month);
        labelDays.setSize(75,20);
        labelDays.setLocation(142, 170);
        this.add(labelDays);
        day.setSize(40, 20);
        day.setLocation(187, 170);
        this.add(day);
        begin.setSize(140, 20);
        begin.setLocation(57, 210);
        this.add(begin);
    }
}
