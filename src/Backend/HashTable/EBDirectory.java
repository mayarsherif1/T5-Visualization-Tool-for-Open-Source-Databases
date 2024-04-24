package Backend.HashTable;

import java.util.*;

public class EBDirectory implements EBPartitionedHashIndex{

    private EBBucketList[] bucketListArray;
    private int bitsAssigned;
    private final int pageSize;

    public EBDirectory(int bitsAssigned, int pageSize) {
        this.bitsAssigned = bitsAssigned;
        this.bucketListArray = new EBBucketList[2];
        this.pageSize = pageSize;
        for (int i = 0; i < this.bucketListArray.length; i++) {
            this.bucketListArray[i] = new EBBucketList(this.pageSize);
        }
    }



    private String binaryHashCode(Object object) {
        if (object == null) {
            return character('0', bitsAssigned);
        }
        int hash = object.hashCode();
        String binaryHash = Integer.toBinaryString(hash);
        return lastCharactersFromString(character('0', bitsAssigned) + binaryHash, bitsAssigned);
    }

    private String character(char c, int times) {
        String str = "";
        for (int i = 0; i < times; i++) {
            str += c;
        }
        return str;
    }
    private String lastCharactersFromString(String str, int length) {
        return (str.length() == 0) ? "" : str.substring(Math.max(str.length() - length, 0));
    }

    private boolean isUniqueEntry(EBIndex index) {
        String[] values = index.getValues();
        for (String value : values) {
            if (value == null) {
                return false;
            }
        }
        return true;
    }
    private int getEntryNumber(EBIndex index) {
        if (index == null || index.getValues() == null) {
            throw new IllegalArgumentException("Index and its values must not be null.");
        }
        String[] values = index.getValues();
        StringBuilder binaryBuilder = new StringBuilder();
        for (String value : values) {
            String binaryHash = binaryHashCode(value);
            System.out.println("Binary hash code for value " + value + ": " + binaryHash);
            binaryBuilder.append(binaryHash);
        }
        String binary = binaryBuilder.toString();
        System.out.println("Concatenated binary string: " + binary);

        int entryNumber = Integer.parseInt(binary, 2);
        System.out.println("Entry number (bucket number): " + entryNumber);

        return entryNumber;
    }

