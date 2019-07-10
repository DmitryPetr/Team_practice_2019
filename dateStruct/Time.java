package dateStruct;

import java.io.IOException;

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
        if(month==0&&day==0&&hour==0){
            return false;}
        else{
            return true;}
    }

    public int getDay() { return day; }

    public int getHour() { return hour; }

    public int getMonth() { return month;}

    public int getYear() {return year; }

    public void setDay(int day) { this.day = day; }

    public void setHour(int hour) { this.hour = hour; }

    public void setMonth(int month) {this.month = month;  }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Month:\n"+ month);
        str.append("Day:\n"+day);
        str.append("Hour:\n"+hour);
        return str.toString();
    }
}