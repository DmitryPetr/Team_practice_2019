package algo;

import dateStruct.Time;
import dateStruct.Vertex;
import dateStruct.WeatherParameters;
import dateStruct.doublePoint;
import logger.Logs;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;

public class MoveBalloonAlgorithm {

    private doublePoint ControlPoint;
    private static MoveBalloonAlgorithm instance;
    private double sizeMapLongitude;
    private double sizeMapLatitude;
    private int scale; /*Необходимо добавить в проект*/
    private boolean nordHemisphere;
    private boolean estHemisphere;

    public MoveBalloonAlgorithm(doublePoint СontrolPoint,  double sizeLongitude, double sizeLatitude, boolean nordSphere, boolean estSphere, int scale) throws IOException {
        this.ControlPoint = СontrolPoint;
        sizeMapLongitude = sizeLongitude;
        sizeMapLatitude = sizeLatitude;

        this.scale = scale;
        nordHemisphere = nordSphere;
        estHemisphere = estSphere;
        Logs.writeLog(" -- Create MoveBalloonAlgorithm instance successful --\n", Level.INFO);
    }

    /**
     * Метод moveBalloon - метод, который вычисляет координаты следующей точки
     */

    private doublePoint moveBalloon(WeatherParameters parameters, doublePoint startPoint, int step) {
        if(scale == 0)
            return null;
        double x = startPoint.getX() + Math.cos(parameters.getWinddirDegree()) * (parameters.getWindGustKmph() * step / (double)scale);
        double y = startPoint.getY() + Math.sin(parameters.getWinddirDegree()) * (parameters.getWindGustKmph() * step / (double)scale);
        doublePoint result = new doublePoint(x, y);
        return result;
    }

    /**
     * Метод coordsIsCorrect - метод, который проверяет выход за границы карты
     */
    private boolean coordsIsCorrect(doublePoint tmp) {
        if(nordHemisphere){
            if(tmp.getX() > ControlPoint.getX() && tmp.getX() < ControlPoint.getX() + sizeMapLatitude){
                return coordsLongitudeIsCorrect(tmp);
            }else{
                return false;
            }
        }else{
            if(tmp.getX() < ControlPoint.getX() && tmp.getX() > ControlPoint.getX() - sizeMapLatitude){
                return coordsLongitudeIsCorrect(tmp);
            }else{
                return false;
            }
        }
    }


    /**
     * Метод coordsLongitudeIsCorrect - метод, который проверяет выход за пределы долготы на карте
     */
    public boolean coordsLongitudeIsCorrect(doublePoint tmp){
        if(estHemisphere){
            if(tmp.getY() > ControlPoint.getY() && tmp.getY() < ControlPoint.getY() + sizeMapLongitude){
                return true;
            }else{
                return false;
            }
        }else {
            if(tmp.getY() < ControlPoint.getY() && tmp.getY() > ControlPoint.getY() - sizeMapLongitude) {
                return true;
            }
            else{
                return false;
            }
        }

    }

    /**
     * Метод methodTimeOut - метод, осуществляющий уменьшение времени полёта после каждого шага
     */
    private int methodTimeOut(Time tmp, int step) {
        if ((tmp.getHour() - step) <= 0) {
            if (tmp.getDay() > 0) {
                tmp.setHour(tmp.getHour() + 24 - step);
                tmp.setDay(tmp.getDay() - 1);
                return step;
            } else {
                int res = tmp.getHour();
                tmp.setHour(0);
                return res;
            }
        } else {
            tmp.setHour(tmp.getHour() - step);
            return step;
        }
    }