    private int getMask(EBIndex index) {
        String[] values = index.getValues();
        String binary = "";
        for (String value : values) {
            if (value == null) {
                binary += character('0', bitsAssigned);
            } else {
                binary += character('1', bitsAssigned);
            }
        }

        return Integer.parseInt(binary, 2);
    }
    private boolean areEquivalentHashes(EBIndex oldIndex, EBIndex newIndex) {
        String[] oldValues = oldIndex.getValues();
        String[] newValues = newIndex.getValues();

        for (int i = 0; i < newValues.length; i++) {
            if (oldValues[i] != null && newValues[i] != null) {
                if (!binaryHashCode(oldIndex).equals(binaryHashCode(newIndex))) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean identicalIndexes(EBIndex oldIndex, EBIndex newIndex) {
        return oldIndex.getValues().equals(newIndex.getValues());
    }

    public int length() {
        return bucketListArray.length;
    }


    @Override
    public boolean addIndex(EBIndex index) {
        int entryNumber = getEntryNumber(index);
        System.out.println("Adding index to bucket number: " + entryNumber + ", Index: " + Arrays.toString(index.getValues()));
        EBBucketList list = bucketListArray[entryNumber];
        if (list == null) {
            list = new EBBucketList(this.pageSize);
            bucketListArray[entryNumber] = list;
        }
        boolean addedSuccessfully = list.addIndex(index);
        if (!addedSuccessfully) {
            System.out.println("Bucket " + entryNumber + " is full, needs splitting.");
            return true;
        }
        return false;
    }

    @Override
    public void updateIndex(EBIndex oldIndex, EBIndex newIndex) {
        if (identicalIndexes(oldIndex, newIndex)) {
            return;
        }

        int entryNumber = getEntryNumber(oldIndex);

        EBIndex indexToDelete = new EBIndex(oldIndex.getValues().clone());

        if (isUniqueEntry(oldIndex)) {
            EBBucketList list = bucketListArray[entryNumber];
            if (areEquivalentHashes(oldIndex, newIndex)) {
                if (list != null) {
                    list.updateIndex(oldIndex, newIndex);
                }
            }
        }
        if (!isUniqueEntry(oldIndex) || !areEquivalentHashes(oldIndex, newIndex)) {
            ArrayList<EBIndex> indexList = this.getIndex(oldIndex);
            for (EBIndex ebIndex : indexList) {
                ebIndex.setValues(newIndex.getValues());
                this.addIndex(ebIndex);
            }
            this.deleteIndex(indexToDelete);
        }

    }

    @Override
    public ArrayList<EBIndex> getIndex(EBIndex index) {
        ArrayList<EBIndex> indexList = new ArrayList<>();
        int entryNumber = getEntryNumber(index);
        if (isUniqueEntry(index)) {
            EBBucketList list = bucketListArray[entryNumber];
            if (list != null) {
                indexList = list.getIndex(index);
            }
        } else {
            int mask = getMask(index);
            for (int i = 0; i < bucketListArray.length; i++) {
                if ((mask & i) == entryNumber) {
                    EBBucketList list = bucketListArray[i];
                    if (list != null) {
                        indexList.addAll(list.getIndex(index));
                    }
                }
            }
        }
        return indexList;
    }

    @Override
    public void deleteIndex(EBIndex index) {
        int entryNumber = getEntryNumber(index);
        if (isUniqueEntry(index)) {
            EBBucketList list = bucketListArray[entryNumber];
            if (list != null) {
                list.deleteIndex(index);
            }
        } else {
            int mask = getMask(index);
            for (int i = entryNumber; i < bucketListArray.length; i++) {
                if ((mask & i) == entryNumber) {
                    EBBucketList list = bucketListArray[i];
                    if (list != null) {
                        bucketListArray[i].deleteIndex(index);
                    }
                }
            }
        }

    }

    public List<EBIndex> getIndexesForBucket(int bucketNumber) {
        if(bucketNumber<0 || bucketNumber>=bucketListArray.length) {
            return Collections.emptyList();
        }

        EBBucketList list = bucketListArray[bucketNumber];
        if(list == null) {
            return Collections.emptyList();
        }
        return list.getAllIndexes();
    }

    public int getNumberOfFilledBuckets() {
        int count = 0;
        for (EBBucketList bucketList : bucketListArray) {
            if (bucketList != null && !bucketList.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public void splitBucket(int bucketIndex) {
        if (bucketIndex >= bucketListArray.length || bucketListArray[bucketIndex] == null) {
            return;
        }
        EBBucketList originalBucket = bucketListArray[bucketIndex];
        if (originalBucket == null) return;
        int localDepth = originalBucket.getLocalDepth();
        int globalDepth = calculateGlobalDepth();

        if (localDepth >= globalDepth) {
            resizeDirectory();
            //globalDepth++;
        }

        int newBucketIndex = bucketIndex + (1 << localDepth);
        if (newBucketIndex >= bucketListArray.length) {
            resizeDirectory();
        }
        EBBucketList newBucket = new EBBucketList(this.pageSize);
        newBucket.setLocalDepth(localDepth + 1);
        bucketListArray[newBucketIndex] = newBucket;
        originalBucket.setLocalDepth(localDepth + 1);
        redistributeEntries(originalBucket, newBucket, bucketIndex, newBucketIndex, localDepth + 1);
    }

    private void resizeDirectory() {
        int newLength = bucketListArray.length * 2;
        EBBucketList[] newDirectory = new EBBucketList[newLength];
        for (int i = 0; i < bucketListArray.length; i++) {
            newDirectory[i] = bucketListArray[i];
            newDirectory[i + bucketListArray.length] = new EBBucketList(this.pageSize);
        }
        bucketListArray = newDirectory;
        this.bitsAssigned++;
    }

    private void redistributeEntries(EBBucketList originalBucket, EBBucketList newBucket, int oldIndex, int newIndex, int depth) {
        Iterator<EBIndex> iterator = originalBucket.getAllIndexes().iterator();
        while (iterator.hasNext()) {
            EBIndex index = iterator.next();
            if (index == null) continue;
            int entryNumber = getEntryNumber(index) & ((1 << depth) - 1);
            if (entryNumber == newIndex) {
                newBucket.addIndex(index);
                iterator.remove();
            }
        }
    }

    public int getGlobalDepth() {
        return calculateGlobalDepth();
    }
    private int calculateGlobalDepth() {
        if (bucketListArray != null && bucketListArray.length > 0) {
            return (int) (Math.log(bucketListArray.length) / Math.log(2));
        }
        return 0;
    }
    public int getBucketIndex(EBIndex index) {
        int hash = index.hashCode();
        int globalDepthMask = (1 << getGlobalDepth()) - 1;
        return hash & globalDepthMask;
    }




}
