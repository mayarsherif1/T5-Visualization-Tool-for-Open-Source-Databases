package Frontend;

import Backend.Database.Database;
import Backend.RelationalAlgebra.*;
import com.yworks.yfiles.geometry.RectD;
import com.yworks.yfiles.graph.DefaultGraph;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.view.GraphComponent;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RelationalAlgebraTreeGUI extends JFrame {
    private RelationalAlgebraNode root;
    private Database database;

    public RelationalAlgebraTreeGUI(String sql, Database database) {
        super("Relational Algebra Visualization");
        this.root = parseSQLToAlgebra(sql);
        this.database = database;
        initializeGUI();

    }
    private void initializeGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);

        GraphComponent graphComponent = toGraphComponent();
        this.add(graphComponent);
    }

    private RelationalAlgebraNode parseSQLToAlgebra(String sql) {
        try {
            if (!sql.toUpperCase().contains("SELECT") || !sql.toUpperCase().contains("FROM")) {
                throw new IllegalArgumentException("Invalid SQL: SELECT or FROM clause missing.");
            }

            String[] parts = sql.split("(?i)FROM");
            if (parts.length < 2 || parts[0].split("(?i)SELECT").length < 2) {
                throw new IllegalArgumentException("Invalid SQL structure.");
            }

            String[] projections = parts[0].split("(?i)SELECT")[1].trim().split(",");
            if (projections.length == 0) {
                throw new IllegalArgumentException("No columns specified in SELECT clause.");
            }

            String tableAndConditions = parts[1].trim();
            String[] tableParts = tableAndConditions.split("(?i)WHERE");
            String tableName = tableParts[0].trim().split("\\s+")[0];
            if (tableName.isEmpty()) {
                throw new IllegalArgumentException("No table specified.");
            }

            String condition = tableParts.length > 1 ? tableParts[1].trim() : "TRUE";

            RelationalAlgebraNode tableNode = new TableNode(tableName, database);
            if (((TableNode)tableNode).getTable() == null) {
                throw new IllegalArgumentException("Table '" + tableName + "' not found.");
            }

            SelectionNode selectionNode = new SelectionNode(condition, tableNode);
            return new ProjectionNode(List.of(projections), selectionNode);
        } catch (Exception e) {
            System.err.println("Error parsing SQL: " + e.getMessage());
            return null;  // Or handle it by throwing it further if that suits your design
        }
    }



    public GraphComponent toGraphComponent() {
        IGraph graph = new DefaultGraph();
        GraphComponent graphComponent = new GraphComponent();
        graphComponent.setGraph(graph);

        if (root != null) {
            buildGraph(graph, root, null);
        }
        return graphComponent;
    }

    private void buildGraph(IGraph graph, RelationalAlgebraNode node, INode parentNode) {
        String label = node.toString();
        INode currentNode = graph.createNode(new RectD(0, 0, 100, 50), null, label);
        if (parentNode != null) {
            graph.createEdge(parentNode, currentNode);
        }

        if (node instanceof BinaryNode) {
            BinaryNode binaryNode = (BinaryNode) node;
            buildGraph(graph, binaryNode.getLeft(), currentNode);
            buildGraph(graph, binaryNode.getRight(), currentNode);
        } else if (node instanceof UnaryNode) {
            UnaryNode unaryNode = (UnaryNode) node;
            buildGraph(graph, unaryNode.getChild(), currentNode);
        }

    }

}
