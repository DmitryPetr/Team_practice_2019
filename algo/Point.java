public class Point {
    private double x;
    private double y;
    private Point prevPoint;

    public double getX() { return x;}

    public double getY() { return y;}

    Point(double X_New, double Y_New, Point prev){
        x = X_New;
        y = Y_New;
        prevPoint = prev;
    }

}
