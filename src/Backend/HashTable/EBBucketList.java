package Backend.HashTable;

import java.util.ArrayList;

public class EBBucketList implements EBPartitionedHashIndex{

    private final ArrayList<EBBucket> buckets;
    private EBBucket currentBucket;
    private final int bucketCapacity;

    public EBBucketList(int bucketCapacity) {
        this.bucketCapacity = bucketCapacity;
        this.buckets = new ArrayList<EBBucket>();
        EBBucket bucket = new EBBucket(this.bucketCapacity);
        this.currentBucket = bucket;
        buckets.add(bucket);
    }

    public int buckets() {
        return this.buckets.size();
    }

    @Override
    public void addIndex(EBIndex index) {
        this.currentBucket.addIndex(index);
        if (this.currentBucket.isFull()) {
            EBBucket bucket = new EBBucket(this.bucketCapacity);
            this.currentBucket = bucket;
            buckets.add(bucket);
        }
    }

    @Override
    public void updateIndex(EBIndex oldIndex, EBIndex newIndex) {
        for (EBBucket ebBucket : buckets) {
            ebBucket.updateIndex(oldIndex, newIndex);
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

    @Override
    public void deleteIndex(EBIndex index) {
        for (EBBucket ebBucket : buckets) {
            ebBucket.deleteIndex(index);
        }
    }
}
