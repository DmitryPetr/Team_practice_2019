package dateStruct;

public class WeatherPrameters {
       /**
        * WindGustKmph - Порыв ветра в километрах в час
        * WinddirDegree - Направление ветра в градусах
        * */
    private int WindGustKmph;
    private int WinddirDegree;

    public WeatherPrameters(int input_WindGustKmph, int input_WinddirDegree){
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
        StringBuilder str = new StringBuilder();
        str.append("wind agle: "+ WinddirDegree + "\n");
        str.append("wind speed: "+ WindGustKmph + "\n");
        return  str.toString();
    }


}
