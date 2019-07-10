package UnitTest;

import algo.MoveBalloonAlgorithm;
import dateStruct.*;
import org.junit.*;

import java.util.LinkedList;
public class JTest {

    @Test
    public void test1() throws Exception{
        LinkedList<Vertex> expected = new LinkedList<>();
        Vertex p1 = new Vertex(new doublePoint(24.0953336, 120.6369979076923));
        p1.setWeatherInPoint(new WeatherParameters(10, 204));
        expected.addLast(p1);
        Vertex p2 = new Vertex(new doublePoint(23.625240468987716, 120.73401568016244));
        p2.setWeatherInPoint(new WeatherParameters(6, 8));
        expected.addLast(p2);
        System.out.println(expected.size());
        Vertex p3 = new Vertex(new doublePoint(23.583336459250834, 121.01895085518997));
        p3.setWeatherInPoint(new WeatherParameters(4, 43));
        expected.addLast(p3);
        System.out.println(expected.size());
        Vertex p4 = new Vertex(new doublePoint(23.689918213142793, 120.85925010460528));
        p4.setWeatherInPoint(new WeatherParameters(9, 38));
        expected.addLast(p4);
        System.out.println(expected.size());

        doublePoint control = new doublePoint(43.73333, 112.93333);
        MoveBalloonAlgorithm algo = new MoveBalloonAlgorithm(control, 41.38333999999999, 36.36666, false, true, 500);
        doublePoint StartP = new doublePoint(24.0953336, 120.6369979076923);
        LinkedList<Vertex> result = algo.AlgorithmTime(StartP, "2019-06-7", 0, new Time(0, 4, 0), 24);
        System.out.println(result.size());
        Assert.assertEquals(expected, result);

    }

    @Test
    public void test2() throws Exception{
        LinkedList<Vertex> expected = new LinkedList<>();
        Vertex p1 = new Vertex(new doublePoint(26.50112803076923, 128.53166584615383));
        p1.setWeatherInPoint(new WeatherParameters(8, 19));
        p1.setMapCoordinate(new doublePoint(342.0, 245.0));
        expected.addLast(p1);
        Vertex p2 = new Vertex(new doublePoint(26.690959317461072, 128.5604422704091));
        p2.setMapCoordinate(new doublePoint(345.0, 245.0));
        p2.setWeatherInPoint(new WeatherParameters(21, 234));
        expected.addLast(p2);
        System.out.println(expected.size());

        doublePoint control = new doublePoint(43.73333, 112.93333);
        MoveBalloonAlgorithm algo = new MoveBalloonAlgorithm(control, 41.38333999999999, 36.36666, false, true, 500);
        doublePoint StartP = new doublePoint(26.50112803076923, 128.53166584615383);
        LinkedList<Vertex> result = algo.AlgorithmTime(StartP, "2019-06-4", 0, new Time(0, 1, 0), 12);
        System.out.println(result.size());
        Assert.assertEquals(expected, result);
    }
}
