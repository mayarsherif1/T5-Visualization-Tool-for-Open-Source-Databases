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
    private BPlusTree bPlusTree;
    private GraphComponent graphComponent;

    public BPlusTreeGUI() {
        this.bPlusTree = new BPlusTree();
        this.graphComponent = new GraphComponent();
        initializeBTreeVis();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("B+ Tree Visualization");
        setSize(800, 600);
        setLocationRelativeTo(null);


    }

    private void initializeBTreeVis() {
        IGraph graph = graphComponent.getGraph();
        if (bPlusTree.getRoot() != null) {
            createGraphForTree(bPlusTree.getRoot(), graph, null);
        }

        HierarchicLayout layout = new HierarchicLayout();
        layout.setNodeToNodeDistance(30);
        layout.setMinimumLayerDistance(50);
        graph.applyLayout(layout);
        add(graphComponent);
        pack();
    }


    private INode createGraphForTree(BPlusTreeNode node, IGraph graph, INode parent) {
        System.out.println("creating graph node for: " + node);
        System.out.println("node keys: " + node.getKeys());
        double width = 50 + node.getKeys().size() * 10;
        double height = 30;
        INode newNode = graph.createNode(new RectD(0, 0, width, height));
        graph.addLabel(newNode, node.getKeys().toString());

        if (parent != null) {
            graph.createEdge(parent, newNode);
        }

        for (Node child : node.getChildren()) {
            createGraphForTree((BPlusTreeNode) child, graph, newNode);
        }

        return newNode;

    }

    private void refreshVis() {
        SwingUtilities.invokeLater(() -> {
            IGraph graph = graphComponent.getGraph();
            graph.clear();
            if (bPlusTree.getRoot() != null) {
                createGraphForTree(bPlusTree.getRoot(), graph, null);
            }
            HierarchicLayout layout = new HierarchicLayout();
            layout.setNodeToNodeDistance(30);
            layout.setMinimumLayerDistance(50);
            graph.applyLayout(layout);
            graphComponent.updateUI();
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BPlusTreeGUI frame = new BPlusTreeGUI();
            frame.populateTree();
            frame.setVisible(true);
        });
    }

    private void populateTree() {
        System.out.println("B+ tree populate sample");
        bPlusTree.insert(12);
        bPlusTree.insert(8);
        bPlusTree.insert(1);
        bPlusTree.insert(23);
        bPlusTree.insert(5);
 //       bPlusTree.insert(7);
//        bPlusTree.insert(2);
//        bPlusTree.insert(28);
//        bPlusTree.insert(9);
//        bPlusTree.insert(18);
//        bPlusTree.insert(24);
//        bPlusTree.insert(40);
//        bPlusTree.insert(48);

        refreshVis();
        System.out.println("after insertion");

    }

}