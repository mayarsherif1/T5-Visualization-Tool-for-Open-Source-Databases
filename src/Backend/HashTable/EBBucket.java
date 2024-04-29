package Backend.HashTable;

import java.util.ArrayList;
import java.util.Arrays;

public class EBBucket{
    private final int pageSize;
    private int freeSlot;
    private final EBIndex[] indexes;

    public EBBucket(int pageSize) {
        this.pageSize = pageSize;
        this.indexes = new EBIndex[this.pageSize];
        this.freeSlot = 0;
    }

    public boolean isFull() {
        return this.freeSlot == this.pageSize;
    }
    public boolean isEmpty() {
        return this.freeSlot == 0;
    }
    public int length() {
        return this.freeSlot;
    }

    public int capacity() {
        return this.pageSize;
    }
    public int size() {
        int count = 0;
        for (int i = 0; i < this.freeSlot; ++i) {
            if (!this.indexes[i].isDeleted()) {
                ++count;
            }
        }
        return count;
    }

    public void print() {
        for (int i = 0; i < this.freeSlot; ++i) {
            EBIndex ebIndex = this.indexes[i];
            System.out.println(ebIndex.toString());
        }
    }


    public boolean addIndex(EBIndex index) {
        if(isFull()){
            return false;
        }
        this.indexes[freeSlot++] = index;
        return true;
    }
    public void updateIndex(EBIndex oldIndex, EBIndex newIndex) {
        int hashCode = oldIndex.hashCode();
        for (int i = 0; i < this.freeSlot; ++i) {
            EBIndex ebIndex = this.indexes[i];
            if (ebIndex.hashCode() == hashCode && ebIndex.equals(oldIndex) && !ebIndex.isDeleted()) {
                ebIndex.setValues(newIndex.getValues());
                return;
            }
        }
    }

    public ArrayList<EBIndex> getIndex(EBIndex index) {
        ArrayList<EBIndex> indexList = new ArrayList<EBIndex>();
        int hashCode = index.hashCode();
        for (int i = 0; i < this.freeSlot; ++i) {
            EBIndex ebIndex = this.indexes[i];
            if (ebIndex.hashCode() == hashCode && ebIndex.equals(index) && !ebIndex.isDeleted()) {
                indexList.add(ebIndex);
            }
        }
        return indexList;
    }
    public ArrayList<EBIndex> getAllIndexes() {
        return new ArrayList<>(Arrays.asList(indexes));
    }

    public void deleteIndex(EBIndex index) {
        int hashCode = index.hashCode();
        for (int i = 0; i < this.freeSlot; ++i) {
            EBIndex ebIndex = this.indexes[i];
            if (ebIndex.hashCode()== hashCode && ebIndex.equals(index) && !ebIndex.isDeleted()) {
                ebIndex.setDeleted(true);
                return;
            }
        }
    }

    public boolean contains(EBIndex oldIndex) {
        int hashCode = oldIndex.hashCode();
        for (int i = 0; i < this.freeSlot; ++i) {
            EBIndex ebIndex = this.indexes[i];
            if (ebIndex.hashCode() == hashCode && ebIndex.equals(oldIndex) && !ebIndex.isDeleted()) {
                return true;
            }
        }
        return false;
    }
}
