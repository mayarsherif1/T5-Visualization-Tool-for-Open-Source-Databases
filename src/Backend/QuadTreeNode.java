package Backend;

public class QuadTreeNode extends Node {
    private QuadTreeNode NW, NE, SE, SW;
    private Point point;
    private Comparable value;

    public QuadTreeNode(Point point, Comparable value){
        super(null);
        this.point= point;
        this.value=value;
    }


}
