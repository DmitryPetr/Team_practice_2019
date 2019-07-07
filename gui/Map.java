package gui;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Map extends JPanel {
    private int width;
    private int height;
    private boolean grid = false;
    private Color gridColor = new Color(0,0,0, 87);
    private Image MapImage;
    private String region = "australia.jpg";

    private Point StartPoint;
    private Color PointColor;
    private double ScaleCoof;
    private Point ScalePoint;
    private double ImageCoof;
    /**
     * @param width - ширина окна
     * @param height - высота окна
     *
     * grid - флаг сетки
     * grid Color - цвет сетки
     * MapImage - фоновое изображение
     * region - имя фонового изображения
     * StartPoint -стартовая точка алгоритма
     * PointColor - цвет точки
     * ScaleCoof - коэфицент масштаба
     * ScalePoint - точка-центр масштабирования
     * ImageCoof - коэфицент сжатия большого изображения
     */

    /**
     * Конструктор класса
     * @param width
     * @param height
     * загрузка стартового региона через setRegion
     * Стартовый масштаб 1
     */
    public Map(int width, int height) {
        super();
        this.width = width;
        this.height = height;

        setRegion(region);
        ScaleCoof = 1;
    }

    /**
     * изменение флага сетки
     * @param value
     */
    public void setGrid(boolean value) {
        grid = value;
    }

    /**
     * установка стартовой точки на карте
      * @param p - стартовая точка
     * обязательная перерисовка области
     */
    public void setStartPoint(Point p){
        StartPoint = new Point(p);
        PointColor = new Color(0, 13, 255);
        repaint();
    }

    public Point getStartPoint(){
        return StartPoint;
    }

    /**
     * метод для изменения масштаба
     * @param coof - прибавка к масштабу
     * @param coord - точка вызова ищменеия масштаба
     */
    public void MapScale(double coof, Point coord){
        if( isVisible(coord) && (ScaleCoof + coof) >= 1.0 && (ScaleCoof + coof ) < 2.5){
            ScaleCoof += coof;
            ScalePoint = new Point(coord);
            repaint();
        }
    }

    /**
     * проверка вхождения точки в область видимой карты
     * @param coord - точка проверки
     * @return true - если входит в область видимой карты
     */
    private boolean isVisible(@NotNull Point coord) {
        int borderX = 10;
        int borderY = 50;
        if( (coord.getX()<= borderX || coord.getX() >= width + borderX) ||
                (coord.getY()<= borderY || coord.getY() >= height + borderY))
            return false;
        return true;
    }

    /*
    public Point getRealCoordiante(){

    }
    */

    public  void setAlgorithmDate(){
        grid = false;
        // загрузка данных алгоритма
        repaint();
    }

    /**
     * Загружает регион и высчитывает некоторые параметры
     * @param value - имя региона
     * безопасная загрузка
     */
    public void setRegion(String value) {
        region = value;
        MapImage = null;
        try {
            String path = new String("");
            //path = "src" + File.separator + "gui" + File.separator + "maps" + File.separator + "australia.jpg";
            path = "src" + File.separator + "gui" + File.separator + "maps" + File.separator + region;
            MapImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            //System.out.println("File not open!");
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
     * @param g
     * offsetX, offsetY - смещение карты за левый верхний угол при масштабировании
     * для получения реальных координат на отрисованной карте умножайте на * ScaleCoof * ImageCoof
     * и после вычитайте смещение угла карты
     */
    @Override
    public void paint(Graphics g) {
        int offsetX=0,offsetY=0;

        if(ScalePoint != null){
            double MoveMapCoofX = (ScalePoint.getX()/width); // тут изменить коэф смещения для большей плавности
            double MoveMapCoofY = (ScalePoint.getY()/height);

            if(MoveMapCoofX <= 0.6 && MoveMapCoofX >= 0.4){
                MoveMapCoofX = 0.5;
                offsetX = (int)Math.round(width*MoveMapCoofX*(ScaleCoof-1));
            }else {
                offsetX = (int) Math.round(ScalePoint.getX()*MoveMapCoofX * ScaleCoof);
            }
            if(MoveMapCoofY <= 0.6 && MoveMapCoofY >= 0.4){
                MoveMapCoofY = 0.5;
                offsetY = (int)Math.round(width*MoveMapCoofY*(ScaleCoof-1));
            }else{
                offsetY = (int) Math.round(ScalePoint.getY()*MoveMapCoofY * ScaleCoof);
            }


            if((MoveMapCoofX - 0.5 >= 0.01)  && (MapImage.getWidth(null)*ImageCoof*ScaleCoof-offsetX)< width ){
                offsetX = (int)Math.round((MapImage.getWidth(null)*ScaleCoof*ImageCoof - width));
            }
            if((MoveMapCoofY - 0.5 >= 0.01) && (MapImage.getHeight(null)*ScaleCoof*ImageCoof-offsetY)< height ){
                offsetY= (int)Math.round((MapImage.getHeight(null)*ScaleCoof*ImageCoof - height));
            }
        }

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
            int X = (int) ((StartPoint.getX() -radius) * ScaleCoof * ImageCoof - offsetX);
            int Y = (int) ((StartPoint.getY() +radius) * ScaleCoof * ImageCoof - offsetY);

            if (isVisible(new Point(X,Y))){
                g.drawOval( X, Y,2*radius,2*radius);
                g.fillOval( X, Y,2*radius,2*radius);
            }

        }


        /*
        Алгоритм отрисовки пути
         */
    }
}
