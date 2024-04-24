package Frontend;

import Backend.Bitmap.BitmapIndex;
import com.yworks.yfiles.geometry.PointD;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.labelmodels.InteriorLabelModel;
import com.yworks.yfiles.graph.styles.ShinyPlateNodeStyle;
import com.yworks.yfiles.view.GraphComponent;
import com.yworks.yfiles.view.Pen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BitmapGUI extends JFrame {
    private GraphComponent graphComponent;
    private JButton setButton;
    private JButton clearButton;
    private JTextField bitField;
    private BitmapIndex bitmap;

    public BitmapGUI(List<BitmapIndex> bitmaps, List<String> values) {
        setTitle("Bitmap Index Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        graphComponent = new GraphComponent();
        getContentPane().add(graphComponent, BorderLayout.CENTER);

        setupControls();
        visualizeBitmap(bitmaps, values);
    }

    private void initializeUI() {
        setTitle("Bitmap Index Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        graphComponent = new GraphComponent();
        getContentPane().add(graphComponent, BorderLayout.CENTER);

        setupControls();
    }

    private void setupControls() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Adjust spacing for better alignment
        bitField = new JTextField(5);
        setButton = new JButton("Set Bit");
        clearButton = new JButton("Clear Bit");

        controlPanel.add(new JLabel("Bit Index:"));
        controlPanel.add(bitField);
        controlPanel.add(setButton);
        controlPanel.add(clearButton);

        setButton.addActionListener(e -> setBit());
        clearButton.addActionListener(e -> clearBit());

        getContentPane().add(controlPanel, BorderLayout.NORTH);
    }

    private void setBit() {
        try {
            int index = Integer.parseInt(bitField.getText());
            if (index < 0 || index >= bitmap.size()) {
                JOptionPane.showMessageDialog(this, "Index out of bounds. Please enter a valid index.", "Index Error", JOptionPane.ERROR_MESSAGE);
            } else {
                bitmap.set(index);
                visualizeBitmap(Collections.singletonList(bitmap), Collections.singletonList("Current Bitmap"));
                JOptionPane.showMessageDialog(this, "Bit set at index: " + index, "Operation Successful", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearBit() {
        try {
            int index = Integer.parseInt(bitField.getText());
            if (index < 0 || index >= bitmap.size()) {
                JOptionPane.showMessageDialog(this, "Index out of bounds. Please enter a valid index.", "Index Error", JOptionPane.ERROR_MESSAGE);
            } else {
                bitmap.clear(index);
                visualizeBitmap(Collections.singletonList(bitmap), Collections.singletonList("Current Bitmap"));
                JOptionPane.showMessageDialog(this, "Bit cleared at index: " + index, "Operation Successful", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void visualizeBitmap(List<BitmapIndex> bitmaps, List<String> values) {
        IGraph graph = graphComponent.getGraph();
        graph.clear();

        int nodeWidth = 50, nodeHeight = 50, x = 50, y = 100;
        for (int j = 0; j < bitmaps.size(); j++) {
            BitmapIndex bitmap = bitmaps.get(j);
            String value = values.get(j);

            for (int i = 0; i < bitmap.size(); i++) {
                boolean isSet = bitmap.isSet(i);
                PointD location = new PointD(x + i * nodeWidth, y + j * (nodeHeight + 20));
                INode node = graph.createNode(location);
                ShinyPlateNodeStyle style = new ShinyPlateNodeStyle();
                style.setPaint(isSet ? Color.GREEN : Color.GRAY);
                style.setPen(new Pen(Color.BLACK, 2));
                graph.setStyle(node, style);
                graph.addLabel(node, isSet ? "1" : "0", InteriorLabelModel.CENTER);
            }
            y += bitmap.size() * nodeHeight + 20;
        }

        graphComponent.fitGraphBounds();
        graphComponent.updateUI();
    }

    public GraphComponent getGraphComponent() {
        return graphComponent;
    }


    public static void main(String[] args) {
        BitmapIndex bitmap1 = new BitmapIndex(10);
        BitmapIndex bitmap2 = new BitmapIndex(10);
        bitmap1.set(1);
        bitmap1.set(3);
        bitmap1.set(5);
        bitmap1.set(7);

        bitmap2.set(2);
        bitmap2.set(4);
        bitmap2.set(6);
        bitmap2.set(8);

        List<BitmapIndex> bitmaps = new ArrayList<>(Arrays.asList(bitmap1, bitmap2));
        List<String> values = new ArrayList<>(Arrays.asList("Bitmap 1", "Bitmap 2"));
        SwingUtilities.invokeLater(() -> {
            BitmapGUI frame = new BitmapGUI(bitmaps, values);
            frame.setVisible(true);
        });
    }
}
