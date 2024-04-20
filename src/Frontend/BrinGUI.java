package Frontend;

import Backend.BRIN.BrinBlock;
import Backend.BRIN.BrinIndex;
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
import java.util.Comparator;

public class BrinGUI extends JFrame {
    private JFrame frame;
    private JTextArea sqlInputArea;
    private JButton executeButton;
    private JPanel visualizationPanel;
    private BrinIndex brinIndex;
    private GraphComponent graphComponent;


    public BrinGUI(BrinIndex brinIndex) {
        this.brinIndex = brinIndex;
        initializeComponents();
        visualizeBrinBlocks(graphComponent.getGraph());
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
                visualizeBrinBlocks(graphComponent.getGraph());
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

    private void visualizeBrinBlocks(IGraph graph) {
        brinIndex.getBlocks().sort(Comparator.comparingInt(BrinBlock::getMin));
        graph.clear();
        for (BrinBlock block : brinIndex.getBlocks()) {
            INode blockNode = createBlockNode(graph, block);
            INode rangeNode = createRangeNode(graph, block.getMin(), block.getMax());

            graph.createEdge(blockNode, rangeNode);
        }

        HierarchicLayout layout = new HierarchicLayout();
        graph.applyLayout(layout);
        graphComponent.updateUI();
    }

    private INode createBlockNode(IGraph graph, BrinBlock block) {
        ShapeNodeStyle nodeStyle = new ShapeNodeStyle();
        nodeStyle.setShape(ShapeNodeShape.RECTANGLE);
        nodeStyle.setPaint(Color.LIGHT_GRAY);
        nodeStyle.setPen(new Pen(Color.BLACK, 2));
        INode node = graph.createNode();
        graph.setStyle(node, nodeStyle);
        graph.addLabel(node, block.toString());

        return node;
    }

    private INode createRangeNode(IGraph graph, int min, int max) {
        ShapeNodeStyle nodeStyle = new ShapeNodeStyle();
        nodeStyle.setShape(ShapeNodeShape.ELLIPSE);
        nodeStyle.setPaint(Color.WHITE);
        nodeStyle.setPen(new Pen(Color.BLACK, 1));
        INode node = graph.createNode();
        graph.setStyle(node, nodeStyle);
        String label = (min == max) ? "Page " + min : String.format("Pages %d-%d", min, max);
        graph.addLabel(node, label);

        return node;
    }




//    private void visualizeBrinBlocks(IGraph graph) {
//        double xOffset = 0;
//        double yOffset = 0;
//        double xGap = 10;
//        double yGap = 50;
//
//        List<List<BrinBlock>> levels = brinIndex.getLevels();
//        for (List<BrinBlock> level : levels) {
//            xOffset = 0;
//            for (BrinBlock block : level) {
//                ShapeNodeStyle nodeStyle = new ShapeNodeStyle();
//                nodeStyle.setShape(ShapeNodeShape.RECTANGLE);
//                nodeStyle.setPaint(Color.WHITE);
//                SizeD size = new SizeD(60, 30);
//                INode node = graph.createNode(new RectD(xOffset, yOffset, size.width, size.height), nodeStyle);
//                node.setTag(block);
//                String label = String.format("[%d, %d]", block.getMin(), block.getMax());
//                graph.addLabel(node, label);
//                if (block.getParent() != null) {
//                    BrinBlock parentBlock = block.getParent();
//                    INode parentNode = findNodeByBlock(graph, parentBlock);
//                    if (parentNode != null) {
//                        graph.createEdge(parentNode, node);
//                    }
//                }
//
//                xOffset += size.width + xGap;
//            }
//            yOffset += yGap;
//        }
//    }

    private INode findNodeByBlock(IGraph graph, BrinBlock block) {
        for (INode node : graph.getNodes()) {
            if (node.getTag() == block) {
                return node;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        BrinIndex brinIndex = new BrinIndex();


        SwingUtilities.invokeLater(() -> {
            BrinGUI frame = new BrinGUI(brinIndex);
            frame.setVisible(true);
        });
    }

}
