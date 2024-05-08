package Backend.HashTable;

import java.util.ArrayList;
import java.util.List;

public class LinearHashingIndex {
    private List<Bucket> buckets;
    private int capacity;
    private double threshold;
    private int globalDepth;
    private int m;

    public LinearHashingIndex(int bucketCapacity, double threshold) {
        this.capacity = bucketCapacity;
        this.threshold = threshold;
        this.buckets = new ArrayList<>();
        this.globalDepth = 1;
        this.m = 0;
        for (int i = 0; i < (1 << globalDepth); i++) {
            this.buckets.add(new Bucket(capacity));
        }
        System.out.println("Linear Hashing Index initialized.");
        System.out.println("Initial number of buckets: " + buckets.size());
        System.out.println("Initial bucket capacity: " + capacity);
        System.out.println("Threshold: " + threshold);
        System.out.println("Global depth: " + globalDepth);
        System.out.println("m: " + m);
    }

    private int hash(int hashValue, int depth) {
        return hashValue % (1 << depth);
    }

    private int getBucketIndex(int hashValue) {
        int hash = hash(hashValue, globalDepth);
        if (hash < m) {
            return hash(hashValue, globalDepth + 1);
        }
        return hash;
    }

    public void insert(int hashValue) {
        int bucketIndex = getBucketIndex(hashValue);
        System.out.println("Inserting " + hashValue + " into Bucket " + bucketIndex);
        Bucket targetBucket = buckets.get(bucketIndex);
        if (!targetBucket.isFull()) {
            targetBucket.insert(hashValue);
            System.out.println("Inserted " + hashValue + " into Bucket " + bucketIndex);
        } else {
            if (targetBucket.getOverflowBucket() != null) {
                targetBucket.getOverflowBucket().insert(hashValue);
            } else {
                targetBucket.insertIntoOverflow(hashValue);
            }
        }
        if (calculateUtilization() >= threshold) {
            System.out.println("Utilization exceeds threshold. Expanding hash table...");
            expand();
            System.out.println("Expansion completed.");
        }
    }

    private double calculateUtilization() {
        int totalItems = 0;
        for (Bucket bucket : buckets) {
            totalItems += bucket.getTotalSize();
        }
        return (double) totalItems / (buckets.size() * capacity);
    }

    private void expand() {
        System.out.println("Expanding hash table...");
        buckets.add(new Bucket(capacity));
        rehashBucket(m);
        m++;
        if (m == (1 << (globalDepth - 1))) {
            m = 0;
            globalDepth++;
        }
        System.out.println("Global depth increased to " + globalDepth);
        System.out.println("m increased to " + m);
        System.out.println("Number of buckets increased to " + buckets.size());
        System.out.println("Expansion completed.");
    }

    private void rehashBucket(int bucketIndex) {
        System.out.println("Rehashing bucket " + bucketIndex + "...");
        Bucket oldBucket = buckets.get(bucketIndex);
        List<Integer> rehashedItems = new ArrayList<>(oldBucket.getContents());
        if (oldBucket.hasOverflow()) {
            rehashedItems.addAll(oldBucket.getOverflowBucket().getContents());
        }
        oldBucket.clear();
        for (int item : rehashedItems) {
            int newBucketIndex = getBucketIndex(item);
            System.out.println("Inserting " + item + " into Bucket " + newBucketIndex);
            Bucket targetBucket = buckets.get(newBucketIndex);
            if (!targetBucket.isFull()) {
                targetBucket.insert(item);
            } else {
                targetBucket.insertIntoOverflow(item);
            }
            System.out.println("Rehashed " + item + " into correct bucket");
        }
        System.out.println("Rehashed bucket " + bucketIndex + " completed.");
    }

    public void printBuckets() {
        System.out.println("Printing buckets...");
        for (int i = 0; i < buckets.size(); i++) {
            System.out.print("Bucket " + i + ": ");
            System.out.println(buckets.get(i));
            if (buckets.get(i).getOverflowBucket() != null) {
                System.out.print("  Overflow: ");
                System.out.println(buckets.get(i).getOverflowBucket());
            }
        }
        System.out.println("Bucket printing completed.");
    }

    public List<Bucket> getBuckets() {
        return buckets;
    }

    public Bucket getBucket(int i) {
        return buckets.get(i);
    }

    public boolean contains(int value) {
        int bucketIndex = getBucketIndex(value);
        return buckets.get(bucketIndex).contains(value);
    }
}