package Frontend;

import Backend.BRIN.BrinBlock;
import Backend.BRIN.BrinIndex;
import Backend.BRIN.BrinLevel;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.styles.PolylineEdgeStyle;
import com.yworks.yfiles.graph.styles.ShapeNodeShape;
import com.yworks.yfiles.graph.styles.ShapeNodeStyle;
import com.yworks.yfiles.layout.hierarchic.HierarchicLayout;
import com.yworks.yfiles.view.GraphComponent;
import com.yworks.yfiles.view.Pen;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BrinGUI extends JFrame {
    private BrinIndex brinIndex;
    private GraphComponent graphComponent;

    public BrinGUI(BrinIndex brinIndex) {
        this.brinIndex = brinIndex;
        initializeComponents();
        visualizeBrinBlocks();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setTitle("BRIN Index Visualization");

        graphComponent = new GraphComponent();
        add(graphComponent, BorderLayout.CENTER);
        JButton insertButton = new JButton("Insert value");
        JTextField valueField = new JTextField(10);

        JPanel controlPanel = new JPanel();
        controlPanel.add(valueField);

        controlPanel.add(insertButton);
        add(controlPanel, BorderLayout.PAGE_START);

        insertButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(valueField.getText().trim());
                brinIndex.insertValue(value);
                visualizeBrinBlocks();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid integer.", "Input Error", JOptionPane.ERROR_MESSAGE);


            }
        });


    }




    private void applyLayout(IGraph graph) {
        PolylineEdgeStyle edgeStyle = new PolylineEdgeStyle();
        edgeStyle.setPen(new Pen(Color.BLACK, 2));
        graph.getEdgeDefaults().setStyle(edgeStyle);
        HierarchicLayout layout = new HierarchicLayout();
        graph.applyLayout(layout);
    }

    private void visualizeBrinBlocks() {
        IGraph graph = graphComponent.getGraph();
        graph.clear();

        int startX = 50;
        int startY = 50;
        int blockWidth = 100;
        int blockHeight = 50;
        int blockGap = 120;
        for (int levelIndex = 0; levelIndex < brinIndex.getLevels().size(); levelIndex++) {
            BrinLevel level = brinIndex.getLevels().get(levelIndex);
            int x = startX;
            int y = startY + levelIndex * (blockHeight + 10);
            List<BrinBlock> blocks = level.getBlocks();
            for (int i = blocks.size() - 1; i >= 0; i--) {
                BrinBlock block = blocks.get(i);
                INode node = createNode(graph, block, x, y);
                x += blockWidth + blockGap;
            }
        }

        applyLayout(graph);
        graphComponent.fitGraphBounds();
        graphComponent.updateUI();
    }


    private INode createNode(IGraph graph, BrinBlock block, int x, int y) {
        ShapeNodeStyle nodeStyle = new ShapeNodeStyle();
        nodeStyle.setShape(ShapeNodeShape.RECTANGLE);
        nodeStyle.setPaint(Color.LIGHT_GRAY);
        nodeStyle.setPen(new Pen(Color.BLACK, 2));

        RectD bounds = new RectD(x, y, 100, 30);
        INode node = graph.createNode(bounds, nodeStyle, block);
        graph.addLabel(node, String.format("[%d, %d]", block.getMin(), block.getMax()));
        return node;
    }


    public static void main(String[] args) {
        BrinIndex brinIndex = new BrinIndex();
        SwingUtilities.invokeLater(() -> {
            BrinGUI frame = new BrinGUI(brinIndex);
            frame.setVisible(true);
        });
    }

    public GraphComponent getGraphComponent() {
        return graphComponent;
    }
}
