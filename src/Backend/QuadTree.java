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
    public void insert(Point point) {
        root.insert(point, maxDepth);
    }
    public QuadTreeNode getRoot() {
        return root;
    }

    public boolean delete(Point point) {
        return root.delete(point, maxDepth);
    }

}
