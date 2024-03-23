package Backend;

import java.util.HashMap;
import java.util.Map;

public class Index {
    private String tableName;
    private Map<Object, Integer[]> indexMap;

    public Index(String tableName) {
        this.tableName = tableName;
        this.indexMap = new HashMap<>();
    }

    public void addEntry(Object key, int pageNumber, int rowIndex) {
        indexMap.put(key, new Integer[]{pageNumber, rowIndex});
    }

    public Map<Object, Integer[]> getIndexMap() {
        return indexMap;
    }
}
