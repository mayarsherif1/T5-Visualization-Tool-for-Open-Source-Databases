package Backend.QuadTree;

import Backend.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuadTreeNode {
    public Rectangle bounds;
    public List<Backend.Point> points;
    public QuadTreeNode[] children;
    public int depth;
    public boolean isLeaf;
    public QuadTreeNode NE,NW,SE,SW;

    public QuadTreeNode(Rectangle bounds, int depth) {
        this.points = new ArrayList<>();
        this.children = new QuadTreeNode[4];
        this.bounds = bounds;
        this.depth = depth;
        this.isLeaf=true;
    }

    void insert(Backend.Point point, int maxDepth) {
        if (isLeaf) {
            if (depth >= maxDepth) {
                points.add(point);
            } else {
                points.add(point);
                if (points.size() >= 2) {
                    subdivide();
                    isLeaf = false;
                    List<Backend.Point> newPoint = new ArrayList<>(points);
                    points.clear();
                    for (Backend.Point p : newPoint) {
                        insertIntoChild(p, maxDepth);
                    }
                    //points.clear();
                }

            }


        }
        else{
            insertIntoChild(point, maxDepth);
        }
    }



    private void insertIntoChild(Backend.Point point, int maxDepth) {
        if (NE.bounds.contains(point.getX(), point.getY())) {
            NE.insert(point, maxDepth);
        } else if (NW.bounds.contains(point.getX(), point.getY())) {
            NW.insert(point, maxDepth);
        } else if (SE.bounds.contains(point.getX(), point.getY())) {
            SE.insert(point, maxDepth);
        } else if (SW.bounds.contains(point.getX(), point.getY())) {
            SW.insert(point, maxDepth);
        }
    }

    void subdivide() {
        int newWidth = bounds.width / 2;
        int newHeight = bounds.height / 2;
        int x = bounds.x;
        int y = bounds.y;
        NE = new QuadTreeNode(new Rectangle(x + newWidth, y, newWidth, newHeight), depth + 1);
        NW = new QuadTreeNode(new Rectangle(x, y, newWidth, newHeight), depth + 1);
        SE = new QuadTreeNode(new Rectangle(x + newWidth, y + newHeight, newWidth, newHeight), depth + 1);
        SW = new QuadTreeNode(new Rectangle(x, y + newHeight, newWidth, newHeight), depth + 1);
    }

    public boolean delete(Backend.Point point, int maxDepth) {
        if (!bounds.contains(point.getX(),point.getY())) {
            return false;
        }
        if (isLeaf) {
            return points.remove(point);
        } else {
            if (NE != null && NE.bounds.contains(point.getX(),point.getY())) {
                return NE.delete(point, maxDepth);
            } else if (NW != null && NW.bounds.contains(point.getX(),point.getY())) {
                return NW.delete(point, maxDepth);
            } else if (SE != null && SE.bounds.contains(point.getX(),point.getY())) {
                return SE.delete(point, maxDepth);
            } else if (SW != null && SW.bounds.contains(point.getX(),point.getY())) {
                return SW.delete(point, maxDepth);
            }
        }
        return false;
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
    @Override
    public String toString() {
        if (points.isEmpty()) {
            return "Empty";
        } else {
            StringBuilder sb = new StringBuilder();
            for (Point p : points) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(String.format("(%d, %d)", p.getX(), p.getY()));
            }
            return sb.toString();
        }
    }
}
