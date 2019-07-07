package gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import dateStruct.doublePoint;
import org.jetbrains.annotations.NotNull;

public class Map extends JPanel {
    /**
     * width - ширина окна
     * height - высота окна
     * grid - флаг сетки
     * grid Color - цвет сетки
     * MapImage - фоновое изображение
     * region - имя фонового изображения
     * StartPoint -стартовая точка алгоритма
     * PointColor - цвет точки
     * ScaleCoof - коэфицент масштаба
     * ScalePoint - точка-центр масштабирования
     * ImageCoof - коэфицент сжатия большого изображения
     * offsetX,offsetY - смещение карты за левый верхний угол
     * bottomLefht - реальные координаты левого нижнего угла карты
     * upperRight - реальные координаты правого верхнего угла карты
     */
    private int width;
    private int height;
    private boolean grid = false;
    private Color gridColor = new Color(0,0,0, 87);
    private Image MapImage;
    private String region = "australia.jpg";
    private doublePoint StartPoint;
    private Color PointColor;
    private double ScaleCoof;
    private doublePoint ScalePoint;
    private double ImageCoof;
    int offsetX,offsetY;                //МОЖНО ЛИ ЗАНЕСТИ В PRIVATE???
    private doublePoint bottomLefht;
    private doublePoint upperRight;

    /**
     * Конструктор класса
     * @param width - ширина окна карты
     * @param height - высота окна карты
     * загрузка стартового региона через setRegion
     * Стартовый масштаб 1
     */
    public Map(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        setRegion(region, bottomLefht, upperRight);
        ScaleCoof = 1;
        offsetX = offsetY =0;
    }

    /**
     * изменение флага сетки
     * @param value - true, если нужна сетка
     */
    public void setGrid(boolean value) {
        grid = value;
    }

    /**
     * установка стартовой точки на карте
     * @param p - стартовая точка
     * обязательная перерисовка области
     */
    public void setStartPoint(doublePoint p){
        StartPoint = new doublePoint((int)Math.abs((( p.getX()+ offsetX) /ScaleCoof )),
                (int) Math.abs(((p.getY()+ offsetY)/ScaleCoof)));
        PointColor = new Color(0, 13, 255);
        repaint();
    }

    /**
     * ДЛЯ ЧЕГО ЭТА ШТУКА???
     * ПОЧЕМУ ВОЗВРАЩАЕТ INT???
     * @return
     */
    public doublePoint getStartPoint(){
        int x = (int) Math.abs( StartPoint.getX()  / ImageCoof );
        int y = (int) Math.abs( StartPoint.getY() / ImageCoof );
        return new doublePoint(x,y);
    }

    /**
     * метод для изменения масштаба
     * @param coof - прибавка к масштабу
     * @param coord - точка вызова ищменеия масштаба
     */
    public void MapScale(double coof, doublePoint coord){
        if( isVisible(coord) && (ScaleCoof + coof) >= 1.0 && (ScaleCoof + coof ) < 3.5){
            ScaleCoof += coof;
            ScalePoint = new doublePoint(coord);

            if(ScalePoint != null) {
                double MoveMapCoofX = (ScalePoint.getX() / width); // тут изменить коэф смещения для большей плавности
                double MoveMapCoofY = (ScalePoint.getY() / height);

                if (MoveMapCoofX <= 0.6 && MoveMapCoofX >= 0.4) {
                    MoveMapCoofX = 0.5;
                    offsetX = (int) Math.round(width * MoveMapCoofX * (ScaleCoof - 1));
                } else {
                    offsetX = (int) Math.round(ScalePoint.getX() * MoveMapCoofX * ScaleCoof);

                    if ((MapImage.getWidth(null) * ImageCoof * ScaleCoof - offsetX) < width) {
                        offsetX = (int) Math.round((MapImage.getWidth(null) * ScaleCoof * ImageCoof - width));
                    }
                }

                if (MoveMapCoofY <= 0.6 && MoveMapCoofY >= 0.4) {
                    MoveMapCoofY = 0.5;
                    offsetY = (int) Math.round(width * MoveMapCoofY * (ScaleCoof - 1));
                } else {
                    offsetY = (int) Math.round(ScalePoint.getY() * MoveMapCoofY * ScaleCoof);

                    if ((MapImage.getHeight(null) * ScaleCoof * ImageCoof - offsetY) < height) {
                        offsetY = (int) Math.round((MapImage.getHeight(null) * ScaleCoof * ImageCoof - height));
                    }
                }
            }
            repaint();
        }
    }

