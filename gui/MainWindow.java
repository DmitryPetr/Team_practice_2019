package gui;

import algo.MoveBalloonAlgorithm;
import dateStruct.Time;
import dateStruct.Vertex;
import dateStruct.doublePoint;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
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
        commands.example.addActionListener(new JToggleButtonExampleActionListener());
        commands.mode.addActionListener(new JComboBoxModeActionListener());
        commands.clear.addActionListener(new JButtonClearActionListener());
        commands.end.addActionListener(new JToggleButtonActionListener());
        commands.day.addActionListener(new JComboBoxDaysActionListener());
        commands.month.addActionListener(new JComboBoxDaysActionListener());
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
            map.setNullWay();
            if(commands.start.isSelected()) {
                map.setStartPoint(new doublePoint(mouseX, mouseY));
            }
            if(commands.end.isSelected()) {
                map.setEndPoint(new doublePoint(mouseX, mouseY));
            }
            commands.begin.setEnabled(true);
            if(commands.mode.getSelectedIndex() == 1  && !map.isPointsInit()){
                commands.begin.setEnabled(false);
            }
            commands.example.setSelected(false);
            commands.example.setEnabled(false);
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
            if(commands.start.isSelected() || commands.end.isSelected()) {
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
            index = commands.air.getSelectedIndex();
            int countDay = ++index;
            countDay *= 7;

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
                    temp = algo.AlgorithmTime(map.getRealCoordinate(true), date, 0, new Time(0, countDay, 0), hour);
                }else{
                    double epsilon = 0.5; //область вокруг конечной точки в дробных градусах
                    temp = algo.AlgorithmEndPoint(map.getRealCoordinate(true), map.getRealCoordinate(false), date, 0, hour, epsilon);
                }
            }catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Ошибка записи в лог-файл!", "Error!", JOptionPane.PLAIN_MESSAGE);
                System.exit(-1);
            }catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Обращение к null! Выполните сброс и выберите другие данные!", "Error!", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            map.setGrid(false);
            commands.start.setSelected(false);
            commands.end.setSelected(false);

            if(temp != null){
                map.setAlgorithmDate(temp);
            }else{
                if(mode){
                    JOptionPane.showMessageDialog(null, "По таким данным не удалось построить путь!", "Fail!", JOptionPane.PLAIN_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "Пути в выбранную точку не нашлось!", "Fail!", JOptionPane.PLAIN_MESSAGE);
                }
            }
            map.repaint();
            commands.begin.setEnabled(false);
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
                case 7:
                    value = "antarctica.jpg";
                    bottomLeft = new doublePoint(83.1, 96.63333);
                    upperRight = new doublePoint(62.46667, 16.65);
                    break;
            }
            map.setRegion(value, bottomLeft, upperRight); //X - широта, Y - долгота
            map.setGrid(false);
            commands.start.setSelected(false);
            commands.end.setSelected(false);
            map.setNullWay();
            map.setNullStartPoint();
            map.setNullEndPoint();

            map.repaint();
            commands.begin.setEnabled(false);
            commands.example.setSelected(false);
            commands.example.setEnabled(true);
        }
    }

    //ОБРАБОТЧИК НАЖАТИЯ НА КНОПКУ "Показать пример"
    class JToggleButtonExampleActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            LinkedList<Vertex> temp = new LinkedList<>();
            if(commands.example.isSelected()) {
                doublePoint garbage = new doublePoint(0, 0);
                for (int i = 0; i < 10; i++) {
                    temp.add(new Vertex(garbage));
                }
                temp.get(0).setMapCoordinate(new doublePoint(100, 100));
                temp.get(1).setMapCoordinate(new doublePoint(269, 200));
                temp.get(2).setMapCoordinate(new doublePoint(158, 358));
                temp.get(3).setMapCoordinate(new doublePoint(173, 489));
                temp.get(4).setMapCoordinate(new doublePoint(343, 457));
                temp.get(5).setMapCoordinate(new doublePoint(429, 387));
                temp.get(6).setMapCoordinate(new doublePoint(401, 361));
                temp.get(7).setMapCoordinate(new doublePoint(389, 379));
                temp.get(8).setMapCoordinate(new doublePoint(406, 410));
                temp.get(9).setMapCoordinate(new doublePoint(485, 523));
            }
            map.setAlgorithmDate(temp);
        }
    }

    class JButtonClearActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            map.setNullWay();
            map.setNullStartPoint();
            map.setNullEndPoint();
            map.setGrid(false);
            map.setNullScale();
            map.repaint();
            commands.example.setSelected(false);
            commands.end.setSelected(false);
            commands.start.setSelected(false);
            commands.example.setEnabled(true);
            commands.begin.setEnabled(false);
        }
    }

    class JComboBoxModeActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if(commands.mode.getSelectedIndex() == 1){
                commands.end.setEnabled(true);
                commands.air.setEnabled(false);
            }else{
                commands.end.setEnabled(false);
                commands.air.setEnabled(true);
                commands.end.setSelected(false);
                if(!commands.start.isSelected()) {
                    map.setGrid(false);
                    map.setNullEndPoint();
                    map.repaint();
                }
            }
        }
    }

    class JComboBoxDaysActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            int month = commands.month.getSelectedIndex();
            int day = commands.day.getSelectedIndex();
            if(day == 30 && month == 1) {
                JOptionPane.showMessageDialog(null, "Несуществующая дата!", "Warning!", JOptionPane.PLAIN_MESSAGE);
                commands.day.setSelectedIndex(29);
            }
            int lastDayJuly = 8;    //последний наступивший день июля(индексация с 0), не забывать менять
            if(day > lastDayJuly && month == 2) {
                JOptionPane.showMessageDialog(null, "Ненаступившая дата!", "Warning!", JOptionPane.PLAIN_MESSAGE);
                commands.day.setSelectedIndex(lastDayJuly);
            }
        }
    }

    class Command extends JPanel{

        private final JLabel labelRegion = new JLabel("Выберите регион:");
        private final JComboBox region = new JComboBox(new String[] {"Австралия", "Балканы", "Китай", "Индия", "Россия",
                "Скандинавия", "США", "Антарктида"});
        private JLabel labelMode = new JLabel("Выберите режим:");
        private final JComboBox mode = new JComboBox(new String[] {"По времени", "К точке"});
        private final JToggleButton start = new JToggleButton("Выбрать стартовую точку");
        private final JLabel date = new JLabel("Выберите дату отправления:");
        private final JLabel labelMonth = new JLabel("Месяц:");
        private final JComboBox month = new JComboBox(new String[] {"Май", "Июнь", "Июль"});
        private final String[] days = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        private final JLabel labelDays = new JLabel("Число:");
        private final JComboBox day = new JComboBox(days);
        private final JButton begin = new JButton("Отправиться");
        private final JLabel labelTime = new JLabel("Выберите интервал запроса:");
        private final JComboBox time = new JComboBox(new String[] {"1 час", "6 часов", "12 часов", "24 часа"});
        private final JToggleButton end = new JToggleButton("Выбрать конечную точку");
        private final JLabel labelAir = new JLabel("Выберите время полёта:");
        private final JComboBox air = new JComboBox(new String[] {"1 неделя", "2 недели", "3 недели", "4 недели",
                "5 недель", "6 недель", "7 недель", "8 недель"});
        private final JToggleButton example = new JToggleButton("Показать пример");
        private final JButton clear = new JButton("Сброс");
        private Image image;

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
            end.setSize(185, 20);
            end.setLocation(33, 130);
            this.add(end);
            date.setSize(200, 20);
            date.setLocation(37, 170);
            this.add(date);
            labelMonth.setSize(75, 20);
            labelMonth.setLocation(27, 210);
            this.add(labelMonth);
            month.setSize(60, 20);
            month.setLocation(77, 210);
            this.add(month);
            labelDays.setSize(75,20);
            labelDays.setLocation(142, 210);
            this.add(labelDays);
            day.setSize(40, 20);
            day.setLocation(187, 210);
            this.add(day);
            labelAir.setSize(200, 20);
            labelAir.setLocation(50, 250);
            this.add(labelAir);
            air.setSize(80, 20);
            air.setLocation( 88,290);
            this.add(air);
            labelTime.setSize(200, 20);
            labelTime.setLocation(40, 330);
            this.add(labelTime);
            time.setSize(80, 20);
            time.setLocation( 88,370);
            this.add(time);
            begin.setSize(140, 20);
            begin.setLocation(57, 600);
            this.add(begin);
            example.setSize(140, 20);
            example.setLocation(15, 630);
            this.add(example);
            clear.setSize(85, 20);
            clear.setLocation(160, 630);
            this.add(clear);

            begin.setEnabled(false);
            end.setEnabled(false);

            try {
                String path = "";
                path = "src" + File.separator + "gui" + File.separator + "balloon" + File.separator + "balloon.jpg";
                image = ImageIO.read(new File(path));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Balloon image not open!", "Error!", JOptionPane.PLAIN_MESSAGE);
                System.exit(-1);
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(image, 65, 400, this);
            g.drawLine(65, 400, 65, 590);
            g.drawLine(65, 400, 189, 400);
            g.drawLine(65, 590, 189, 590);
            g.drawLine(189, 400, 189, 590);
        }
    }
}
