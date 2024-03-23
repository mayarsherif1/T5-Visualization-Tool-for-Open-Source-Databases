package Frontend;

import Backend.*;
import antlr4.PostgreSQLLexer;
import antlr4.PostgreSQLParser;
import com.yworks.yfiles.graph.IGraph;
import com.yworks.yfiles.graph.INode;
import com.yworks.yfiles.view.GraphComponent;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DatabaseGUI extends JFrame {
    private JTextArea sqlInputArea;
    private JButton executeSqlButton;
    private JComboBox<String> tableSelector;
    private JTable dataTable;
    private Database database;
    private GraphComponent graphComponent;


    public DatabaseGUI(Database database) {
        super("Database Visualization Tool");
        this.database = database;
        initializeUI();
    }

    private void initializeUI() {
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        sqlInputArea = new JTextArea(5, 20);
        executeSqlButton = new JButton("Execute");
        executeSqlButton.addActionListener(e -> {
            try {
                executeSQL(sqlInputArea.getText());
            } catch (TableNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        tableSelector = new JComboBox<>();
        tableSelector.addActionListener(e -> updateDataTable((String) tableSelector.getSelectedItem()));

        dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);
        graphComponent = new GraphComponent();
        add(graphComponent, BorderLayout.CENTER);
        JPanel sqlPanel = new JPanel(new BorderLayout());
        sqlPanel.add(new JScrollPane(sqlInputArea), BorderLayout.CENTER);
        sqlPanel.add(executeSqlButton, BorderLayout.EAST);

        add(sqlPanel, BorderLayout.NORTH);
        add(tableSelector, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
        updateGraph();
    }

    private void executeSQL(String sql) throws TableNotFoundException {
        CharStream stream = CharStreams.fromString(sql);
        PostgreSQLLexer lexer = new PostgreSQLLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PostgreSQLParser parser = new PostgreSQLParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                JOptionPane.showMessageDialog(DatabaseGUI.this, "Failed to parse SQL at line " + line + ": " + msg, "Syntax Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        ParseTree tree = parser.stmt();
        NewSQLListener listener = new NewSQLListener(database);
        ParseTreeWalker.DEFAULT.walk(listener, tree);
        if (sql.trim().toUpperCase().startsWith("CREATE TABLE")) {
            parseCreateTable(sql);
        } else if (sql.trim().toUpperCase().startsWith("INSERT INTO")) {
            parseInsertInto(sql);
        }

        updateGraph();
        updateTableSelector();
        updateDataTable((String) tableSelector.getSelectedItem());
    }
    private void updateGraph() {
        IGraph graph = graphComponent.getGraph();
        graph.clear();
        database.getAllTables().forEach(table -> {
            INode node = graph.createNode();
            graph.addLabel(node, table.getName());
        });

        graphComponent.fitGraphBounds();
    }
    private void parseCreateTable(String sql) {
        Pattern createTablePattern = Pattern.compile("CREATE TABLE (\\w+) \\((.*)\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = createTablePattern.matcher(sql);
        if (matcher.find()) {
            String tableName = matcher.group(1);
            String columnsPart = matcher.group(2);
            List<Column> columns = parseColumns(columnsPart);
            database.createTable(tableName, columns);
        }
    }
    private List<Column> parseColumns(String columnsPart) {
        List<Column> columns = new ArrayList<>();
        String[] columnDefinitions = columnsPart.split(",");
        for (String def : columnDefinitions) {
            String[] parts = def.trim().split(" ");
            if (parts.length >= 2) {
                String name = parts[0];
                String type = parts[1];
                columns.add(new Column(name, type));
            }
        }
        return columns;
    }

    private void parseInsertInto(String sql) throws TableNotFoundException {
        Pattern insertIntoPattern = Pattern.compile("INSERT INTO (\\w+) \\((.*)\\) VALUES \\((.*)\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = insertIntoPattern.matcher(sql);
        if (matcher.find()) {
            String tableName = matcher.group(1);
            String columnNamesPart = matcher.group(2);
            String valuesPart = matcher.group(3);
            List<String> columnNames = Arrays.asList(columnNamesPart.split(","));
            List<String> values = Arrays.asList(valuesPart.split(","));
            columnNames = columnNames.stream().map(String::trim).collect(Collectors.toList());
            values = values.stream().map(String::trim).collect(Collectors.toList());

            try {
                database.insertIntoTable(tableName, columnNames, values);
            } catch (TableNotFoundException e) {
                throw new TableNotFoundException(e.getMessage());
            }
        }
    }

    private void updateTableSelector() {
        tableSelector.removeAllItems();
        for (String tableName : database.getTableNames()) {
            tableSelector.addItem(tableName);
        }
    }

    private void updateDataTable(String tableName) {
        if (tableName == null) return;
        try {
            Table table = database.getTable(tableName);
            List<Column> columns = table.getColumns();
            List<Map<String, String>> rows = table.getRows();

            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            model.setRowCount(0);
            model.setColumnCount(0);

            for (Column column : columns) {
                model.addColumn(column.getName());
            }

            for (Map<String, String> row : rows) {
                Object[] rowData = new Object[columns.size()];
                int i = 0;
                for (Column column : columns) {
                    rowData[i++] = row.get(column.getName());
                }
                model.addRow(rowData);
            }
        } catch (TableNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Table not found: " + tableName, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Database database = new Database();
        SwingUtilities.invokeLater(() -> new DatabaseGUI(database).setVisible(true));
    }
}
