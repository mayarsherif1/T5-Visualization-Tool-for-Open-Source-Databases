package Frontend;

import Backend.BPlusTree;
import Backend.BPlusTreeNode;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.layout.tree.TreeLayout;
import com.yworks.yfiles.layout.tree.TreeLayoutData;
import com.yworks.yfiles.view.GraphComponent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class BPlusTreeGUI extends JFrame {
    private BPlusTree bPlusTree;
    private GraphComponent graphComponent;

    public BPlusTreeGUI() {
        this.bPlusTree = new BPlusTree(3);
        this.graphComponent = new GraphComponent();
        System.out.println("before initialized B tree Vis");
        initializeBTreeVis();
        System.out.println("after initialized B tree Vis");
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        TreeLayout layout = new TreeLayout();
        TreeLayoutData layoutData = new TreeLayoutData();
        HashMap<BPlusTreeNode, INode> nodeToINodeMapping = new HashMap<>();
        graph.applyLayout(layout, layoutData);

        add(graphComponent);
        pack();

//        HierarchicLayout layout = new HierarchicLayout();
//        layout.setNodeToNodeDistance(30);
//        layout.setMinimumLayerDistance(50);
//        graph.applyLayout(layout);
//        add(graphComponent);
//        pack();
    }


    private void createGraphForTree(BPlusTreeNode node, IGraph graph, INode parent) {
        System.out.println("creating graph node for: " + node);
        System.out.println("node keys: " + node.getKeys());
        double width = 50 + node.getKeys().size() * 10;
        double height = 30;
        INode newNode = graph.createNode(new RectD(0, 0, width, height));
        graph.addLabel(newNode, node.getKeys().toString());

        if (parent != null) {
            graph.createEdge(parent, newNode);
            System.out.println("creating edge between parent: " + parent + " and new node: " + newNode);
        }
        //node.rearrangeChildren();
//        List<Node> children = new ArrayList<>(node.getChildren());

        if (!node.isLeaf()) {
            List<BPlusTreeNode> sortedChildren = new ArrayList<>(node.getChildren());
            sortedChildren.sort(Comparator.comparingInt(o -> o.getKeys().isEmpty() ? Integer.MAX_VALUE : o.getKeys().get(0)));

            for (BPlusTreeNode child : node.getChildren()) {
                System.out.println("child in createGraphForTree: " + child);
                System.out.println("child keys: " + child.getKeys());
                System.out.println("sorted children: " + sortedChildren);
                createGraphForTree(child, graph, newNode);
            }
        }

//        for (Node child : node.getChildren()) {
//            createGraphForTree(child, graph, newNode);
//            System.out.println("child in createGraphForTree: " + child);
//        }

    }

    private void debugTreeStructure(BPlusTreeNode node, String indent) {
        if (node != null) {
            System.out.println(indent + "Node: " + node + ", Keys: " + node.getKeys());
            if (!node.isLeaf()) {
                for (BPlusTreeNode child : node.getChildren()) {
                    debugTreeStructure(child, indent + "    ");
                }
            }
        }
    }
    private void refreshVis() {
        SwingUtilities.invokeLater(() -> {
            IGraph graph = graphComponent.getGraph();
            graph.clear();
            if (bPlusTree.getRoot() != null) {
                //rearrangeTree(bPlusTree.getRoot());
                createGraphForTree(bPlusTree.getRoot(), graph, null);
                //debugTreeStructure(bPlusTree.getRoot(), "");

            }
            //debugTreeStructure(bPlusTree.getRoot(), "");

            TreeLayout layout = new TreeLayout();
            TreeLayoutData layoutData = new TreeLayoutData();
            HashMap<BPlusTreeNode, INode> nodeToINodeMapping = new HashMap<>();
            graph.applyLayout(layout, layoutData);

            add(graphComponent);
            //pack();

//            HierarchicLayout layout = new HierarchicLayout();
//            layout.setNodeToNodeDistance(30);
//            layout.setMinimumLayerDistance(50);
//            layout.setOrthogonalRoutingEnabled(true);
//            graph.applyLayout(layout);
//            graphComponent.updateUI();
        });
    }
    //    private void rearrangeTree(Node node) {
//        if (!node.isLeaf()) {
//            //node.rearrangeChildren();
//            for (Node child : node.getChildren()) {
//                rearrangeTree( child);
//            }
//        }
//    }
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
        System.out.println("after insertion of 12"+ bPlusTree.toString());
        bPlusTree.insert(8);
        System.out.println("after insertion of 8"+ bPlusTree.toString());

        bPlusTree.insert(1);
        System.out.println("after insertion of 1"+ bPlusTree.toString());

        bPlusTree.insert(23);
        System.out.println("after insertion of 23"+ bPlusTree.toString());

        bPlusTree.insert(5);
        System.out.println("after insertion of 5"+ bPlusTree.toString());

        bPlusTree.insert(7);
        bPlusTree.insert(2);
        bPlusTree.insert(28);
        bPlusTree.insert(9);
        bPlusTree.insert(18);
        bPlusTree.insert(24);
        bPlusTree.insert(40);
        bPlusTree.insert(48);

        refreshVis();
        bPlusTree.printTree();
        System.out.println("after insertion");

    }

}