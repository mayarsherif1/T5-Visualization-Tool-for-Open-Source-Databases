package Backend;

public class QuadTreeNode {
    private QuadTreeNode NW, NE, SE, SW;
    private Point point;
    private Comparable value;

    public QuadTreeNode(Point point, Comparable value){
        this.point= point;
        this.value=value;
    }


}
