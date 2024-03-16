package Frontend;

import Backend.BPlusTree;
import Backend.BPlusTreeNode;
import Backend.Node;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.layout.hierarchic.HierarchicLayout;
import com.yworks.yfiles.view.GraphComponent;

import javax.swing.*;

public class BPlusTreeGUI extends JFrame {
    private BPlusTree BPlusTree;
    private GraphComponent graphComponent;

    public BPlusTreeGUI(){
        this.BPlusTree= new BPlusTree();
        this.graphComponent= new GraphComponent();
        initializeBTreeVis();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("B+ Tree Visualization");
        setSize(800,600);
        setLocationRelativeTo(null);


    }

    private void initializeBTreeVis() {
        IGraph graph = graphComponent.getGraph();
        if(BPlusTree.getRoot()!=null){
            createGraphForTree(BPlusTree.getRoot(),graph,null);
        }

        HierarchicLayout layout = new HierarchicLayout();
        layout.setNodeToNodeDistance(30);
        layout.setMinimumLayerDistance(50);
        graph.applyLayout(layout);
        add(graphComponent);
        pack();


    }

    private INode createGraphForTree(BPlusTreeNode node, IGraph graph, INode parent) {
        System.out.println("creating graph node for: "+ node);
        System.out.println("node keys: "+ node.getKeys());

        double width = 50+ node.getKeys().size()*10;
        double height = 30;


        INode newNode = graph.createNode(new RectD(0,0,width,height));
        graph.addLabel(newNode,node.getKeys().toString());

        if(parent!=null){
            graph.createEdge(parent,newNode);
        }

        for (Node child : node.getChildren()){
            createGraphForTree((BPlusTreeNode) child,graph,newNode);
        }

        return newNode;

    }

    //will be used after insert/delete
    private void refreshVis(){
        SwingUtilities.invokeLater(()->{
            IGraph graph= graphComponent.getGraph();
            graph.clear();
            if (BPlusTree.getRoot()!=null){
                createGraphForTree(BPlusTree.getRoot(),graph,null);
            }
            HierarchicLayout layout= new HierarchicLayout();
            layout.setNodeToNodeDistance(30);
            layout.setMinimumLayerDistance(50);
            graph.applyLayout(layout);
            graphComponent.updateUI();

        });

    }

    public static void main(String [] args){
        SwingUtilities.invokeLater(()->{
            BPlusTreeGUI bPlusTreeGUI = new BPlusTreeGUI();
            bPlusTreeGUI.populateTree();
            bPlusTreeGUI.refreshVis();
            bPlusTreeGUI.setVisible(true);

        });
    }

    private void populateTree() {
        System.out.println("B+ tree populate sample");
        BPlusTree.insert(12);
        BPlusTree.insert(8);
        BPlusTree.insert(1);
        BPlusTree.insert(23);
        BPlusTree.insert(5);
        BPlusTree.insert(7);
        BPlusTree.insert(2);
        BPlusTree.insert(28);
        BPlusTree.insert(9);
        BPlusTree.insert(18);
        BPlusTree.insert(24);
        BPlusTree.insert(40);
        BPlusTree.insert(48);



        refreshVis();
        System.out.println("after insertion");

    }

}
