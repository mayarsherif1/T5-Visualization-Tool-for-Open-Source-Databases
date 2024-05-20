package Frontend;

import Backend.BRIN.BrinBlock;
import Backend.BRIN.BrinIndex;
import Backend.Bitmap.BitmapIndex;
import Backend.Database.Column;
import Backend.Database.Database;
import Backend.Database.Table;
import Backend.Exception.TableNotFoundException;
import Backend.GRID.Grid;
import Backend.HashTable.ExtensibleHashTable;
import Backend.HashTable.LinearHashingIndex;
import Backend.KDTree.KDTree;
import Backend.NewSQLListener;
import Backend.Node;
import Backend.QuadTree.QuadTree;
import antlr4.PostgreSQLLexer;
import antlr4.PostgreSQLParser;
import com.yworks.yfiles.view.GraphComponent;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class MainApp extends JPanel {
    private JTabbedPane tabbedPane;
    private TreePanel treePanel;
    private JTextField sqlTestField;
    private Point lastDragPoint;
    private JPanel tablesPanel;
    private Map<String,JComponent> tables;
    private Database database;
    private BrinIndex brinIndex;
    private Random random = new Random();

    private JList<String> indexList;
    private DefaultListModel<String> indexListModel;
    private Map<String, JComponent> indexVisualizations;


    public MainApp() {
        database= new Database();
        setLayout(new BorderLayout());
        tabbedPane= new JTabbedPane();
        treePanel = new TreePanel(null);
        indexListModel = new DefaultListModel<>();
        indexList = new JList<>(indexListModel);
        indexVisualizations = new HashMap<>();
        indexList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        indexList.addListSelectionListener(this::indexSelected);
        JScrollPane indexScrollPane = new JScrollPane(indexList);
        indexScrollPane.setPreferredSize(new Dimension(200, getHeight()));
        indexScrollPane.setBorder(BorderFactory.createTitledBorder("Indexes"));


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
        add(indexScrollPane, BorderLayout.WEST);

    }

    private void indexSelected(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String selectedIndex = indexList.getSelectedValue();
            if (selectedIndex != null && indexVisualizations.containsKey(selectedIndex)) {
                JComponent visualization = indexVisualizations.get(selectedIndex);
                treePanel.removeAll();
                treePanel.setLayout(new BorderLayout());
                treePanel.add(visualization, BorderLayout.CENTER);
                treePanel.revalidate();
                treePanel.repaint();
            }
        }
    }


    private void setSqlPanel() {
        JPanel sqlPanel = new JPanel();
        JButton submitButton = new JButton("Visualize");
        JButton generateDataButton = new JButton("Generate Data");

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
                    try {
                        createIndexFromTable(sql);
                    } catch (TableNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    visualizeRelationalAlgebraTree(sql);
                }
            }
        });

        generateDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tableName = JOptionPane.showInputDialog(MainApp.this, "Enter the table name for data generation:");
                String numRecordsString = JOptionPane.showInputDialog(MainApp.this, "Enter the number of records to generate:");
                if (tableName != null && !tableName.isEmpty() && numRecordsString != null && !numRecordsString.isEmpty()) {
                    try {
                        int numRecords = Integer.parseInt(numRecordsString.trim());
                        //generateAndInsertData(tableName, numRecords);
                        updateTableVis(tableName);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(MainApp.this, "Please enter a valid number for the number of records.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (TableNotFoundException ex) {
                        JOptionPane.showMessageDialog(MainApp.this, "Table not found: " + tableName, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        sqlPanel.add(new JLabel("SQL:"));
        sqlPanel.add(sqlTestField);
        sqlPanel.add(submitButton);
        //sqlPanel.add(generateDataButton);
        add(sqlPanel, BorderLayout.PAGE_START);
        updateVisual();
        refreshTablesPanel();
        tablesPanel.revalidate();
        tablesPanel.repaint();
    }

    private void visualizeRelationalAlgebraTree(String sql) {
        RelationalAlgebraTreeGUI algebraTree = new RelationalAlgebraTreeGUI(sql, database);
        GraphComponent algebraGraph = algebraTree.toGraphComponent();
        treePanel.setGraphComponent(algebraGraph);
        treePanel.repaint();
    }

//    private void generateAndInsertData(String tableName, int numRecords) {
//        try {
//            Table table = database.getTable(tableName);
//            List<Column> columns = table.getColumns();
//            StringBuilder insertSQL;
//
//            for (int i = 0; i < numRecords; i++) {
//                insertSQL = new StringBuilder("INSERT INTO " + tableName + " (");
//                List<String> values = new ArrayList<>();
//                insertSQL.append(String.join(", ", columns.stream().map(Column::getName).collect(Collectors.toList())));
//                insertSQL.append(") VALUES (");
//
//                for (Column column : columns) {
//                    switch (column.getType().toUpperCase()) {
//                        case "INT":
//                            values.add("'"+random.nextInt(1000)+"'");
//                            break;
//                        case "VARCHAR":
//                            values.add("'" + generateRandomString(10) + "'");
//                            break;
//                        default:
//                            values.add("NULL");
//                            break;
//                    }
//                }
//                insertSQL.append(String.join(", ", values));
//                insertSQL.append(");");
//                System.out.println(insertSQL);
//                insertIntoTable(insertSQL.toString());
//            }
//        } catch (TableNotFoundException e) {
//            JOptionPane.showMessageDialog(this, "Table " + tableName + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private String generateRandomString(int length) {
//        return random.ints(48, 122)
//                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
//                .limit(length)
//                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                .toString();
//    }



    private void createIndexFromTable(String sql) throws TableNotFoundException {
        Pattern pattern = Pattern.compile("CREATE INDEX (\\w+) ON (\\w+) USING (\\w+)\\((\\w+)(?:,\\s*(\\w+))?(?:,\\s*(\\w+))?\\);", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        if (matcher.find()) {
            String indexName = matcher.group(1).trim();
            String tableName = matcher.group(2).trim();
            String  Type = matcher.group(3).trim();
            String firstColumnName = matcher.group(4).trim();
            String secondColumnName = (matcher.groupCount() > 4 && matcher.group(5) != null) ? matcher.group(5).trim() : null;
            String thirdColumnName = (matcher.groupCount()>5 && matcher.group(6) != null) ? matcher.group(6).trim() : null;

            switch (Type.toLowerCase()) {
                case "b_tree":
                    System.out.println("createBPlusTree");
                    createBPlusTree(tableName, firstColumnName);
                    break;
                case "kd_tree":
                    System.out.println("Creating KD Tree Index");
                    if (secondColumnName == null) {
                        System.out.println("Error: KD tree requires two columns.");
                    } else {
                        createKDTree(tableName, firstColumnName, secondColumnName);
                    }
                    break;
                case "quad_tree":
                    if (secondColumnName == null) {
                        JOptionPane.showMessageDialog(this, "Quad tree requires two columns.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        System.out.println("Creating Quad Tree Index");
                        createQuadTree(tableName, firstColumnName, secondColumnName);
                    }
                    break;
                case "brin":
                    System.out.println("Creating BRIN Index");
                    createBrinIndex(tableName, firstColumnName);
                    break;
                case "bitmap":
                    System.out.println("Creating Bitmap Index");
                    visualizeColumnBitmap(tableName, firstColumnName);
                    break;
                case "grid":
                    System.out.println("Creating Grid Index");
                    createGridIndex(tableName, firstColumnName, secondColumnName,thirdColumnName);
                    break;
                case "extensible_hashtable":
                    System.out.println("Creating Extensible Hash Table Index");
                    createExtensibleHashTableIndex(tableName, firstColumnName);
                    break;
                case "linear_hashtable":
                    System.out.println("Creating Linear Hash Table Index");
                    createLinearHashTable(tableName, firstColumnName);
                    break;
                default:
                    System.out.println("Unsupported tree type specified.");
                    break;
            }
            updateTableVis(tableName);
        } else {
            System.out.println("Invalid SQL syntax for creating index.");
        }
    }

    private void createLinearHashTable(String tableName, String columnName) {
        try {
            Table table = database.getTable(tableName);
            if (table == null) {
                JOptionPane.showMessageDialog(this, "Table not found: " + tableName, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int bucketCapacity = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the bucket capacity for the linear hash table:", "Bucket Capacity", JOptionPane.QUESTION_MESSAGE));
            double threshold = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter the load factor threshold for the linear hash table (e.g., 0.75):", "Threshold", JOptionPane.QUESTION_MESSAGE));

            LinearHashingIndex hashTable = new LinearHashingIndex(bucketCapacity, threshold);
            List<String> columnData = table.getColumnData(columnName);
            for (Object key : columnData) {
                if (key != null) {
                    hashTable.insert(Integer.parseInt(key.toString()));
                }
            }
            LinearHashTableGUI linearHashTableGUI = new LinearHashTableGUI(hashTable);
            visualizeHashTable(linearHashTableGUI);

            indexVisualizations.put(tableName + "_LinearHashtable_" + columnName, linearHashTableGUI.getGraphComponent());
            indexListModel.addElement(tableName + "_LinearHashtable_" + columnName);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numerical values.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void visualizeHashTable(LinearHashTableGUI linearHashTableGUI) {
        SwingUtilities.invokeLater(() -> {
            treePanel.removeAll();
            treePanel.setGraphComponent(linearHashTableGUI.getGraphComponent());
            treePanel.revalidate();
            treePanel.repaint();

        });
    }

    private void createExtensibleHashTableIndex(String tableName, String firstColumnName) {
        try {
            Table table = database.getTable(tableName);
            List<String> columnData = table.getColumnData(firstColumnName);
            int rows = getRows();
            if (columnData.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No data found in the column: " + firstColumnName, "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int bucketCapacity = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the bucket capacity for the extensible hash table:", "Bucket Capacity", JOptionPane.QUESTION_MESSAGE));

            ExtensibleHashTable hashTable = new ExtensibleHashTable(bucketCapacity-1);
            for (String key : columnData) {
                if (key != null && !key.isEmpty()) {
                    hashTable.insert(Integer.parseInt(key.replace("'", "").trim()));
                }
            }
            EHashTableGUI extensibleHashTableGUI = new EHashTableGUI(hashTable);
            visualizeExtensibleHashTable(extensibleHashTableGUI);
            indexVisualizations.put(tableName + "_ExtensibleHashtable_" + firstColumnName, extensibleHashTableGUI.getGraphComponent());
            indexListModel.addElement(tableName + "_ExtensibleHashtable_" + firstColumnName);
        } catch (TableNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Table not found: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void visualizeExtensibleHashTable(EHashTableGUI extensibleHashTableGUI) {
        SwingUtilities.invokeLater(() -> {
            treePanel.removeAll();
            treePanel.setGraphComponent(extensibleHashTableGUI.getGraphComponent());
            treePanel.revalidate();
            treePanel.repaint();
        });
    }

    private void createGridIndex(String tableName, String firstColumnName, String secondColumnName, String thirdColumnName) {
        try {
            Table table = database.getTable(tableName);
            int rows = table.getRows().size();
            int columns = table.getColumns().size();
            Grid gridIndex = new Grid();
            List<int[]> ranges = promptForRanges(columns);

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    Object value = table.getValueAt(i, j);
                    gridIndex.setCellRange(i, j, ranges.get(j));
                    try {
                        int intValue = Integer.parseInt(value.toString().replace("'", "").trim());
                        gridIndex.addToBucket(i, j, intValue);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid integer format in grid data: " + e.getMessage());
                    }
                }
            }
            GridGUI gridIndexGUI = new GridGUI(gridIndex);

            visualizeGridIndex(gridIndexGUI);
            indexVisualizations.put(tableName + "_GridIndex_" + firstColumnName + " & " + secondColumnName + " & " + thirdColumnName, gridIndexGUI.getGraphComponent());
            indexListModel.addElement(tableName + "_GridIndex_" + firstColumnName + " & " + secondColumnName + " & " + thirdColumnName);
        } catch (TableNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Table not found: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating grid index: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<int[]> promptForRanges(int columns) {
        List<int[]> ranges = new ArrayList<>();
        for (int i = 0; i < columns; i++) {
            String range = JOptionPane.showInputDialog(this, "Enter range for column " + (i + 1) + " (format: min,max):");
            String[] parts = range.split(",");
            int min = Integer.parseInt(parts[0].trim());
            int max = Integer.parseInt(parts[1].trim());
            ranges.add(new int[]{min, max});
        }
        return ranges;
    }

    private void visualizeGridIndex(GridGUI gridIndexGUI) {
        SwingUtilities.invokeLater(() -> {
            treePanel.removeAll();
            treePanel.setGraphComponent(gridIndexGUI.getGraphComponent());
            treePanel.revalidate();
            treePanel.repaint();
        });
    }

    private void visualizeColumnBitmap(String tableName, String columnName) {
        try {
            List<String> data = database.getDataFromTable(tableName, columnName);
            Map<String, BitmapIndex> bitmapMap = new HashMap<>();

            for (int i = 0; i < data.size(); i++) {
                String value = data.get(i).replace("'", "").trim();
                BitmapIndex bitmapIndex = bitmapMap.computeIfAbsent(value, v -> new BitmapIndex(data.size()));
                bitmapIndex.set(i);
            }

            visualizeAllBitmaps(bitmapMap, tableName, columnName);


        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to create Bitmap Index for column " + columnName + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void visualizeAllBitmaps(Map<String, BitmapIndex> bitmapMap, String tableName, String columnName) {
        SwingUtilities.invokeLater(() -> {
            treePanel.removeAll();
            //tabbedPane.removeAll();
            JTabbedPane tabbedPane = new JTabbedPane();
            bitmapMap.forEach((value, bitmapIndex) -> {
                GraphComponent graphComponent = new GraphComponent();
                BitmapGUI bitmapGUI = new BitmapGUI(Collections.singletonList(bitmapIndex), Collections.singletonList(value));
                graphComponent = bitmapGUI.getGraphComponent();
                tabbedPane.addTab(value, graphComponent);

            });
            indexVisualizations.put(tableName + "_BitMap_" + columnName, tabbedPane);
            indexListModel.addElement(tableName + "_BitMap_" + columnName);
            treePanel.add(tabbedPane, BorderLayout.CENTER);
            treePanel.revalidate();
            treePanel.repaint();
        });
    }


    private void createQuadTree(String tableName, String columnName, String secondColumnName) {
        try {
            List<String> xData = database.getDataFromTable(tableName, columnName);
            List<String> yData = database.getDataFromTable(tableName, secondColumnName);
            System.out.println("xData: " + xData);
            System.out.println("yData: " + yData);

            List<Backend.Point> points = new ArrayList<>();
            for (int i = 0; i < xData.size(); i++) {
                int x = Integer.parseInt(xData.get(i).replace("'", "").trim());
                int y = Integer.parseInt(yData.get(i).replace("'", "").trim());
                System.out.println("x: " + x + ", y: " + y);
                points.add(new Backend.Point(x, y));
            }

            QuadTree quadTree = new QuadTree(new Rectangle(0, 0, 400, 400), 4);
            for (Backend.Point point : points) {
                quadTree.insert(point);
            }
            QuadTreeGUI quadTreeGUI = new QuadTreeGUI(quadTree);

            visualizeQuadTree(quadTreeGUI);

            indexVisualizations.put(tableName + "_QuadTree_" + columnName + " & " + secondColumnName, quadTreeGUI);
            indexListModel.addElement(tableName + "_QuadTree_" + columnName + " & " + secondColumnName);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error parsing coordinate data.", "Data Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to create and visualize QuadTree.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void visualizeQuadTree(QuadTreeGUI quadTreeGUI) {
        SwingUtilities.invokeLater(() -> {
            treePanel.removeAll();
            quadTreeGUI.applyHierarchicLayout();
            treePanel.add(quadTreeGUI,BorderLayout.CENTER);
            //treePanel.setGraphComponent(quadTreeGUI.getGraphComponent());
            treePanel.revalidate();
            treePanel.repaint();
        });
    }



    private void createKDTree(String tableName, String firstColumnName, String secondColumnName) {
        List<String> xData = database.getDataFromTable(tableName, firstColumnName);
        List<String> yData = database.getDataFromTable(tableName, secondColumnName);
        System.out.println("xData: " + xData);
        System.out.println("yData: " + yData);

        List<Backend.Point> points = new ArrayList<>();
        //KDTree kdTree = new KDTree();
        for (int i = 0; i < xData.size(); i++) {
            int x = Integer.parseInt(xData.get(i).replace("'", "").trim());
            int y = Integer.parseInt(yData.get(i).replace("'", "").trim());
            Backend.Point newPoint = new Backend.Point(x, y, firstColumnName, secondColumnName);
            points.add(newPoint);
        }
        KDTree kdTree = new KDTree(points);
        KDTreeGUI kdTreeGUI = new KDTreeGUI(kdTree);

        visualizeKDTree(kdTreeGUI);
        indexVisualizations.put(tableName + "_KDTree_" + firstColumnName + " & " + secondColumnName, kdTreeGUI.getGraphComponent());
        indexListModel.addElement(tableName + "_KDTree_" + firstColumnName + " & " + secondColumnName);

    }

    private void visualizeKDTree(KDTreeGUI kdTreeGUI) {
        SwingUtilities.invokeLater(() -> {
            treePanel.removeAll();
            treePanel.setGraphComponent(kdTreeGUI.getGraphComponent());
            treePanel.revalidate();
            treePanel.repaint();
        });
    }

    private void createBPlusTree(String tableName, String columnName) {
        List<String> data = database.getDataFromTable(tableName, columnName);
        System.out.println("createBPlusTree data: " + data);
        BPlusTreeGUI<String> bPlusTreeGUI = new BPlusTreeGUI<>();

        visualizeBPlusTree(bPlusTreeGUI,data);
        indexVisualizations.put(tableName + "_BPlusTree_" + columnName, bPlusTreeGUI.getGraphComponent());
        indexListModel.addElement(tableName + "_BPlusTree_" + columnName);

        System.out.println("createBPlusTree after visualizeBPlusTree method");
    }

    private void visualizeBPlusTree(BPlusTreeGUI bPlusTreeGUI,List<String> data) {
        SwingUtilities.invokeLater(() -> {
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

    private void createBrinIndex(String tableName, String firstColumnName) {
        int pages = getPages();
        int rows = getRows();
        brinIndex = new BrinIndex();
        List<String> data = database.getDataFromTable(tableName, firstColumnName);
        if (!data.isEmpty()) {
            System.out.println("First few data elements: " + data.subList(0, Math.min(5, data.size())));
        }

        for (int i = 0; i < pages; i++) {
            int startIndex = i * rows;
            int endIndex = Math.min((i + 1) * rows, data.size());
            if (startIndex< endIndex){
                List<String> pageData = data.subList(startIndex, endIndex);
                try{
                    int min = Integer.parseInt(Collections.min(pageData).replace("'", "").trim());
                    int max = Integer.parseInt(Collections.max(pageData).replace("'", "").trim());
                    brinIndex.addBlock(new BrinBlock(min, max));
                }
                catch (NumberFormatException e){
                    System.err.println("Error processing numbers: " + e.getMessage());

                }
            }
        }
        BrinGUI brinGUI = new BrinGUI(brinIndex);
        visualizeBrinIndex(brinGUI);
        indexVisualizations.put(tableName + "_BRIN_" + firstColumnName, brinGUI.getGraphComponent());
        indexListModel.addElement(tableName + "_BRIN_" + firstColumnName);
    }

    private void visualizeBrinIndex(BrinGUI brinGUI) {
        SwingUtilities.invokeLater(() -> {
            treePanel.removeAll();
//            treePanel.add(brinGUI,BorderLayout.CENTER);
            treePanel.setGraphComponent(brinGUI.getGraphComponent());
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
                    updateTableVis(tableName);
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

    public void updateTableVis(String tableName) throws TableNotFoundException {
        displayPages(tableName,getPages(),getRows());
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
            List<String> columnNames = Arrays.asList(matcher.group(2).split(",")).stream().map(String::trim).collect(Collectors.toList());
            List<String> values = Arrays.asList(matcher.group(3).split(",")).stream().map(String::trim).collect(Collectors.toList());
            try {
                Table table = database.getTable(tableName);
                List<Column> columns = table.getColumns();
                List<String> formattedValues = new ArrayList<>();

                for (int i = 0; i < values.size(); i++) {
                    Column column = columns.get(columnNames.indexOf(columnNames.get(i)));
                    String value = values.get(i);
                    switch (column.getType().toUpperCase()) {
                        case "VARCHAR":
                            formattedValues.add(value);
                            break;
                        case "INT":
                            formattedValues.add(value.replaceAll("'", ""));
                            break;
                        default:
                            formattedValues.add(value);
                    }
                }
                database.insertIntoTable(tableName, columnNames, formattedValues);
                SwingUtilities.invokeLater(() -> {
                    try {
                        updateTableVis(tableName);
                    } catch (TableNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                JOptionPane.showMessageDialog(null, "Row inserted and UI updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (TableNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Table not found: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid insert statement", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

//    private void insertIntoTable(String sql) {
//        Pattern insertPattern = Pattern.compile("INSERT INTO (\\w+) \\(([^)]+)\\) VALUES \\(([^)]+)\\)", Pattern.CASE_INSENSITIVE);
//        Matcher matcher = insertPattern.matcher(sql);
//        if (matcher.find()) {
//            String tableName = matcher.group(1);
//            String columnNames = matcher.group(2);
//            String valuesPart = matcher.group(3);
//            System.out.println("Attempting to insert into table: " + tableName);
//            List<String> columns = Arrays.asList(columnNames.split(","));
//            List<String> values = Arrays.asList(valuesPart.split(","));
//            columns = columns.stream().map(String::trim).collect(Collectors.toList());
//            values = values.stream().map(String::trim).collect(Collectors.toList());
//
//            List<String> finalColumns = columns;
//            List<String> finalValues = values;
//            System.out.println("Columns: " + finalColumns);
//            System.out.println("Values: " + finalValues);
//
//            SwingUtilities.invokeLater(() -> {
//                try {
//                    System.out.println("Inserting into table: " + tableName);
//                    System.out.println("Inserting columns: " + finalColumns);
//                    System.out.println("Inserting values: " + finalValues);
//                    database.insertIntoTable(tableName, finalColumns, finalValues);
//                    JTable table = findTableInUI(tableName);
//                    if (table != null) {
//                        DefaultTableModel model = (DefaultTableModel) table.getModel();
//                        model.addRow(finalValues.toArray());
//                        //addRowToTable(tableName, finalValues);
//                        updateTableVis(tableName);
//                        table.revalidate();
//                        table.repaint();
//                    }
//                    else {
//                        JOptionPane.showMessageDialog(null, "Row did not insert.", "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                    //updatePageAfterInsert(tableName);
//                    JOptionPane.showMessageDialog(null, "Row inserted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
//                } catch (TableNotFoundException e) {
//                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Table Not Found", JOptionPane.ERROR_MESSAGE);
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                }
//
//            });
//        } else {
//            JOptionPane.showMessageDialog(this, "Invalid insert statement", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }

    private JTable findTableInUI(String tableName) {
        for (Component comp : tablesPanel.getComponents()) {
            if (comp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) comp;
                JTable table = (JTable) scrollPane.getViewport().getView();
                if (table.getName() != null && tableName.equals(table.getName())) {
                    return table;
                }
            }
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
                JOptionPane.showMessageDialog(MainApp.this, "Failed to parse SQL at line " + line + ": " + msg, "Syntax Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return parser.stmt();
    }

    public static void main(String [] args){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Database Visualizer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new MainApp());
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
