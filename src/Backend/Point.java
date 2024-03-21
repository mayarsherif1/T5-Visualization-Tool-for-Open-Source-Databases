package Backend;

public class Point implements Comparable<Point>{
    private int x;
    private int y;
    public Point(int x, int y){
        this.x=x;
        this.y=y;
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
        return "(" + x + ", " + y + ")";
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
