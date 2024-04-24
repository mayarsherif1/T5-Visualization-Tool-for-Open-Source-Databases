//package Frontend;
//
//import Backend.BinaryInteger;
//import Backend.DataType;
//import Backend.HashTable.LinearHashTable;
//import com.yworks.yfiles.geometry.RectD;
//import com.yworks.yfiles.graph.IGraph;
//import com.yworks.yfiles.graph.INode;
//import com.yworks.yfiles.graph.labelmodels.ILabelModelParameter;
//import com.yworks.yfiles.graph.labelmodels.InteriorLabelModel;
//import com.yworks.yfiles.view.GraphComponent;
//
//import javax.swing.*;
//import java.util.Map;
//
//public class LHashtableGUI extends JFrame {
//
//    private GraphComponent graphComponent;
//    private IGraph graph;
//    private LinearHashTable hashTable;
//    private static final double ENTRY_WIDTH = 100.0;
//    private static final double ENTRY_HEIGHT = 50.0;
//    private static final double VERTICAL_GAP = 10.0;
//
//    public LHashtableGUI(LinearHashTable hashTable) {
//        setTitle("HashTable Visualization");
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        graphComponent = new GraphComponent();
//        getContentPane().add(graphComponent);
//        graph = graphComponent.getGraph();
//        this.hashTable = hashTable;
//        visualizeHashTable();
//
//        setVisible(true);
//        graphComponent.revalidate();
//        graphComponent.repaint();
//    }
//
//    public void addRecord(BinaryInteger key) {
//        SwingUtilities.invokeLater(() -> {
//            hashTable.addToHashTable(key);
//            visualizeHashTable();
//        });
//    }
//
//    private void visualizeHashTable() {
//        graph.clear();
//        INode[] bucketNodes = new INode[hashTable.getBuckets().size()];
//
//        for (int i = 0; i < hashTable.getBuckets().size(); i++) {
//            bucketNodes[i] = visualizeBucket(hashTable.getBuckets().get(i), i);
//        }
//        applyLayout();
//    }
//
//    private INode visualizeBucket(LinearHashTable.Bucket bucket, int index) {
//        int bucketSize = bucket.getBucketSize();
//        double startY = index * (ENTRY_HEIGHT + VERTICAL_GAP);
//
//        // Create the bucket node
//        RectD bucketRect = new RectD(0, startY, ENTRY_WIDTH, ENTRY_HEIGHT);
//        INode bucketNode = graph.createNode(bucketRect);
//        graph.addLabel(bucketNode, "Bucket " + index, InteriorLabelModel.CENTER);
//
//        // Starting Y position for entries below the bucket
//        startY += ENTRY_HEIGHT + VERTICAL_GAP;
//
//        int entryIndex = 0;
//        for (Map.Entry<DataType, DataType> entry : bucket.getAllEntries()) {
//            RectD entryRect;
//            if (index == 2 && entryIndex >= bucketSize) {
//                // For bucket 2, place overflow entries vertically
//                entryRect = new RectD(
//                        0,
//                        startY + (entryIndex - bucketSize) * (ENTRY_HEIGHT + VERTICAL_GAP),
//                        ENTRY_WIDTH,
//                        ENTRY_HEIGHT
//                );
//            } else {
//                // Horizontal placement for other entries
//                entryRect = new RectD(
//                        ENTRY_WIDTH * (entryIndex + 1),
//                        startY,
//                        ENTRY_WIDTH,
//                        ENTRY_HEIGHT
//                );
//            }
//            INode entryNode = graph.createNode(entryRect);
//            graph.addLabel(entryNode, entry.getKey().toString(), InteriorLabelModel.CENTER);
//            graph.createEdge(bucketNode, entryNode);
//            entryIndex++;
//        }
//
//        return bucketNode;
//    }
//
//    private ILabelModelParameter createLabelModelParameter() {
//        return InteriorLabelModel.CENTER;
//    }
//
//    private void applyLayout() {
////        HierarchicLayout layout = new HierarchicLayout();
////        graph.applyLayout(layout);
//        graphComponent.fitGraphBounds();
//        graphComponent.updateUI();
//        graphComponent.validate();
//        graphComponent.repaint();
//
//    }
//
//    public static void main(String[] args) {
//        LinearHashTable hashTable = new LinearHashTable(0.8f, 2);
//        LHashtableGUI visualization = new LHashtableGUI(hashTable);
//        visualization.addRecord(new BinaryInteger(Integer.parseInt("0011", 2)));
//        visualization.addRecord(new BinaryInteger(Integer.parseInt("1100", 2)));
//        visualization.addRecord(new BinaryInteger(Integer.parseInt("0101", 2)));
//        visualization.addRecord(new BinaryInteger(Integer.parseInt("1011",2)));
//        visualization.addRecord(new BinaryInteger(Integer.parseInt("0010", 2)));
//        visualization.addRecord(new BinaryInteger(Integer.parseInt("1101", 2)));
//        visualization.addRecord(new BinaryInteger(Integer.parseInt("0111",2)));
//        visualization.addRecord(new BinaryInteger(Integer.parseInt("1001",2)));
//        visualization.addRecord(new BinaryInteger(Integer.parseInt("0001",2)));
//        visualization.setVisible(true);
//    }
//}
