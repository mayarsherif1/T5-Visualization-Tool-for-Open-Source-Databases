package Backend;

import java.util.ArrayList;
import java.util.List;

public class KDTree extends GenericTree {
    private KDNode root;
    public KDTree(){
        this.root=null;
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
        if(node== null){
            return new KDNode(point);
        }
        int dimension = depth%2;
        if((dimension==0&& point.getX()<node.getPoint().getX())
                ||(dimension==1 && point.getY()<node.getPoint().getY())){
            node.setLeft(insertHelper(node.getLeft(),point,depth+1));

        }else {
            node.setRight(insertHelper(node.getRight(),point,depth+1));
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
}
