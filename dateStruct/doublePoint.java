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
    public boolean equals(Object obj){
        if(this==obj){
            return true;
        }
        if(obj instanceof dateStruct.doublePoint){
            dateStruct.doublePoint otherObj = (dateStruct.doublePoint)obj;
            if(otherObj.x==x){
                if(otherObj.y==y) return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        int result = 31;
        long longBits1 = Double.doubleToLongBits(x);
        long longBits2 = Double.doubleToLongBits(y);
        result = 11 * result + (int)(longBits1 - (longBits1 >>> 32)) + (int)(longBits2 - (longBits2 >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "X: "+x+" Y: "+y;
    }

}
