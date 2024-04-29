package Backend.HashTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ExtensibleHashTable {
    private List<List<String>> directory;
    private List<Integer> localDepths;
    private int bitDepth;
    private int bucketCapacity;


    public ExtensibleHashTable(int bucketCapacity) {
        this.bucketCapacity = bucketCapacity;
        this.bitDepth = 1;
        this.directory = new ArrayList<>(1 << bitDepth);
        for (int i = 0; i < (1 << bitDepth); i++) {
            directory.add(new LinkedList<>());
        }
        this.localDepths = new ArrayList<>(Collections.nCopies((1 << bitDepth), 1));
    }


    private String prepareKey(int key) {
        StringBuilder binaryKey = new StringBuilder(Integer.toBinaryString(key));
        System.out.println("binaryKey: "+ binaryKey);
        while (binaryKey.length() < bitDepth+1){
            binaryKey.insert(0, "0");
        }
        System.out.println("binaryKey after loop: "+ binaryKey);
        return binaryKey.toString();
    }


    private int hash(String binaryKey) {
        if (binaryKey.length() > bitDepth) {
            binaryKey = binaryKey.substring(0, bitDepth); // Use the most significant bits
        }
        return Integer.parseInt(binaryKey, 2) & ((1 << bitDepth) - 1);
    }


    public void insert(int key) {
        String binaryKey = prepareKey(key);
        int bucketIndex = hash(binaryKey);
        List<String> bucket = directory.get(bucketIndex);

        if (!bucket.contains(binaryKey)) {
            bucket.add(binaryKey);
            System.out.println("Inserting key: " + key + ", Prepared Binary Key: " + binaryKey + ", Bucket Index: " + bucketIndex);
            if (bucket.size() > bucketCapacity) {
                expandAndRedistribute();
                if (directory.get(bucketIndex).contains(binaryKey)) {
                    directory.get(bucketIndex).remove(binaryKey); // Remove and reinsert to correct bucket
                    insert(key);
                }
            }
        }
    }

    private void splitBucket(int bucketIndex) {
        List<String> oldBucket = directory.get(bucketIndex);
        int localDepth = localDepths.get(bucketIndex);
        int newLocalDepth = localDepth + 1;

        List<String> newBucket1 = new LinkedList<>();
        List<String> newBucket2 = new LinkedList<>();

        int splitBit = 1 << localDepth;  // Calculate the bit used to split the bucket
        for (String key : oldBucket) {
            if ((Integer.parseInt(key, 2) & splitBit) == 0) {
                newBucket1.add(key);
            } else {
                newBucket2.add(key);
            }
        }

        // Replace old bucket and add new bucket in the directory
        directory.set(bucketIndex, newBucket1);
        directory.add(bucketIndex + splitBit, newBucket2); // This assumes that bucket order can be maintained

        // Update local depths
        localDepths.set(bucketIndex, newLocalDepth);
        localDepths.add(bucketIndex + splitBit, newLocalDepth);
    }

    public int getBucketIndex(String binaryKey) {
        System.out.println("binaryKey: "+ binaryKey);
        String trimmedKey = binaryKey.length() >= bitDepth ? binaryKey.substring(0, bitDepth)
                : String.format("%" + bitDepth + "s", binaryKey).replace(' ', '0');

        System.out.println("trimmedKey: "+ trimmedKey);
        System.out.println("integer trimmedKey: "+ Integer.parseInt(trimmedKey, 2));

        return Integer.parseInt(trimmedKey, 2);
    }


    private void expandAndRedistribute() {
        System.out.println("Before redistribution: " + directory);

        bitDepth++;
        List<List<String>> newDirectory = new ArrayList<>(1 << bitDepth);
        List<Integer> newLocalDepths = new ArrayList<>(1 << bitDepth);

        for (int i = 0; i < (1 << bitDepth); i++) {
            newDirectory.add(new LinkedList<>());
            newLocalDepths.add(i < directory.size() ? localDepths.get(i) + 1 : 1);
        }
        System.out.println("Expanding table. New bit depth: " + bitDepth);

        for (int i = 0; i < directory.size(); i++) {
            List<String> oldBucket = directory.get(i);
            for (String key : oldBucket) {
                String binaryKey = prepareKey(Integer.parseInt(key, 2));
                int newIndex = hash(binaryKey);
                newDirectory.get(newIndex).add(binaryKey);
                System.out.println("Redistributing key: " + binaryKey + " to new bucket index: " + newIndex);

            }
        }
        directory = newDirectory;
        localDepths = newLocalDepths;
        System.out.println("After redistribution: " + directory);


    }



    public void delete(int key) {
        String binaryKey = Integer.toBinaryString(key);
        int index = getBucketIndex(binaryKey);
        directory.get(index).remove(binaryKey);
    }

    public boolean contains(int key) {
        String binaryKey = Integer.toBinaryString(key);
        int index = getBucketIndex(binaryKey);
        return directory.get(index).contains(binaryKey);
    }

    public int size() {
        int size = 0;
        for (List<String> bucket : directory) {
            size += bucket.size();
        }
        return size;
    }
    public boolean isEmpty() {
        for (List<String> bucket : directory) {
            if (!bucket.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void clear() {
        for (List<String> bucket : directory) {
            bucket.clear();
        }
    }



    public List<List<String>> getBuckets() {
        return directory;
    }

    public int getLocalDepth(int i) {
        return localDepths.get(i);
    }
}
