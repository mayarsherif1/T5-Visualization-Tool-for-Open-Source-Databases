package Backend;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuadTree {
    private final int MAX_CAPACITY =4;
    private int level;
    private List<Point> points;
    private Rectangle bounds;
    private QuadTree[] nodes;

    public QuadTree(int level, Rectangle bounds){
        this.level=level;
        this.bounds=bounds;
        this.points=new ArrayList<>();
        this.nodes= new QuadTree[4]; // NE,NW,SE,SW
    }

    public void split(){
        int subWidth= (int)(bounds.width/2);
        int subHeight = (int)(bounds.height/2);
        int x= bounds.x;
        int y = bounds.y;
        nodes[0]= new QuadTree(level+1,new Rectangle(x+subWidth, y,subWidth, subHeight));
        nodes[1]= new QuadTree(level+1, new Rectangle(x,y,subWidth,subHeight));
        nodes[2]= new QuadTree(level+1, new Rectangle(x+subWidth,y+subHeight,subWidth,subHeight));
        nodes[3]=new QuadTree(level+1, new Rectangle(x,y+subHeight,subWidth,subHeight));
    }

    private int getIndex(Point p){
        int index =-1;
        double midPointx = bounds.x+(bounds.width/2);
        double midPointy = bounds.y+(bounds.height/2);
        boolean topQuad = (p.getY()< midPointy);
        boolean bottomQuad =(p.getY()> midPointy);
        if(p.getX() < midPointx){
            if(topQuad){
                index =1;
            } else if (bottomQuad) {
                index=3;
            }
        } else if (p.getX()> midPointx) {
            if(topQuad){
                index =0;
            } else if (bottomQuad) {
                index =2;
            }

        }
        return index;
    }

    public void insert(Point p){
        if(nodes[0]!=null){
            int index = getIndex(p);
            if (index != -1){
                nodes[index].insert(p);
                return;
            }
        }
        points.add(p);
        if(points.size()>MAX_CAPACITY){
            if(nodes[0]==null){
                split();
            }
            for (int i =0; i< points.size();i++){
                int index = getIndex(points.get(i));
                if(index!=-1){
                    nodes[index].insert(points.remove(i));
                }
            }
        }
    }

    //find points in specific range
    public void query(Rectangle range, List<Point> found){
        if(!bounds.intersects(range)){
            return;
        }
        for (Point p: points){
            if(range.contains(p.getX(),p.getY())){
                found.add(p);
            }
        }
        if (nodes[0]==null){
            return;
        }
        for (int i=0; i< nodes.length; i++){
            nodes[i].query(range,found);
        }
    }

}
