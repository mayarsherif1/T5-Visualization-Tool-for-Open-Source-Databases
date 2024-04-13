package Backend.HashTable;

import java.util.ArrayList;

public class EBDirectory implements EBPartitionedHashIndex{

    private final EBBucketList[] bucketListArray;
    private final int bitsAssigned;
    private final int pageSize;

    public EBDirectory(int numberOfEntries, int bitsAssigned, int pageSize) {
        this.bitsAssigned = bitsAssigned;
        this.bucketListArray = new EBBucketList[numberOfEntries];
        this.pageSize = pageSize;
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
        String[] values = index.getValues();
        String binary = "";
        for (String value : values) {
            binary += binaryHashCode(value);
        }

        return Integer.parseInt(binary, 2);
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
    public void addIndex(EBIndex index) {
        int entryNumber = getEntryNumber(index);

        EBBucketList list = bucketListArray[entryNumber];
        if (list == null) {
            list = bucketListArray[entryNumber] = new EBBucketList(this.pageSize);
        }

        list.addIndex(index);
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
}
