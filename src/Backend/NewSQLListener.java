package Backend;

import antlr4.PostgreSQLParser;
import antlr4.PostgreSQLParser.ColumnDefContext;
import antlr4.PostgreSQLParserBaseListener;

public class NewSQLListener extends PostgreSQLParserBaseListener {
    private Table currentTable;
    private Database database;

    public NewSQLListener(Database database) {
        //super();
        this.database =database;
    }

    @Override
    public void enterCreatetablespacestmt(PostgreSQLParser.CreatetablespacestmtContext ctx) {
        String tableName = ctx.name().getText();
        if(!database.containsTable(tableName)){
            currentTable = new Table(tableName);
            database.addTable(currentTable);

        }
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

//    public List<Table> getTables() {
//        return tables;
//    }
}
