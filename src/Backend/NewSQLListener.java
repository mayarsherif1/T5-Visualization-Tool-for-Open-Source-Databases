package Backend;

import Backend.Database.Column;
import Backend.Database.Database;
import Backend.Database.Table;
import antlr4.PostgreSQLParser;
import antlr4.PostgreSQLParser.ColumnDefContext;
import antlr4.PostgreSQLParserBaseListener;

import java.util.ArrayList;
import java.util.List;

public class NewSQLListener extends PostgreSQLParserBaseListener {
    private Table currentTable;
    private Database database;

    public NewSQLListener(Database database) {
        //super();
        this.database =database;
    }

    @Override
    public void exitCreatetablespacestmt(PostgreSQLParser.CreatetablespacestmtContext ctx){
        if(currentTable!= null){
            database.addTable(currentTable);
            currentTable=null;
        }
    }

    @Override
    public void enterColumnDef(ColumnDefContext ctx){
        if(currentTable != null){
            String columnName = ctx.colid().getText();
            String columnType = ctx.typename().getText();
            Column column = new Column(columnName, columnType);
            currentTable.addColumn(column);
        }
    }

    @Override
    public void enterIndexstmt(PostgreSQLParser.IndexstmtContext ctx){
        String indexName = ctx.opt_index_name().getText();
        String tableName = ctx.relation_expr().qualified_name().getText();
        List<String> columnNames = new ArrayList<>();
        if (ctx.index_params() != null) {
            for (PostgreSQLParser.Index_elemContext elem : ctx.index_params().index_elem()) {
                String columnName = elem.colid().getText();
                columnNames.add(columnName);
            }
        }

        try {
            database.createIndex(indexName,tableName,columnNames);
        } catch (TableNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

//    public List<Table> getTables() {
//        return tables;
//    }
}
