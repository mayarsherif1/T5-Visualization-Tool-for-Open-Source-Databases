package Frontend;

import Backend.*;
import antlr4.PostgreSQLLexer;
import antlr4.PostgreSQLParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TreeVisualization extends JPanel {
    private JTabbedPane tabbedPane;
    private TreePanel treePanel;
    private JTextField sqlTestField;
    private Point lastDragPoint;
    private JPanel tablesPanel;
    private Map<String,JComponent> tables;
    private Database database;

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
                else {
                    Node rootNode = processSQL(sql);
                    treePanel.setRoot(rootNode);
                    treePanel.repaint();
                }
            }
        });
        sqlPanel.add(new JLabel("SQL:"));
        sqlPanel.add(sqlTestField);
        sqlPanel.add(submitButton);
        add(sqlPanel, BorderLayout.PAGE_START);
    }

    private void executeSQL(String sql) {
        if(sql.trim().isEmpty()){
            return;
        }
        //convert text to ANTLR input stream
        CharStream charStream = CharStreams.fromString(sql);
        //initiate lexer
        PostgreSQLLexer lexer = new PostgreSQLLexer(charStream);
        //generate tokens from lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //initiate parser
        PostgreSQLParser parser = new PostgreSQLParser(tokens);
        ParseTree tree = parser.root();
        ParseTreeWalker walker = new ParseTreeWalker();
        CustomSQLListener listener = new CustomSQLListener(database,this);
        walker.walk(listener, tree);

//        if(sql.toLowerCase().startsWith("create table")){
//            setCreateTable(tree);
//        }
//        else if (sql.toLowerCase().startsWith("insert into")){
//            setInsertInto(tree);
//        }
        updateVisual();

    }

    private void setInsertInto(ParseTree tree) {
        String tableName = getTableName(tree);
        String [] rowData = getRowData(tree);
        addRowToTable(tableName,rowData);
        
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
    public void addColumnToTable(String tableName, String columnName, String columnType) {
        Table table = database.getTable(tableName);
        if (table != null) {
            Column newColumn = new Column(columnName, columnType);
            table.addColumn(newColumn);
        }
    }


    private Node buildTreeFromDatabase() {
        Node rootNode = new Node("Database");
        for(Table table: database.getAllTables()){
            Node tableNode = new Node(table.getName());
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
            tablePanel.setBorder(BorderFactory.createTitledBorder(table.getName()));
            for(Column column : table.getColumns()){
                JLabel columnLabel = new JLabel(column.getName()+" ("+ column.getType()+ ")");
                tablePanel.add(columnLabel);
            }
            tablePanel.add(tablePanel);
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
    

    private void CreateTableTab(String sql) {
        JTextField pagesField = new JTextField();
        JTextField rowsField = new JTextField();
        Object[] message = {
                "Pages: ", pagesField,
                "Rows: ", rowsField
        };
        int option = JOptionPane.showConfirmDialog(null,message, "Enter Table Details", JOptionPane.OK_CANCEL_OPTION);
        if(option==JOptionPane.OK_OPTION) {
            try {
                int pages = Integer.parseInt(pagesField.getText());
                int rows = Integer.parseInt(rowsField.getText());
                Pattern pattern = Pattern.compile("CREATE TABLE (\\w+)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(sql);
                if(matcher.find()){
                    String tableName = matcher.group(1);
                    //System.out.print(tableName);
                    updateTableVis(tableName, pages, rows);
                }
                else {
                    JOptionPane.showMessageDialog(this, "Table name not found in the command.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void updateTableVis(String tableName,int pages,int rows){
        displayPages(tableName,pages,rows);
        if (tables.containsKey(tableName)){
            JLabel tableLabel =(JLabel) tables.get(tableName);
            tableLabel.setText(String.format("%s: %d pages,%d rows", tableName, pages, rows));

        }
        else {
            JLabel tableLabel = new JLabel(String.format("%s: %d pages,%d rows", tableName, pages, rows));
            //JLabel tableLabel = new JLabel("Test Table: 10 pages, 100 rows");
            System.out.print(tableLabel);
            tableLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            tableLabel.setPreferredSize(new Dimension(200, 50));
            tables.put(tableName, tableLabel);
            tablesPanel.add(tableLabel);
        }
        tablesPanel.revalidate();
        tablesPanel.repaint();
    }

    private void displayPages(String tableName, int pages, int rows){
        tablesPanel.removeAll();
        tablesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (int pageIndex =0; pageIndex< pages; pageIndex++){
            JPanel pagePanel = new JPanel(new GridLayout(rows, 1));
            pagePanel.setBorder(BorderFactory.createTitledBorder("Page "+ (pageIndex+1)));
            pagePanel.setPreferredSize(new Dimension(100,rows*20));
            for(int rowIndex =0; rowIndex<rows; rowIndex++){
                JLabel rowLabel = new JLabel("Row "+(rowIndex+1));
                rowLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                pagePanel.add(rowLabel);
            }
            tablesPanel.add(pagePanel);
        }
        tablesPanel.revalidate();
        tablesPanel.repaint();
    }

    private void insertIntoTable(String sql){
        Pattern insertPattern = Pattern.compile("INSERT INTO (\\w+) VALUES \\((.*)\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = insertPattern.matcher(sql);
        if(matcher.find()){
            String tableName = matcher.group(1);
            String values = matcher.group(2);

            String[] splitValues = values.split(",");
            addRowToTable(tableName.trim(), splitValues);
        }
        else {
            JOptionPane.showMessageDialog(this, "Invalid insert statement", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRowToTable(String tableName, String[] rowData) {
        JComponent tableComponent = tables.get(tableName.toLowerCase());
        if(tableComponent instanceof JScrollPane){
            JScrollPane scrollPane = (JScrollPane) tableComponent;
            JViewport viewPort = scrollPane.getViewport();
            JTable table = (JTable) viewPort.getView();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(rowData);
        }
        else {
            JOptionPane.showMessageDialog(this, "Table not found: " + tableName, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Node processSQL(String sql){
        Pattern pattern = Pattern.compile("CREATE TABLE (\\w+)");
        Matcher matcher = pattern.matcher(sql);
        if(matcher.find()){
            String tableName = matcher.group(1);
            System.out.print(tableName);
            Node tableNode = new Node(tableName);
            System.out.print(tableNode);

            return tableNode;
        }

        //convert text to ANTLR input stream
        CharStream charStream = CharStreams.fromString(sql);
        //initiate lexer
        PostgreSQLLexer lexer = new PostgreSQLLexer(charStream);
        //generate tokens from lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //initiate parser
        PostgreSQLParser parser = new PostgreSQLParser(tokens);
        //starting rule
        ParseTree tree = parser.root();

        CustomPostgreSQLListener columnListener = new CustomPostgreSQLListener();
        ParseTreeWalker.DEFAULT.walk(columnListener,tree);
        
        return buildTree(tree);
    }


    private Node buildTree(ParseTree tree) {
        if(tree.getChildCount()==0){
            return new Node(tree.getText());
        }
        Node node = new Node(tree.getClass().getSimpleName().replace("Context",""));
        for(int i=0; i<tree.getChildCount();i++){
            node.addChild(buildTree(tree.getChild(i)));
        }
        return node;
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
