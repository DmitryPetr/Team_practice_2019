/**
 * Структура данных для алгоритма
 * RealCoordinate - координаты в реальном мире
 * MapCoordinate - координаты на карте
 * PointWeather - Погода в точке
 */


public class Vertex{
    private Point RealCoordinate;
    private Point MapCoordinate;
    private WeatherDate PointWeather;


    public Vertex( Point p1, Point p2, WeatherDate weater){
        RealCoordinate = p1;
        MapCoordinate = p2;
        PointWeather = weater;
    }

    public Point getRealCoordinate(){
        return RealCoordinate;
    }

    public Point getMapCoordinate(){
        return  MapCoordinate;
    }

    public WeatherDate getPointWeather(){
        return PointWeather;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("real coordinate:\n"+ RealCoordinate.toString());
        str.append("map coordinate:\n"+ MapCoordinate.toString());
        str.append("weather:\n"+PointWeather.toString());

        return str.toString();
    }
}