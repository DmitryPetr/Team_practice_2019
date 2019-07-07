package algo;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;

import logger.Logs;
import dateStruct.*;
import dateStruct.doublePoint;

public class MoveBalloonAlgorithm {
    /**
     * Приватный конструктор и метод getInstance реализуют паттерн Singleton
     */
    private doublePoint ControlPoint;
    private static MoveBalloonAlgorithm instance;
    private final double sizeMap;
    private final int scale; /*Необходимо добавить в проект*/

    public static MoveBalloonAlgorithm getInstance(doublePoint СontrolPoint, double size, int scale) throws IOException {
        if (instance == null) {
            Date date = new Date();
            Logs.writeLog(" -- " + date.toString() + "--\n", Level.INFO);
            Logs.writeLog(" -- Create first instance of algorithm! -- \n", Level.INFO);
            instance = new MoveBalloonAlgorithm(СontrolPoint, size, scale);
        }
        Logs.writeLog(" -- The request instance of the algorithm! -- \n", Level.INFO);
        return instance;
    }

    private MoveBalloonAlgorithm(doublePoint СontrolPoint, double size, int scale) throws IOException {
        this.ControlPoint = СontrolPoint;
        sizeMap = size;
        this.scale = scale;
        Logs.writeLog(" -- Create MoveBalloonAlgorithm instance successful --\n", Level.INFO);
    }

    /**
     * Метод moveBalloon - метод, который вычисляет координаты следующей точки
     */
    private doublePoint moveBalloon(WeatherPrameters parameters, doublePoint startPoint, int step) {
        if (scale == 0) return null;
        /*
         * Посмотреть корректно ли строятся координаты!!!
         * */
        double x = startPoint.getX() + Math.cos(parameters.getWinddirDegree()) * (parameters.getWindGustKmph() * step / scale);
        double y = startPoint.getY() + Math.sin(parameters.getWinddirDegree()) * (parameters.getWindGustKmph() * step / scale);
        doublePoint result = new doublePoint(x, y);
        return result;
    }

    /**
     * Необходимая функция, проверяющая выход за границу
     * нужна ли она? может нужно делать проверку при отрисовке
     *
     */
    private boolean coordsIsCorrect(doublePoint tmp, doublePoint C_Point, double size) {
        if (tmp.getX() > C_Point.getX() && tmp.getX() < C_Point.getX() + size) {
            if (tmp.getY() > C_Point.getY() && tmp.getY() < C_Point.getY() + size) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /*
     * Необходима функция, проверяющая выход за границу, если граница не квадратная
     *
     * */

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
    public Vertex AlgorithmTime(doublePoint startPoint, String startData, int startHour, Time TimeInAir, int step) throws IOException {
        Logs.writeLog(" -- Start alhorithm -- \n", Level.INFO);
        Parsing pars = Parsing.getInstance(startData, step, startHour);
        doublePoint tmp = startPoint;
        Vertex vert = new Vertex(tmp, null);
        while (TimeInAir.TimeNotOut()) {
            if (!coordsIsCorrect(tmp, ControlPoint, sizeMap)) {
                Logs.writeLog(" -!- Error: out of bounds   -!- \n", Level.WARNING);
                return null;
            }
            pars.setLocation(tmp.getX(), tmp.getY());
            Logs.writeLog(" -- Getting started --\n", Level.INFO);
            WeatherPrameters parameters = pars.getParameters();
            int res = methodTimeOut(TimeInAir, step);
            if (res <= 0) {
                Logs.writeLog(" -- So little step! --\n", Level.WARNING);
                return null;
            }
            if (parameters != null) {
                Logs.writeLog(" -- Successful receipt of the parameters --\n", Level.INFO);
                vert.setWeatherInPoint(parameters);
                Logs.writeLog(" -- Start moving the balloon --\n", Level.INFO);
                tmp = moveBalloon(parameters, tmp, res);
                if (tmp == null) {
                    Logs.writeLog(" -!- Error in the movement of the balloon   -!- \n", Level.WARNING);
                    return null;
                }
                Logs.writeLog(" -- The successful relocation of the balloon --\n", Level.INFO);
                Vertex newVert = new Vertex(tmp, vert);
                vert = newVert;
            } else {
                Logs.writeLog(" -!- An error retrieving the parameters -!- \n", Level.WARNING);
                return null;
            }
        }
        Logs.writeLog(" -- A successful exit of the algorithm -- \n\n", Level.INFO);
        return vert;
    }

    /**
     * Метод isNotEnd - метод, проверяющий достиг ли алгоритм конечной точки в вариантре программы "Полёт к конечной координате",
     * причём равенство конца вычисляется с учётом какой то области
     */
    private boolean isNotEnd(doublePoint tmp, doublePoint End, double SizeEpsilon) {
        //System.out.println(SizeEpsilon/2);
        double Control_x = End.getX() - (SizeEpsilon / 2);
        double Control_y = End.getY() - (SizeEpsilon / 2);
        doublePoint C_Point = new doublePoint(Control_x, Control_y);
        if (!coordsIsCorrect(tmp, C_Point, SizeEpsilon)) return true;
        else return false;
    }


    /**
     * Метод AlgorithmEndPoint - алгоритм вычисления конечной точки, при задании пользователем варианта программы "Полёт к конечной координате"
     */
    public Vertex AlgorithmEndPoint(doublePoint startPoint, doublePoint endPoint, String startData, int startHour, int step, double SizeEpsilon) throws IOException {
        Logs.writeLog(" -- Start alhorithm -- \n", Level.INFO);
        Parsing pars = Parsing.getInstance(startData, step, startHour);
        doublePoint tmp = startPoint;
        Vertex vert = new Vertex(tmp, null);
        while (isNotEnd(tmp, endPoint, SizeEpsilon)) {
            if (!coordsIsCorrect(tmp, ControlPoint, sizeMap)) {
                Logs.writeLog(" -!- Error: out of bounds   -!- \n", Level.WARNING);
                return null;
            }
            pars.setLocation(tmp.getX(), tmp.getY());
            Logs.writeLog(" -- Getting started --\n", Level.INFO);
            WeatherPrameters parameters = pars.getParameters();
            if (parameters != null) {
                Logs.writeLog(" -- Successful receipt of the parameters --\n", Level.INFO);
                vert.setWeatherInPoint(parameters);
                Logs.writeLog(" -- Start moving the balloon --\n", Level.INFO);
                tmp = moveBalloon(parameters, tmp, step);
                if (tmp == null) {
                    Logs.writeLog(" -!- Error in the movement of the balloon   -!- \n", Level.WARNING);
                    return null;
                }
                Logs.writeLog(" -- The successful relocation of the balloon --\n", Level.INFO);
                Vertex newVert = new Vertex(tmp, vert);
                vert = newVert;
            } else {
                Logs.writeLog(" -!- An error retrieving the parameters -!- \n", Level.WARNING);
                return null;
            }
        }
        Logs.writeLog(" -- A successful exit of the algorithm -- \n\n", Level.INFO);
        return vert;
    }
}
