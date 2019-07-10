package algo;

import dateStruct.Time;
import dateStruct.WeatherParameters;
import logger.Logs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.logging.Level;

public class Parsing {
    /**
     * Параметры
     * source - источник
     * key - ключ(обязательное поле для доступа)
     * location - местоположение
     * format - формат ответа сайта
     * dataStart и dataend -начальные и конечные даты для погоды
     * timeReload - время обновления погоды для конкретного дня
     */
    private static final String source = "https://api.worldweatheronline.com/premium/v1/past-weather.ashx?";
    //private static final String key = "key=b60f705b24864ca6bc891344190307";
    private static final String key = "key=a0fdc17cd5084361937220857190807";
    private String location = "q=Moscow";
    private static final String format = "format=json";
    private String dataStart;
    private static final String timeReload = "tp=1";
    private HttpURLConnection connection = null;
    private String answser;
    private int step;
    private Time countTime;
    private Calendar calendar = Calendar.getInstance();
    private int EndDay = calendar.get(Calendar.DAY_OF_MONTH);

    public Parsing(String data, int step, int startHour) throws IOException {
        countTime = new Time(data, startHour);
        this.step = step;
        setDataStart(data);
        Logs.writeLog(" -- Create parsing class instance successful --", Level.INFO);
    }

    public void setDataStart(String dataStart) {
        StringBuilder str = new StringBuilder("date=");
        this.dataStart = str.append(dataStart).toString();
    }

    public void setLocation(double x, double y) {
        StringBuilder str = new StringBuilder("q=");
        this.location = str.append(x + "," + y).toString();
    }

    public void setLocation(String location) {
        StringBuilder str = new StringBuilder("q=");
        this.location = str.append(location).toString();
    }

    /**
     * Отравление HTTP запроса на сервер API для получения необходимой информации
     */
    private void getMesWeather() throws IOException {
        String request = new String(source + key + "&" + location + "&" + format + "&" + dataStart + "&" + timeReload);

        boolean flagTry = false;
        for(int i =0;!flagTry&&i!=2;i++){
            try {
                connection = (HttpURLConnection) new URL(request).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(1250);
                connection.setReadTimeout(1250);
                connection.connect();
                StringBuilder sb = new StringBuilder();

                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;

                    while ((line = in.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                }else{
                    Logs.writeLog(" -- Fail" + connection.getResponseCode() + ", " + connection.getResponseMessage() + " --\n", Level.WARNING);
                }
                answser = sb.toString();
                flagTry = true;
            } catch (Throwable cause) {
                Logs.writeLog(" !--! Fatal  error sending HTTP request! !--!\n", Level.SEVERE);
            } finally {
                if (connection != null)
                    connection.disconnect();
            }
        }
        if(!flagTry){

            /**
             * Показывать окошко с информацией о том, что подключиться не удалось, пользователь должен попробовать отправить запрос ещё раз!
             * Но это стоит обговорить!
             *
             * */

        }
    }

    /**
     * Метод getParameters - метода, осуществяющий получение данных о погоде путём отправления HTTTP запроса и осуществяющий парсинг ответа, в случае успеха
     */
    public WeatherParameters getParameters() throws IOException {
        if (countTime.getMonth() > 7 && countTime.getDay() > EndDay - 1) {
            Logs.writeLog(" -- Error, out of available time! --\n"+ "Last day: "+ EndDay+"\n", Level.WARNING);
            /*
             * Возможно сделать более красиво
             */
            return null;
        }

        if (countTime.getHour() >= 24) {
            countTime.setDay(countTime.getDay() + 1);
            countTime.setHour(countTime.getHour() - 24);
            String dataNext = new String(countTime.getYear() + "-" + countTime.getMonth() + "-" + countTime.getDay());
            setDataStart(dataNext);
        }

        if (countTime.getMonth() == 5 && countTime.getDay() >= 31 || countTime.getMonth() == 6 && countTime.getDay() >= 30) {
            countTime.setMonth(countTime.getMonth() + 1);
            countTime.setDay(1);
            String dataNext = new String(countTime.getYear() + "-" + countTime.getMonth() + "-" + countTime.getDay());
            setDataStart(dataNext);
        }

        if (countTime.getHour() < 24) {
            answser = "";
            getMesWeather();
            String find = "\"time\":\"" + (countTime.getHour() * 100) + "\"";
            int index = answser.indexOf(find);

            if (index < 0) {
                Logs.writeLog(" -- Error: unable to find any matching weather location to the query submitted! --\n", Level.WARNING);
                return null;
            }
            answser = answser.substring(index);
            index = answser.indexOf("windspeedKmph");
            index += 16;

            StringBuilder strBuf = new StringBuilder();
            for (int i = index; i < index + 3; i++) {
                if (Character.isDigit(answser.charAt(i))) {
                    strBuf.append(answser.charAt(i));
                }
            }

            int parameter1 = Integer.parseInt(strBuf.toString());
            index += 21;
            strBuf.setLength(0);
            for (int i = index; i < index + 3; i++) {
                if (Character.isDigit(answser.charAt(i))) {
                    strBuf.append(answser.charAt(i));
                }
            }

            int parameter2 = Integer.parseInt(strBuf.toString());
            WeatherParameters result = new WeatherParameters(parameter1, parameter2);
            countTime.setHour(countTime.getHour() + step);
            return result;
        }
        Logs.writeLog(" -- Error at parsing! --\n", Level.WARNING);
        return null;
    }
}