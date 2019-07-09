package gui;

import algo.MoveBalloonAlgorithm;
import dateStruct.Time;
import dateStruct.Vertex;
import dateStruct.doublePoint;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedList;

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

        @Override
        public void actionPerformed(ActionEvent event) {
            boolean mode = false;
            int index = commands.mode.getSelectedIndex();

            if(index == 0){
                mode = true;
            }
            index = commands.month.getSelectedIndex();
            String date = new String("2019-");
            switch(index){
                case 0:
                    date += "05-";
                    break;
                case 1:
                    date += "06-";
                    break;
                case 2:
                    date += "07-";
                    break;
            }
            index = commands.day.getSelectedIndex();
            index++;
            date += index;
            index = commands.time.getSelectedIndex();
            int hour = 0;
            switch(index){
                case 0:
                    hour = 1;
                    break;
                case 1:
                    hour = 6;
                    break;
                case 2:
                    hour = 12;
                    break;
                case 3:
                    hour = 24;
                    break;
            }

            LinkedList<Vertex> temp = null;
            //для определения полушарий
            boolean nordSphere = true;
            boolean estSphere = true;
            double sizeLatitude = map.getUpperRight().getX() - map.getBottomLefht().getX();
            if(sizeLatitude < 0){
                sizeLatitude *= -1;
                nordSphere = false;
            }
            double sizeLongitude = map.getUpperRight().getY() - map.getBottomLefht().getY();
            if(sizeLongitude < 0){
                sizeLongitude *= -1;
                estSphere = false;
            }

            MoveBalloonAlgorithm algo;
            try {
                //500 - км в градусе - const
                algo = new MoveBalloonAlgorithm(map.getBottomLefht(), sizeLongitude, sizeLatitude, nordSphere, estSphere, 500);
                if(mode){
                    //ЗДЕСЬ ЗАДАЁТСЯ ПРОДОЛЖИТЕЛЬНОСТЬ ПОЛЁТА КАК НЕДЕЛЯ!!! МОЖНО МЕНЯТЬ!!! А ЛУЧШЕ СДЕЛАТЬ КНОПКУ ДЛЯ ВЫБОРА ПОЛЬЗОВАТЕЛЕМ!!!
                    temp = algo.AlgorithmTime(map.getStartRealCoordinate(), date, 0, new Time(0, 7, 0), hour);
                }else{
                    //РЕАЛИЗОВАТЬ ЗАПУСК АЛГОРИТМА ПО ТОЧКЕ!!!
                }
            } catch (IOException e) {
                // ДОБАВИТЬ ОБРАБОТКУ ИСКЛЮЧЕНИЯ!!! (ЛОГГЕР???)
                // e.printStackTrace()
            }
            //System.out.println("SIZE LIST = " + temp.size());   //ДЛЯ ПРОВЕРКИ(УДАЛИТЬ В ФИНАЛЬНОЙ ВЕРСИИ!!!


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
            map.setNullWay();

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
        private final JComboBox time = new JComboBox(new String[] {"1 час", "6 часов", "12 часов", "24 часа"});


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
