package Backend.HashTable;

import java.util.ArrayList;

public class EBBucketList implements EBPartitionedHashIndex{

    private final ArrayList<EBBucket> buckets;
    private EBBucket currentBucket;
    private final int bucketCapacity;
    private int localDepth;

    public EBBucketList(int bucketCapacity) {
        this.bucketCapacity = bucketCapacity;
        this.buckets = new ArrayList<EBBucket>();
        this.currentBucket = new EBBucket(this.bucketCapacity);
        this.localDepth=1;
        this.buckets.add(this.currentBucket);
    }


    public int getLocalDepth() {
        return localDepth;
    }

    public void setLocalDepth(int localDepth) {
        this.localDepth = localDepth;
    }

    public int buckets() {
        return this.buckets.size();
    }

    @Override
    public boolean addIndex(EBIndex index) {
        if (isFull()){
            return false;
        }
        if (this.currentBucket.isFull()) {
            this.currentBucket = new EBBucket(this.bucketCapacity);
            this.buckets.add(this.currentBucket);
            System.out.println("New bucket created due to overflow.");
        }
        return this.currentBucket.addIndex(index);
    }

    @Override
    public void updateIndex(EBIndex oldIndex, EBIndex newIndex) {
        for (EBBucket ebBucket : buckets) {
            if (ebBucket.contains(oldIndex)) {
                ebBucket.updateIndex(oldIndex, newIndex);
                return;
            }
        }
    }

    @Override
    public ArrayList<EBIndex> getIndex(EBIndex index) {
        ArrayList<EBIndex> indexList = new ArrayList<EBIndex>();
        for (EBBucket ebBucket : buckets) {
            indexList.addAll(ebBucket.getIndex(index));
        }
        return indexList;
    }

    public ArrayList<EBIndex> getAllIndexes() {
        ArrayList<EBIndex> indexList = new ArrayList<EBIndex>();
        for (EBBucket ebBucket : buckets) {
            indexList.addAll(ebBucket.getAllIndexes());
        }
        return indexList;

    }

    @Override
    public void deleteIndex(EBIndex index) {
        for (EBBucket ebBucket : buckets) {
            if (ebBucket.contains(index)) {
                ebBucket.deleteIndex(index);
                return;
            }
        }
    }

    public boolean isEmpty() {
        return buckets.stream().allMatch(EBBucket::isEmpty);
    }
    public boolean isFull() {
        return buckets.stream().allMatch(EBBucket::isFull);
    }

}
