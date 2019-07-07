package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainWindow extends JFrame{

    private Command commands = new Command();
    private Map map = new Map(650, 650);
    MapMouseAdapter adapter = new MapMouseAdapter();

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
        Color PointColor;
        private int mouseX;
        private int mouseY;

        /**
         * ОРГАНИЗОВАТЬ ВЫВОД ПОЛУЧЕННОГО ПОЛОЖЕНИЯ КУРСОРА!!! - сделано
         * ДОБАВИТЬ КЛАСС ДЛЯ ТЕКУЩЕГО ПОЛОЖЕНИЯ!!! - координаты при движении обновляются
         * ОТРИСОВАТЬ ТЕКУЩЕЕ ПОЛОЖЕНИЕ!!! - добавлена передача точки в Map и её отображение
         */
        public MapMouseAdapter(){
            PointColor = new Color(0, 13, 255);
            addMouseWheelListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent event) {
            mouseX = event.getX();
            mouseY = event.getY();
            System.out.println("X = " + mouseX + "; Y = " + mouseY);
            map.setStartPoint(new Point(mouseX,mouseY));
            commands.unlockBegin();
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            mouseX = me.getX();
            mouseY = me.getY();
        }

        /**
         * Событие на вращение колеса мышки
         * @param e
         * Имеет отдельного слушателя т.е. не зависит от включенного режима выбора
         */
        @Override
        public  void mouseWheelMoved(MouseWheelEvent e){
            double step = 0.05;
            if(e.getWheelRotation()> 0) {
                step *= -1;
            }
            map.MapScale(step,new Point(e.getX(),e.getY()));
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
         * СДЕЛАТЬ КНОПКУ НЕДОСТУПНОЙ, ПОКА НЕ БУДЕТ УСТАНОВЛЕНО СТАРТОВОЕ ПОЛОЖЕНИЕ!!! - сделано
         * АНАЛОГИЧНО ИЗВЛЕЧЬ ИНФОРМАЦИЮ ИЗ ПОЛЯ "МЕСЯЦ", "ЧИСЛО" и "ИНТЕРВАЛ ВРЕМЕНИ"!!!
         * ОПЦИОНАЛЬНО: СДЛЕАТЬ ДЛЯ МАЯ ДОСТУПНЫМИ НЕ ВСЕ ДНИ, ДЛЯ ИЮНЯ ИСКЛЮЧИТЬ 31, ДЛЯ ИЛЯ - НЕНАСТУПИВШИЕ ДНИ!!!
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            System.out.println("START!");
            int index = commands.mode.getSelectedIndex();
            String value = new String((String)commands.mode.getItemAt(index));
            //System.out.println(commands.mode.getItemAt(index));
            System.out.println(value);


           //  map.setAlgorithmData();
             map.repaint();
        }
    }

    class JComboBoxActionListener implements ActionListener {

        /**
         * ДОБАВИТЬ НАЗВАНИЯ ОСТАЛЬНЫХ РЕГИОНОВ!!!
         * НЕ ЗАБЫТЬ, ЧТО ИЗНАЧАЛЬНО ОТКРЫВАЕТСЯ ФАЙЛ AUSTRALIA.JPG!!!
         * ИСПРАВИТЬ ВЫПАДЕНИЯ ПРИ ВЫБОРЕ НЕЗАГРУЖЕННОГО РЕГИОНА!!!
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            //System.out.println("REGION!!!");
            String value = "";
            int regionIndex = commands.region.getSelectedIndex();
            switch(regionIndex) {
                case 0:
                    value = "australia.jpg";
                    break;
                case 1:
                    value = "spain.jpg";
                    break;
                case 2:
                    value = "";
                    break;
            }
            map.setRegion(value);
            map.repaint();
        }
    }

    class Command extends JPanel{

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
        private JLabel labelTime = new JLabel("Выберите временной интервал:");
        private JComboBox time = new JComboBox(new String[] {"1 час", "6 часов", "12 часов", "Сутки", "Неделя"});

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

        public void unlockBegin(){
            begin.setEnabled(true);
        }
    }
}
