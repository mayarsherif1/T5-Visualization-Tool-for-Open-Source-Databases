package Backend;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class QuadTreeNode {
    public Rectangle bounds;
    public List<Point> points = new ArrayList<>();
    public QuadTreeNode[] children = new QuadTreeNode[4];
    int depth;
    boolean isLeaf;
    QuadTreeNode NE,NW,SE,SW;

    public QuadTreeNode(Rectangle bounds, int depth) {
        this.bounds = bounds;
        this.depth = depth;
    }

    void insert(Point point, int maxDepth) {
        if (depth == maxDepth) {
            points.add(point);
            return;
        }

        if (children[0] == null) {
            subdivide();
        }

        for (QuadTreeNode child : children) {
            if (child.bounds.contains(point.getX(),point.getY())) {
                child.insert(point, maxDepth);
                return;
            }
        }

        points.add(point);
    }

    void subdivide() {
        int newWidth = bounds.width / 2;
        int newHeight = bounds.height / 2;

        children[0] = new QuadTreeNode(new Rectangle(bounds.x, bounds.y, newWidth, newHeight), depth + 1);
        children[1] = new QuadTreeNode(new Rectangle(bounds.x + newWidth, bounds.y, newWidth, newHeight), depth + 1);
        children[2] = new QuadTreeNode(new Rectangle(bounds.x, bounds.y + newHeight, newWidth, newHeight), depth + 1);
        children[3] = new QuadTreeNode(new Rectangle(bounds.x + newWidth, bounds.y + newHeight, newWidth, newHeight), depth + 1);
    }


    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public QuadTreeNode getNE() {
        return this.NE;
    }

    public QuadTreeNode getNW() {
        return this.NW;
    }

    public QuadTreeNode getSE() {
        return this.SE;
    }

    public QuadTreeNode getSW() {
        return this.SW;
    }
}
