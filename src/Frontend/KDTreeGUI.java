package Frontend;

import Backend.KDNode;
import Backend.KDTree;
import Backend.Point;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.layout.LayoutExecutor;
import com.yworks.yfiles.layout.tree.TreeLayout;
import com.yworks.yfiles.view.GraphComponent;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;

public class KDTreeGUI extends JFrame {
    private GraphComponent graphComponent;
    private KDTree kdTree;

    public KDTreeGUI(KDTree kdTree) {
        this.kdTree = kdTree;
        initializeComponents();
    }

    private void initializeComponents() {
        graphComponent = new GraphComponent();
        getContentPane().add(graphComponent, BorderLayout.CENTER);

        setTitle("KD-Tree Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeGraph();
    }

    private void initializeGraph() {
        IGraph graph = graphComponent.getGraph();
        visualizeKDNode(graph, kdTree.getRoot(), null);
        applyTreeLayout(graphComponent);

    }

    private void applyTreeLayout(GraphComponent graphComponent) {
        TreeLayout treeLayout = new TreeLayout();

        LayoutExecutor layoutExecutor = new LayoutExecutor(graphComponent, treeLayout);
        layoutExecutor.setDuration(Duration.ofMillis(500));
        layoutExecutor.start();
    }

    private void visualizeKDNode(IGraph graph, KDNode kdNode, INode parentNode) {
        if (kdNode == null) return;

        INode yNode = graph.createNode();
        graph.addLabel(yNode, kdNode.getPoint().toString());


        if (parentNode != null) {
            graph.createEdge(parentNode, yNode);
        }

        // Recursively visualize left and right children
        visualizeKDNode(graph, kdNode.getLeft(), yNode);
        visualizeKDNode(graph, kdNode.getRight(), yNode);
    }

    public static void main(String[] args) {

        KDTree kdTree = new KDTree();
        Point point1 = new Point(2, 3);
        Point point2 = new Point(5, 2);
        Point point3 = new Point(4, 4);
        Point point4 = new Point(1, 3);
        Point point5 = new Point(2, 8);
        Point point6 = new Point(6, 1);
        Point point7 = new Point(0, 2);
        Point point8 = new Point(0, 1);
        Point point9 = new Point(1,4);
        Point point10 = new Point(3,2);
        kdTree.insert(point1);
        kdTree.insert(point2);
        kdTree.insert(point3);
        kdTree.insert(point4);
        kdTree.insert(point5);
        kdTree.insert(point6);
        kdTree.insert(point7);
        kdTree.insert(point8);
        kdTree.insert(point9);
        kdTree.insert(point10);
        kdTree.printKDTree(kdTree.getRoot(), 0);

        SwingUtilities.invokeLater(() -> {
            KDTreeGUI frame = new KDTreeGUI(kdTree);
            frame.setVisible(true);
        });
    }
}
