package Backend;

import antlr4.PostgreSQLParser;
import antlr4.PostgreSQLParserBaseListener;
import java.util.ArrayList;
import java.util.List;

public class CustomPostgreSQLListener extends PostgreSQLParserBaseListener {
    private List<Column> columns = new ArrayList<>();

    @Override
    public void enterColumnDef(PostgreSQLParser.ColumnDefContext colDef){
        String columnName = colDef.colid().getText();
        String columnType = colDef.typename().getText();
        Column column = new Column(columnName,columnType);
        columns.add(column);
    }

    public List<Column> getColumns(){
        return columns;
    }


}
