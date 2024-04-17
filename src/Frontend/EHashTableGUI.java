package Frontend;

import Backend.HashTable.EBDirectory;
import Backend.HashTable.EBIndex;
import com.yworks.yfiles.geometry.PointD;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.styles.ShapeNodeStyle;
import com.yworks.yfiles.view.GraphComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class EHashTableGUI {

    private JFrame frame;
    private GraphComponent graphComponent;
    private JTextField inputField, capacityField;
    private JButton addButton, initButton;
    private IGraph graph;
    private INode[] bucketNodes;
    private EBDirectory directory;

    public EHashTableGUI() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Hashtable Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        graphComponent = new GraphComponent();
        frame.add(graphComponent, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        capacityField = new JTextField(5);
        initButton = new JButton("Set Capacity and Initialize");
        initButton.addActionListener(this::initButtonClicked);

        inputField = new JTextField(10);
        inputField.setEnabled(false);
        addButton = new JButton("Add");
        addButton.setEnabled(false);
        addButton.addActionListener(this::addButtonClicked);
        inputPanel.add(new JLabel("Bucket Capacity:"));
        inputPanel.add(capacityField);
        inputPanel.add(initButton);
        inputPanel.add(new JLabel("Add Value:"));
        inputPanel.add(inputField);
        inputPanel.add(addButton);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initButtonClicked(ActionEvent e) {
        try {
            String input = capacityField.getText().trim();
            if (!input.isEmpty()) {
                int capacity = Integer.parseInt(input);
                directory = new EBDirectory(8, 2, capacity);
                initializeGraph();
                inputField.setEnabled(true);
                addButton.setEnabled(true);
                capacityField.setEnabled(false);
                initButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a valid capacity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid number format. Please enter a valid integer for capacity.", "Number Format Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addButtonClicked(ActionEvent e) {
        String input = inputField.getText().trim();
        if (!input.isEmpty()) {
            addInputToHashTable(input);
            inputField.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter some text to add.", "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void initializeGraph() {
        graph = graphComponent.getGraph();
        ShapeNodeStyle nodeStyle = new ShapeNodeStyle();
        nodeStyle.setPaint(Color.WHITE);
        graph.getNodeDefaults().setStyle(nodeStyle);
        updateGraphVisualization();

//        int totalNumberOfBuckets = directory.length();
//        int digits = calculateBinaryDigits(totalNumberOfBuckets);
//
////        String[] buckets = {"000", "001", "010", "011", "100", "101", "110", "111"};
//        bucketNodes = new INode[totalNumberOfBuckets];
//
//        for (int i = 0; i < totalNumberOfBuckets; i++) {
//            Point2D location = new Point2D.Double(100, i * 100);
//            bucketNodes[i] = graph.createNode(PointD.fromPoint2D(location));
//            String bucketLabel = formatBucketLabel(i, digits);
//            graph.addLabel(bucketNodes[i], bucketLabel);
//        }
    }


    private void addInputToHashTable(String input) {
        String[] values = new String[]{input};
        EBIndex newIndex = new EBIndex(values);
        System.out.println("Adding index with values: " + Arrays.toString(values));
        boolean bucketFull = directory.addIndex(newIndex);
        if (bucketFull) {
            int bucketIndex = directory.getBucketIndex(newIndex);
            handleBucketSplit(bucketIndex);
        }
        updateGraphVisualization();
    }
    private void handleBucketSplit(int bucketIndex) {
        directory.splitBucket(bucketIndex);
        System.out.println("Bucket " + bucketIndex + " split due to overflow.");
    }

    private void updateGraphVisualization() {
        graph.clear();
        int xPosition = 100;
        int yPositionIncrement = 100;
        int xEntryOffset = 300;
        int yEntryIncrement = 30;
        int totalNumberOfBuckets = directory.length();
        int digits = directory.getGlobalDepth();
        bucketNodes = new INode[totalNumberOfBuckets];

        for (int i = 0; i < totalNumberOfBuckets; i++) {
            PointD bucketPosition = new PointD(xPosition, i * yPositionIncrement);
            bucketNodes[i]= graph.createNode(bucketPosition);
            String bucketLabel = formatBucketLabel(i, digits);
            graph.addLabel(bucketNodes[i], bucketLabel);
            System.out.println("Visualizing Bucket: " + bucketLabel);
            Iterable<EBIndex> indexes = directory.getIndexesForBucket(i);
            int entryIndex = 0;

            for (EBIndex index : indexes) {
                if (index != null && !index.isDeleted()) {
                    PointD entryPosition = new PointD(xPosition + xEntryOffset, bucketPosition.getY() + entryIndex * yEntryIncrement);
                    INode entryNode = graph.createNode(entryPosition);
                    graph.addLabel(entryNode, index.toString());
                    graph.createEdge(bucketNodes[i], entryNode);
                    entryIndex++;
                    System.out.println("Adding index to graph: " + index.toString());
                }
            }
        }
        graphComponent.fitGraphBounds();
        graphComponent.updateUI();
    }

    private int calculateBinaryDigitsForFilledBuckets() {
        int filledBuckets = calculateFilledBuckets();
        if(filledBuckets <= 0) {
            return 1;
        }
        return (int) Math.ceil(Math.log(filledBuckets) /Math.log(2));
    }

    private int calculateFilledBuckets() {
        return directory.getNumberOfFilledBuckets();
    }


    private int calculateBinaryDigits(int numberOfBuckets) {
        return (int) Math.ceil(Math.log(numberOfBuckets) / Math.log(2));
    }
    private String formatBucketLabel(int bucketIndex, int totalDigits) {
        if(totalDigits<=0){
            totalDigits=1;
        }
        return String.format("%" + totalDigits + "s", Integer.toBinaryString(bucketIndex)).replace(' ', '0');
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(EHashTableGUI::new);
    }
}
