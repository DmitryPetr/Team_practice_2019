package dateStruct;

/**
 * Структура хранения данных для алгоритма
 * RealCoordinate - координаты в реальном мире
 * MapCoordinate - координаты на карте
 * PointWeather - Погода в точке
 */

public class Vertex{
    private final doublePoint RealCoordinate;
    private doublePoint MapCoordinate;
    private WeatherParameters weatherInPoint;

    public Vertex(doublePoint coord){
        RealCoordinate = coord;
        MapCoordinate = null;
        weatherInPoint = null;
    }

    /**
     * устанавливает координаты map координаты
     * @param mapCoordinate
     */
    public void setMapCoordinate(doublePoint mapCoordinate) {
        MapCoordinate = mapCoordinate;
    }

    /**
     * Устаналивает значение погоды в точке
     * @param weatherInPoint
     */
    public void setWeatherInPoint(WeatherParameters weatherInPoint) {
        this.weatherInPoint = weatherInPoint;
    }

    /**
     * возвращает реальный координаты точки
     * @return
     */
    public doublePoint getRealCoordinate(){
        return RealCoordinate;
    }

    /**
     * возвращает map координаты точки
     * @return
     */
    public doublePoint getMapCoordinate(){
        return  MapCoordinate;
    }

    /**
     * возвращает текущую погоду
     * @return
     */
    public WeatherParameters getWeatherInPoint(){
        return  weatherInPoint;
    }

    @Override
    public boolean equals(Object obj){
        if(this==obj){
            return true;
        }
        if(obj instanceof Vertex){
            Vertex otherObj = (Vertex)obj;
            if(otherObj.RealCoordinate.equals(RealCoordinate)){
                if(otherObj.weatherInPoint.equals(weatherInPoint)) return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        int result = 31;
        result = 11 * result + RealCoordinate.hashCode() + weatherInPoint.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("real coordinate:\n").append(RealCoordinate.toString());
        if (MapCoordinate != null)  str.append("map coordinate:\n").append(MapCoordinate.toString());
        if (weatherInPoint != null) str.append("\nWeather parameter in this point:\n").append(weatherInPoint.toString());
        return str.toString();
    }
}
