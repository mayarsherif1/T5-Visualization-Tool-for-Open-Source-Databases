package Backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
    private String name;
    private List<Column> columns;
    private List<Map<String, String>> rows;
    private List<Page> pages;

    public Table(String name){
        this.name=name;
        this.columns = new ArrayList<>();
        this.rows=new ArrayList<>();

    }

    public String getName(){
        return this.name;
    }
    public void addColumn(Column column){
        columns.add(column);
    }

    public List<Column> getColumns(){
        return columns;
    }

    public int columnsSize(){
        return columns.size();
    }


    public void insertRow(List<String> columnNames, List<String> values) {
        if (columnNames.size() != values.size()) {
            throw new IllegalArgumentException("Number of columns and values do not match.");
        }
        Map<String, String> newRow = new HashMap<>();
        for (int i = 0; i < columnNames.size(); i++) {
            String columnName = columnNames.get(i);
            String value = values.get(i);
            boolean colFound = false;
            for (Column column: columns){
                if (column.getName().equals(columnName)){
                    //todo type checking
                    colFound=true;
                    newRow.put(columnName,value);
                    break;
                }
            }
            if (!colFound){
                throw new IllegalArgumentException("Column does not exist"+ columnName);
            }
        }
        rows.add(newRow);
    }

    public List<Map<String, String>> getRows() {
        return rows;
    }

    public void insertDefRow() {
        Map<String, String> defRow= new HashMap<>();
        for(Column column: columns){
            String defaultValue = getDefColType(column.getType());
            defRow.put(column.getName(),defaultValue);
        }
        rows.add(defRow);
        System.out.println("Inserted a row with default values into table: " + this.name);

    }

    private String getDefColType(String columnType) {
        switch (columnType.toLowerCase()) {
            case "int":
                return "0";
            case "varchar":
                return "";
            default:
                return "default";
        }
    }
    public void printTableData() {
        System.out.println("Data in table '" + name + "':");
        for (Map<String, String> row : rows) {
            System.out.println(row);
        }
    }

    public void deleteRow(Map<String, String> row) {
        List<Map<String,String>> removeRows= new ArrayList<>();
        for (Map<String,String> r : rows){
            if(r.equals(row)){
                removeRows.add(row);
            }

        }
       rows.removeAll(removeRows);
    }

    public void updateRow(Map<String,String> newRow, Map<String,String> oldRow){
        for(Map<String,String> row: rows){
            if(row.equals(oldRow)){
                for(String col : newRow.keySet()){
                    row.put(col,newRow.get(col));
                }
            }
        }
    }


}
