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
        Vertex p3 = new Vertex(new doublePoint(23.583336459250834, 121.01895085518997));
        p3.setWeatherInPoint(new WeatherParameters(4, 43));
        expected.addLast(p3);
        Vertex p4 = new Vertex(new doublePoint(23.689918213142793, 120.85925010460528));
        p4.setWeatherInPoint(new WeatherParameters(9, 38));
        expected.addLast(p4);

        doublePoint control = new doublePoint(43.73333, 112.93333);
        MoveBalloonAlgorithm algo = new MoveBalloonAlgorithm(control, 41.38333999999999, 36.36666, false, true, 500);
        doublePoint StartP = new doublePoint(24.0953336, 120.6369979076923);
        LinkedList<Vertex> result = algo.AlgorithmTime(StartP, "2019-06-7", 0, new Time(0, 4, 0), 24);
        Assert.assertEquals(expected, result);

    }

    @Test
    public void test2() throws Exception{
        LinkedList<Vertex> expected = new LinkedList<>();
        Vertex p1 = new Vertex(new doublePoint(26.50112803076923, 128.53166584615383));
        p1.setWeatherInPoint(new WeatherParameters(8, 19));
        expected.addLast(p1);
        Vertex p2 = new Vertex(new doublePoint(26.690959317461072, 128.5604422704091));
        p2.setWeatherInPoint(new WeatherParameters(21, 234));
        expected.addLast(p2);

        doublePoint control = new doublePoint(43.73333, 112.93333);
        MoveBalloonAlgorithm algo = new MoveBalloonAlgorithm(control, 41.38333999999999, 36.36666, false, true, 500);
        doublePoint StartP = new doublePoint(26.50112803076923, 128.53166584615383);
        LinkedList<Vertex> result = algo.AlgorithmTime(StartP, "2019-06-4", 0, new Time(0, 1, 0), 12);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void test3() throws Exception{
        LinkedList<Vertex> expected = new LinkedList<>();
        Vertex p1 = new Vertex(new doublePoint(20.92, 70.54));
        p1.setWeatherInPoint(new WeatherParameters(14, 237));
        expected.addLast(p1);
        Vertex p2 = new Vertex(new doublePoint(20.86, 70.21));
        p2.setWeatherInPoint(new WeatherParameters(15, 261));
        expected.addLast(p2);
        Vertex p3 = new Vertex(new doublePoint(20.51, 70.12));
        p3.setWeatherInPoint(new WeatherParameters(15, 261));
        expected.addLast(p3);
        Vertex p4 = new Vertex(new doublePoint(20.16, 70.03));
        p4.setWeatherInPoint(new WeatherParameters(18, 279));
        expected.addLast(p4);
        Vertex p5 = new Vertex(new doublePoint(19.80, 70.28));
        p5.setWeatherInPoint(new WeatherParameters(15, 271));
        expected.addLast(p5);
        Vertex p6 = new Vertex(new doublePoint(20.05, 70.54));
        p6.setWeatherInPoint(new WeatherParameters(17, 270));
        expected.addLast(p6);
        Vertex p7 = new Vertex(new doublePoint(20.45, 70.47));
        p7.setWeatherInPoint(new WeatherParameters(13, 265));
        expected.addLast(p7);
        Vertex p8 = new Vertex(new doublePoint(20.59, 70.75));
        p8.setWeatherInPoint(new WeatherParameters(17, 239));
        expected.addLast(p8);
        Vertex p9 = new Vertex(new doublePoint(20.98, 70.85));
        p9.setWeatherInPoint(new WeatherParameters(16, 256));
        expected.addLast(p9);
        Vertex p10 = new Vertex(new doublePoint(20.97, 70.46));
        p10.setWeatherInPoint(new WeatherParameters(15, 268));
        expected.addLast(p10);
        Vertex p11 = new Vertex(new doublePoint(20.76, 70.17));
        p11.setWeatherInPoint(new WeatherParameters(13, 278));
        expected.addLast(p11);
        Vertex p12 = new Vertex(new doublePoint(20.77, 70.48));
        p12.setWeatherInPoint(new WeatherParameters(15, 266));
        expected.addLast(p12);
        Vertex p13 = new Vertex(new doublePoint(20.59, 70.79));
        p13.setWeatherInPoint(new WeatherParameters(19, 260));
        expected.addLast(p13);
        Vertex p14 = new Vertex(new doublePoint(20.26, 71.10));
        p14.setWeatherInPoint(new WeatherParameters(23, 258));
        expected.addLast(p14);

        doublePoint control = new doublePoint(5.37, 65.40);
        MoveBalloonAlgorithm algo = new MoveBalloonAlgorithm(control, 27.183329999999998, 25.71666, true, true, 500);
        doublePoint StartP = new doublePoint(20.92, 70.54);
        LinkedList<Vertex> result = algo.AlgorithmTime(StartP, "2019-06-21", 0, new Time(0, 7, 0), 12);
        Assert.assertEquals(expected, result);
    }


    @Test
    public void test4() throws Exception{
        LinkedList<Vertex> expected = new LinkedList<>();
        Vertex p1 = new Vertex(new doublePoint(20.92, 70.54));
        p1.setWeatherInPoint(new WeatherParameters(14, 237));
        expected.addLast(p1);
        Vertex p2 = new Vertex(new doublePoint(20.40, 70.00));
        p2.setWeatherInPoint(new WeatherParameters(14, 237));
        expected.addLast(p2);

        doublePoint control = new doublePoint(5.37, 65.40);
        MoveBalloonAlgorithm algo = new MoveBalloonAlgorithm(control, 27.183329999999998, 25.71666, true, true, 500);
        doublePoint StartP = new doublePoint(20.92, 70.54);
        doublePoint EndP = new doublePoint(20.40,70.00);
        LinkedList<Vertex> result = algo.AlgorithmEndPoint(StartP, EndP, "2019-06-21", 0, 24, 1.0);
        System.out.println(result.size());
        Assert.assertEquals(expected, result);
    }

    @Test
    public void test5() throws Exception{
        LinkedList<Vertex> expected = new LinkedList<>();
        Vertex p1 = new Vertex(new doublePoint(34.56, 136.87));
        p1.setWeatherInPoint(new WeatherParameters(3, 5));
        expected.addLast(p1);
        Vertex p2 = new Vertex(new doublePoint(34.58 , 136.80));
        p2.setWeatherInPoint(new WeatherParameters(8, 27));
        expected.addLast(p2);
        System.out.println(expected.size());
        Vertex p3 = new Vertex(new doublePoint(34.52 , 136.98));
        p3.setWeatherInPoint(new WeatherParameters(6, 55));
        expected.addLast(p3);
        System.out.println(expected.size());
        Vertex p4 = new Vertex(new doublePoint(34.53 , 136.84));
        p4.setWeatherInPoint(new WeatherParameters(13, 151));
        expected.addLast(p4);
        Vertex p5 = new Vertex(new doublePoint(34.83 , 136.90));
        p5.setWeatherInPoint(new WeatherParameters(9, 49));
        expected.addLast(p5);
        Vertex p6 = new Vertex(new doublePoint(34.90 , 136.70));
        p6.setWeatherInPoint(new WeatherParameters(9, 55));
        expected.addLast(p6);
        Vertex p8 = new Vertex(new doublePoint(34.90 , 136.48));
        p8.setWeatherInPoint(new WeatherParameters(6, 98));
        expected.addLast(p8);
        Vertex p9 = new Vertex(new doublePoint(34.78 , 136.40));
        p9.setWeatherInPoint(new WeatherParameters(13, 305));
        expected.addLast(p9);
        Vertex p10 = new Vertex(new doublePoint(34.48 , 136.32));
        p10.setWeatherInPoint(new WeatherParameters(6, 67));
        expected.addLast(p10);
        Vertex p7 = new Vertex(new doublePoint(34.95 , 136.49));
        p7.setWeatherInPoint(new WeatherParameters(6, 67));
        expected.addLast(p7);

        doublePoint control = new doublePoint(43.73333, 112.93333);
        MoveBalloonAlgorithm algo = new MoveBalloonAlgorithm(control, 41.38333999999999, 36.36666, false, true, 500);
        doublePoint StartP = new doublePoint(34.56, 136.87);
        doublePoint EndP = new doublePoint(34.95 , 136.49);
        LinkedList<Vertex> result = algo.AlgorithmEndPoint(StartP, EndP,"2019-06-3", 0, 12, 1.0);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void test6() throws Exception{
        LinkedList<Vertex> expected = new LinkedList<>();
        Vertex p1 = new Vertex(new doublePoint(41.93 , 18.12));
        p1.setWeatherInPoint(new WeatherParameters(6, 8));
        expected.addLast(p1);
        Vertex p2 = new Vertex(new doublePoint(41.89 , 18.40));
        p2.setWeatherInPoint(new WeatherParameters(8, 4));
        expected.addLast(p2);
        System.out.println(expected.size());
        Vertex p3 = new Vertex(new doublePoint(41.64 , 18.11));
        p3.setWeatherInPoint(new WeatherParameters(9, 2));
        expected.addLast(p3);
        System.out.println(expected.size());
        Vertex p4 = new Vertex(new doublePoint(41.46 , 18.51));
        p4.setWeatherInPoint(new WeatherParameters(6, 45));
        expected.addLast(p4);
        Vertex p5 = new Vertex(new doublePoint(41.61 , 18.75));
        p5.setWeatherInPoint(new WeatherParameters(11, 154));
        expected.addLast(p5);
        Vertex p6 = new Vertex(new doublePoint(40.63 , 19.13));
        p6.setWeatherInPoint(new WeatherParameters(11, 154));
        expected.addLast(p6);

        doublePoint control = new doublePoint(36.07 , 13.43);
        MoveBalloonAlgorithm algo = new MoveBalloonAlgorithm(control, 16.450000000000003, 12.100000000000001, true, true, 500);
        doublePoint StartP = new doublePoint(41.93 , 18.12);
        doublePoint EndP = new doublePoint(40.63 , 19.13);
        LinkedList<Vertex> result = algo.AlgorithmEndPoint(StartP, EndP,"2019-05-17", 0, 24, 1.0);
        Assert.assertEquals(expected, result);
    }
}
