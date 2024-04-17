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

public class BPlusTreeGUI<T extends Comparable<T>> extends JFrame {
    private final BPlusTree<T> bPlusTree;
    private GraphComponent graphComponent;

    public BPlusTreeGUI() {
        this.bPlusTree = new BPlusTree<>(3);
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
        HashMap<BPlusTreeNode<T>, INode> nodeToINodeMapping = new HashMap<>();
        graph.applyLayout(layout, layoutData);

        add(graphComponent);
        pack();
    }

    private void createGraphForTree(BPlusTreeNode<T> node, IGraph graph, INode parent) {
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

        if (!node.isLeaf()) {
            List<BPlusTreeNode<T>> sortedChildren = new ArrayList<>(node.getChildren());
            sortedChildren.sort(Comparator.comparing(o -> ((Comparable) o.getKeys().get(0))));

            for (BPlusTreeNode<T> child : node.getChildren()) {
                System.out.println("child in createGraphForTree: " + child);
                System.out.println("child keys: " + child.getKeys());
                System.out.println("sorted children: " + sortedChildren);
                createGraphForTree(child, graph, newNode);
            }
        }
    }

    public void refreshVis() {
        SwingUtilities.invokeLater(() -> {
            IGraph graph = graphComponent.getGraph();
            graph.clear();
            if (bPlusTree.getRoot() != null) {
                createGraphForTree(bPlusTree.getRoot(), graph, null);
            }
            TreeLayout layout = new TreeLayout();
            TreeLayoutData layoutData = new TreeLayoutData();
            HashMap<BPlusTreeNode<T>, INode> nodeToINodeMapping = new HashMap<>();
            graph.applyLayout(layout, layoutData);

            add(graphComponent);
        });
    }
    public GraphComponent getGraphComponent() {
        return graphComponent;
    }

    private void populateTree() {
//        System.out.println("B+ tree populate sample");
//        bPlusTree.insert((T) Integer.valueOf(12));
//        System.out.println("after insertion of 12"+ bPlusTree.toString());
//        bPlusTree.insert((T) Integer.valueOf(8));
//        System.out.println("after insertion of 8"+ bPlusTree.toString());
//
//        bPlusTree.insert((T) Integer.valueOf(1));
//        System.out.println("after insertion of 1"+ bPlusTree.toString());
//
//        bPlusTree.insert((T) Integer.valueOf(23));
//        System.out.println("after insertion of 23"+ bPlusTree.toString());
//
//        bPlusTree.insert((T) Integer.valueOf(5));
//        System.out.println("after insertion of 5"+ bPlusTree.toString());
//
//        bPlusTree.insert((T) Integer.valueOf(7));
//        bPlusTree.insert((T) Integer.valueOf(2));
//        bPlusTree.insert((T) Integer.valueOf(28));
//        bPlusTree.insert((T) Integer.valueOf(9));
//        bPlusTree.insert((T) Integer.valueOf(18));
//        bPlusTree.insert((T) Integer.valueOf(24));
//        bPlusTree.insert((T) Integer.valueOf(40));
//        bPlusTree.insert((T) Integer.valueOf(48));

        //String testing
//
//        System.out.println("B+ tree populate sample");
//        bPlusTree.insert((T) "apple");
//        bPlusTree.insert((T) "pear");
//        System.out.println("after insertion of apple: " + bPlusTree.toString());
//        bPlusTree.insert((T) "banana");
//        System.out.println("after insertion of banana: " + bPlusTree.toString());
//
//        bPlusTree.insert((T) "orange");
//        System.out.println("after insertion of orange: " + bPlusTree.toString());
//
//        bPlusTree.insert((T) "grape");
//        System.out.println("after insertion of grape: " + bPlusTree.toString());
//
//        bPlusTree.insert((T) "strawberry");
//        System.out.println("after insertion of strawberry: " + bPlusTree.toString());


        refreshVis();
        bPlusTree.printTree();
        System.out.println("after insertion");

    }

    //testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BPlusTreeGUI<Integer> frame = new BPlusTreeGUI<>();
            frame.populateTree();
            frame.setVisible(true);
        });
    }

    public BPlusTree<T> getBPlusTree() {
        return bPlusTree;
    }
}
