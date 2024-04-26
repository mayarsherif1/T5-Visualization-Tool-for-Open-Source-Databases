package Backend.HashTable;

import java.util.ArrayList;
import java.util.List;

public class LinearHashingIndex {
    private List<Bucket> buckets;
    private int capacity;
    private double threshold;
    private int m;
    private int n; //num of buckets in use

    public LinearHashingIndex(int bucketCapacity, double threshold) {
        this.capacity = bucketCapacity;
        this.buckets = new ArrayList<>();
        this.buckets.add(new Bucket(bucketCapacity));
        this.threshold = threshold;
        this.m = 0;
        this.n = 1;
    }

    private int hash(String hashValue) {
        int key = Integer.parseInt(hashValue, 2);
        return key % n;
    }


    public void insert(String hashValue) {
        int bucketIndex = hash(hashValue);
        Bucket targetBucket = buckets.get(bucketIndex);
        boolean overflowFlag = true;
        if (!targetBucket.isFull()) {
            targetBucket.insert(hashValue);
            overflowFlag =true;
            System.out.println("Inserted " + hashValue + " into Bucket " + bucketIndex);
        } else {
            targetBucket.insertIntoOverflow(hashValue);
            overflowFlag= false;
            System.out.println("Inserted " + hashValue + " into overflow for Bucket " + bucketIndex);

        }
        if (calculateUtilization() > threshold) {
            System.out.println("Utilization exceeds 80%. Expanding hash table...");
            expand();
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
        int oldNumBuckets = n;
        n++;
        buckets.add(new Bucket(capacity));
//
//        for (int i = 0; i < oldNumBuckets; i++) {
//            //buckets.get(i).setOverflowBucket(buckets.get(oldNumBuckets + i));
//
//        }
        //setupOverflowBuckets();
        rehash(oldNumBuckets);
        //rehash();
    }
//    private void rehash() {
//        System.out.println("Rehashing...");
//        for (int i = 0; i < buckets.size(); i++) {
//            Bucket bucket = buckets.get(i);
//            List<String> overflowItems = new ArrayList<>(List.of(bucket.getOverflow()));
//            bucket.clearOverflow(); // Clear the overflow items to re-insert them.
//
//            for (String item : overflowItems) {
//                int newBucketIndex = hash(item);
//                Bucket newBucket = buckets.get(newBucketIndex);
//                if (newBucket.isFull()) {
//                    newBucket.insertIntoOverflow(item);
//                } else {
//                    newBucket.insert(item);
//                }
//            }
//        }
//        System.out.println("Rehashing completed.");
//    }
//    private void rehash() {
//        System.out.println("Rehashing...");
//        List<Bucket> newBuckets = new ArrayList<>(Collections.nCopies(n, null));
//        for (int i = 0; i < n; i++) {
//            newBuckets.set(i, new Bucket(capacity));
//        }
//
//        for (Bucket oldBucket : buckets) {
//            rehashBucketContents(oldBucket, newBuckets);
//        }
//
//        buckets = newBuckets;
//        System.out.println("Rehashing completed.");
//    }


    public void setupOverflowBuckets() {
        int size = buckets.size();
        for (int i = 0; i < size; i++) {
            int overflowIndex = (i + 1) % size;
            buckets.get(i).setOverflowBucket(buckets.get(overflowIndex));
        }
    }

    private void rehash(int oldNumBuckets) {
        System.out.println("Rehashing...");
        List<Bucket> newBuckets = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            newBuckets.add(new Bucket(capacity));
        }
        for (int i = 0; i < oldNumBuckets; i++) {
            Bucket oldBucket = buckets.get(i);
            for (String item : oldBucket.getRecords()) {
                int newBucketIndex = hash(item);
                newBuckets.get(newBucketIndex).insert(item);
                System.out.println("rehash Inserted " + item + " into Bucket " + newBucketIndex);
            }
            if (oldBucket.hasOverflow()) {
                for (String item : oldBucket.getOverflow()) {
                    int newBucketIndex = hash(item);
                    newBuckets.get(newBucketIndex).insert(item);
                    System.out.println("rehash Inserted " + item + " into Bucket " + newBucketIndex);
                }
            }
        }

        for (Bucket bucket : newBuckets) {
            if (bucket.getRecords().size() > capacity) {
                List<String> toOverflow = new ArrayList<>(bucket.getRecords().subList(capacity, bucket.getRecords().size()));
                bucket.getRecords().subList(capacity, bucket.getRecords().size()).clear();
                for (String item : toOverflow) {
                    bucket.insertIntoOverflow(item);
                }
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

}
