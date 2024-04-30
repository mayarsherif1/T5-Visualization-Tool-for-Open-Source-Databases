package Backend.RelationalAlgebra;

import Backend.Database.Database;
import Backend.Database.Table;

public class TableNode extends RelationalAlgebraNode{
    private String tableName;
    private Table table;

    public TableNode(String tableName, Database database) {
        this.tableName = tableName;
        this.table = fetchTableFromDatabase(tableName, database);
    }

    private Table fetchTableFromDatabase(String tableName, Database database) {
        try {
            return database.getTable(tableName);
        } catch (Exception e) {
            System.err.println("Table not found: " + tableName);
            return null;
        }
    }
    @Override
    public void accept(RelationalAlgebraVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    int estimateResultSize() {
        return table != null ? table.getRows().size() : 0;

    }

    public String getTableName() {
        return tableName;
    }

    public Table getTable() {
        return table;
    }
}
