package Backend.HashTable;

import java.util.ArrayList;

public class EBPartitionedHashTable implements EBPartitionedHashIndex{

    private final EBDirectory directory;
    private String[] keys;
    private final int bitsAssigned;

    public EBPartitionedHashTable(String[] keys, int pageSize) {
        this.setKeys(keys);
        bitsAssigned = 1;
        this.directory = new EBDirectory(bitsAssigned, pageSize);
    }
    public String[] getKeys() {
        return keys;
    }

    private void setKeys(String[] keys) {
        this.keys = keys;
    }

    private boolean isValidIndex(EBIndex index) {
        return index.getValues().length == keys.length && index.getPageName() != null;
    }


    @Override
    public boolean addIndex(EBIndex index) {
        if (isValidIndex(index)) {
            this.directory.addIndex(index);
        }
        return false;
    }

    @Override
    public void updateIndex(EBIndex oldIndex, EBIndex newIndex) {
        if (oldIndex.getValues().length == newIndex.getValues().length && oldIndex.getValues().length == keys.length) {
            this.directory.updateIndex(oldIndex, newIndex);
        }
    }

    @Override
    public ArrayList<EBIndex> getIndex(EBIndex index) {
        if (index.getValues().length == keys.length) {
            return this.directory.getIndex(index);
        }
        return null;
    }

    @Override
    public void deleteIndex(EBIndex index) {
        if (index.getValues().length == keys.length) {
            this.directory.deleteIndex(index);
        }
    }
}
