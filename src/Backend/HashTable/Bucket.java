package Backend.HashTable;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private List<String> records;
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

    public void insert(String hash) {
        if (!isFull()) {
            records.add(hash);
            System.out.println("Inserted: " + hash + " into main bucket.");
        } else {
            if (overflowBucket == null) {
                overflowBucket = new Bucket(capacity);
                System.out.println("Creating new overflow bucket for hash: " + hash);
            }
            overflowBucket.insert(hash);
            System.out.println("Inserted: " + hash + " into overflow bucket.");
        }
    }

    public List<String> getContents() {
        List<String> contents = new ArrayList<>(records);
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

    public boolean hasOverflow() {
        return overflowBucket != null && !overflowBucket.getContents().isEmpty();
    }

    public String getOverflowContents() {
        return overflowBucket == null ? "" : String.join(", ", overflowBucket.getContents());
    }

    public int getTotalSize() {
        return records.size() + (overflowBucket == null ? 0 : overflowBucket.getTotalSize());
    }

    public void setOverflowBucket(Bucket bucket) {
        this.overflowBucket = bucket;
    }

    public List<String> getRecords() {
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


    public void insertIntoOverflow(String hashValue) {
        if (overflowBucket == null) {
            overflowBucket = new Bucket(capacity);
        }
        overflowBucket.insert(hashValue);
        System.out.println("Inserted: " + hashValue + " into overflow bucket.");
    }

    public String[] getOverflow() {
        return overflowBucket == null ? new String[0] : overflowBucket.getContents().toArray(new String[0]);
    }


    public void clearOverflow() {
        overflowBucket.clear();
        overflowBucket = null;
    }
}
