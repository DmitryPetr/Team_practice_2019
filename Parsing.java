import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Parsing {
    private static Parsing instance;
    private static int countHour;
    private static int countDay;
    private static int countMonth;
    private static int step;
    private int EndDay;
    private int EndMonth;

    /**
     * Приватный конструктор и метод getInstance реализуют паттерн Singleton
     * */
    public static Parsing getInstance(String data, int step, int startHour){
        if(instance==null){
        instance = new Parsing(data,step,startHour);}
        return  instance;
    }
    private Parsing(String data, int step, int startHour){
        countHour = startHour;
        String[] split = data.split("[-]");
        countMonth = Integer.parseInt(split[1]);
        countDay = Integer.parseInt(split[2]);
        this.step = step;
        setDataStart(data);
        setDataEnd(data);
        setLasts(data);
    }

    public void setLasts(String ends) {
        String[] split = ends.split("[-]");
        EndDay = Integer.parseInt(split[2]);
        EndMonth = Integer.parseInt(split[1]);
    }

    /**
     * Параметры
     * source - источник
     * key - ключ(обязательное поле для доступа)
     * location - местоположение
     * format - формат ответа сайта
     * dataStart и dataend -начальные и конечные даты для погоды
     * timeReload - время обновления погоды для конкретного дня
     * */
    private static final String source = "https://api.worldweatheronline.com/premium/v1/past-weather.ashx?";
    private static final String key = "key=b60f705b24864ca6bc891344190307";
    private String location = "q=Moscow";
    private static final String format = "format=json";
    private String dataStart;
    private String dataEnd;
    private static final String timeReload = "tp=1";
    HttpURLConnection connection = null;
        String answser;

    public void setDataStart(String dataStart) {
        StringBuilder str = new StringBuilder("date=");
        this.dataStart = str.append(dataStart).toString();
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDataEnd(String dataEnd) {
        StringBuilder str = new StringBuilder("enddate=");
        this.dataEnd =  str.append(dataEnd).toString();
    }
    /**
     * Отравление HTTP запроса на сервер API для получения необходимой информации
     * */
    private void getMesWeather() {
        String request = new String(source+key+"&"+location+"&"+format+"&"+dataStart+"&"+dataEnd+"&"+timeReload);
        try {
            connection = (HttpURLConnection) new URL(request).openConnection();
            connection.setRequestMethod("GET");
            //connection.setUseCaches(false);
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);
            connection.connect();
            StringBuilder sb = new StringBuilder();

            if(HttpURLConnection.HTTP_OK == connection.getResponseCode()){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while((line = in.readLine())!= null){
                    sb.append(line+"\n");
                }
                }else{
                System.out.println("Fail" + connection.getResponseCode()+ ", "+ connection.getResponseMessage());
                }
            answser = sb.toString();
        } catch (Throwable cause) {
            cause.printStackTrace();
        }finally{
            if (connection != null)
                connection.disconnect();
        }
    }

    public WeatherPrameters getParameters(){
        if(countMonth>7&&countDay>4){
            /*
            * Возможно сделать более красиво
            * */
            System.out.println("Error, no information on this month!");
            return null;
        }
        if(countHour>24){
            countDay++;
            countHour -= 24;
        }
        if(countMonth==5&&countDay>31||countMonth==6&&countDay>30){
            countMonth++;
            countDay = 1;
        }
        if(countDay>EndDay||countMonth>countDay)
            return null;
        if(countHour<24){
          answser = "";
          getMesWeather();
            String find = "\"time\":\"" + (countHour*100) + "\"";
            int index = answser.indexOf(find);
            answser = answser.substring(index);
            index = answser.indexOf("windspeedKmph");
            index += 16;
            StringBuilder strBuf = new StringBuilder();
            for(int i = index;i<index+2;i++)
                strBuf.append(answser.charAt(i));
            int parameter1 = Integer.parseInt(strBuf.toString());
            index += 21;
            strBuf.setLength(0);
            for(int i = index;i<index+3;i++)
                strBuf.append(answser.charAt(i));
            int parameter2 = Integer.parseInt(strBuf.toString());
            WeatherPrameters result = new WeatherPrameters(parameter1, parameter2);
            countHour += step;
            return result;
    }
        return null;
    }


}
