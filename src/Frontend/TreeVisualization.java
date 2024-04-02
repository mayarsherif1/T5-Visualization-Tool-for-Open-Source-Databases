package Frontend;

import Backend.*;
import antlr4.PostgreSQLLexer;
import antlr4.PostgreSQLParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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

//    private void testStaticContent() {
//        JLabel testTreeLabel = new JLabel("Test Tree Content");
//        treePanel.add(testTreeLabel);
//
//        JLabel testTableLabel = new JLabel("Test Table: 5 pages, 100 rows");
//        testTableLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        tablesPanel.add(testTableLabel);
//    }

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
                }
                else if(sql.trim().toLowerCase().startsWith("insert into")){
                    insertIntoTable(sql);
                }
                else {
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

    private void executeSQL(String sql) {
        if(sql.trim().isEmpty()){
            return;
        }
        ParseTree tree = processSQL(sql);
        NewSQLListener listener = new NewSQLListener(database);
        ParseTreeWalker.DEFAULT.walk(listener, tree);
        updateVisual();

    }

    private void getInsertInto(String sql) throws TableNotFoundException {
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
                addRowToTable(tableName, values);
                updateTableVis(tableName, getPages(), getRows());
            } catch (TableNotFoundException e) {
                throw new TableNotFoundException(e.getMessage());
            }
        }
    }

    private void setInsertInto(String sql) {
        Pattern pattern = Pattern.compile("INSERT INTO (\\w+) \\((.*)\\) VALUES \\((.*)\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        if (matcher.find()) {
            String tableName = matcher.group(1).trim();
            String[] columnNames = matcher.group(2).trim().split("\\s*,\\s*");
            String[] values = matcher.group(3).trim().split("\\s*,\\s*(?=(?:[^']*'[^']*')*[^']*$)");

            System.out.println("/n Table Name: " + tableName); // Debugging
            System.out.println("Column Names: " + Arrays.toString(columnNames)); // Debugging
            System.out.println("Values: " + Arrays.toString(values)); // Debugging

            try {

                database.insertIntoTable(tableName, Arrays.asList(columnNames), Arrays.asList(values));
            } catch (TableNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Table not found: " + tableName, "Error", JOptionPane.ERROR_MESSAGE);
            }
            //addRowToTable(tableName, values);
            updatePageAfterInsert(tableName);
        } else {
            JOptionPane.showMessageDialog(this, "The SQL statement is not in the correct format for INSERT INTO.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String[] getRowData(ParseTree tree) {
        String sql = tree.getText();
        Pattern pattern = Pattern.compile("INSERT INTO \\w+ VALUES \\((.*)\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        if(matcher.find()){
            String values = matcher.group(1);
            return values.split(",");

        }
        else {
            throw new IllegalArgumentException("Row data not found in INSERT INTO statement");
        }
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

    private void refreshTablesPanel() {
        tablesPanel.removeAll();
        for(Table table: database.getAllTables()){
            JPanel tablePanel = new JPanel();
            tablePanel.setBorder(BorderFactory.createTitledBorder(table.getName().toLowerCase()));
            for(Column column : table.getColumns()){
                JLabel columnLabel = new JLabel(column.getName()+" ("+ column.getType()+ ")");
                tablePanel.add(columnLabel);
            }
            tablesPanel.add(tablePanel);
        }
        tablesPanel.revalidate();
        tablesPanel.repaint();
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
                    String columnsPart = matcher.group(2).trim();
                    List<Column> columns = parseColumns(columnsPart);
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


    //    private void CreateTableTab(String sql) {
//        JTextField pagesField = new JTextField("1");
//        JTextField rowsField = new JTextField("10");
//        Object[] message = {
//                "Pages: ", pagesField,
//                "Rows: ", rowsField
//        };
//        int option = JOptionPane.showConfirmDialog(null,message, "Enter Table Details", JOptionPane.OK_CANCEL_OPTION);
//        if(option==JOptionPane.OK_OPTION) {
//            try {
//                int pages = Integer.parseInt(pagesField.getText());
//                int rows = Integer.parseInt(rowsField.getText());
//                Pattern pattern = Pattern.compile("CREATE TABLE (\\w+) \\((.*)\\)", Pattern.CASE_INSENSITIVE);
//                Matcher matcher = pattern.matcher(sql);
//                if(matcher.find()){
//                    String tableName = matcher.group(1);
//                    String columnsPart = matcher.group(2);
//                    List<Column> columns = parseColumns(columnsPart);
//                    database.createTable(tableName, columns);
//                    updateTableVis(tableName, pages, rows);
//                }
//                else {
//                    JOptionPane.showMessageDialog(this, "Table name not found in the command.", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//            catch (NumberFormatException e){
//                JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
//            } catch (TableNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
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
        if (tables.containsKey(tableName.toLowerCase())){
            JLabel tableLabel =(JLabel) tables.get(tableName.toLowerCase());
            tableLabel.setText(String.format("%s: %d pages,%d rows", tableName, pages, rows));

        }
        else {
            JLabel tableLabel = new JLabel(String.format("%s: %d pages,%d rows", tableName, pages, rows));
            System.out.print(tableLabel);
            tableLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            tableLabel.setPreferredSize(new Dimension(200, 50));
            tables.put(tableName, tableLabel);
            tablesPanel.add(tableLabel);
        }
        tablesPanel.revalidate();
        tablesPanel.repaint();
    }

    private void displayPages(String tableName, int pages, int rows) throws TableNotFoundException {
        tablesPanel.removeAll();
        tablesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        Table table = database.getTable(tableName);

        List<Column> columns = table.getColumns();

        for (int pageIndex = 0; pageIndex < pages; pageIndex++) {
            JPanel pagePanel = new JPanel(new BorderLayout());
            pagePanel.setBorder(BorderFactory.createTitledBorder("Page " + (pageIndex + 1)));
            JPanel headerPanel = new JPanel(new GridLayout(1, columns.size()));
            for (Column column : columns) {
                JLabel headerLabel = new JLabel(column.getName());
                headerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                headerPanel.add(headerLabel);
            }
            pagePanel.add(headerPanel, BorderLayout.NORTH);
            JPanel rowsPanel = new JPanel(new GridLayout(rows, 1));
            for(int rowIndex = 0; rowIndex < rows; rowIndex++) {
                JPanel rowPanel = new JPanel(new GridLayout(1, columns.size()));
                for (Column column : columns) {
                    JLabel cellLabel = new JLabel();
                    cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    rowPanel.add(cellLabel);
                }
                rowsPanel.add(rowPanel);
            }
            pagePanel.add(rowsPanel, BorderLayout.CENTER);
            tablesPanel.add(pagePanel);
        }

        tablesPanel.revalidate();
        tablesPanel.repaint();
    }


//    private void displayPages(String tableName, int pages, int rows){
//        tablesPanel.removeAll();
//        tablesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//        for (int pageIndex =0; pageIndex< pages; pageIndex++){
//            JPanel pagePanel = new JPanel(new GridLayout(rows, 1));
//            pagePanel.setBorder(BorderFactory.createTitledBorder("Page "+ (pageIndex+1)));
//            pagePanel.setPreferredSize(new Dimension(100,rows*20));
//            for(int rowIndex =0; rowIndex<rows; rowIndex++){
//                JLabel rowLabel = new JLabel("Row "+(rowIndex+1));
//                rowLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//                pagePanel.add(rowLabel);
//            }
//            tablesPanel.add(pagePanel);
//        }
//        tablesPanel.revalidate();
//        tablesPanel.repaint();
//    }

    private void insertIntoTable(String sql) {
        Pattern insertPattern = Pattern.compile("INSERT INTO (\\w+) \\(([^)]+)\\) VALUES \\(([^)]+)\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = insertPattern.matcher(sql);
        if (matcher.find()) {
            String tableName = matcher.group(1).trim();
            String columnNames = matcher.group(2);
            String values = matcher.group(3);

            // Split column names and values
            String[] columns = columnNames.split(",");
            String[] splitValues = values.split(",");

            // Trim whitespace from column names and values
            for (int i = 0; i < columns.length; i++) {
                columns[i] = columns[i].trim();
            }
            for (int i = 0; i < splitValues.length; i++) {
                splitValues[i] = splitValues[i].trim();
            }
            System.out.println("Extracted table name: " + tableName);

            if (tables.containsKey(tableName)) {
                JTable table = findTableInUI(tableName);
                if (table != null) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.addRow(splitValues);
                } else {
                    JOptionPane.showMessageDialog(this, "Table not found in UI: " + tableName, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Table not found: " + tableName, "Error", JOptionPane.ERROR_MESSAGE);
            }
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
                if (scrollPane.getBorder() != null && scrollPane.getBorder() instanceof TitledBorder) {
                    TitledBorder border = (TitledBorder) scrollPane.getBorder();
                    if (border.getTitle().equals(tableName)) {
                        return (JTable) scrollPane.getViewport().getView();
                    }
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
            JComponent component = tables.get(tableName.toLowerCase());
            if (component instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) component;
                JTable table = (JTable) scrollPane.getViewport().getView();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                Object[] rowData = values.toArray(new Object[0]);
                if (model.getRowCount() < getRowsPerPage(tableName)) {
                    model.addRow(rowData);
                } else {
                    try {
                        createNewPageForTable(tableName, values);
                    } catch (TableNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                table.revalidate();
                table.repaint();
            } else {
                System.err.println("Table not found in UI: " + tableName);
            }
        });
    }
    private void createNewPageForTable(String tableName, List<String> values) throws TableNotFoundException {
        DefaultTableModel model = new DefaultTableModel();
        List<Column> columns = database.getTable(tableName).getColumns();
        for (Column col : columns) {
            model.addColumn(col.getName());
        }
        JTable newTable = new JTable(model);
        model.addRow(values.toArray(new Object[0]));
        JScrollPane newScrollPane = new JScrollPane(newTable);
        newScrollPane.setBorder(BorderFactory.createTitledBorder("Page " + (getCurrentPageIndex(tableName) + 1)));
        tablesPanel.add(newScrollPane);
        currentPages.put(tableName, getCurrentPageIndex(tableName) + 1);
        tablesPanel.revalidate();
        tablesPanel.repaint();
    }



//    public void addRowToTable(String tableName, List<String> values) {
//        JComponent component = tables.get(tableName.toLowerCase());
//        if (component instanceof JScrollPane) {
//            JScrollPane scrollPane = (JScrollPane) component;
//            JTable table = (JTable) scrollPane.getViewport().getView();
//            DefaultTableModel model = (DefaultTableModel) table.getModel();
//            Object[] rowData = values.toArray(new Object[0]);
//            model.addRow(rowData);
//            int newRowIndex = model.getRowCount() - 1;
//            table.scrollRectToVisible(table.getCellRect(newRowIndex, 0, true));
//        }
//    }


//    private void addRowToTable(String tableName, String[] rowData) {
//        SwingUtilities.invokeLater(() -> {
//            // Find the visual component (JTable) for the first page
//            JTable table = getFirstPageTable(tableName);
//            if (table != null) {
//                DefaultTableModel model = (DefaultTableModel) table.getModel();
//
//                // Add the row to the table's model
//                if (model.getColumnCount() == rowData.length) {
//                    model.addRow(rowData);
//                    System.out.println("Row added to table: " + tableName);
//                } else {
//                    System.err.println("Mismatch between column count and provided data length.");
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "Table not found: " + tableName, "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//    }

//    private void addRowToTable(String tableName, String[] rowData) {
//        SwingUtilities.invokeLater(() -> {
//            JComponent tableComponent = tables.get(tableName.toLowerCase());
//            if (tableComponent instanceof JScrollPane) {
//                JTable table = (JTable) ((JScrollPane) tableComponent).getViewport().getView();
//                DefaultTableModel model = (DefaultTableModel) table.getModel();
//
//                if (model.getColumnCount() == rowData.length) {
//                    model.addRow(rowData);
//                    System.out.println("Row added to table: " + tableName); // Debugging
//                } else {
//                    System.err.println("Mismatch between column count and provided data length."); // Debugging
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "Table not found: " + tableName, "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//    }

    private JTable getFirstPageTable(String tableName) {
        JComponent component = tables.get(tableName.toLowerCase());
        if (component instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) component;
            return (JTable) scrollPane.getViewport().getView();
        }
        return null;
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
