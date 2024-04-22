package Frontend;

import Backend.GRID.Grid;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.ILabel;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.styles.Arrow;
import com.yworks.yfiles.graph.styles.ArrowType;
import com.yworks.yfiles.graph.styles.PolylineEdgeStyle;
import com.yworks.yfiles.graph.styles.ShapeNodeStyle;
import com.yworks.yfiles.view.GraphComponent;
import com.yworks.yfiles.view.Pen;

import javax.swing.*;
import java.awt.*;

public class GridGUI extends JPanel {
    private GraphComponent graphComponent;
    private final int numRows = 4;
    private final int numCols = 3;
    private final int cellWidth = 100;
    private final int cellHeight = 100;
    private final int bucketWidth = 50;
    private final int bucketHeight = 60;
    private final int bucketPadding = 10;

    private Grid backendGrid;


    public GridGUI() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        graphComponent = new GraphComponent();
        add(graphComponent, BorderLayout.CENTER);
        backendGrid = new Grid(numRows, numCols);
        JButton addButton = new JButton("Add Value to Bucket 0");
        addButton.addActionListener(e -> addValueToBucket(0, 42));
        this.add(addButton, BorderLayout.SOUTH);
        backendGrid.initializeBuckets(numRows);
        initializeGraph();
    }


    private void addValueToBucket(int bucketIndex, int value) {
        if (bucketIndex < 0 || bucketIndex >= backendGrid.getBucketsSize()) {
            JOptionPane.showMessageDialog(this, "Invalid bucket index", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        backendGrid.addToBucket(bucketIndex, value);
        IGraph graph = graphComponent.getGraph();
        INode bucketNode = findBucketNode(bucketIndex);
        if (bucketNode != null) {
            ILabel label = getFirstLabelOrDefault(bucketNode, null);
            if (label != null) {
                String updatedLabelText = label.getText() + " " + value;
                graph.setLabelText(label, updatedLabelText);
            } else {
                graph.addLabel(bucketNode, String.valueOf(value));
            }
        }
        graphComponent.repaint();
    }
    private ILabel getFirstLabelOrDefault(INode node, ILabel defaultLabel) {
        if (node.getLabels().size() > 0) {
            return node.getLabels().getItem(0);
        } else {
            return defaultLabel;
        }
    }

    private INode findBucketNode(int bucketIndex) {
        IGraph graph = graphComponent.getGraph();
        for (INode node : graph.getNodes()) {
            if (node.getTag() instanceof Integer && (Integer) node.getTag() == bucketIndex) {
                return node;
            }
        }
        return null;
    }

    private void initializeGraph() {
        IGraph graph = graphComponent.getGraph();
        ShapeNodeStyle cellStyle = new ShapeNodeStyle();
        cellStyle.setPen(new Pen(Color.DARK_GRAY, 1.5));
        ShapeNodeStyle bucketStyle = new ShapeNodeStyle();
        bucketStyle.setPen(new Pen(Color.GRAY, 1.5));

        INode[][] cells = new INode[numRows][numCols];
        INode[] buckets = new INode[numRows];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                cells[row][col] = graph.createNode(new RectD(col * cellWidth, row * cellHeight, cellWidth, cellHeight), cellStyle);
            }
            buckets[row] = graph.createNode(new RectD(numCols * cellWidth + 50, row * (bucketHeight + bucketPadding), bucketWidth, bucketHeight), bucketStyle);
            buckets[row].setTag(row);
        }

        createEdges(graph, cells, buckets);
    }
    private void createEdges(IGraph graph, INode[][] cells, INode[] buckets) {
        PolylineEdgeStyle edgeStyle = new PolylineEdgeStyle();
        edgeStyle.setPen(new Pen(Color.BLACK, 2));
        edgeStyle.setTargetArrow(new Arrow(ArrowType.DEFAULT, Color.BLACK));

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                graph.createEdge(cells[row][col], buckets[row], edgeStyle);
            }
        }
    }

    public GraphComponent getGraphComponent() {
        return graphComponent;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Grid Index Visualization with yFiles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GridGUI());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
