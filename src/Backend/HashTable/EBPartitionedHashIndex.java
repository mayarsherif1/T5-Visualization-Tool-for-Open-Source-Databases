package Backend.HashTable;

import java.util.ArrayList;

public interface EBPartitionedHashIndex {

    void addIndex(EBIndex index);
    void updateIndex(EBIndex oldIndex, EBIndex newIndex);

    ArrayList<EBIndex> getIndex(EBIndex index);
    void deleteIndex(EBIndex index);

}
