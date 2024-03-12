package Backend;

public class TableNotFoundException extends Exception {
    public TableNotFoundException(String tableName){
        super("Table "+ tableName + "does not exist");
    }
}
