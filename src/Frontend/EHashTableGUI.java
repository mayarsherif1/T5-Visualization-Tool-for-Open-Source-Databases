package Frontend;

import Backend.HashTable.ExtensibleHashTable;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.styles.ShapeNodeStyle;
import com.yworks.yfiles.view.GraphComponent;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EHashTableGUI extends JFrame {
    private ExtensibleHashTable hashTable;
    private GraphComponent graphComponent;
    private IGraph graph;

    public EHashTableGUI(ExtensibleHashTable hashTable) {
        this.hashTable = hashTable;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("HashTable Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        graphComponent = new GraphComponent();
        getContentPane().add(graphComponent);
        initializeGraph();
    }

    private void initializeGraph() {
        graph = graphComponent.getGraph();
        graph.clear();
        ShapeNodeStyle nodeStyle = new ShapeNodeStyle();
        nodeStyle.setPaint(Color.WHITE);
        graph.getNodeDefaults().setStyle(nodeStyle);
        updateGraphVisualization();
    }

    private void updateGraphVisualization() {
        graph.clear();
        List<List<String>> buckets = hashTable.getBuckets();
        int xPosition = 0;

        for (int i = 0; i < buckets.size(); i++) {
            List<String> bucket = buckets.get(i);
            RectD bucketRect = new RectD(xPosition, 100, 100, 50);
            INode bucketNode = graph.createNode(bucketRect);
            graph.addLabel(bucketNode, "Bucket " + i + ": " + bucket.size() + " items");
            int valueYPosition = 150;
            for (String value : bucket) {
                RectD valueRect = new RectD(xPosition, valueYPosition, 100, 30);
                INode valueNode = graph.createNode(valueRect);
                graph.addLabel(valueNode, value);
                graph.createEdge(bucketNode, valueNode);
                valueYPosition += 40;
            }
            xPosition += 110;
        }
        graphComponent.fitGraphBounds();
        graphComponent.updateUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExtensibleHashTable hashTable = new ExtensibleHashTable(2);
            hashTable.insert(0);
            hashTable.insert(1);
            hashTable.insert(2);
            hashTable.insert(3);
            hashTable.insert(4);
            hashTable.insert(6);
            hashTable.insert(8);

            EHashTableGUI eHashTableGUI = new EHashTableGUI(hashTable);
            eHashTableGUI.setVisible(true);
        });
    }

    public GraphComponent getGraphComponent() {
        return graphComponent;
    }
}
