package dateStruct;

/**
 * стуктура точка основанная на double
 */
public class doublePoint {
    private double x;
    private double y;
    //private doublePoint prevPoint;

    public double getX() { return x;}

    public double getY() { return y;}

    public doublePoint(double X_New, double Y_New){
        x = X_New;
        y = Y_New;
    }

    public doublePoint(doublePoint NewPoint){
        if (NewPoint != null){
            x = NewPoint.getX();
            y = NewPoint.getY();
        }
    }

    @Override
    public String toString() {
        return "X: "+x+" Y: "+y;
    }

}
