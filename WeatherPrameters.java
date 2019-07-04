import java.util.Date;

public class WeatherPrameters {
       /**
        * WindGustKmph - Порыв ветра в километрах в час
        * WinddirDegree - Направление ветра в градусах
        * MeasureTime; - время проведения замера
        * */
    private double WindGustKmph;
    private double WinddirDegree;
    private Date MeasureTime;

    WeatherPrameters(double input_WindGustKmph, double input_WinddirDegree, Data time){
        WindGustKmph = input_WindGustKmph;
        WinddirDegree = input_WinddirDegree;
        MeasureTime = time;
    }

    public double getWinddirDegree() {
        return WinddirDegree;
    }

    public double getWindGustKmph() {
        return WindGustKmph;
    }

    public Date getMeasureTime(){
        return MeasureTime;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("wind agle: "+ WinddirDegree + "\n");
        str.append("wind speed: "+ WinddirDegree + "\n");
        str.append("date: "+ MeasureTime.toString());
        return  str.toString();
    }
}
