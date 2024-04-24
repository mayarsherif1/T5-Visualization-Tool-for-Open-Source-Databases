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

public class GridGUI extends JPanel {
    private GraphComponent graphComponent;
    private int numRows;
    private int numCols;
    private final int cellWidth = 100;
    private final int cellHeight = 100;
    private final int bucketWidth = 50;
    private final int bucketHeight = 60;
    private final int bucketPadding = 10;

    private Grid backendGrid;

    public GridGUI(Grid grid) {
        this.backendGrid = grid;
        this.numRows = grid.getRows();
        this.numCols = grid.getColumns();
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        graphComponent = new GraphComponent();
        add(graphComponent, BorderLayout.CENTER);
        backendGrid.initializeBuckets(numRows, numCols);
        initializeGraph();
    }

    private void initializeGraph() {
        IGraph graph = graphComponent.getGraph();
        ShapeNodeStyle cellStyle = new ShapeNodeStyle();
        ShapeNodeStyle bucketStyle = new ShapeNodeStyle();
        setupStyles(cellStyle, bucketStyle);

        INode[][] cells = new INode[numRows][numCols];
        INode[] buckets = new INode[numRows];

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                cells[row][col] = createNode(graph, new RectD(col * cellWidth, row * cellHeight, cellWidth, cellHeight), cellStyle);
                int value = backendGrid.getCellValue(row, col);
                updateCellValue(cells[row][col], value);
            }
            int bucketValue = backendGrid.getBucketValue(row);
            buckets[row] = createNode(graph, new RectD(numCols * cellWidth + bucketPadding, row * (bucketHeight + bucketPadding), bucketWidth, bucketHeight), bucketStyle);
            updateBucketValue(buckets[row], bucketValue);
        }
        createEdges(graph, cells, buckets);
        graphComponent.fitGraphBounds();
    }

    private void updateBucketValue(INode bucketNode, int value) {
        IGraph graph = graphComponent.getGraph();
        graph.addLabel(bucketNode, String.valueOf(value));
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

    private void updateCellValue(INode cellNode, int value) {
        IGraph graph = graphComponent.getGraph();
        graph.addLabel(cellNode, String.valueOf(value));
    }

    private void createEdges(IGraph graph, INode[][] cells, INode[] buckets) {
        PolylineEdgeStyle edgeStyle = new PolylineEdgeStyle();
        edgeStyle.setPen(new Pen(Color.BLACK, 2));
        edgeStyle.setTargetArrow(new Arrow(ArrowType.DEFAULT, Color.BLACK));

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                graph.createEdge(cells[row][col], buckets[row], edgeStyle);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Grid Index Visualization with yFiles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Grid grid = new Grid(10, 2);
        grid.initializeBuckets(10, 2);
        grid.addToBucket(0, 0, 1);
        grid.addToBucket(0, 1, 2);
        grid.addToBucket(1, 0, 3);
        grid.addToBucket(1, 1, 4);
        frame.add(new GridGUI(grid));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public GraphComponent getGraphComponent() {
        return graphComponent;
    }
}
