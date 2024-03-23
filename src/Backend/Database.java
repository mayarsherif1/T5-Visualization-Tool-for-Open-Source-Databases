package Backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private Map<String,Table> tables;

    public Database(){
        this.tables=new HashMap<>();
    }

    public void addTable(Table table){
        tables.put(table.getName(), table);
        System.out.println("Table inside database: "+ tables.get(table.getName()));

    }

    public Table getTable(String tableName) throws TableNotFoundException{
        Table table = tables.get(tableName);
        if(table==null){
            throw new TableNotFoundException(tableName);
        }
        return table;
    }

    public List<Table> getAllTables(){
        return new ArrayList<>(tables.values());
    }

    public void insertIntoTable(String tableName, List<String> columnNames, List<String> values) throws TableNotFoundException {
        Table table = tables.get(tableName);

        if(table!=null){
            table.insertRow(columnNames,values);
            System.out.println("Table row inside database: "+ table.getRows());
        }
        else {
            throw new TableNotFoundException(tableName);
        }
    }
    public void createTable(String tableName, List<Column> columns){
        if(!tables.containsKey(tableName)){
            Table newTable = new Table(tableName);
            for(Column column: columns){
                newTable.addColumn(column);
            }
            tables.put(tableName,newTable);
        }
        else {
            System.out.println("Table " + tableName + " already exists.");
        }
    }

    public void insertDefValuesToTable(String tableName){
        Table table = tables.get(tableName);
        if(table!=null){
            table.insertDefRow();
        }
        else {
            System.out.println("Table " + tableName + " does not exist.");
        }
    }

    public void deleteFromTable(String tableName, Map<String,String> row){
        Table table = tables.get(tableName);
        if(table!=null){
            table.deleteRow(row);
        }
        else {
            System.out.println("Table " + tableName + " does not exist.");

        }
    }

    public void updateTable(String tableName, Map<String,String> newTable,Map<String,String> oldTable){
        Table table = tables.get(tableName);
        if(table != null){
            table.updateRow(newTable, oldTable);
        }
        else {
            System.out.println("Table " + tableName + " does not exist.");
        }
    }


    public String[] getTableNames() {
        return tables.keySet().toArray(new String[0]);
    }

    public boolean containsTable(String tableName) {
        return tables.containsKey(tableName);
    }
}
