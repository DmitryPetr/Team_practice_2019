package algo;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;

import logger.Logs;
import dateStruct.*;
import dateStruct.doublePoint;

public class MoveBalloonAlgorithm {

    private doublePoint ControlPoint;
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
        Date date = new Date();
        Logs.writeLog(date.toString()+"\n", Level.INFO);
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
    private boolean coordsIsCorrect(doublePoint tmp, doublePoint Control, double sizeLatitude, double sizeLongitude) {
        if(nordHemisphere){
            if(tmp.getX() > Control.getX() && tmp.getX() < Control.getX() + sizeLatitude){
                return coordsLongitudeIsCorrect(tmp, Control, sizeLongitude);
            }else{
                return false;
            }
        }else{
            if(tmp.getX() < Control.getX() && tmp.getX() > Control.getX() - sizeLatitude){
                return coordsLongitudeIsCorrect(tmp, Control, sizeLongitude);
            }else{
                return false;
            }
        }
    }

    /**
     * Метод coordsLongitudeIsCorrect - метод, который проверяет выход за пределы долготы на карте
     */
    public boolean coordsLongitudeIsCorrect(doublePoint tmp, doublePoint Control, double sizeLongitude){
        if(estHemisphere){
            if(tmp.getY() > Control.getY() && tmp.getY() < Control.getY() + sizeLongitude){
                return true;
            }else{
                return false;
            }
        }else {
            if(tmp.getY() < Control.getY() && tmp.getY() > Control.getY() - sizeLongitude) {
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
        Vertex vert;
        LinkedList<Vertex> List = new LinkedList<>();
        while (TimeInAir.TimeNotOut()) {
            Logs.writeLog(" -- NEXT STEP! --\n", Level.INFO);
            if (!coordsIsCorrect(tmp, ControlPoint, sizeMapLatitude, sizeMapLongitude)) {
                Logs.writeLog(" \n-!- Error: out of bounds   -!- \n" +"Last successful Point:\n"+List.getLast().getRealCoordinate().toString()+"\n" , Level.WARNING);
                return List;
            } else {
                vert = new Vertex(tmp);
                List.addLast(vert);
            }
            pars.setLocation(tmp.getX(), tmp.getY());
            Logs.writeLog(" -- Start getting data from the server --\n", Level.INFO);
            WeatherParameters parameters = pars.getParameters();
            int res = methodTimeOut(TimeInAir, step);
            if (res <= 0) {
                Logs.writeLog(" \n-- So little step! --\n" + " -- Step value we can step on:" + res+ "\n\n", Level.WARNING);
                return null;
            }
            if (parameters != null) {
                Logs.writeLog(" -- Successful receipt of the parameters --\n", Level.INFO);
                vert.setWeatherInPoint(parameters);
                Logs.writeLog(" -- Start moving the balloon --\n", Level.INFO);
                tmp = moveBalloon(parameters, tmp, res);
                if (tmp == null) {
                    Logs.writeLog(" -!- ERRORR in the movement of the balloon  -!- \n"+"Last successful Point:\n"+List.getLast().getRealCoordinate().toString()+"\n", Level.WARNING);
                    return null;
                }
                Logs.writeLog(" -- The successful relocation of the balloon --\n\n", Level.INFO);
            } else {
                Logs.writeLog(" -!- An error retrieving the parameters -!- \n" + "Last successful weaher parameter:\n" + List.getLast().getWeatherInPoint().toString()+"\n", Level.WARNING);
                return List;
            }
        }
        Logs.writeLog(" -- A successful exit of the algorithm -- \n", Level.INFO);
        return List;
    }

    /**
     * Метод isNotEnd - метод, проверяющий достиг ли алгоритм конечной точки в вариантре программы "Полёт к конечной координате",
     * причём равенство конца вычисляется с учётом какой то области
     */
    private boolean isNotEnd(doublePoint tmp, doublePoint End, double SizeEpsilon) {
        double Control_x = End.getX() - (SizeEpsilon / 2);
        double Control_y = End.getY() - (SizeEpsilon / 2);
        return !coordsIsCorrect(tmp, new doublePoint(Control_x, Control_y), SizeEpsilon, SizeEpsilon);
    }

    /**
     * Метод AlgorithmEndPoint - алгоритм вычисления конечной точки, при задании пользователем варианта программы "Полёт к конечной координате"
     */
    public LinkedList<Vertex> AlgorithmEndPoint(doublePoint startPoint, doublePoint endPoint, String startData, int startHour, int step, double SizeEpsilon) throws IOException {

        Logs.writeLog(" -- Start alhorithm -- ", Level.INFO);
        Parsing pars = new Parsing(startData, step, startHour);
        doublePoint tmp = startPoint;
        WeatherParameters tmpW = null;
        Vertex vert;
        LinkedList<Vertex> List = new LinkedList<>();
        while (isNotEnd(tmp, endPoint, SizeEpsilon)) {
            //System.out.println("FLYYYYYY!!!");  //УБРАТЬ В ФИНАЛЬНОЙ ВЕРСИИ!!!
            Logs.writeLog(" -- NEXT STEP! --\n", Level.INFO);
            if (!coordsIsCorrect(tmp, ControlPoint, sizeMapLatitude, sizeMapLongitude)) {
                Logs.writeLog(" \n-!- Error: out of bounds   -!- \n" +"Last successful Point:\n"+List.getLast().getRealCoordinate().toString()+"\n" , Level.WARNING);
                return List;
            }else {
                vert = new Vertex(tmp);
                List.addLast(vert);
            }
            pars.setLocation(tmp.getX(), tmp.getY());
            Logs.writeLog(" -- Start getting data from the server --\n", Level.INFO);
            WeatherParameters parameters = pars.getParameters();
            tmpW = parameters;
            if (parameters != null) {
                Logs.writeLog(" -- Successful receipt of the parameters --\n", Level.INFO);
                vert.setWeatherInPoint(parameters);
                Logs.writeLog(" -- Start moving the balloon --\n", Level.INFO);
                tmp = moveBalloon(parameters, tmp, step);
                if (tmp == null) {
                    Logs.writeLog(" -!- ERRORR in the movement of the balloon  -!- \n"+"Last successful Point:\n"+List.getLast().getRealCoordinate().toString()+"\n", Level.WARNING);
                    return null;
                }
                Logs.writeLog(" -- The successful relocation of the balloon -- \n\n", Level.INFO);
            } else {
                Logs.writeLog(" -!- An error retrieving the parameters -!- \n" + "Last successful weaher parameter:\n" + List.getLast().getWeatherInPoint().toString()+"\n", Level.WARNING);
                return null;
            }
        }
        vert = new Vertex(endPoint);
        vert.setWeatherInPoint(tmpW);
        List.addLast(vert);
        Logs.writeLog(" -- A successful exit of the algorithm -- \n", Level.INFO);
        return List;
    }
}
