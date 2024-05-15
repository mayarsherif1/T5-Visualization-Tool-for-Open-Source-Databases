package Frontend;

import Backend.GRID.Grid;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.graph.styles.*;
import com.yworks.yfiles.view.GraphComponent;
import com.yworks.yfiles.view.Pen;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class GridGUI extends JPanel {
    private GraphComponent graphComponent;
    private int numRows;
    private int numCols;
    private final int cellWidth = 100;
    private final int cellHeight = 100;
    private final int bucketWidth = 50;
    private final int bucketHeight = 60;
    private final int bucketPadding = 10;
    private Grid grid;

    public GridGUI(Grid grid) {
        this.grid = grid;
        this.numRows = grid.getRows();
        this.numCols = grid.getColumns();
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        graphComponent = new GraphComponent();
        add(graphComponent, BorderLayout.CENTER);
        initializeGraph();
    }

    private void initializeGraph() {
        IGraph graph = graphComponent.getGraph();
        ShapeNodeStyle cellStyle = new ShapeNodeStyle();
        ShapeNodeStyle bucketStyle = new ShapeNodeStyle();
        setupStyles(cellStyle, bucketStyle);

        INode[][] cells = new INode[numRows][numCols];
        INode[][] buckets = new INode[numRows][numCols];

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                cells[row][col] = createNode(graph, new RectD(col * cellWidth, row * cellHeight, cellWidth, cellHeight), cellStyle);
                int[] range = grid.getCellRange(row, col);
                updateCellValue(cells[row][col], range);

                String rangeText = formatRangeLabel(range);
                graph.addLabel(cells[row][col], rangeText);
                RectD bucketLayout = new RectD((col + 3) * cellWidth + bucketPadding, row * cellHeight, bucketWidth, bucketHeight);
                buckets[row][col] = createNode(graph, bucketLayout, bucketStyle);
                updateBucketValue(buckets[row][col], grid.getBucket(row, col));
                graph.createEdge(cells[row][col], buckets[row][col], new PolylineEdgeStyle());
            }
        }
        graphComponent.fitGraphBounds();
    }

    private String formatRangeLabel(int[] range) {
        return range[0] + " - " + range[1];
    }

    private void updateBucketValue(INode bucketNode, List<Integer> values) {
        IGraph graph = graphComponent.getGraph();
        String bucketText = values.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        graph.addLabel(bucketNode, bucketText);
    }

    private void setupStyles(ShapeNodeStyle cellStyle, ShapeNodeStyle bucketStyle) {
        cellStyle.setShape(ShapeNodeShape.RECTANGLE);
        cellStyle.setPen(new Pen(Color.DARK_GRAY, 1.5));
        bucketStyle.setShape(ShapeNodeShape.RECTANGLE);
        bucketStyle.setPen(new Pen(Color.GRAY, 1.5));
    }

    private INode createNode(IGraph graph, RectD layout, ShapeNodeStyle style) {
        INode node = graph.createNode(layout, style);
        graph.setStyle(node, style);
        return node;
    }

    private void updateCellValue(INode cellNode, int[] value) {
        IGraph graph = graphComponent.getGraph();
        String range = formatRangeLabel(value);
        graph.addLabel(cellNode, range);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Grid Index Visualization with yFiles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Grid grid = new Grid();
        grid.setCellRange(0, 0, new int[]{1, 5});
        grid.setCellRange(0, 1, new int[]{6, 10});
        grid.setCellRange(0, 2, new int[]{11, 15});
        grid.setCellRange(1, 0, new int[]{16, 20});
        grid.setCellRange(1, 1, new int[]{21, 25});
        grid.setCellRange(1, 2, new int[]{26, 30});
        grid.setCellRange(2, 0, new int[]{31, 35});
        grid.setCellRange(2, 1, new int[]{36, 40});
        grid.setCellRange(2, 2, new int[]{41, 45});
        grid.addToBucket(0, 0, 1);
        grid.addToBucket(0, 1, 2);
        grid.addToBucket(0, 2, 3);
        grid.addToBucket(1, 0, 4);
        grid.addToBucket(1, 1, 5);
        grid.addToBucket(1, 2, 6);
        grid.addToBucket(2, 0, 7);
        grid.addToBucket(2, 1, 8);
        grid.addToBucket(2, 2, 9);
        frame.add(new GridGUI(grid));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public GraphComponent getGraphComponent() {
        return graphComponent;
    }
}
