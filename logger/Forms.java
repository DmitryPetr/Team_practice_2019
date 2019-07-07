package logger;

import java.util.logging.*;

public class Forms extends Formatter {
    @Override
    public  String format(LogRecord record){
        return record.getLevel() + ":" + record.getMessage();
    }
}