package dateStruct;

public class WeatherParameters {
       /**
        * WindGustKmph - Порыв ветра в километрах в час
        * WinddirDegree - Направление ветра в градусах
        * */
    private final int WindGustKmph;
    private final int WinddirDegree;

    public WeatherParameters(int input_WindGustKmph, int input_WinddirDegree){
        WindGustKmph = input_WindGustKmph;
        WinddirDegree = input_WinddirDegree;
    }

    public int getWinddirDegree() {
        return WinddirDegree;
    }

    public int getWindGustKmph() {
        return WindGustKmph;
    }

    @Override
    public String toString() {

        return "angle: " + WinddirDegree + "speed: " + WinddirDegree;
    }
}
