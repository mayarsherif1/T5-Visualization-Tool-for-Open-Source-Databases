package Backend;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Index {
    private String tableName;
    private Map<Object, Integer[]> indexMap;
    private String indexName;
    private List<String> columnNames;

    public Index(String indexName, String tableName, List<String> columnNames) {
        this.indexName = indexName;
        this.tableName = tableName;
        this.columnNames = columnNames;
        this.indexMap = new HashMap<>();
    }

    public void addEntry(List<Object> key, int pageNumber, int rowIndex) {
        if (key.size() != columnNames.size()) {
            throw new IllegalArgumentException("Key size does not match the number of columns in the index.");
        }
        indexMap.put(key, new Integer[]{pageNumber, rowIndex});
    }

    public Map<Object, Integer[]> getIndexMap() {
        return indexMap;
    }

    public List<Object> createCompositeKey(Map<String, Object> row) {
        Object[] keyValues = new Object[columnNames.size()];
        for (int i = 0; i < columnNames.size(); i++) {
            keyValues[i] = row.get(columnNames.get(i));
        }
        return Arrays.asList(keyValues);
    }

}
