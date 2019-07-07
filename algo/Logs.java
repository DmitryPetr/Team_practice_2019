import java.io.IOException;
import java.util.logging.*;

public class Logs {

    private static final Logger logger = Logger.getLogger(MainAlgorithm.class.getName());

    public static void writeLog(String Message, Level level) throws IOException {
        Handler fileLog = new FileHandler();
        fileLog.setFormatter(new Forms());
        logger.setUseParentHandlers(false);
        logger.addHandler(fileLog);
        logger.log(level, Message);
    }
}
