package Backend.KDTree;

import Backend.GenericTree;
import Backend.Point;

import java.util.ArrayList;
import java.util.List;

public class KDTree extends GenericTree {
    private KDNode root;
    public KDTree(){
        this.root=null;
    }

    public KDTree(List<Point> points) {
        this.root = buildTree(points, 0);
    }

    private KDNode buildTree(List<Point> points, int depth) {
        if (points.isEmpty()) {
            return null;
        }
        int dimension = depth % 2;
        points.sort((p1, p2) -> (dimension == 0) ? Integer.compare(p1.getX(), p2.getX()) : Integer.compare(p1.getY(), p2.getY()));
        int medianIndex = points.size() / 2;
        Point medianPoint = points.get(medianIndex);
        KDNode node = new KDNode(medianPoint);


        List<Point> leftSublist = points.subList(0, medianIndex);
        List<Point> rightSublist = points.subList(medianIndex + 1, points.size());
        node.setLeft(buildTree(leftSublist, depth + 1));
        node.setRight(buildTree(rightSublist, depth + 1));
        return node;
    }

    @Override
    public void insert(Comparable x) {
        if(!(x instanceof Point)){
            throw new IllegalArgumentException("KDTree only supports Point Objects");
            
        }
        Point point= (Point) x;
        root= insertHelper(root,point,0);

    }

    private KDNode insertHelper(KDNode node, Point point, int depth) {
        if (node == null) {
            System.out.println("Inserting new node at depth " + depth + ": " + point);
            return new KDNode(point);
        }

        int cd = depth % 2;
        int cmp = (cd == 0) ? Integer.compare(point.getX(), node.getPoint().getX())
                : Integer.compare(point.getY(), node.getPoint().getY());
        if (cmp < 0) {
            System.out.println("Going left at depth " + depth + ", point: " + point);
            node.setLeft(insertHelper(node.getLeft(), point, depth + 1));
        } else {
            System.out.println("Going:  right at depth " + depth + ", point: " + point);
            node.setRight(insertHelper(node.getRight(), point, depth + 1));
        }
        return node;
    }


    @Override
    public void delete(Comparable x) {
        if(!(x instanceof Point)){
            throw new IllegalArgumentException("KDTree only supports Point Objects");
        }
        root = deleteHelper(root, (Point)x, 0);


    }

    private KDNode deleteHelper(KDNode node, Point point, int depth) {
        if(node==null){
            return null;
        }
        int dimension = depth%2;
        if (point.equals(node.getPoint())){
            if (node.isLeaf()){
                return null;
            }
            if (dimension==0){
                Point nextPoint = findMin(node.getRight(),depth,dimension);
                node.setPoint(nextPoint);
                node.setRight(deleteHelper(node.getRight(),nextPoint,depth+1));

            }
            else {
                Point nextPoint= findMin(node.getRight(),depth,dimension);
                node.setPoint(nextPoint);
                node.setRight(deleteHelper(node.getRight(),nextPoint,depth+1));

            }
        } else if ((dimension==0&& point.getX()<node.getPoint().getX())|| (dimension==1&& point.getY()<node.getPoint().getY())) {
            node.setLeft(deleteHelper(node.getLeft(),point,depth+1));

        }
        else {
            node.setRight(deleteHelper(node.getRight(),point,depth+1));
        }
        return node;
    }

    private Point findMin(KDNode node, int depth, int dimension) {
        if (node==null){
            return null;
        }
        int k= depth%2;
        if (k==dimension){
            if (node.getLeft()==null){
                return node.getPoint();
            }
            return findMin(node.getLeft(),depth+1,dimension);
        }
        Point minLeft = findMin(node.getLeft(),depth+1,dimension);
        Point minRight = findMin(node.getRight(),depth+1,dimension);
        Point minPoint = node.getPoint();
        if(minLeft != null && (dimension==0? minLeft.getX()< minPoint.getX(): minLeft.getY()< minPoint.getY())){
            minPoint= minLeft;
        }
        if(minRight!= null && (dimension==0? minRight.getX()<minPoint.getX(): minRight.getY()< minPoint.getY())){
            minPoint= minRight;
        }
        return minPoint;
    }

    @Override
    public boolean search(Comparable x) {
        if(!(x instanceof Point)){
            return false;
        }

            return searchHelper(root, (Point)x,0);
    }

    private boolean searchHelper(KDNode node, Point point, int depth) {
        if(node==null){
            return false;
        }
        if(point.compareTo(node.getPoint())==0){
            return true;

        }
        int dimension= depth%2;
        if((dimension==0&& point.getX()<node.getPoint().getX())||
                (dimension==1&&point.getY()<node.getPoint().getY())){
            return searchHelper(node.getLeft(),point,depth+1);

        }else {
            return searchHelper(node.getRight(),point,depth+1);
        }
    }

    @Override
    public void update(Comparable oldValue, Comparable newValue) {
        if(!(oldValue instanceof Point)|| !(newValue instanceof Point)){
            throw new IllegalArgumentException("Only Point instances are supported.");
        }
        delete(oldValue);
        insert(newValue);

    }

    @Override
    public List<Comparable> traverse() {
        List<Comparable> points = new ArrayList<>();
        traverseHelper(root, points);
        return points;
    }

    private void traverseHelper(KDNode node, List<Comparable> points) {
        if(node==null){
            return;
        }
        points.add(node.getPoint());
        traverseHelper(node.getLeft(),points);
        traverseHelper(node.getRight(),points);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public KDNode getRoot(){
        return this.root;
    }
    public void printKDTree(KDNode node, int depth) {
        if (node == null) return;
        String indent = " ".repeat(depth * 2);

        System.out.println(indent + node.getPoint().toString());
        printKDTree(node.getLeft(), depth + 1);
        printKDTree(node.getRight(), depth + 1);
    }

}
