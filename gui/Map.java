package gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Map extends JPanel {

    private int width;
    private int height;
    private boolean grid = false;
    Color gridColor = new Color(0,0,0, 87);
    private String region = "australia.jpg";

    Point StartPoint;
    Color PointColor;

    public Map(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        StartPoint = null;
    }

    public void setGrid(boolean value) {
        grid = value;
    }
    public void setStartPoint(Point p){
        StartPoint = new Point(p);
        PointColor = new Color(0, 13, 255);
    }

    public Point getStartPoint(){
        return StartPoint;
    }

    /*
    public Point getRealCoordiante(){

    }
    */

    public  void setAlgorithmData(){
        // загрузка данных алгоритма
    }

    public void setRegion(String value) {
        region = value;
    }

    @Override
    public void paint(Graphics g) {
        Image im = null;
        try {
            String path = new String("");
            //path = "src" + File.separator + "gui" + File.separator + "maps" + File.separator + "australia.jpg";
            path = "src" + File.separator + "gui" + File.separator + "maps" + File.separator + region;
            im = ImageIO.read(new File(path));
        } catch (IOException e) {
            //System.out.println("File not open!");
            String message = "File not open!";
            JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.PLAIN_MESSAGE);
            System.exit(-1);
        }
        g.drawImage(im, 0, 0, null);

        if(grid) {
            g.setColor(gridColor);
            for(int i = 0; i <= width; i += 10) {
                g.drawLine(i, 0, i, height);
            }
            for(int i = 0; i <= height; i += 10) {
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
            g.drawOval((int) StartPoint.getX() - 3,  (int)StartPoint.getY() +3,6,6);
            g.fillOval((int) StartPoint.getX() - 3,  (int)StartPoint.getY() +3,6,6);
        }


        /*
        Алгоритм отрисовки пути
         */
    }
}
