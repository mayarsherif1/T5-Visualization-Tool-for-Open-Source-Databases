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
    }

    public Table getTable(String tableName){
        return tables.get(tableName);
    }

    public List<Table> getAllTables(){
        return new ArrayList<>(tables.values());
    }

    public void insertIntoTable(String tableName, List<String> columnNames, List<String> values){
        Table table = tables.get(tableName);
        if(table!=null){
            table.insertRow(columnNames,values);
        }
        else {
            System.out.println("Table " + tableName + " does not exist.");
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



}
