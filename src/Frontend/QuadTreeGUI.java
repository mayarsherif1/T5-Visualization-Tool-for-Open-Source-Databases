package Frontend;

import Backend.Point;
import Backend.QuadTree.QuadTree;
import Backend.QuadTree.QuadTreeNode;
import com.yworks.yfiles.geometry.InsetsD;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.ILabel;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.labelmodels.ILabelModelParameter;
import com.yworks.yfiles.graph.labelmodels.InteriorLabelModel;
import com.yworks.yfiles.graph.styles.DefaultLabelStyle;
import com.yworks.yfiles.graph.styles.ShapeNodeStyle;
import com.yworks.yfiles.layout.hierarchic.HierarchicLayout;
import com.yworks.yfiles.view.GraphComponent;
import com.yworks.yfiles.view.Pen;
import com.yworks.yfiles.view.input.GraphViewerInputMode;

import javax.swing.*;
import java.awt.*;

public class QuadTreeGUI extends JPanel {
    private QuadTree quadTree;
    private GraphComponent graphComponent;
    private SpatialPanel spatialPanel;

    public QuadTreeGUI(QuadTree quadTree) {
        this.quadTree = quadTree;
        this.graphComponent = new GraphComponent();
        this.spatialPanel = new SpatialPanel(quadTree);
        setLayout(new BorderLayout());
        initUI();

    }

    private void initUI() {
        graphComponent.setInputMode(new GraphViewerInputMode());
        visualizeHierarchicalComponent(quadTree.getRoot(), null);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, spatialPanel, graphComponent);
        splitPane.setDividerLocation(400);

        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
    }


    private void visualizeHierarchicalComponent(QuadTreeNode node, INode parent) {
        IGraph graph = graphComponent.getGraph();
        if (node != null) {
            ShapeNodeStyle nodeStyle = new ShapeNodeStyle();
            nodeStyle.setPen(Pen.getBlack());
            nodeStyle.setPaint(node.isLeaf ? Color.GREEN:Color.YELLOW);

            if(node.isLeaf){
                nodeStyle.setPaint(Color.GREEN);
                if(node.points.isEmpty()){
                    nodeStyle.setPaint(Color.YELLOW);
                }
            }
            else {
                nodeStyle.setPaint(Color.GREEN);

            }

            RectD bounds = new RectD(0,0,50,30);
            INode yNode = graph.createNode(null, bounds, nodeStyle, null);
            DefaultLabelStyle labelStyle = new DefaultLabelStyle();
            labelStyle.setFont(new Font("Arial", Font.PLAIN, 10));
            labelStyle.setInsets(new InsetsD(2));
            InteriorLabelModel interiorLabelModel = new InteriorLabelModel();
            ILabelModelParameter labelModelParameter = interiorLabelModel.createParameter(InteriorLabelModel.Position.CENTER);

            ILabel label = graph.addLabel(yNode, node.toString(), labelModelParameter, labelStyle);

            graph.setLabelPreferredSize(label, bounds.toSizeD());
            //graph.addLabel(yNode, node.toString());

            if (parent != null) {
                graph.createEdge(parent, yNode);
            }
            if (!node.isLeaf) {
                visualizeHierarchicalComponent(node.NE, yNode);
                visualizeHierarchicalComponent(node.NW, yNode);
                visualizeHierarchicalComponent(node.SE, yNode);
                visualizeHierarchicalComponent(node.SW, yNode);
            }
        }
    }

    public SpatialPanel getSpatialPanel() {
        return spatialPanel;
    }

    public void applyHierarchicLayout() {
        HierarchicLayout layout = new HierarchicLayout();
        layout.setMinimumLayerDistance(50);
        layout.setNodeToNodeDistance(25);
        //layout.setOrthogonalRoutingEnabled(true);
        graphComponent.getGraph().applyLayout(layout);
        graphComponent.fitGraphBounds();
        graphComponent.updateUI();
    }

    private static class SpatialPanel extends JPanel {
        private QuadTree quadTree;

        public SpatialPanel(QuadTree quadTree) {
            this.quadTree = quadTree;
            setPreferredSize(new Dimension(400, 400));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawQuadTree(g, quadTree.getRoot());
        }

        private void drawQuadTree(Graphics g, QuadTreeNode node) {
            g.setColor(Color.black);
            g.drawRect(node.bounds.x, node.bounds.y, node.bounds.width, node.bounds.height);
            g.setColor(Color.RED);
            for (Point point : node.points) {
                int pointRadius = 3;
                g.fillOval(point.getX() - pointRadius, point.getY() - pointRadius, pointRadius * 2, pointRadius * 2);
                g.drawString(point.toString(), point.getX(), point.getY());
            }
            if (!node.isLeaf) {
                if (node.NE != null) drawQuadTree(g, node.NE);
                if (node.NW != null) drawQuadTree(g, node.NW);
                if (node.SE != null) drawQuadTree(g, node.SE);
                if (node.SW != null) drawQuadTree(g, node.SW);
            }
        }
    }

    public GraphComponent getGraphComponent() {
        return graphComponent;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            JFrame frame = new JFrame("QuadTree Visualization");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Rectangle boundary = new Rectangle(0, 0, 400, 400);
            QuadTree quadTree = new QuadTree(boundary, 4);
            quadTree.insert(new Point(30, 20, "X", "Y"));
            quadTree.insert(new Point(110, 160, "X", "Y"));
            quadTree.insert(new Point(310, 320, "X", "Y"));
            quadTree.insert(new Point(220, 80, "X", "Y"));
            quadTree.insert(new Point(30, 120, "X", "Y"));
            quadTree.insert(new Point(190, 340, "X", "Y"));
            quadTree.insert(new Point(350, 350, "X", "Y"));

            QuadTreeGUI visualizationPanel = new QuadTreeGUI(quadTree);
            frame.add(visualizationPanel);
            visualizationPanel.applyHierarchicLayout();

            visualizationPanel.graphComponent.invalidate();
            visualizationPanel.graphComponent.validate();
            frame.setSize(800,800);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
