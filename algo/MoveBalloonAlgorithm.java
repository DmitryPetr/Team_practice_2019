import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.*;

public class MoveBalloonAlgorithm {
    /**
     * Приватный конструктор и метод getInstance реализуют паттерн Singleton
     * */
    private Point ControlPoint;
    private static MoveBalloonAlgorithm instance;
    private final double sizeMap;
    private final int scale; /*Необходимо добавить в проект*/
    public static MoveBalloonAlgorithm getInstance(Point СontrolPoint, double size, int scale){
        if(instance==null){
            instance = new MoveBalloonAlgorithm(СontrolPoint, size, scale);}
        return  instance;
    }

    private MoveBalloonAlgorithm(Point СontrolPoint, double size,int scale){
        this.ControlPoint = СontrolPoint;
        sizeMap = size;
        this.scale = scale;
    }

    /**
     * Метод moveBalloon - метод, который вычисляет координаты следующей точки
     * */
    private Point moveBalloon(WeatherPrameters parameters, Point startPoint, int step){
        System.out.println("\n------Step on map: "+step+"----\n");
        if(scale==0) return null;
        /*
        * Посмотреть корректно ли строятся координаты!!!
        * */
            double x = startPoint.getX()+Math.cos(parameters.getWinddirDegree())*(parameters.getWindGustKmph()*step/scale);
            double y = startPoint.getY()+Math.sin(parameters.getWinddirDegree())*(parameters.getWindGustKmph()*step/scale);
            Point result = new Point(x, y, startPoint);
            return result;
    }
    /**
     * Необходимая функция, проверяющая выход за границу
     *
     * */
    private boolean coordsIsCorrect(Point tmp, Point C_Point, double size){
        if(tmp.getX()>C_Point.getX()&&tmp.getX()<C_Point.getX()+size){
            if(tmp.getY()>C_Point.getY()&&tmp.getY()<C_Point.getY()+size){
                return true;
            }else{ return false;}
        }else{ return false;}
    }
    /*
    * Необходима функция, проверяющая выход за границу, если граница не квадратная
    *
    * */

    /**
     * Метод methodTimeOut - метод, осуществляющий уменьшение времени полёта после каждого шага
     * */
    private int methodTimeOut(Time tmp, int step){
        if((tmp.getHour()-step)<=0){
            if(tmp.getDay()>0){
                tmp.setHour(tmp.getHour()+24-step);
                tmp.setDay(tmp.getDay()-1);
                return step;
                }else{
                    int res = tmp.getHour();
                    tmp.setHour(0);
                    return res;
                    }
            }else{
            tmp.setHour(tmp.getHour()-step);
            return step;}
        }


        /**
         * Метод AlgorithmTime - алгоритм вычисления конечной точки, при задании пользователем варианта программы "Полёт по времени"
         * */
    public Point AlgorithmTime(Point startPoint, String startData, int startHour, Time TimeInAir, int step) {
        Parsing pars = Parsing.getInstance(startData, step, startHour);
        Point tmp = startPoint;
        while (TimeInAir.TimeNotOut()) {
            if(!coordsIsCorrect(tmp, ControlPoint, sizeMap)){
                return null;
            }
            pars.setLocation(tmp.getX(), tmp.getY());
            WeatherPrameters parameters = pars.getParameters();
            int res = methodTimeOut(TimeInAir, step);
            if(res<=0) return null;
            if(parameters!=null){
            tmp = moveBalloon(parameters, tmp, res);
            if(tmp==null) return null;
            }else return null;
        }
        return tmp;
    }

    /**
     * Метод isNotEnd - метод, проверяющий достиг ли алгоритм конечной точки в вариантре программы "Полёт к конечной координате",
     * причём равенство конца вычисляется с учётом какой то области
     * */
    private boolean isNotEnd(Point tmp, Point End, double SizeEpsilon){
        System.out.println(SizeEpsilon/2);
        double Control_x = End.getX()-(SizeEpsilon/2);
        double Control_y = End.getY()-(SizeEpsilon/2);
        Point C_Point = new Point(Control_x, Control_y, null);
        if(!coordsIsCorrect(tmp, C_Point, SizeEpsilon)) return true;
        else return false;
    }


    /**
     * Метод AlgorithmEndPoint - алгоритм вычисления конечной точки, при задании пользователем варианта программы "Полёт к конечной координате"
     * */
    public Point AlgorithmEndPoint(Point startPoint, Point endPoint, String startData, int startHour, int step, double SizeEpsilon){
        Parsing pars = Parsing.getInstance(startData, step, startHour);
        Point tmp = startPoint;
        while (isNotEnd(tmp, endPoint, SizeEpsilon)) {
            if(!coordsIsCorrect(tmp, ControlPoint, sizeMap)){
                return null;
            }
            pars.setLocation(tmp.getX(), tmp.getY());
            WeatherPrameters parameters = pars.getParameters();
            if(parameters!=null){
                tmp = moveBalloon(parameters, tmp, step);
                if(tmp==null) return null;
            }else return null;
        }
        return tmp;
    }
}
