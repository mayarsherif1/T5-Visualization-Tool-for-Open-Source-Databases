package Backend.HashTable;

import java.util.ArrayList;
import java.util.List;

public class LinearHashingIndex {
    private List<Bucket> buckets;
    private int capacity;
    private int m;
    private int n;

    public LinearHashingIndex(int bucketCapacity) {
        this.capacity = bucketCapacity;
        this.buckets = new ArrayList<>();
        this.buckets.add(new Bucket(bucketCapacity));
        this.m = 0;
        this.n = 1;
    }

    private int hash(String hashValue, int numBuckets) {
        int key = Integer.parseInt(hashValue, 2);
        int bucketIndex = key % numBuckets;
        System.out.println("Hashing " + hashValue + " to index " + bucketIndex + " with numBuckets " + numBuckets);
        return bucketIndex;
    }


    public void insert(String hashValue) {
        int bucketIndex = hash(hashValue, n);
        System.out.println("Attempting to insert " + hashValue + " into Bucket " + bucketIndex);
        if (buckets.get(bucketIndex).isFull()) {
            System.out.println("Bucket " + bucketIndex + " is full. Triggering expansion...");
            expand();
            bucketIndex = hash(hashValue, n);
        }
        System.out.println("Inserting " + hashValue + " into Bucket " + bucketIndex);
        buckets.get(bucketIndex).insert(hashValue);
        System.out.println("Inserted " + hashValue + " into Bucket " + bucketIndex);
        double utilization = calculateUtilization();
        System.out.println("Current utilization: " + utilization);
        if (utilization > 0.8) {
            System.out.println("Utilization exceeds 80%. Expanding hash table...");
            expand();
        }
        System.out.println("Insertion completed.");
    }

    private void expand() {
        System.out.println("Expanding hash table...");
        n++;
        buckets.add(new Bucket(capacity));
//        if (++m == n) { m = 0; }
        System.out.println("Expanding hash table... New number of buckets: " + n);
        rehash();
    }

    private void rehash() {
        System.out.println("Rehashing...");
        List<String> oldRecords = new ArrayList<>();
        for (Bucket bucket : buckets) {
            oldRecords.addAll(bucket.getRecords());
            bucket.getRecords().clear();
        }
        for (String hash : oldRecords) {
            int newBucketIndex = hash(hash, n);
            buckets.get(newBucketIndex).insert(hash);
        }
        System.out.println("Rehashing completed.");
    }



    public double calculateUtilization() {
        int totalItems = 0;
        for (Bucket bucket : buckets) {
            totalItems += bucket.getRecords().size();
        }
        return (double) totalItems / (n * capacity);
    }

    public void printBuckets() {
        System.out.println("Printing buckets...");
        for (int i = 0; i < buckets.size(); i++) {
            System.out.print("Bucket " + i + ": ");
            for (String record : buckets.get(i).getRecords()) {
                System.out.print(record + " ");
            }
            System.out.println();
        }
        System.out.println("Bucket printing completed.");
    }

    public List<Bucket> getBuckets() {
        return buckets;
    }
}
