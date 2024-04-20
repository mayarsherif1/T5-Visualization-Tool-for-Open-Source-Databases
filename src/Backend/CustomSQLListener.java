package Backend;

import Backend.Database.Database;
import Backend.Database.Table;
import Frontend.TreeVisualization;
import antlr4.PostgreSQLParser;
import antlr4.PostgreSQLParserBaseListener;

import java.util.ArrayList;
import java.util.List;

public class CustomSQLListener extends PostgreSQLParserBaseListener {
    private Database database;
    private TreeVisualization visualization;

    public CustomSQLListener(Database database, TreeVisualization visualization){
        this.database=database;
        this.visualization=visualization;
    }

    @Override
    public void enterCreatetablespacestmt(PostgreSQLParser.CreatetablespacestmtContext createTable) {
        String tableName = createTable.name().getText();
        String location = createTable.sconst().getText().replace("'","");


        System.out.println("Creating tablespace: " + tableName + " at location: " + location);
        Table table= new Table(tableName);
        database.addTable(table);
        visualization.updateVisual();

    }

    @Override
    public void enterInsertstmt(PostgreSQLParser.InsertstmtContext insertStmt){
        String tableName = insertStmt.insert_target().qualified_name().getText();
        List<String> columnNames = new ArrayList<>();
        List<String> values= new ArrayList<>();

        if(insertStmt.insert_rest().DEFAULT()==null&& insertStmt.insert_rest().selectstmt()==null) {
            if (insertStmt.insert_rest().insert_column_list() != null) {
                for (PostgreSQLParser.Insert_column_itemContext columnItemContext : insertStmt.insert_rest().insert_column_list().insert_column_item()) {
                    columnNames.add(columnItemContext.colid().getText());
                }
            }

            Table table = null;
            try {
                table = database.getTable(tableName);
            } catch (TableNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (table != null) {
                table.insertRow(columnNames, values);
                System.out.println("Inserted into table: " + tableName + "Column: " + columnNames + "Values: " + values);
            } else {
                System.out.println("Table not found " + tableName);
            }
        }
        else if(insertStmt.insert_rest().DEFAULT()!=null) {

            Table table1 = null;
            try {
                table1 = database.getTable(tableName);
            } catch (TableNotFoundException e) {
                throw new RuntimeException(e);
            }
            if(table1 !=null){
                table1.insertDefRow();
                System.out.println("Inserted default values into table: " + tableName);

            }
            else {
                System.out.println("Table not found: " + tableName);

            }
        }
        visualization.updateVisual();

    }


}
