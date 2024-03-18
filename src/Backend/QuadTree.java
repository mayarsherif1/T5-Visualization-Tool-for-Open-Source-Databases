package Backend;

import java.awt.*;

public class QuadTree {
    private QuadTreeNode root;
    private int maxDepth;
    private Comparable data;

    public QuadTree(Rectangle boundary, int maxDepth) {
        this.root = new QuadTreeNode(boundary, 1);
        this.maxDepth = maxDepth;
    }
    public QuadTree(Rectangle boundary, Comparable data) {
        this.root = new QuadTreeNode(boundary, 1);
        this.maxDepth = maxDepth;
    }
    public void insert(Point point) {
        root.insert(point, maxDepth);
    }
    public QuadTreeNode getRoot() {
        return root;
    }

}
