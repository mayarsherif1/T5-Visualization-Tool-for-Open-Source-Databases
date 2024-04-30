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
        sql = sql.toLowerCase();
        if (sql.contains("select") && sql.contains("from")) {
            String[] parts = sql.split("from");
            String[] projections = parts[0].split("select")[1].trim().split(",");
            String tableName = parts[1].trim().split(" ")[0];

            RelationalAlgebraNode tableNode = new TableNode(tableName, database);
            return new ProjectionNode(List.of(projections), new SelectionNode("Example Condition", tableNode));
        }
        return new TableNode("Unknown", database);
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
        INode currentNode = graph.createNode(new RectD(0, 0, 100, 50), null, node.toString());
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
