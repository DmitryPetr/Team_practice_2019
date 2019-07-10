package gui;

import dateStruct.Vertex;
import dateStruct.doublePoint;
//import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Map extends JPanel {
    /**
     * @value width - ширина окна
     * @value height - высота окна
     * @value grid - флаг сетки
     * @value gridColor - цвет сетки
     * @value MapImage - фоновое изображение
     * @value region - имя фонового изображения
     * @value StartPoint -стартовая точка алгоритма
     * @value EndPoint - конечная точка алгоритма
     * @value PointColor - цвет точки
     * @value ScaleFactor - коэфицент масштаба
     * @value ScalePoint - точка-центр масштабирования
     * @value ImageFactor - коэфицент сжатия большого изображения
     * @value offsetX,offsetY - смещение карты за левый верхний угол
     * @value bottomLeft - реальные координаты левого нижнего угла карты
     * @value upperRight - реальные координаты правого верхнего угла карты
     */
    private final int width;
    private final int height;
    private boolean grid = false;
    private final Color gridColor = new Color(0,0,0, 87);
    private Image MapImage;
    private String region = "australia.jpg";
    private doublePoint StartPoint;
    private doublePoint EndPoint;
    private final Color PointColor = new Color(0, 13, 255);
    private double ScaleFactor;
    private doublePoint ScalePoint;
    private double ImageFactor;
    private int offsetX,offsetY;
    private doublePoint bottomLeft = new doublePoint(43.73333, 112.93333); //значения для Австралии
    private doublePoint upperRight = new doublePoint(7.36667, 154.31667);  //для загрузки по умолчанию
    private LinkedList<Vertex> BalloonWay;


    /**
     * Конструктор класса
     * @param width - ширина окна карты
     * @param height - высота окна карты
     * загрузка стартового региона через setRegion
     * Стартовый масштаб = 1
     * Смещение карты = 0
     * Путь шара отсутствует
     */
    public Map(int width, int height) {
        super();
        this.width = width;
        this.height = height;

        setRegion(region, bottomLeft, upperRight);

        ScaleFactor = 1;
        offsetX = offsetY =0;
        BalloonWay = null;
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
        StartPoint = new doublePoint((int)Math.abs((( p.getX()+ offsetX) / ScaleFactor)),
                (int) Math.abs(((p.getY()+ offsetY)/ ScaleFactor)));

        repaint();
    }

    public void setEndPoint(doublePoint p){
        EndPoint = new doublePoint((int)Math.abs((( p.getX()+ offsetX) / ScaleFactor)),
                (int) Math.abs(((p.getY()+ offsetY)/ ScaleFactor)));

        repaint();
    }

    /**
     * метод для изменения масштаба
     * @param step - прибавка к масштабу
     * @param coord - точка вызова изменения масштаба
     */
    public void MapScale(double step, doublePoint coord){
        if( isVisible(coord) && (ScaleFactor + step) >= 1.0 && (ScaleFactor + step) < 5.0){
            ScaleFactor += step;
            ScalePoint = new doublePoint(coord);

            double MoveMapFactorX = (ScalePoint.getX() / width); // тут изменить коэф смещения для большей плавности
            double MoveMapFactorY = (ScalePoint.getY() / height);

            offsetX = (int) Math.round(  MoveMapFactorX * width * (ScaleFactor -1));

            if ((MapImage.getWidth(null) * ImageFactor * ScaleFactor - offsetX) < width) {
                offsetX = (int) Math.round((MapImage.getWidth(null) * ScaleFactor * ImageFactor - width));
            }


            offsetY = (int) Math.round( MoveMapFactorY * height * (ScaleFactor -1));
            if ((MapImage.getHeight(null) * ScaleFactor * ImageFactor - offsetY) < height) {
                offsetY = (int) Math.round((MapImage.getHeight(null) * ScaleFactor * ImageFactor - height));
            }
            repaint();
        }
    }

    /**
     * проверка вхождения точки в область видимой карты
     * @param coord - точка проверки
     * @return true - если входит в область видимой карты
     */
    private boolean isVisible(/*@NotNull*/ doublePoint coord) {
        int borderX = 10;
        int borderY = 50;
        return (!(coord.getX() <= borderX) && !(coord.getX() >= width + borderX)) &&
                (!(coord.getY() <= borderY) && !(coord.getY() >= height + borderY));
    }

    /**
     * вычисление реальных координат в дробных градусах для стартовой или конечной точки
     * @param value - true, если для стартовой; false, если для конечной
     * @return - реальные координаты стартовой точки
     */
    public doublePoint getRealCoordinate(boolean value){
        //коэффициенты показывают сколько приходится градусов на один пиксель Map
        double FactorX = (bottomLeft.getX() - upperRight.getX()) / height;
        double FactorY = (upperRight.getY() - bottomLeft.getY()) / width;
        double x, y;
        if(value) {
            x = upperRight.getX() + FactorX * StartPoint.getX();    //широта
            y = bottomLeft.getY() + FactorY * StartPoint.getY();   //долгота
        }else{
            x = upperRight.getX() + FactorX * EndPoint.getX();    //широта
            y = bottomLeft.getY() + FactorY * EndPoint.getY();   //долгота
        }
        return new doublePoint(x, y);
    }

    /**
     * Вычисление координат на карте приложения без масштабирования
     * @param realCoordinate - реальные географические координаты в дробных градусах
     * @return - координаты на карте в приложении, возвращаются int, для точного расположения на карте
     */
    public doublePoint getMapCoordinate(doublePoint realCoordinate){

        double FactorX = (bottomLeft.getX() - upperRight.getX()) / height;
        double FactorY = (upperRight.getY() - bottomLeft.getY()) / width;
        double x = (realCoordinate.getX() - upperRight.getX()) / FactorX;
        double y = (realCoordinate.getY() - bottomLeft.getY()) / FactorY;
	    x = Math.round(x);
        y = Math.round(y);

        return new doublePoint(x, y);
    }

    public doublePoint getBottomLefht(){return bottomLeft;}

    public doublePoint getUpperRight(){return upperRight;}

    public void setNullWay() { BalloonWay = null; }

    public void setNullStartPoint() { StartPoint = null; }

    public void setNullEndPoint() { EndPoint = null; }

    /**
     * проверка установки стартовой и конечной точки для алогритма к точке
     * @return - true, если точки установлены; false, если одна из них null
     */
    public boolean isPointsInit() { return StartPoint != null && EndPoint != null;}

    /**
     * загрузка данных в класс map
     * также высчитывает координаты относительно карты для всех точек
     */
    public  void setAlgorithmDate(LinkedList<Vertex> date){
        grid = false;
        BalloonWay = date;

        for (Vertex vertex: date) {
            if(vertex.getMapCoordinate() == null){
                System.out.println(vertex.toString());

                doublePoint temp = vertex.getRealCoordinate();
                doublePoint temp2 = getMapCoordinate(temp);
                vertex.setMapCoordinate(temp2);

            }
        }
        repaint();
    }

    /**
     * Загружает регион и высчитывает некоторые параметры
     * @param value - имя региона
     * @param bottomLeft - реальные координаты левого нижнего угла в дробных градусах
     * @param upperRight - реальные координаты правого верхнего угла в дробных градусах
     * безопасная загрузка
     */
    public void setRegion(String value, doublePoint bottomLeft, doublePoint upperRight) {
        region = value;
        this.bottomLeft = bottomLeft;
        this.upperRight = upperRight;
        MapImage = null;
        try {
            String path = "";
            path = "src" + File.separator + "gui" + File.separator + "maps" + File.separator + region;
            MapImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Map not open!", "Error!", JOptionPane.PLAIN_MESSAGE);
            System.exit(-1);
        }

        ImageFactor = width / (double )MapImage.getWidth(null);
        ScalePoint = null;
        offsetX = offsetY = 0;
        ScaleFactor = 1;
    }

    /**
     * Метод для отрисовки
     * @param g - графический контекст
     * для получения реальных координат на отрисованной карте умножайте на * ScaleFactor * ImageFactor
     * и после вычитайте смещение угла карты
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(MapImage,
                -offsetX, -offsetY,
                (int)(MapImage.getWidth(null) * ScaleFactor * ImageFactor),
                (int)(MapImage.getHeight(null) * ScaleFactor * ImageFactor),null);

        if(grid) {
            g.setColor(gridColor);
            for(int i = -offsetX; i <= width; i += (int)(15 * ScaleFactor)) {
                g.drawLine(i, 0, i, height);
            }
            for(int i = -offsetY; i <= height; i += (int)(15 * ScaleFactor)) {
                g.drawLine(0, i, width, i);
            }
        } else {
            g.drawLine(0, 0, 0, height);
            g.drawLine(0, 0, width, 0);
            g.drawLine(width, 0, width, height);
            g.drawLine(0, height, width, height);
        }

        int radius = 3;
        g.setColor(PointColor);


        if(BalloonWay !=null){
            doublePoint prevPoint = null;
            for (Vertex vertex: BalloonWay) {
                doublePoint temp = vertex.getMapCoordinate();

                int X = (int) Math.round(temp.getX() * ScaleFactor - offsetX - radius);
                int Y = (int) Math.round(temp.getY() * ScaleFactor - offsetY - radius);
                g.drawOval( X , Y ,2*radius,2*radius);
                g.fillOval( X, Y,2*radius,2*radius);
                g.setColor(Color.BLACK);

                g.setFont(new Font("Serif", Font.PLAIN, 10));

                if(ScaleFactor >= 2.2){
                    if(vertex.getWeatherInPoint() != null){
                        g.drawString(vertex.getWeatherInPoint().toString(), X -30, Y - 15);
                    }

                    g.drawString(vertex.getRealCoordinate().toString(), X -30, Y - 5);
                }

                if(prevPoint == null){
                    prevPoint = new doublePoint(X+radius,Y+radius);
                    continue;
                }

                g.drawLine((int)prevPoint.getX(),(int)prevPoint.getY(),X+radius,Y+radius);
                prevPoint = new doublePoint(X+radius,Y+radius);

            }
        }

        if(EndPoint != null && StartPoint != null){
            if (StartPoint.equals(EndPoint)) {
                Color temp = new Color(128, 0, 128);
                g.setColor(temp);

                int X = (int) Math.round(StartPoint.getX() * ScaleFactor - offsetX - 1.5 * radius);
                int Y = (int) Math.round(StartPoint.getY() * ScaleFactor - offsetY - 1.5 * radius);

                g.drawOval( X, Y,3*radius,3*radius);
                g.fillOval( X, Y,3*radius,3*radius);
                g.drawString("s-t/f-h", X -10, Y + 20);
                return;
            }
        }

        g.setColor(PointColor);
        if(StartPoint != null){

            int X = (int) Math.round(StartPoint.getX() * ScaleFactor - offsetX - 1.5 * radius);
            int Y = (int) Math.round(StartPoint.getY() * ScaleFactor - offsetY - 1.5 * radius);

            g.drawOval( X, Y,3*radius,3*radius);
            g.fillOval( X, Y,3*radius,3*radius);
            g.drawString("start", X -10, Y + 20);
        }

        g.setColor(Color.RED);
        if(EndPoint != null){

            int X = (int) Math.round(EndPoint.getX() * ScaleFactor - offsetX - 1.5 * radius);
            int Y = (int) Math.round(EndPoint.getY() * ScaleFactor - offsetY - 1.5 * radius);

            g.drawOval( X, Y,3*radius,3*radius);
            g.fillOval( X, Y,3*radius,3*radius);
            g.drawString("finish", X -10, Y + 20);
        }
    }
}
