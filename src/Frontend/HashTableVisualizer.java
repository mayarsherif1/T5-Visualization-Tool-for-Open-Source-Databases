package Frontend;

import Backend.HashTable.Bucket;
import Backend.HashTable.LinearHashingIndex;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.layout.hierarchic.HierarchicLayout;
import com.yworks.yfiles.view.GraphComponent;

import javax.swing.*;
import java.util.List;

public class HashTableVisualizer extends JFrame {
    private LinearHashingIndex hashingIndex;
    private GraphComponent graphComponent;

    public HashTableVisualizer(LinearHashingIndex hashingIndex) {
        this.hashingIndex = hashingIndex;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Hash Table Visualization with yFiles");
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
        for (int i = 0; i < buckets.size(); i++) {
            Bucket bucket = buckets.get(i);
            double width = 100 + bucket.getRecords().size() * 10;
            double height = 50;
            INode bucketNode = graph.createNode(new RectD(0, 0, width, height));
            String labelContent = "Bucket " + i + ": " + bucket.toString();
            graph.addLabel(bucketNode, labelContent);
        }
        HierarchicLayout layout = new HierarchicLayout();
        graph.applyLayout(layout);
        graphComponent.fitGraphBounds();
        graphComponent.updateUI();
    }

//    private void updatePanel() {
//        panel.removeAll();
//        List<Bucket> buckets = hashingIndex.getBuckets();
//        for (int i = 0; i < buckets.size(); i++) {
//            Bucket bucket = buckets.get(i);
//            String bucketContent = "Bucket " + i + ": " + bucket.toString();
//            JLabel label = new JLabel(bucketContent);
//            panel.add(label);
//        }
//        JLabel utilizationLabel = new JLabel("Utilization: " + String.format("%.2f%%", hashingIndex.calculateUtilization() * 100));
//        panel.add(utilizationLabel);
//        panel.revalidate();
//        panel.repaint();
//    }

    public void visualize() {
        updateGraph();
        setVisible(true);
    }

    public static void main(String[] args) {
        LinearHashingIndex hashingIndex = new LinearHashingIndex(2);

        hashingIndex.insert("0011");
        hashingIndex.insert("1100");
        hashingIndex.insert("0101");
        hashingIndex.insert("1011");
        hashingIndex.insert("0010");
        hashingIndex.insert("1101");
        hashingIndex.insert("0111");
        hashingIndex.insert("1001");
        hashingIndex.insert("0001");

        HashTableVisualizer visualizer = new HashTableVisualizer(hashingIndex);
        visualizer.visualize();
        visualizer.updateGraph();
    }

}
