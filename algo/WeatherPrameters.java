public class WeatherPrameters {
       /**
        * WindGustKmph - Порыв ветра в километрах в час
        * WinddirDegree - Направление ветра в градусах
        * */
    private int WindGustKmph;
    private int WinddirDegree;

    WeatherPrameters(int input_WindGustKmph, int input_WinddirDegree){
        WindGustKmph = input_WindGustKmph;
        WinddirDegree = input_WinddirDegree;
    }

    public int getWinddirDegree() {
        return WinddirDegree;
    }

    public int getWindGustKmph() {
        return WindGustKmph;
    }
}
