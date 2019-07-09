package dateStruct;

/**
 * Структура данных для алгоритма
 * RealCoordinate - координаты в реальном мире
 * MapCoordinate - координаты на карте
 * PointWeather - Погода в точке
 */

/*
 * Добавить вычисление map координат
 * Можно в конструктор, но лучше через сеттер или наоборот, реальные кооординаты добавлять через сеттер, а map координаты через конструктор
 * */

public class Vertex{
    private doublePoint RealCoordinate;
    private doublePoint MapCoordinate;
    private WeatherPrameters weatherInPoint;

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
    public void setWeatherInPoint(WeatherPrameters weatherInPoint) {
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
    public WeatherPrameters getWeatherInPoint(){
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
        if (weatherInPoint != null) str.append("Weather parameter in this point:\n").append(weatherInPoint.toString());

        return str.toString();
    }
}