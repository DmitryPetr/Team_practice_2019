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
    private String region = "australia.jpg";

    public Map(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }

    public void setGrid(boolean value) {
        grid = value;
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
            g.setColor(Color.RED);
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
    }
}
