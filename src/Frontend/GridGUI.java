package Frontend;

import Backend.GRID.Grid;
import com.yworks.yfiles.geometry.PointD;
import com.yworks.yfiles.graph.DefaultGraph;
import com.yworks.yfiles.graph.ILabel;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.view.GraphComponent;

import javax.swing.*;
import java.awt.*;

public class GridGUI extends JFrame {

    private Grid grid;

    public GridGUI(int rows, int columns, int numberOfBuckets) {
        setTitle("GRID Index Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DefaultGraph graph = new DefaultGraph();
        GraphComponent graphComponent = new GraphComponent();
        graphComponent.setSize(getSize());
        graphComponent.setGraph(graph);
        double cellWidth = 50;
        double cellHeight = 50;
        INode[][] nodes = new INode[rows][columns];

        // Create grid nodes
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double x = j * cellWidth + 100;
                double y = i * cellHeight + 100;
                nodes[i][j] = graph.createNode(new PointD(x, y));
                graph.setStyle(nodes[i][j], graphComponent.getGraph().getNodeDefaults().getStyle());
                ILabel label = graph.addLabel(nodes[i][j], "");
                graph.setStyle(nodes[i][j], graph.getNodeDefaults().getStyle());
            }
        }

        // Create edges between cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i < rows - 1) {
                    INode sourceNode = nodes[i][j];
                    INode targetNode = nodes[i + 1][j];
                    graph.createEdge(sourceNode, targetNode);
                }
                if (j < columns - 1) {
                    INode sourceNode = nodes[i][j];
                    INode targetNode = nodes[i][j + 1];
                    graph.createEdge(sourceNode, targetNode);
                }
            }
        }

        // Create buckets below the grid
        double bucketsX = 100;
        double bucketsY = rows * cellHeight + 150;
        for (int i = 0; i < numberOfBuckets; i++) {
            double x = bucketsX + i * cellWidth;
            double y = bucketsY;
            INode bucketNode = graph.createNode(new PointD(x, y));
            graph.setStyle(bucketNode, graph.getNodeDefaults().getStyle());
            ILabel label = graph.addLabel(bucketNode, "Bucket " + i);
        }

        // Initialize the grid
        grid = new Grid(rows, columns);
        grid.initializeBuckets(numberOfBuckets);

        add(graphComponent, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int rows = 5;
            int columns = 5;
            int numberOfBuckets = 3;
            GridGUI visualization = new GridGUI(rows, columns, numberOfBuckets);
            visualization.setVisible(true);
        });
    }
}