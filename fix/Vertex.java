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
    private Point RealCoordinate;
    private Point MapCoordinate;
    private WeatherPrameters weatherInPoint;


    public Vertex(Point p1){
        RealCoordinate = p1;
    }

    public void setMapCoordinate(Point mapCoordinate) {
        MapCoordinate = mapCoordinate;
    }

    public void setWeatherInPoint(WeatherPrameters weatherInPoint) {
        this.weatherInPoint = weatherInPoint;
    }

    public Point getRealCoordinate(){
        return RealCoordinate;
    }

    public Point getMapCoordinate(){
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
