package Frontend;

import Backend.Node;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class TreePanel extends JPanel {
    private Node root;
    double scaleFactor = 1.0;

    public TreePanel(Node root){
        this.root=root;

    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.scale(scaleFactor,scaleFactor);
        if(root!=null){
            drawTree(g2,root,(int)(getWidth()/2/scaleFactor),30,(int)(getWidth()/4/scaleFactor));
        }

    }
    private void drawTree(Graphics g, Node node, int x, int y, int offset) {
        int width =30;
        int height =30;

        g.setColor(Color.BLACK);
        g.fillRect(x-width/2, y-height/2,width,height);
        g.setColor(Color.white);
        g.drawString(node.getData().toString(),x-width/3,y+height/4);
        if(node.getChildren()==null){
            return;
        }
        int newOffset=offset/2;

        List<Node>children = node.getChildren();
        for (int i =0; i<children.size();i++){
            int childX= x-offset+(i*offset/Math.max(1,children.size()-1));
            int childY = y+50+height;
            g.setColor(Color.BLACK);
            g.drawLine(x,y+height/2, childX,childY-height/2);
            g.setColor(Color.black);
            drawTree(g,children.get(i),childX,childY,newOffset);

        }
    }
    public void setRoot(Node root) {
        this.root = root;
    }

    public void setScaleFactor(double scaleFactor){
        this.scaleFactor=scaleFactor;
    }

}
