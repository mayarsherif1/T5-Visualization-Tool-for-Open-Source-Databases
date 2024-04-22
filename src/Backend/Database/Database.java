package Backend.Database;

import Backend.Index;
import Backend.TableNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private Map<String, Table> tables;
    private Map<String, Index> indexes;

    public Database(){
        this.tables=new HashMap<>();
        this.indexes= new HashMap<>();
    }

    public void addTable(Table table){
        tables.put(table.getName(), table);
        System.out.println("Table inside database: "+ tables.get(table.getName()));

    }

    public Table getTable(String tableName) throws TableNotFoundException {
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

    public void createIndex(String indexName, String tableName, List<String> columnNames) throws TableNotFoundException {
        if (!tables.containsKey(tableName)) {
            throw new TableNotFoundException(tableName);
        }
        if (indexes.containsKey(indexName)) {
            System.out.println("Index " + indexName + " already exists.");
            return;
        }
        Index index = new Index(indexName, tableName, columnNames);
        indexes.put(indexName, index);
        System.out.println("Index " + indexName + " created on table " + tableName);
    }

    public Index getIndex(String indexName) {
        return indexes.get(indexName);
    }

    public boolean containsIndex(String indexName) {
        return indexes.containsKey(indexName);
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

    public List<String> getDataFromTable(String tableName, String columnName) {
        List<String> data = new ArrayList<>();
        try {
            Table table = getTable(tableName);
            List<Map<String, String>> rows = table.getRows();
            for (Map<String, String> row : rows) {
                String value = row.get(columnName);
                data.add(value);
            }
        } catch (TableNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }



}


