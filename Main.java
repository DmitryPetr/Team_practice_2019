import gui.*;
import logger.Logs;

import java.io.IOException;
import java.util.logging.Level;

public class Main {
    public static void main(String[] args) throws IOException {
        Logs.writeLog("start program", Level.INFO);
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}
