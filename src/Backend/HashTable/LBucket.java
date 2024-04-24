//package Backend.HashTable;
//
//import Backend.DataType;
//
//import java.util.Iterator;
//import java.util.LinkedList;
//
//public class LBucket {
//    LHTEntry[] entries;
//    int lastItem;
//    LinkedList<LHTEntry> overflow;
//
//    public LBucket(int bucketSize) {
//        entries = new LHTEntry[bucketSize];
//        lastItem = 0;
//    }
//    public LHTEntry remove(DataType key) {
//       LHTEntry r = null;
//        for (int i = 0; i < lastItem; i++) {
//            if (entries[i].getKey().equals(key)) {
//                r = entries[i];
//                for (int j = i; j < lastItem - 1; j++) {
//                    entries[j] = entries[j + 1];
//                }
//                if (overflow != null) {
//                    if (!overflow.isEmpty()) {
//                        LHTEntry entry = overflow.removeFirst();
//                        entries[entries.length - 1] = entry;
//                        lastItem++;
//                    }
//                }
//                lastItem--;
//                return r;
//            }
//        }
//        if (overflow != null) {
//            Iterator<LHTEntry> itr = overflow.iterator();
//            while (itr.hasNext()) {
//               LHTEntry element = itr.next();
//                if ((element.getKey()).equals(key)) {
//                    r = element;
//                    itr.remove();
//                    break;
//                }
//            }
//        }
//        return r;
//    }
//
//    public LHTEntry getEntry(Object key) {
//        for (int i = 0; i < lastItem; i++) {
//            DataType dataKey = (DataType) key;
//            if (entries[i].getKey().equals(dataKey)) {
//                return entries[i];
//            }
//        }
//        return null;
//    }
//
//    public void put(DataType key, DataType value, int hash) {
//        if (lastItem == entries.length) {
//            overflow.add(new LHTEntry(key, value, hash));
//        } else {
//            entries[lastItem++] = new LHTEntry(key, value, hash);
//            if (lastItem == entries.length) {
//                overflow = new LinkedList<>();
//            }
//        }
//    }
//
//    public void scan() {
//        for (int i = 0; i < lastItem; i++) {
//            int bits = entries[i].getHash() & ((int) Math.pow(2, digits) - 1);
//            if (bits > (int) Math.pow(2, digits - 1) - 1) {
//                LHTEntry entry = entries[i];
//                remove(entries[i].key);
//                numberOfItems--;
//                this.put(entry.getKey(), entry.getValue());
//                i--;
//            }
//        }
//        if (overflow != null) {
//            Iterator<LinearHashTable.LHTEntry> itr = overflow.iterator();
//            while (itr.hasNext()) {
//                LinearHashTable.LHTEntry element;
//                element = itr.next();
//                int bits = element.hash & ((int) Math.pow(2, digits) - 1);
//                if (bits > (int) Math.pow(2, digits - 1) - 1) {
//                    itr.remove();
//                    numberOfItems--;
//                    this.put(element.getKey(), element.getValue());
//                }
//            }
//        }
//    }
//}