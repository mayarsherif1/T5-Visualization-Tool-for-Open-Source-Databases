package Frontend;

import Backend.KDNode;
import Backend.KDTree;
import Backend.Point;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.geometry.SizeD;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.styles.ShapeNodeShape;
import com.yworks.yfiles.graph.styles.ShapeNodeStyle;
import com.yworks.yfiles.layout.LayoutExecutor;
import com.yworks.yfiles.layout.tree.TreeLayout;
import com.yworks.yfiles.view.GraphComponent;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;

public class KDTreeGUI extends JFrame {
    private GraphComponent graphComponent;
    private KDTree kdTree;
    private String labelX = "X";
    private String labelY = "Y";

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
        JTextField xField = new JTextField(10);
        JTextField yField = new JTextField(10);


        initializeGraph();
    }

    private void initializeGraph() {
        IGraph graph = graphComponent.getGraph();
        visualizeKDNode(graph, kdTree.getRoot(), null,0);
        applyTreeLayout(graphComponent);

    }

    private void applyTreeLayout(GraphComponent graphComponent) {
        TreeLayout treeLayout = new TreeLayout();

        LayoutExecutor layoutExecutor = new LayoutExecutor(graphComponent, treeLayout);
        layoutExecutor.setDuration(Duration.ofMillis(500));
        layoutExecutor.start();
    }

    private void visualizeKDNode(IGraph graph, KDNode kdNode, INode parentNode, int depth){
        if (kdNode == null) return;
        ShapeNodeStyle nodeStyle = new ShapeNodeStyle();
        nodeStyle.setShape(ShapeNodeShape.ELLIPSE);
        nodeStyle.setPaint(Color.WHITE);
        SizeD size = new SizeD(40,20);
        nodeStyle.setKeepingIntrinsicAspectRatioEnabled(false);

        INode yNode = graph.createNode(new RectD(0,0, size.width,size.height), nodeStyle);
        graph.setStyle(yNode, nodeStyle);
        Point point = kdNode.getPoint();
        String label = depth % 2 ==0 ? point.getLabelX() + ": " + point.getX() : point.getLabelY() + ": " + point.getY();

        graph.addLabel(yNode, label);

        if (parentNode != null) {
            graph.createEdge(parentNode, yNode);
        }

        visualizeKDNode(graph, kdNode.getLeft(), yNode, depth+1);
        visualizeKDNode(graph, kdNode.getRight(), yNode, depth+1);
    }

    public static void main(String[] args) {

        KDTree kdTree = new KDTree();
        Point point1 = new Point(2, 3, "X", "Y");
        Point point2 = new Point(5, 2, "X", "Y");
        Point point3 = new Point(4, 4, "X", "Y");
        Point point4 = new Point(1, 3, "X", "Y");
        Point point5 = new Point(2, 8, "X", "Y");
        Point point6 = new Point(6, 1, "X", "Y");
        Point point7 = new Point(0, 2, "X", "Y");
        Point point8 = new Point(0, 1, "X", "Y");
        Point point9 = new Point(1,4, "X", "Y");
        Point point10 = new Point(3,2, "X", "Y");
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
