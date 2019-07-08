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
    private Time MeasuringTime;


    public void setMeasuringTime(Time measuringTime) {
        MeasuringTime = measuringTime;
    }
    public Vertex(doublePoint p1, Time measuringTime){
        RealCoordinate = p1;

    }

    public void setMapCoordinate(doublePoint mapCoordinate) {
        MapCoordinate = mapCoordinate;
    }

    public void setWeatherInPoint(WeatherPrameters weatherInPoint) {
        this.weatherInPoint = weatherInPoint;
    }

    public doublePoint getRealCoordinate(){
        return RealCoordinate;
    }

    public doublePoint getMapCoordinate(){
        return  MapCoordinate;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("real coordinate:\n"+ RealCoordinate.toString());
        str.append("map coordinate:\n"+ MapCoordinate.toString());
        str.append("Weather parameter in this point:\n"+weatherInPoint.toString());
        return str.toString();
    }
}