    /**
     * Метод AlgorithmTime - алгоритм вычисления конечной точки, при задании пользователем варианта программы "Полёт по времени"
     */
    public LinkedList<Vertex> AlgorithmTime(doublePoint startPoint, String startData, int startHour, Time TimeInAir, int step) throws IOException {

        Logs.writeLog(" -- Start alhorithm -- \n", Level.INFO);
        Parsing pars = new Parsing(startData, step, startHour);
        doublePoint tmp = startPoint;
        Vertex vertex;
        LinkedList<Vertex> List = new LinkedList<>();

        while (TimeInAir.TimeNotOut()) {
            System.out.println("NEXT STEP!"); //ДЛЯ ПРОВЕРКИ(УДАЛИТЬ В ФИНАЛЬНОЙ ВЕРСИИ!!!
            //if (!coordsIsCorrect(tmp, ControlPoint, sizeMapLongitude, sizeMapLatitude)) {
            if (!coordsIsCorrect(tmp)) {
                Logs.writeLog(" -!- Error: out of bounds   -!- \n", Level.WARNING);
                return List;
            } else {
                vertex = new Vertex(tmp);

                List.addLast(vertex);
            }
            pars.setLocation(tmp.getX(), tmp.getY());
            Logs.writeLog(" -- Getting started --\n", Level.INFO);
            WeatherParameters parameters = pars.getParameters();
            int res = methodTimeOut(TimeInAir, step);
            if (res <= 0) {
                Logs.writeLog(" -- So little step! --\n", Level.WARNING);

                return null;
            }
            if (parameters != null) {

                Logs.writeLog(" -- Successful receipt of the parameters --\n", Level.INFO);
                vertex.setWeatherInPoint(parameters);
                Logs.writeLog(" -- Start moving the balloon --\n", Level.INFO);
                tmp = moveBalloon(parameters, tmp, res);
                if (tmp == null) {
                    Logs.writeLog(" -!- Error in the movement of the balloon   -!- \n", Level.WARNING);

                   // return List;
                    return null;
                }
                Logs.writeLog(" -- The successful relocation of the balloon --\n", Level.INFO);


            } else {
                Logs.writeLog(" -!- An error retrieving the parameters -!- \n", Level.WARNING);


                return null;
            }
        }
        
        Logs.writeLog(" -- A successful exit of the algorithm -- \n\n", Level.INFO);
        return List;
    }

    /**
     * Метод isNotEnd - метод, проверяющий достиг ли алгоритм конечной точки в вариантре программы "Полёт к конечной координате",
     * причём равенство конца вычисляется с учётом какой то области
     */
    private boolean isNotEnd(doublePoint tmp, doublePoint End, double SizeEpsilon) {
        double Control_x = End.getX() - (SizeEpsilon / 2);
        double Control_y = End.getY() - (SizeEpsilon / 2);
        if (tmp.getX() > Control_x && tmp.getX() < Control_x + SizeEpsilon){
            if (tmp.getY() > Control_y && tmp.getY() < Control_y + SizeEpsilon) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }

    }


    /**
     * Метод AlgorithmEndPoint - алгоритм вычисления конечной точки, при задании пользователем варианта программы "Полёт к конечной координате"
     */
    public LinkedList<Vertex> AlgorithmEndPoint(doublePoint startPoint, doublePoint endPoint, String startData, int startHour, int step, double SizeEpsilon) throws IOException {

        Logs.writeLog(" -- Start alhorithm -- \n", Level.INFO);
        Parsing pars = new Parsing(startData, step, startHour);
        doublePoint tmp = startPoint;
        Vertex vertex;
        LinkedList<Vertex> List = new LinkedList<>();

        while (isNotEnd(tmp, endPoint, SizeEpsilon)) {
            //if (!coordsIsCorrect(tmp, ControlPoint, sizeMapLongitude, sizeMapLatitude)) {
            if (!coordsIsCorrect(tmp)) {
                Logs.writeLog(" -!- Error: out of bounds   -!- \n", Level.WARNING);
                return List;
            }else {
                vertex = new Vertex(tmp);
                List.addLast(vertex);
            }
            pars.setLocation(tmp.getX(), tmp.getY());
            Logs.writeLog(" -- Getting started --\n", Level.INFO);
            WeatherParameters parameters = pars.getParameters();
            if (parameters != null) {
                Logs.writeLog(" -- Successful receipt of the parameters --\n", Level.INFO);
                vertex.setWeatherInPoint(parameters);
                Logs.writeLog(" -- Start moving the balloon --\n", Level.INFO);
                tmp = moveBalloon(parameters, tmp, step);
                if (tmp == null) {
                    Logs.writeLog(" -!- Error in the movement of the balloon   -!- \n", Level.WARNING);
                    return null;
                }
                Logs.writeLog(" -- The successful relocation of the balloon --\n", Level.INFO);

            } else {
                Logs.writeLog(" -!- An error retrieving the parameters -!- \n", Level.WARNING);
                return null;
            }
        }
        Logs.writeLog(" -- A successful exit of the algorithm -- \n\n", Level.INFO);
        return List;
    }
}
