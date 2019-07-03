package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Map extends JPanel {

    private int width;
    private int height;

    public Map(int width, int height) {
        super();
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics g) {
        Image im = null;
        try {
            String path = new String("");
            path = "src" + File.separator + "gui" + File.separator + "maps" + File.separator + "australia.jpg";
            im = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Not File!");
        }
        g.drawImage(im, 0, 0, null);

        for(int i = 0; i <= width; i += 10) {
            g.drawLine(i, 0, i, height);
        }
        for(int i = 0; i <= height; i += 10) {
            g.drawLine(0, i, width, i);
        }
    }
}
