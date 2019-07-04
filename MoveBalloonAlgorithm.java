import java.math.*;

public class MoveBalloonAlgorithm {
    /**
     * Приватный конструктор и метод getInstance реализуют паттерн Singleton
     * */
    private static MoveBalloonAlgorithm instance;
    private int sizeMap;
    public static MoveBalloonAlgorithm getInstance(int size){
        if(instance==null){
            instance = new MoveBalloonAlgorithm(size);}
        return  instance;
    }
    private MoveBalloonAlgorithm(int size){
        sizeMap = size;
    }

    private Point moveBalloon(WeatherPrameters parameters, Point startPoint){
            double x = startPoint.getX()*Math.cos(parameters.getWinddirDegree())*parameters.getWindGustKmph();
            double y = startPoint.getY()*Math.sin(parameters.getWinddirDegree())*parameters.getWindGustKmph();
            Point result = new Point(x, y, startPoint);
            return result;
    }

}
