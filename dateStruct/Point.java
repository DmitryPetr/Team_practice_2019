package dateStruct;

public class Point {
    private double x;
    private double y;
    private Point prevPoint;

    public double getX() { return x;}

    public double getY() { return y;}

    public Point(double X_New, double Y_New){
        x = X_New;
        y = Y_New;
    }

    public Point(Point NewPoint){
        if (NewPoint != null){
            x = NewPoint.getX();
            y = NewPoint.getY();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("x: "+ x+ "\n");
        str.append("y: "+ y+ "\n");
        return str.toString();
    }

}