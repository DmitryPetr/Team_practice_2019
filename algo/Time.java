import java.io.IOException;
import java.util.logging.Level;

public class Time {
    private int year;
    private int month;
    private int day;
    private int hour;


    public Time(String data, int hour){
        this(data);
        this.hour = hour;}

    public Time(String data){
    String[] split = data.split("[-]");
    year = Integer.parseInt(split[0]);
    month = Integer.parseInt(split[1]);
    day = Integer.parseInt(split[2]);
    hour = 0;}

    /**
     * Конструктор от 3х аргументов необходим для задания времени полёта
     * */

    public Time(int month, int day, int hour){
        this.month = month;
        this.day = day;
        this.hour = hour;
    }

    /**
     * Метод TimeNotOut необходим для работы алгоритме в режиме полёта по времени, который вычисляет "закончилось ли время полёта?"
     * */
    public boolean TimeNotOut() throws IOException {
        //Logs.writeLog(" -- In TimeNotOut: \n", Level.INFO);
        //System.out.println("Month: "+month+"\nDay: "+day+"\nHour: "+hour+"\n");

        if(month==0&&day==0&&hour==0){
           // System.out.println("In TimeNotOut - false\n");

            return false;}
        else{
           // System.out.println("In TimeNotOut - true\n");
            return true;}
    }

    public int getDay() { return day; }

    public int getHour() { return hour; }

    public int getMonth() { return month;}

    public int getYear() {return year; }

    public void setDay(int day) { this.day = day; }

    public void setHour(int hour) { this.hour = hour; }

    public void setMonth(int month) {this.month = month;  }
}
