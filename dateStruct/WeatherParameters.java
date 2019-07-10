package dateStruct;

public class WeatherParameters {
    /**
     * WindGustKmph - Порыв ветра в километрах в час
     * WinddirDegree - Направление ветра в градусах
     */
    private final int WindGustKmph;
    private final int WinddirDegree;

    public WeatherParameters(int input_WindGustKmph, int input_WinddirDegree) {
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof WeatherParameters) {
            WeatherParameters otherObj = (WeatherParameters) obj;
            if (otherObj.WindGustKmph == WindGustKmph) {
                if (otherObj.WinddirDegree == WinddirDegree) return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 31;
        result = 11 * result + WindGustKmph + WinddirDegree;
        return result;
    }

    @Override
    public String toString() {
        return "A: " + String.format("%.2f", WinddirDegree) + " S: " + String.format("%.2f", WinddirDegree);
    }
}
