package Backend.HashTable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExtensibleHashTable {
    private List<List<String>> directory;
    private List<Integer> localDepths;
    private int globalDepth;
    private int bucketCapacity;

    public ExtensibleHashTable(int bucketCapacity) {
        this.bucketCapacity = bucketCapacity;
        this.globalDepth = 1;
        this.directory = new ArrayList<>();
        this.localDepths = new ArrayList<>();
        for (int i = 0; i < (1 << globalDepth); i++) {
            directory.add(new LinkedList<>());
            localDepths.add(globalDepth);
        }
    }

    private String prepareKey(int key) {
        int bits = Math.max(1, (int) Math.ceil(Math.log(key + 1) / Math.log(2)));
        String binaryKey = String.format("%" + bits + "s", Integer.toBinaryString(key)).replace(' ', '0');
        while (binaryKey.length() < 4) {
            binaryKey = "0" + binaryKey;
        }
        return binaryKey;
    }

    private int hash(String binaryKey) {
        if (binaryKey.length() < globalDepth) {
            binaryKey = String.format("%" + globalDepth + "s", binaryKey).replace(' ', '0');
        }
        return Integer.parseInt(binaryKey.substring(0, globalDepth), 2);
    }

    public void insert(int key) {
        String binaryKey = prepareKey(key);
        int bucketIndex = hash(binaryKey);
        List<String> bucket = directory.get(bucketIndex);

        if (!bucket.contains(binaryKey)) {
            bucket.add(binaryKey);
            if (bucket.size() > bucketCapacity) {
                if (localDepths.get(bucketIndex) == globalDepth) {
                    expandAndRedistribute();
                }
                splitBucket(bucketIndex);
            }
        }
        printDirectory();
    }

    private void splitBucket(int index) {
        int localDepth = localDepths.get(index);
        if (localDepth == globalDepth) {
            expandAndRedistribute();
        }

        localDepth = localDepths.get(index);
        int splitBit = 1 << localDepth;
        int newLocalDepth = localDepth + 1;
        int newIndex = index | splitBit;

        List<String> newBucket = new LinkedList<>();
        if (newIndex < directory.size()) {
            directory.set(newIndex, newBucket);
        } else {
            directory.add(newIndex, newBucket);
        }
        localDepths.set(newIndex, newLocalDepth);

        localDepths.set(index, newLocalDepth);

        List<String> oldBucket = new ArrayList<>(directory.get(index));
        directory.get(index).clear();

        oldBucket.forEach(key -> reinsert(prepareKey(Integer.parseInt(key, 2))));
    }

    private void expandAndRedistribute() {
        int oldSize = directory.size();
        for (int i = 0; i < oldSize; i++) {
            directory.add(new LinkedList<>());
            localDepths.add(globalDepth + 1);
        }
        globalDepth++;
    }

    private void reinsert(String binaryKey) {
        int bucketIndex = hash(binaryKey);
        List<String> bucket = directory.get(bucketIndex);
        bucket.add(binaryKey);
    }

    private void printDirectory() {
        for (int i = 0; i < directory.size(); i++) {
            if (!directory.get(i).isEmpty()) {
                System.out.print("Bucket " + String.format("%03d", i) + ": Contains ");
                List<String> keys = directory.get(i);
                for (int j = 0; j < keys.size(); j++) {
                    System.out.print(keys.get(j));
                    if (j < keys.size() - 1) System.out.print(", ");
                }
                System.out.println(keys.size() == bucketCapacity ? " (full)" : "");
            }
        }
    }

    public static void main(String[] args) {
        ExtensibleHashTable hashTable = new ExtensibleHashTable(2);
        hashTable.insert(0);
        hashTable.insert(1);
        hashTable.insert(2);
        hashTable.insert(3);
        hashTable.insert(4);
        hashTable.insert(6);
        hashTable.insert(8);
    }

    public List<List<String>> getBuckets() {
        return directory;
    }
}
