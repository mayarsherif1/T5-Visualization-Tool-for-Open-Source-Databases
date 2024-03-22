//package Frontend;
//
//import Backend.BPlusTree;
//import Backend.BPlusTreeNode;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class BPlusTreeVisualizer extends JPanel {
//    private BPlusTree bPlusTree;
//
//    public BPlusTreeVisualizer(BPlusTree bPlusTree) {
//        this.bPlusTree = bPlusTree;
//        setPreferredSize(new Dimension(800, 600));
//        setBackground(Color.WHITE);
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        drawTree(g, bPlusTree.root, 20, getWidth() / 2, 50, getWidth() / 4);
//    }
//
//    private void drawTree(Graphics g, BPlusTreeNode node, int x, int y, int xIncrement, int levelGap) {
//        if (node == null) return;
//
//        // Draw the current node
//        String nodeText = getNodeText(node);
//        g.drawOval(x, y, 60, 30);
//        g.drawString(nodeText, x + 10, y + 20);
//
//        if (!node.isLeafNode) {
//            // Calculate positions for child nodes
//            int childXIncrement = xIncrement / 2;
//            int childY = y + levelGap;
//            for (int i = 0; i <= node.m; i++) {
//                // Recursive call to draw child nodes
//                drawTree(g, node.pointers[i], x - xIncrement + (childXIncrement * i * 2), childY, childXIncrement, levelGap);
//            }
//        }
//    }
//
//    private String getNodeText(BPlusTreeNode node) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<");
//        for (int i = 0; i < node.m; i++) {
//            if (i > 0) sb.append(",");
//            sb.append(node.keys[i]);
//        }
//        sb.append(">");
//        return sb.toString();
//    }
//
//    public static void showBPlusTree(BPlusTree bPlusTree) {
//        JFrame frame = new JFrame("B+ Tree Visualization");
//        BPlusTreeVisualizer visualizer = new BPlusTreeVisualizer(bPlusTree);
//        frame.setContentPane(visualizer);
//        frame.pack();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            BPlusTree bPlusTree = new BPlusTree(2);
//
//            List<String[]> sampleInputs = new ArrayList<>();
//            sampleInputs.add(new String[]{"12", "100"});
//            sampleInputs.add(new String[]{"8", "50"});
//            sampleInputs.add(new String[]{"1"});
//
//            bPlusTree.insertList(sampleInputs);
//            showBPlusTree(bPlusTree);
//        });
//    }
//
////           BPlusTree.insert(12);
////        BPlusTree.insert(8);
////        BPlusTree.insert(1);
////        BPlusTree.insert(23);
////        BPlusTree.insert(5);
////        BPlusTree.insert(7);
////        BPlusTree.insert(2);
////        BPlusTree.insert(28);
////        BPlusTree.insert(9);
////        BPlusTree.insert(18);
////        BPlusTree.insert(24);
////        BPlusTree.insert(40);
////        BPlusTree.insert(48);
//}
