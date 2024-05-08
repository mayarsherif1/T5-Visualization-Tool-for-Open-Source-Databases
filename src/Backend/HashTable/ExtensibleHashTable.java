package Backend.HashTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ExtensibleHashTable {
    private List<List<String>> directory;
    private List<Integer> localDepths;
    private int bitDepth;
    private int globalDepth;
    private int bucketCapacity;

    public ExtensibleHashTable(int bucketCapacity) {
        this.bucketCapacity = bucketCapacity;
        this.globalDepth = 1;
        this.directory = new ArrayList<>();
        this.localDepths = new ArrayList<>();
        this.bitDepth = 1;
        for (int i = 0; i < (1 << globalDepth); i++) {
            directory.add(new LinkedList<>());
            localDepths.add(globalDepth);
        }
    }


    public String prepareKey(int key) {
        StringBuilder binaryKey = new StringBuilder(Integer.toBinaryString(key));
        System.out.println("binaryKey: "+ binaryKey);
        while (binaryKey.length() < bitDepth+1){
            binaryKey.insert(0, "0");
        }
        System.out.println("binaryKey after loop: "+ binaryKey);
        return binaryKey.toString();
    }


    public int hash(String binaryKey) {
        int significantBits = (int) Math.ceil(Math.log(directory.size()) / Math.log(2));
        if (binaryKey.length() > significantBits) {
            binaryKey = binaryKey.substring(0, significantBits); // Use the most significant bits
        }
        return Integer.parseInt(binaryKey, 2) & ((1 << significantBits) - 1);
    }


    public void insert(int key) {
        String binaryKey = prepareKey(key);
        int bucketIndex = hash(binaryKey);
        List<String> bucket = directory.get(bucketIndex);

        if (!bucket.contains(binaryKey)) {
            bucket.add(binaryKey);
            System.out.println("Inserting key: " + key + ", Prepared Binary Key: " + binaryKey + ", Bucket Index: " + bucketIndex);
            if (bucket.size() > bucketCapacity) {
                splitBucket(bucketIndex);
                if (directory.get(bucketIndex).contains(binaryKey)) {
                    directory.get(bucketIndex).remove(binaryKey); // Remove and reinsert to correct bucket
                    insert(key);
                }
            }
        }
    }


    private void splitBucket(int index) {
        int localDepth = localDepths.get(index);
        if (localDepth == globalDepth) {
            expandAndRedistribute();
        }
        List<String> bucket = directory.get(index);
        List<String> newBucket = new LinkedList<>();
        int newLocalDepth = localDepth + 1;

        int splitBit = 1 << localDepth;
        bucket.removeIf(key -> {
            if ((Integer.parseInt(key, 2) & splitBit) != 0) {
                newBucket.add(key);
                return true;
            }
            return false;
        });

        // Update directory and depths
        int newIndex = index | splitBit;
        directory.set(index, bucket);
        directory.set(newIndex, newBucket);
        localDepths.set(index, newLocalDepth);
        localDepths.set(newIndex, newLocalDepth);
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
        globalDepth++;
        List<List<String>> newDirectory = new ArrayList<>(Collections.nCopies(1 << globalDepth, null));
        List<Integer> newLocalDepths = new ArrayList<>(Collections.nCopies(1 << globalDepth, 0));

        for (int i = 0; i < directory.size(); i++) {
            List<String> bucket = directory.get(i);
            int depthMask = (1 << localDepths.get(i)) - 1;
            int baseIndex = i & depthMask;
            for (int j = 0; j < (1 << (globalDepth - localDepths.get(i))); j++) {
                int newIndex = baseIndex | (j << localDepths.get(i));
                newDirectory.set(newIndex, bucket);
                newLocalDepths.set(newIndex, localDepths.get(i));
            }
        }

        directory = newDirectory;
        localDepths = newLocalDepths;
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
        int totalSize = 0;
        for (List<String> bucket : directory) {
            totalSize += bucket.size();
        }
        return totalSize;
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

    public List<List<String>> getDirectory(){
        return directory;
    }

    public List<List<String>> getBuckets() {
        return directory;
    }

    public int getLocalDepth(int i) {
        return localDepths.get(i);
    }
}
