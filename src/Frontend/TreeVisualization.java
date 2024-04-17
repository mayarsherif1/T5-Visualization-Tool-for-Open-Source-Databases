package Frontend;

import Backend.*;
import antlr4.PostgreSQLLexer;
import antlr4.PostgreSQLParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Point;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class TreeVisualization extends JPanel {
    private JTabbedPane tabbedPane;
    private TreePanel treePanel;
    private JTextField sqlTestField;
    private Point lastDragPoint;
    private JPanel tablesPanel;
    private Map<String,JComponent> tables;
    private Database database;
    private Map<String, Integer> currentPages = new HashMap<>();
    private Map<String, Integer> rowsPerPage = new HashMap<>();
    private Map<String, Integer> rowsCountPerTable = new HashMap<>();


    public TreeVisualization() {
        database= new Database();
        setLayout(new BorderLayout());
        tabbedPane= new JTabbedPane();
        treePanel = new TreePanel(null);
        treePanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.getScrollType()==MouseWheelEvent.WHEEL_UNIT_SCROLL){
                    double delta = 0.05f*e.getWheelRotation();
                    double scaleFactor = Math.max(0.1,treePanel.scaleFactor-delta);
                    treePanel.setScaleFactor(scaleFactor);
                    treePanel.revalidate();
                    treePanel.repaint();
                }
            }
        });
        treePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(lastDragPoint!=null){
                    Point dragPoint = e.getPoint();
                    int diffX = dragPoint.x - lastDragPoint.x;
                    int diffY = dragPoint.y - lastDragPoint.y;
                    Rectangle view = treePanel.getVisibleRect();
                    view.x -= diffX;
                    view.y -= diffY;
                    treePanel.scrollRectToVisible(view);
                    lastDragPoint = dragPoint;
                }
            }
        });
        treePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastDragPoint= e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lastDragPoint=null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point dragPoint = e.getPoint();
                int x = dragPoint.x- lastDragPoint.x;
                int y = dragPoint.y - lastDragPoint.y;
                JScrollPane scrollPane =(JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class,treePanel);
                JViewport viewport = scrollPane.getViewport();
                Point viewPos = viewport.getViewPosition();
                viewPos.translate(-x,-y);
                treePanel.scrollRectToVisible(new Rectangle(viewPos,viewport.getSize()));
                lastDragPoint= dragPoint;
            }
        });

        treePanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(treePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tabbedPane.add("Tree",scrollPane);
        add(tabbedPane,BorderLayout.CENTER);

        JPanel sqlPanel = new JPanel();
        sqlTestField = new JTextField(30);

        //JButton submitButton = getSubmitButton();
        tables= new HashMap<>();

        tablesPanel = new JPanel();
        tablesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JScrollPane tablesScrollPane = new JScrollPane(tablesPanel);
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
        tablesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tablesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tablesScrollPane.setPreferredSize(new Dimension(800,200));

        setSqlPanel();
        //testStaticContent();

        add(tabbedPane, BorderLayout.CENTER);
        add(tablesScrollPane, BorderLayout.SOUTH);

    }


    private void setSqlPanel() {
        JPanel sqlPanel = new JPanel();
        JButton submitButton = new JButton("Visualize");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = sqlTestField.getText();
                executeSQL(sql);
                if(sql.toLowerCase().startsWith("create table")){
                    CreateTableTab(sql);
                } else if(sql.trim().toLowerCase().startsWith("insert into")){
                    insertIntoTable(sql);
                } else if (sql.toLowerCase().startsWith("create index")) {
                    System.out.println("create index sql panel");
                    createIndexFromTable(sql);
                } else {
                    ParseTree parseTree = processSQL(sql);
                    //todo setParseTree()
                    treePanel.repaint();
                }
            }
        });
        sqlPanel.add(new JLabel("SQL:"));
        sqlPanel.add(sqlTestField);
        sqlPanel.add(submitButton);
        add(sqlPanel, BorderLayout.PAGE_START);
        updateVisual();
        refreshTablesPanel();
        tablesPanel.revalidate();
        tablesPanel.repaint();
    }

    private void createIndexFromTable(String sql) {
        Pattern pattern = Pattern.compile("CREATE INDEX (\\w+) ON (\\w+) USING (\\w+)_tree \\((\\w+)\\);", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        if (matcher.find()) {
            String indexName = matcher.group(1).trim();
            String tableName = matcher.group(2).trim();
            String  treeType = matcher.group(3).trim();
            String columnName = matcher.group(4).trim();

            switch (treeType.toLowerCase()) {
                case "b":
                    System.out.println("createBPlusTree");
                    createBPlusTree(tableName, columnName);
                    break;
                case "kd":
                    // Implement creation of KD tree
                    break;
                case "quad":
                    // Implement creation of Quad tree
                    break;
                default:
                    // Handle unsupported tree type
                    break;
            }
        } else {
            // Handle invalid SQL syntax for index creation
        }
    }

    private void createBPlusTree(String tableName, String columnName) {
        List<String> data = database.getDataFromTable(tableName, columnName);
        System.out.println("createBPlusTree data: " + data);
        visualizeBPlusTree(data);
        System.out.println("createBPlusTree after visualizeBPlusTree method");
    }
    private void visualizeBPlusTree(List<String> data) {
        SwingUtilities.invokeLater(() -> {
            BPlusTreeGUI<String> bPlusTreeGUI = new BPlusTreeGUI<>();
            for (String value : data) {
                bPlusTreeGUI.getBPlusTree().insert(value);
                System.out.println("value: " + value);
            }
            System.out.println("before refreshVis");
            bPlusTreeGUI.refreshVis();
            System.out.println("after refreshVis");

            treePanel.setGraphComponent(bPlusTreeGUI.getGraphComponent());
            System.out.println("after setGraphComponent");
            treePanel.revalidate();
            treePanel.repaint();
        });
    }


    private void executeSQL(String sql) {
        if(sql.trim().isEmpty()){
            return;
        }
        ParseTree tree = processSQL(sql);
        NewSQLListener listener = new NewSQLListener(database);
        ParseTreeWalker.DEFAULT.walk(listener, tree);
        updateVisual();

    }


    public void updateVisual() {
        treePanel.removeAll();
        tablesPanel.removeAll();
        for(Table table: database.getAllTables()){
            DefaultTableModel model = new DefaultTableModel();
            JTable jTable = new JTable();
//            JPanel tablePanel = new JPanel();
//            tablesPanel.setBorder(BorderFactory.createTitledBorder(table.getName()));
//
            for (Column column: table.getColumns()){
                model.addColumn(column.getName());
                //JLabel columnLabel = new JLabel(column.getName()+" ("+ column.getType()+ ")");
                //tablePanel.add(columnLabel);
            }
            jTable.setModel(model);
            jTable.setName(table.getName());
            //tablesPanel.add(tablePanel);
            JScrollPane scrollPane=new JScrollPane(jTable);
            scrollPane.setBorder(BorderFactory.createTitledBorder(table.getName()));
            tablesPanel.add(scrollPane);
        }
        refreshTablesPanel();
        treePanel.setRoot(buildTreeFromDatabase());
        treePanel.revalidate();
        treePanel.repaint();
        tablesPanel.revalidate();
        tablesPanel.repaint();
    }


    public void addColumnToTable(String tableName, String columnName, String columnType) throws TableNotFoundException {
        Table table = database.getTable(tableName);
        if (table != null) {
            Column newColumn = new Column(columnName, columnType);
            table.addColumn(newColumn);
        }
    }


    private Node buildTreeFromDatabase() {
        Node rootNode = new Node("Database");
        for(Table table: database.getAllTables()){
            Node tableNode = new Node(table.getName().toLowerCase());
            for(Column column: table.getColumns()){
                Node columnNode = new Node(column.getName()+ " ("+ column.getType() +")");
                tableNode.addChild(columnNode);
            }
            rootNode.addChild(tableNode);
        }
        return rootNode;
    }


    private void setCreateTable(ParseTree tree) {
        String tableName = getTableName(tree);
        List<Column> columnList = getColumns(tree);
        Table table = new Table(tableName);
        for(Column column : columnList){
            table.addColumn(column);
        }
        database.addTable(table);

    }

    private List<Column> getColumns(ParseTree tree) {
        List<Column> columns = new ArrayList<>();
        String sql = tree.getText();
        Pattern pattern = Pattern.compile("\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(sql);

        if (matcher.find()){
            String allColumns = matcher.group(1);
            String[] columnDef = allColumns.split(",(?![^()]*\\))");
            for (String cd :columnDef ){
                cd = cd.trim();
                String [] parts= cd.split("\\s+");
                if (parts.length>=2){
                    String columnName = parts[0];
                    String columnType = parts[1];
                    columns.add(new Column(columnName,columnType));
                }
            }
        }
        return columns;
    }

    private String getTableName(ParseTree tree) {
        String sql = tree.getText();
        System.out.println("Processing SQL: " + sql);
        Pattern pattern = Pattern.compile("CREATE\\s+TABLE\\s+(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        if(matcher.find()){
            return matcher.group(1);
        }
        else {
            throw new IllegalArgumentException("Table name not found in CREATE TABLE statement");
        }
    }

    class PageInfo {
        int pages;
        int rows;

        PageInfo(int pages, int rows) {
            this.pages = pages;
            this.rows = rows;
        }
        private int getPages() {
            return this.pages;
        }
        private int getRows() {
            return this.rows;
        }
    }

    JTextField pagesField;
    JTextField rowsField;
    int pages;
    int rows;

    public int getPages() {
        return pages;
    }
    public int getRows() {
        return rows;
    }



    private PageInfo promptForPageInfo() {
        pagesField = new JTextField("1");
        rowsField = new JTextField("10");
        Object[] message = {
                "Pages: ", pagesField,
                "Rows: ", rowsField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Enter Table Details", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                 pages = Integer.parseInt(pagesField.getText());
                 rows = Integer.parseInt(rowsField.getText());
                return new PageInfo(pages, rows);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }


    private void CreateTableTab(String sql) {
        PageInfo pageInfo = promptForPageInfo();
        if (pageInfo != null) {
            try {
                Pattern pattern = Pattern.compile("CREATE TABLE (\\w+) \\((.*)\\)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(sql);
                if (matcher.find()) {
                    String tableName = matcher.group(1).trim();
                    System.out.println("Table name: " + tableName);
                    String columnsPart = matcher.group(2).trim();
                    System.out.println("Columns part: " + columnsPart);
                    List<Column> columns = parseColumns(columnsPart);
                    System.out.println("Columns: " + columns);
                    database.createTable(tableName, columns);
                    updateTableVis(tableName, getPages(), getRows());
                } else {
                    JOptionPane.showMessageDialog(this, "Table name not found in the command.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException | TableNotFoundException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
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

    public void updateTableVis(String tableName,int pages,int rows) throws TableNotFoundException {
        displayPages(tableName,pages,rows);
        if (tables.containsKey(tableName)){
            JScrollPane scrollPane = (JScrollPane) tables.get(tableName);
            JTable table = (JTable) scrollPane.getViewport().getView();
            updateTableData(table, tableName);
//            JLabel tableLabel =(JLabel) tables.get(tableName.toLowerCase());
//            tableLabel.setText(String.format("%s: %d pages,%d rows", tableName, pages, rows));
        }
        else {
            DefaultTableModel model = new DefaultTableModel();
            for (Column column : database.getTable(tableName).getColumns()) {
                model.addColumn(column.getName());
            }

            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createTitledBorder(String.format("%s: %d pages, %d rows", tableName, pages, rows)));

            tables.put(tableName, scrollPane);
            tablesPanel.add(scrollPane);

//            JLabel tableLabel = new JLabel(String.format("%s: %d pages,%d rows", tableName, pages, rows));
//            System.out.print(tableLabel);
//            tableLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//            tableLabel.setPreferredSize(new Dimension(200, 50));
//            tables.put(tableName, tableLabel);
//            tablesPanel.add(tableLabel);
        }
        tablesPanel.revalidate();
        tablesPanel.repaint();
    }

    private void updateTableData(JTable table, String tableName) throws TableNotFoundException {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        Table backendTable = database.getTable(tableName);

        for (Map<String, String> row : backendTable.getRows()) {
            Vector<Object> rowData = new Vector<>();
            for (Column column : backendTable.getColumns()) {
                rowData.add(row.get(column.getName()));
            }

            model.addRow(rowData);
        }
    }

    private void displayPages(String tableName, int pages, int rows) throws TableNotFoundException {
        tablesPanel.removeAll();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.X_AXIS));
        Table table = database.getTable(tableName);
        List<Map<String, String>> allRows = table.getRows();
        int totalRows = allRows.size();
        int startRow = 0;

        for (int pageIndex = 0; pageIndex < pages; pageIndex++) {
            int endRow = Math.min(startRow + rows, totalRows);
            List<Map<String, String>> pageRows = allRows.subList(startRow, endRow);
            DefaultTableModel model = new DefaultTableModel();


            for (Column column : table.getColumns()) {
                model.addColumn(column.getName());
            }
            for (Map<String, String> rowData : pageRows) {
                model.addRow(rowData.values().toArray());
            }
            JTable pageTable = new JTable(model);
            pageTable.setPreferredScrollableViewportSize(new Dimension(200, pageTable.getRowHeight() * rows));
            JScrollPane scrollPane = new JScrollPane(pageTable);
            scrollPane.setBorder(BorderFactory.createTitledBorder("Page " + (pageIndex + 1)));
            tablesPanel.add(scrollPane);
            startRow = endRow;

        }
        tablesPanel.setPreferredSize(new Dimension(200, 200));
        tablesPanel.revalidate();
        tablesPanel.repaint();
    }


    public void refreshTablesPanel() {
        tablesPanel.removeAll();
        for (Table table : database.getAllTables()) {
            JTable jTable = new JTable(toTableModel(table));
            jTable.setName(table.getName());
            JScrollPane scrollPane = new JScrollPane(jTable);
            scrollPane.setBorder(BorderFactory.createTitledBorder(table.getName()));
            tablesPanel.add(scrollPane);
        }
        tablesPanel.revalidate();
        tablesPanel.repaint();
    }
    private DefaultTableModel toTableModel(Table table) {
        DefaultTableModel model = new DefaultTableModel();
        for (Column column : table.getColumns()) {
            model.addColumn(column.getName());
        }
        for (Map<String, String> row : table.getRows()) {
            model.addRow(row.values().toArray());
        }
        return model;
    }

    private void insertIntoTable(String sql) {
        Pattern insertPattern = Pattern.compile("INSERT INTO (\\w+) \\(([^)]+)\\) VALUES \\(([^)]+)\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = insertPattern.matcher(sql);
        if (matcher.find()) {
            String tableName = matcher.group(1);
            String columnNames = matcher.group(2);
            String valuesPart = matcher.group(3);
            List<String> columns = Arrays.asList(columnNames.split(","));
            List<String> values = Arrays.asList(valuesPart.split(","));
            columns = columns.stream().map(String::trim).collect(Collectors.toList());
            values = values.stream().map(String::trim).collect(Collectors.toList());

            List<String> finalColumns = columns;
            List<String> finalValues = values;
            System.out.println("Columns: " + finalColumns);
            System.out.println("Values: " + finalValues);

            SwingUtilities.invokeLater(() -> {
                try {
                    System.out.println("Inserting into table: " + tableName);
                    System.out.println("Inserting columns: " + finalColumns);
                    System.out.println("Inserting values: " + finalValues);
                    database.insertIntoTable(tableName, finalColumns, finalValues);
                    JTable table = findTableInUI(tableName);
                    if (table != null) {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.addRow(finalValues.toArray());
                        //addRowToTable(tableName, finalValues);
                        updateTableVis(tableName, getPages(), getRows());
                        table.revalidate();
                        table.repaint();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Row did not insert.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    //updatePageAfterInsert(tableName);
                    JOptionPane.showMessageDialog(null, "Row inserted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (TableNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Table Not Found", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            });
        } else {
            JOptionPane.showMessageDialog(this, "Invalid insert statement", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    public void updateTableVisualization(String tableName, List<String> values) {
        SwingUtilities.invokeLater(() -> {
            JTable table = findTableInUI(tableName);
            if (table != null) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(values.toArray());
                table.revalidate();
                table.repaint();
            } else {
                System.err.println("Table '" + tableName + "' not found in the visualization.");
            }
        });
    }

    private JTable findTableInUI(String tableName) {
        for (Component comp : tablesPanel.getComponents()) {
            if (comp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) comp;
                JTable table = (JTable) scrollPane.getViewport().getView();
                if (table.getName().equals(tableName)) {
                    return table;
                }
            }
        }
        return null;
    }


    private void updatePageAfterInsert(String tableName) {
        int rows = rowsCountPerTable.getOrDefault(tableName, 0) + 1;
        int rowsPerPage = getRowsPerPage(tableName);
        int currentPageIndex = getCurrentPageIndex(tableName);
        int totalPages = (int) Math.ceil((double) rows / rowsPerPage);

        rowsCountPerTable.put(tableName, rows);

        if (totalPages > currentPageIndex + 1) {
            currentPages.put(tableName, currentPageIndex + 1);
        }
    }

    public void addRowToTable(String tableName, List<String> values) {
        SwingUtilities.invokeLater(() -> {
            JScrollPane scrollPane = (JScrollPane) tables.get(tableName);
            if (scrollPane != null) {

                JTable table = (JTable) scrollPane.getViewport().getView();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int currentPage = getCurrentPageIndex(tableName);
                int rowCount = rowsCountPerTable.getOrDefault(tableName, 0);
                int maxRows = getRowsPerPage(tableName);
                if (rowCount < maxRows * currentPage) {

                    model.addRow(values.toArray(new Object[0]));
                    rowsCountPerTable.put(tableName, rowCount + 1);

                } else {

                    JOptionPane.showMessageDialog(null, "Error in addRowToTable: " + tableName, "Error", JOptionPane.ERROR_MESSAGE);

                }

                table.revalidate();
                table.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Table not found in UI: " + tableName, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


    private ParseTree processSQL(String sql){
        //convert text to ANTLR input stream
        CharStream charStream = CharStreams.fromString(sql);
        //initiate lexer
        PostgreSQLLexer lexer = new PostgreSQLLexer(charStream);
        //generate tokens from lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //initiate parser
        PostgreSQLParser parser = new PostgreSQLParser(tokens);
        //starting rule
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                JOptionPane.showMessageDialog(TreeVisualization.this, "Failed to parse SQL at line " + line + ": " + msg, "Syntax Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return parser.stmt();
    }

    public int getCurrentPageIndex(String tableName) {
        return currentPages.getOrDefault(tableName, 0);
    }

    public int getRowsPerPage(String tableName) {
        return rowsPerPage.getOrDefault(tableName, 10);
    }


    public static void main(String [] args){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Database Visualizer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new TreeVisualization());
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
