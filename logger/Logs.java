package logger;


import com.sun.tools.javac.Main;

import java.io.IOException;
import java.util.logging.*;

public class Logs{
    private final static Logger logger = Logger.getLogger(Main.class.getName());

    static {
        Handler fileLog = null;
        try {
            fileLog = new FileHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileLog.setFormatter(new Forms());
        fileLog.setFormatter(new Forms());
        logger.setUseParentHandlers(false);
        logger.addHandler(fileLog);
    }

    public static void writeLog(String Message, Level level) throws IOException {
        logger.log(level, Message);
    }

    static class Forms extends Formatter {
        @Override
        public  String format(LogRecord record){
            return record.getLevel() + ":" + record.getMessage();
        }
    }
}
