package Frontend;

import Backend.QuadTree;
import Backend.QuadTreeNode;

import javax.swing.*;
import java.awt.*;

public class QuadTreeVisualization extends JFrame {
    private QuadTree quadTree;

    public QuadTreeVisualization(QuadTree quadTree) {
        this.quadTree = quadTree;
        setTitle("QuadTree Structure Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new DrawingPanel());
    }

    class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (quadTree.getRoot() != null) {
                drawNode(g, quadTree.getRoot(), getWidth() / 2, 30, getWidth() / 4, 80);
            }
        }

        private void drawNode(Graphics g, QuadTreeNode node, int x, int y, int xGap, int yGap) {
            int nodeSize = 20;
            g.fillOval(x - nodeSize / 2, y - nodeSize / 2, nodeSize, nodeSize);

            if (node.isLeaf()) return;
            int newXGap = xGap / 2;
            if (node.getNE() != null) {
                g.drawLine(x, y, x + newXGap, y + yGap);
                drawNode(g, node.getNE(), x + newXGap, y + yGap, newXGap, yGap);
            }
            if (node.getNW() != null) {
                g.drawLine(x, y, x - newXGap, y + yGap);
                drawNode(g, node.getNW(), x - newXGap, y + yGap, newXGap, yGap);
            }
            if (node.getSE() != null) {
                g.drawLine(x, y + nodeSize, x + newXGap, y + yGap);
                drawNode(g, node.getSE(), x + newXGap, y + yGap, newXGap, yGap);
            }
            if (node.getSW() != null) {
                g.drawLine(x, y + nodeSize, x - newXGap, y + yGap);
                drawNode(g, node.getSW(), x - newXGap, y + yGap, newXGap, yGap);
            }
        }
    }

    public static void main(String[] args) {
        QuadTree qt = new QuadTree(new Rectangle(0, 0, 800, 600), 3);
        //qt.insert(new Point(100, 100), "data"); // Example insertions

        SwingUtilities.invokeLater(() -> {
            QuadTreeVisualization frame = new QuadTreeVisualization(qt);
            frame.setVisible(true);
        });
    }
}
