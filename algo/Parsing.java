import java.io.*;
import java.net.*;

public class Parsing {
    private static Parsing instance;
    private static int step;
    private Time countTime;
    /**
     * Приватный конструктор и метод getInstance реализуют паттерн Singleton
     * */
    public static Parsing getInstance(String data, int step, int startHour){
        if(instance==null){
        instance = new Parsing(data,step,startHour);}
        return  instance;
    }
    private Parsing(String data, int step, int startHour){
        countTime = new Time(data, startHour);
        this.step = step;
        setDataStart(data);
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
    private static final String timeReload = "tp=1";
    HttpURLConnection connection = null;
        String answser;

    public void setDataStart(String dataStart) {
        StringBuilder str = new StringBuilder("date=");
        this.dataStart = str.append(dataStart).toString();
    }

    public void setLocation(double x, double y) {
        StringBuilder str = new StringBuilder("q=");
        this.location = str.append(x+","+y).toString();
    }

    public void setLocation(String location) {
        StringBuilder str = new StringBuilder("q=");
        this.location = str.append(location).toString();
    }

    /**
     * Отравление HTTP запроса на сервер API для получения необходимой информации
     * */
    private void getMesWeather() {
        String request = new String(source+key+"&"+location+"&"+format+"&"+dataStart+"&"+timeReload);
        try {
            connection = (HttpURLConnection) new URL(request).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(1250);
            connection.setReadTimeout(1250);
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

    /**
     * Метод getParameters - метода, осуществяющий получение данных о погоде путём отправления HTTTP запроса и осуществяющий парсинг ответа, в случае успеха
     * */
    public WeatherPrameters getParameters(){
        if(countTime.getMonth()>7&&countTime.getDay()>4){
            /*
            * Возможно сделать более красиво
            * */
            return null;
        }
        if(countTime.getHour()>=24){
            countTime.setDay(countTime.getDay()+1);
            countTime.setHour(countTime.getHour()-24);
            String dataNext = new String(countTime.getYear()+"-"+countTime.getMonth()+"-"+countTime.getDay());
            setDataStart(dataNext);
        }
        if(countTime.getMonth()==5&&countTime.getDay()>=31||countTime.getMonth()==6&&countTime.getDay()>=30){
            countTime.setMonth(countTime.getMonth()+1);
            countTime.setDay(1);
            String dataNext = new String(countTime.getYear()+"-"+countTime.getMonth()+"-"+countTime.getDay());
            setDataStart(dataNext);
        }
        if(countTime.getHour()<24){
          answser = "";
          getMesWeather();
            String find = "\"time\":\"" + (countTime.getHour()*100) + "\"";
            int index = answser.indexOf(find);
            if(index<0) return null;
            answser = answser.substring(index);
            index += 16;
            StringBuilder strBuf = new StringBuilder();
            for(int i = index;i<index+3;i++){
                if(Character.isDigit(answser.charAt(i))){
                strBuf.append(answser.charAt(i));}
            }
            int parameter1 = Integer.parseInt(strBuf.toString());
            index += 21;
            strBuf.setLength(0);
            for(int i = index;i<index+3;i++) {
                if (Character.isDigit(answser.charAt(i))) {
                    strBuf.append(answser.charAt(i));
                }
            }
            int parameter2 = Integer.parseInt(strBuf.toString());
            WeatherPrameters result = new WeatherPrameters(parameter1, parameter2);
            countTime.setHour(countTime.getHour()+step);
            return result;
    }
        return null;
    }
}
