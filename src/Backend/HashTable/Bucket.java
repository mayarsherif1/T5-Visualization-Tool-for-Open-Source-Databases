package Backend.HashTable;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private List<String> records;
    private final int capacity;

    public Bucket(int capacity) {
        this.capacity = capacity;
        this.records = new ArrayList<>(capacity);
    }

    public boolean isFull() {
        return records.size() >= capacity;
    }

    public void insert(String hash) {
        if (!isFull()) {
            records.add(hash);
        }
    }

    public List<String> getRecords() {
        return records;
    }
    @Override
    public String toString() {
        if (records.isEmpty()) {
            return "[Empty]";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < records.size(); i++) {
            sb.append(records.get(i));
            if (i < records.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

}
