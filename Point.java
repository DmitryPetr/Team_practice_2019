import org.jetbrains.annotations.NotNull;

/**
 * класс точки
 */

public class Point {

    private double x;
    private double y;

    public double getX() { return x;}
    public double getY() { return y;}

    public Point(double NewX, double NewY){
        x = NewX;
        y = NewY;
    }

    Point(@NotNull Point NewPoint){
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