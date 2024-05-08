package Frontend;

import Backend.HashTable.Bucket;
import Backend.HashTable.LinearHashingIndex;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.styles.ShapeNodeStyle;
import com.yworks.yfiles.view.GraphComponent;
import com.yworks.yfiles.view.Pen;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LinearHashTableGUI extends JFrame {
    private LinearHashingIndex hashingIndex;
    private GraphComponent graphComponent;

    public LinearHashTableGUI(LinearHashingIndex hashingIndex) {
        this.hashingIndex = hashingIndex;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Linear HashTable Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        graphComponent = new GraphComponent();
        getContentPane().add(graphComponent);
        updateGraph();
    }


    private void updateGraph() {
        IGraph graph = graphComponent.getGraph();
        graph.clear();
        List<Bucket> buckets = hashingIndex.getBuckets();

        ShapeNodeStyle nodeStyle = new ShapeNodeStyle();
        nodeStyle.setPaint(Color.LIGHT_GRAY);
        nodeStyle.setPen(new Pen(Color.BLACK, 2));
        ShapeNodeStyle overflowStyle = new ShapeNodeStyle();
        overflowStyle.setPaint(Color.ORANGE);
        overflowStyle.setPen(new Pen(Color.BLACK, 2));

        double xPosition = 0;
        double yPosition = 0;
        double nodeWidth = 100;
        double nodeHeight = 50;
        double horizontalSpacing = 20;
        int bucketcount =0;
        for (Bucket bucket : buckets) {
            RectD bounds = new RectD(xPosition, yPosition, nodeWidth, nodeHeight);
            INode mainNode = graph.createNode(bounds);
            graph.setStyle(mainNode, nodeStyle);
            graph.addLabel(mainNode, "Bucket "+ bucketcount +": " +bucket.toString());
            bucketcount++;
            if (bucket.hasOverflow()) {
                INode overflowNode = graph.createNode(new RectD(xPosition, yPosition + nodeHeight + 10, nodeWidth, nodeHeight));
                graph.setStyle(overflowNode, overflowStyle);
                graph.addLabel(overflowNode, "Overflow: " + bucket.getOverflowContents());
                graph.createEdge(mainNode, overflowNode);
            }
            xPosition += nodeWidth + horizontalSpacing;
        }
        graphComponent.fitGraphBounds();
        graphComponent.updateUI();
    }

    public void visualize() {
        updateGraph();
        setVisible(true);
    }

    public static void main(String[] args) {
        LinearHashingIndex hashingIndex = new LinearHashingIndex(2, 0.8);
        hashingIndex.insert(3);
        hashingIndex.insert(12);
        hashingIndex.insert(5);
        hashingIndex.insert(11);
        hashingIndex.insert(2);
        hashingIndex.insert(13);
        hashingIndex.insert(7);
        hashingIndex.insert(9);
        hashingIndex.insert(1);


        //hashingIndex.debugRehash();

        LinearHashTableGUI visualizer = new LinearHashTableGUI(hashingIndex);
        visualizer.visualize();
        visualizer.updateGraph();
    }

    public GraphComponent getGraphComponent() {
        return graphComponent;
    }
}
