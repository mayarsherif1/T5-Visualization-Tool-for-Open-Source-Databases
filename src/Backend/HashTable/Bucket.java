package Backend.HashTable;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private List<Integer> records;
    private final int capacity;
    private Bucket overflowBucket;

    public Bucket(int capacity) {
        this.capacity = capacity;
        this.records = new ArrayList<>(capacity);
        this.overflowBucket = null;
    }

    public boolean isFull() {
        return records.size() >= capacity;
    }

    public boolean insert(int hash) {
        if (isFull()) {
            if (overflowBucket == null) {
                overflowBucket = new Bucket(capacity);
            }
            return overflowBucket.insert(hash);
        } else {
            records.add(hash);
            return true;
        }
    }

    public boolean contains(int hash) {
        return records.contains(hash) || (overflowBucket != null && overflowBucket.contains(hash));
    }

    public List<Integer> getContents() {
        List<Integer> contents = new ArrayList<>(records);
        if (overflowBucket != null) {
            contents.addAll(overflowBucket.getContents());
        }
        return contents;
    }

    public void clear() {
        records.clear();
        if (overflowBucket != null) {
            overflowBucket.clear();
            overflowBucket = null;
        }
    }

    public Bucket getOverflowBucket() {
        return overflowBucket;
    }

    public void insertIntoOverflow(int hash) {
        if (!isFull()) {
            insert(hash);
            System.out.println("Inserted: " + hash + " into main bucket after overflow check.");
        } else {
            if (!records.contains(hash)) {
                if (overflowBucket == null) {
                    overflowBucket = new Bucket(capacity);
                }
                overflowBucket.insert(hash);
                System.out.println("Inserted: " + hash + " into overflow bucket.");
            }
        }
    }

    public boolean hasOverflow() {
        return overflowBucket != null && !overflowBucket.getContents().isEmpty();
    }

    public int getTotalSize() {
        return records.size() + (overflowBucket == null ? 0 : overflowBucket.getTotalSize());
    }

    public List<Integer> getRecords() {
        return new ArrayList<>(records);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        records.forEach(record -> {
            if (sb.length() > 0) sb.append(", ");
            sb.append(record);
        });
        return sb.toString();
    }

    public String getOverflowContents() {
        return overflowBucket.toString();
    }
}
