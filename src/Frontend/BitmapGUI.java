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

public class BitmapGUI extends JFrame {
    private GraphComponent graphComponent;
    private JButton setButton;
    private JButton clearButton;
    private JTextField bitField;
    private BitmapIndex bitmap;

    public BitmapGUI(BitmapIndex bitmap) {
        this.bitmap = bitmap;
        initializeUI();
        visualizeBitmap();
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
            bitmap.set(index);
            visualizeBitmap();
            JOptionPane.showMessageDialog(this, "Bit set at index: " + index, "Operation Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearBit() {
        try {
            int index = Integer.parseInt(bitField.getText());
            bitmap.clear(index);
            visualizeBitmap();
            JOptionPane.showMessageDialog(this, "Bit cleared at index: " + index, "Operation Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void visualizeBitmap() {
        IGraph graph = graphComponent.getGraph();
        graph.clear();
        int nodeWidth = Math.max(50, 800 / bitmap.size());

        for (int i = 0; i < bitmap.size(); i++) {
            boolean isSet = bitmap.isSet(i);
            PointD location = new PointD(i * nodeWidth, 100);
            INode node = graph.createNode(location, new ShinyPlateNodeStyle(), isSet ? "1" : "0");

            ShinyPlateNodeStyle style = new ShinyPlateNodeStyle();
            style.setPaint(isSet ? Color.GREEN : Color.GRAY);
            style.setPen(new Pen(Color.BLACK, 2));
            graph.setStyle(node, style);

            graph.addLabel(node, String.valueOf(i), InteriorLabelModel.CENTER);
        }
        graphComponent.fitGraphBounds();
        graphComponent.updateUI();
    }


    public GraphComponent getGraphComponent() {
        return graphComponent;
    }


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            BitmapGUI frame = new BitmapGUI(bitmap);
//            frame.setVisible(true);
//        });
//    }
}
