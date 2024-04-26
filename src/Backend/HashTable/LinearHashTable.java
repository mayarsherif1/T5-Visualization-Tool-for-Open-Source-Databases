package Backend.HashTable;

import Backend.data_type.DataType;

import java.util.*;

public class LinearHashTable implements Map<DataType, DataType> {

    private final float loadFactor;

    private final int bucketSize;

    private int size;

    private int digits;

    private int hashSeed;

    private int numberOfItems;

    private final ArrayList<Bucket> buckets;

    public LinearHashTable(float loadFactor, int bucketSize) {
        this.loadFactor = loadFactor;
        this.bucketSize = bucketSize;
        buckets = new ArrayList<>();
        init();
    }

    private void init() {
        size = 0;
        digits = 1;
        Bucket bucket = new Bucket(bucketSize);
        buckets.add(bucket);
        Random generator = new Random();
        hashSeed = generator.nextInt();
    }

    @Override
    public int size() {
        return numberOfItems;
    }

    @Override
    public boolean isEmpty() {
        return numberOfItems == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return getEntry(key) != null;
    }

    private LHTEntry getEntry(Object key) {
        if (key instanceof DataType) {
            int b = getBucket((DataType) key);
            Bucket bucket = buckets.get(b);
            LHTEntry entry;
            entry = bucket.getEntry(key);
            return entry;
        }
        return null;
    }

    @Override
    public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public DataType get(Object key) {
        LHTEntry entry = getEntry(key);
        return null == entry ? null : entry.getValue();
    }

    public int getBucket(DataType key) {
        int hash = hash(key);
        int bits = hash & ((int) Math.pow(2, digits) - 1);
        if (bits <= size) {
            return bits;
        } else {
            bits = bits - (int) Math.pow(2, (digits - 1));
            return bits;
        }
    }

    @Override
    public DataType put(DataType key, DataType value) {
        int b = getBucket(key);
        Bucket bucket = buckets.get(b);
        int hash = hash(key);
        bucket.put(key, value, hash);
        numberOfItems++;
        if (numberOfItems / ((size + 1) * bucketSize) >= loadFactor) {
            resize();
        }
        return null;
    }

    private void resize() {
        size++;
        Bucket b = new Bucket(bucketSize);
        buckets.add(b);
        if (size == (int) Math.pow(2, digits)) {
            digits++;
        }
        int index = size - (int) Math.pow(2, digits - 1);
        Bucket bucket = buckets.get(index);
        bucket.scan();
    }

    public void downSize() {

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    final int hash(Object k) {
        int h = hashSeed;
        h ^= k.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    @Override
    public DataType remove(Object key) {
        int b = getBucket((DataType) key);
        Bucket bucket = buckets.get(b);
        LHTEntry entry = bucket.remove((DataType) key);
        numberOfItems--;
        return entry.value;
    }

    @Override
    public void putAll(Map<? extends DataType, ? extends DataType> m) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<DataType> keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<DataType> values() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Entry<DataType, DataType>> entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    class Bucket {

        LHTEntry[] entries;
        int lastItem;
        LinkedList<LHTEntry> overflow;

        public Bucket(int bucketSize) {
            entries = new LHTEntry[bucketSize];
            lastItem = 0;
        }

        public LHTEntry remove(DataType key) {
            LHTEntry r = null;
            for (int i = 0; i < lastItem; i++) {
                if (entries[i].getKey().equals(key)) {
                    r = entries[i];
                    for (int j = i; j < lastItem - 1; j++) {
                        entries[j] = entries[j + 1];
                    }
                    if (overflow != null) {
                        if (!overflow.isEmpty()) {
                            LHTEntry entry = overflow.removeFirst();
                            entries[entries.length - 1] = entry;
                            lastItem++;
                        }
                    }
                    lastItem--;
                    return r;
                }
            }
            if (overflow != null) {
                Iterator<LHTEntry> itr = overflow.iterator();
                while (itr.hasNext()) {
                    LHTEntry element = itr.next();
                    if ((element.getKey()).equals(key)) {
                        r = element;
                        itr.remove();
                        break;
                    }
                }
            }
            return r;
        }

        public LHTEntry getEntry(Object key) {
            for (int i = 0; i < lastItem; i++) {
                DataType dataKey = (DataType) key;
                if (entries[i].getKey().equals(dataKey)) {
                    return entries[i];
                }
            }
            return null;
        }

        public void put(DataType key, DataType value, int hash) {
            if (lastItem == entries.length) {
                overflow.add(new LHTEntry(key, value, hash));
            } else {
                entries[lastItem++] = new LHTEntry(key, value, hash);
                if (lastItem == entries.length) {
                    overflow = new LinkedList<>();
                }
            }
        }

        public void scan() {
            for (int i = 0; i < lastItem; i++) {
                int bits = entries[i].hash & ((int) Math.pow(2, digits) - 1);
                if (bits > (int) Math.pow(2, digits - 1) - 1) {
                    LHTEntry entry = entries[i];
                    remove(entries[i].key);
                    numberOfItems--;
                    LinearHashTable.this.put(entry.getKey(), entry.getValue());
                    i--;
                }
            }
            if (overflow != null) {
                Iterator<LHTEntry> itr = overflow.iterator();
                while (itr.hasNext()) {
                    LHTEntry element;
                    element = itr.next();
                    int bits = element.hash & ((int) Math.pow(2, digits) - 1);
                    if (bits > (int) Math.pow(2, digits - 1) - 1) {
                        itr.remove();
                        numberOfItems--;
                        LinearHashTable.this.put(element.getKey(), element.getValue());
                    }
                }
            }
        }
    }

    class LHTEntry implements Entry<DataType, DataType> {

        private final DataType key;

        private DataType value;
        private final int hash;

        public LHTEntry(DataType key, DataType value, int hash) {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }


        public DataType getValue() {
            return value;
        }

        @Override
        public DataType getKey() {
            return key;
        }


        @Override
        public DataType setValue(DataType value) {
            DataType old = this.value;
            this.value = value;
            return old;
        }
    }

}