    /**
     * проверка вхождения точки в область видимой карты
     * @param coord - точка проверки
     * @return true - если входит в область видимой карты
     */
    private boolean isVisible(@NotNull doublePoint coord) {
        int borderX = 10;
        int borderY = 50;
        if( (coord.getX()<= borderX || coord.getX() >= width + borderX) ||
                (coord.getY()<= borderY || coord.getY() >= height + borderY))
            return false;
        return true;
    }

    /**
     * ЕЩЁ РАЗ ПРОВЕРИТЬ КОРРЕКТНОСТЬ ПОДСЧЁТА!!!
     * ДОБАВИТЬ УЧЁТ МАСШТАБА(СЕЙЧАС РАБОТАЕТ ПРАВИЛЬНО КОГДА НЕТ ПРИБЛИЖЕНИЯ)!!!
     * вычисление реальных координат в дробных градусах для стартовой точки
     * @return - реальные координаты стартовой точки
     */
    public doublePoint getRealCoordiante(){
        //коэффициенты показывают сколько приходится градусов на один пиксель
        double coeffX = (bottomLefht.getX() - upperRight.getX()) / height;
        double coeffY = (upperRight.getY() - bottomLefht.getY()) / width;
        double x = upperRight.getX() + coeffX*StartPoint.getX();    //широта
        double y = bottomLefht.getY() + coeffY*StartPoint.getY();   //долгота
        return new doublePoint(x, y);
    }

    /**
     * РЕАЛИЗОВАТЬ!!!
     */
    public  void setAlgorithmDate(){
        grid = false;
        // загрузка данных алгоритма
        repaint();
    }

    /**
     * Загружает регион и высчитывает некоторые параметры
     * @param value - имя региона
     * @param bottomLefht - реальные координаты левого нижнего угла в дробных градусах
     * @param upperRight - реальные координаты правого верхнего угла в дробных градусах
     * безопасная загрузка
     */
    public void setRegion(String value, doublePoint bottomLefht, doublePoint upperRight) {
        region = value;
        this.bottomLefht = bottomLefht;
        this.upperRight = upperRight;
        MapImage = null;
        try {
            String path = new String("");
            path = "src" + File.separator + "gui" + File.separator + "maps" + File.separator + region;
            MapImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            String message = "File not open!";
            JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.PLAIN_MESSAGE);
            System.exit(-1);
        }

        ImageCoof = width / (double )MapImage.getWidth(null);
        ScalePoint = null;
        StartPoint = null;
    }

    /**
     * Метод для отрисовки
     * @param g - графический контекст
     * для получения реальных координат на отрисованной карте умножайте на * ScaleCoof * ImageCoof
     * и после вычитайте смещение угла карты
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(MapImage,
                -offsetX,(int) -offsetY,
                (int)(MapImage.getWidth(null) * ScaleCoof *ImageCoof),
                (int)(MapImage.getHeight(null) * ScaleCoof *ImageCoof),null);

        if(grid) {
            g.setColor(gridColor);
            for(int i = -offsetX; i <= width; i += (int)(15 * ScaleCoof)) {
                g.drawLine(i, 0, i, height);
            }
            for(int i = -offsetY; i <= height; i += (int)(15 * ScaleCoof)) {
                g.drawLine(0, i, width, i);
            }
        } else {
            g.drawLine(0, 0, 0, height);
            g.drawLine(0, 0, width, 0);
            g.drawLine(width, 0, width, height);
            g.drawLine(0, height, width, height);
        }


        if(StartPoint != null){
            g.setColor(PointColor);
            int radius = 3;
            int X = (int) (StartPoint.getX() * ScaleCoof - offsetX - radius);
            int Y = (int) (StartPoint.getY() * ScaleCoof - offsetY - radius);

            //МОЖНО ЛИ УДАЛИТЬ ЗАКОММЕНЧЕННОЕ УСЛОВИЕ???
            //if (isVisible(new doublePoint(X,Y))){

            g.drawOval( X , Y ,2*radius,2*radius);
            g.fillOval( X, Y,2*radius,2*radius);
            // }
        }

        /*
        Алгоритм отрисовки пути
         */
    }

    /**
     * ДЛЯ ЧЕГО???
     * РЕАЛИЗОВАТЬ!!!
     * @param p
     */
    private void drawPoint(doublePoint p){

    }
}