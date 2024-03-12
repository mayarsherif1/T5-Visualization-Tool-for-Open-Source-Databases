package Backend;

public class KDNode extends Node {
    private Point point;
    private KDNode left=null;
    private KDNode right =null;
    private int level;

    public KDNode(Point point) {
        super(null);
        this.point=point;
    }
    public Point getPoint() {
        return point;
    }

    public KDNode getLeft() {
        return left;
    }

    public void setLeft(KDNode left) {
        this.left = left;
    }

    public KDNode getRight() {
        return right;
    }

    public void setRight(KDNode right) {
        this.right = right;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "KDNode{" + "point=(" + point.getX() + ", " + point.getY() + ")" + '}';
    }

    public void setPoint(Point point) {
        this.point=point;

    }
}
