package Backend.HashTable;

import java.util.ArrayList;
import java.util.List;

public class LinearHashingIndex {
    private List<Bucket> buckets;
    private int capacity;
    private double threshold;
    private int n;

    public LinearHashingIndex(int bucketCapacity, double threshold) {
        this.capacity = bucketCapacity;
        this.buckets = new ArrayList<>();
        this.buckets.add(new Bucket(bucketCapacity));
        this.threshold = threshold;
        this.n = 1;
    }




    private int hash(String hashValue) {
        try {
            return Integer.parseInt(hashValue, 2) % n;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("For input string: \"" + hashValue + "\"");
        }
    }


    public void insert(String hashValue) {
        try {

            int bucketIndex = hash(hashValue);
            Bucket targetBucket = buckets.get(bucketIndex);
            if (!targetBucket.isFull()) {
                targetBucket.insert(hashValue);
                System.out.println("Inserted " + hashValue + " into Bucket " + bucketIndex);
            } else {
                targetBucket.insertIntoOverflow(hashValue);
                System.out.println("Inserted " + hashValue + " into overflow for Bucket " + bucketIndex);
            }
            if (calculateUtilization() >= threshold) {
                System.out.println("Utilization exceeds threshold. Expanding hash table...");
                expand();
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new NumberFormatException(e.getMessage());
        }
    }

    private double calculateUtilization() {
        int totalItems = 0;
        for (Bucket bucket : buckets) {
            totalItems += bucket.getTotalSize();

        }
        return (double) totalItems / (n * capacity);
    }

    private void expand() {
        System.out.println("Expanding hash table...");
        int oldSize = buckets.size();
        n = 2 * oldSize;
        for (int i = oldSize; i < n; i++) {
            buckets.add(new Bucket(capacity));

        }
        rehash();
    }

    private void rehash() {
        System.out.println("Rehashing...");
        List<Bucket> newBuckets = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            newBuckets.add(new Bucket(capacity));
        }

        for (Bucket bucket : buckets) {
            for (String item : bucket.getContents()) {
                int newBucketIndex = hash(item);
                newBuckets.get(newBucketIndex).insert(item);
            }
        }
        buckets = newBuckets;

        System.out.println("Rehashing completed.");
    }

    public void printBuckets() {
        System.out.println("Printing buckets...");
        for (int i = 0; i < buckets.size(); i++) {
            System.out.print("Bucket " + i + ": ");
            buckets.get(i).getRecords().forEach(record -> System.out.print(record + " "));
            System.out.println();

        }
        System.out.println("Bucket printing completed.");
    }

    public List<Bucket> getBuckets() {
        return buckets;
    }

    public Bucket getBucket(int i) {
        return buckets.get(i);
    }

    public boolean contains(String value) {
        for (Bucket bucket : buckets) {
            if (bucket.contains(value)) {
                return true;
            }
        }
        return false;
    }

}
