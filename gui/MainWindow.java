package gui;

import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

import algo.MoveBalloonAlgorithm;
import dateStruct.Vertex;
import dateStruct.doublePoint;

public class MainWindow extends JFrame{

    private final Command commands = new Command();
    private final Map map = new Map(650, 650);
    final MapMouseAdapter adapter = new MapMouseAdapter();

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

        commands.start.addActionListener(new JToggleButtonActionListener());
        commands.begin.addActionListener(new JButtonActionListener());
        commands.region.addActionListener(new JComboBoxActionListener());
    }

    class MapMouseAdapter extends MouseAdapter implements  MouseWheelListener{
        private int mouseX;
        private int mouseY;

        public MapMouseAdapter(){
            addMouseWheelListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent event) {
            mouseX = event.getX();
            mouseY = event.getY();
            map.setStartPoint(new doublePoint(mouseX,mouseY));
            commands.begin.setEnabled(true);
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            mouseX = me.getX();
            mouseY = me.getY();
        }

        /**
         * Событие на вращение колеса мышки
         * @param e - событие
         * Имеет отдельного слушателя т.е. не зависит от включенного режима выбора
         */
        @Override
        public  void mouseWheelMoved(MouseWheelEvent e){
            double step = 0.05;
            if(e.getWheelRotation()> 0) {
                step *= -1;
            }
            map.MapScale(step,new doublePoint(e.getX(),e.getY()));
        }
    }

    class JToggleButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if(commands.start.isSelected()) {
                map.setGrid(true);
                map.addMouseListener(adapter);
            } else {
                map.setGrid(false);
                map.removeMouseListener(adapter);
            }
            map.repaint();
        }
    }

    class JButtonActionListener implements ActionListener {

        /**
         * ОРГАНИЗОВАТЬ ЗАПУСК АЛГОРИТМА!!!
         * АНАЛОГИЧНО ИЗВЛЕЧЬ ИНФОРМАЦИЮ ИЗ ПОЛЯ "МЕСЯЦ", "ЧИСЛО" и "ИНТЕРВАЛ ВРЕМЕНИ"!!!
         * ОПЦИОНАЛЬНО: СДЛЕАТЬ ДЛЯ МАЯ ДОСТУПНЫМИ НЕ ВСЕ ДНИ, ДЛЯ ИЮНЯ ИСКЛЮЧИТЬ 31, ДЛЯ ИЛЯ - НЕНАСТУПИВШИЕ ДНИ!!!
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            System.out.println("START!");
            int index = commands.mode.getSelectedIndex();
            String value = (String) commands.mode.getItemAt(index);
            //System.out.println(commands.mode.getItemAt(index));
            System.out.println(value);




            /*
            тут нужно вызвать алгоритм и присвоить его temp
             map.getStartRealCoordinate(); - возвращает координаты стартовой точки

             остальные парметры за тобой


            отрисовка работает
            нужны методы

             */

            LinkedList<Vertex> temp = new LinkedList<>();
            Vertex temp1 = new Vertex(new doublePoint(31.5, 135.87));
            Vertex temp2 = new Vertex(new doublePoint(30.98, 137.97));
            Vertex temp3 = new Vertex(new doublePoint(28.46, 137.43));

            temp.addLast(temp1);
            temp.addLast(temp2);
            temp.addLast(temp3);


            if(temp != null){
                map.setAlgorithmDate(temp);
            }else{
                System.out.println("null LinkedList");
            }
            map.repaint();
        }
    }

    class JComboBoxActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            String value = "";
            doublePoint bottomLeft = null;
            doublePoint upperRight = null;
            int regionIndex = commands.region.getSelectedIndex();
            switch(regionIndex) {
                case 0:
                    value = "australia.jpg";
                    bottomLeft = new doublePoint(43.73333, 112.93333);
                    upperRight = new doublePoint(7.36667, 154.31667);
                    break;
                case 1:
                    value = "balkans.jpg";
                    bottomLeft = new doublePoint(36.06667, 13.43333);
                    upperRight = new doublePoint(48.16667, 29.88333);
                    break;
                case 2:
                    value = "china.jpg";
                    bottomLeft = new doublePoint(17.7, 100.1);
                    upperRight = new doublePoint(44.1, 131.35);
                    break;
                case 3:
                    value = "india.jpg";
                    bottomLeft = new doublePoint(5.36667, 65.4);
                    upperRight = new doublePoint(31.08333, 92.58333);
                    break;
                case 4:
                    value = "russia.jpg";
                    bottomLeft = new doublePoint(52.86667, 28.83333);
                    upperRight = new doublePoint(60.63333, 43.1);
                    break;
                case 5:
                    value = "scandinavia.jpg";
                    bottomLeft = new doublePoint(54.88333, 4.18333);
                    upperRight = new doublePoint(71.31667, 41.3);
                    break;
                case 6:
                    value = "usa.jpg";
                    bottomLeft = new doublePoint(4.86667, 124.1);
                    upperRight = new doublePoint(49.33333, 72.05);
                    break;
            }
            map.setRegion(value, bottomLeft, upperRight); //X - широта, Y - долгота
            map.repaint();
            commands.begin.setEnabled(false);
        }
    }

    class Command extends JPanel{

        private final JLabel labelRegion = new JLabel("Выберите регион:");
        private final JComboBox region = new JComboBox(new String[] {"Австралия", "Балканы", "Китай", "Индия", "Россия",
                "Скандинавия", "США"});
        private JLabel labelMode = new JLabel("Выберите режим:");
        private final JComboBox mode = new JComboBox(new String[] {"По времени", "По точке"});
        private final JToggleButton start = new JToggleButton("Выбрать стартовую точку");
        private final JLabel date = new JLabel("Выберите дату отправления:");
        private final JLabel labelMonth = new JLabel("Месяц:");
        private final JComboBox month = new JComboBox(new String[] {"Май", "Июнь", "Июль"});
        private final String[] days = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        private final JLabel labelDays = new JLabel("Число:");
        private final JComboBox day = new JComboBox(days);
        private final JButton begin = new JButton("Отправиться");
        private final JLabel labelTime = new JLabel("Выберите временной интервал:");
        private final JComboBox time = new JComboBox(new String[] {"1 час", "6 часов", "12 часов", "Сутки", "Неделя"});

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
            labelTime.setSize(200, 20);
            labelTime.setLocation(30, 210);
            this.add(labelTime);
            time.setSize(80, 20);
            time.setLocation( 88,250);
            this.add(time);
            begin.setSize(140, 20);
            begin.setLocation(57, 290);
            this.add(begin);

            begin.setEnabled(false);
        }
    }
}