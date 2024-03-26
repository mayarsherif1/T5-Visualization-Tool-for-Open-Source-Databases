package Backend;

public class Point implements Comparable<Point>{
    private int x;
    private int y;
    private String labelX;
    private String labelY;
    public Point(int x, int y){
        this.x=x;
        this.y=y;
    }
    public Point(int x, int y, String labelX, String labelY){
        this.x=x;
        this.y=y;
        this.labelX=labelX;
        this.labelY=labelY;
    }

    public String getLabelX() {
        return labelX;
    }
    public String getLabelY() {
        return labelY;
    }

    public void setLabelX(String labelX) {
        this.labelX = labelX;
    }
    public void setLabelY(String labelY) {
        this.labelY = labelY;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Point p) {
        if (this.x == p.x) {
            return Integer.compare(this.y, p.y);
        }
        return Integer.compare(this.x, p.x);
    }

    public int compareTo(Point p, int level) {
        if(level%2 ==0) {
            return Integer.compare(this.x, p.x);
        }
        return Integer.compare(this.y, p.y);
    }

    @Override
    public String toString() {
        return labelX + ": " + x + ", " + labelY + ": " + y;
    }
    @Override
    public boolean equals(Object o){
        if(this==o){
            return true;
        }
        if(!(o instanceof Point)){
            return false;

        }
        Point point =(Point) o;
        return this.x==point.x && this.y==point.y;
    }
    @Override
    public int hashCode(){
        return 31*x+y;
    }
}
