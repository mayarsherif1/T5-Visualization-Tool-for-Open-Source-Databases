package Backend.RelationalAlgebra;

import Backend.Database.Database;
import Backend.Database.Table;

public class TableNode extends RelationalAlgebraNode{
    private String tableName;
    private Table table;
    private boolean isValid;


    public TableNode(String tableName, Database database) {
        this.tableName = tableName;
        this.table = fetchTableFromDatabase(tableName, database);
        this.isValid = true;
    }

    private Table fetchTableFromDatabase(String tableName, Database database) {
        if (database == null) {
            System.err.println("Database object is null, cannot fetch table: " + tableName);
            return null;
        }
        try {
            System.out.println("Fetching table from database: " + tableName);
            return database.getTable(tableName);
        } catch (Exception e) {
            System.err.println("Table not found: " + tableName + ". Error: " + e.getMessage());
            return null;
        }
    }


    @Override
    public void accept(RelationalAlgebraVisitor visitor) {
        if (!isValid) {
            System.err.println("Error: Invalid table - " + tableName);
        } else {
            visitor.visit(this);
        }
    }

    @Override
    int estimateResultSize() {
        return (table != null) ? table.getRows().size() : 0;
    }

    public String getTableName() {
        return tableName;
    }

    public Table getTable() {
        return table;
    }
    public boolean isValid() {
        return isValid;
    }
}
