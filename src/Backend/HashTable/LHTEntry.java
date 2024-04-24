//package Backend.HashTable;
//
//import Backend.DataType;
//
//import java.util.Map;
//
//public class LHTEntry implements Map.Entry<DataType, DataType> {
//    private final DataType key;
//    private DataType value;
//    private final int hash;
//
//    public LHTEntry(DataType key, DataType value, int hash) {
//        this.key = key;
//        this.value = value;
//        this.hash = hash;
//    }
//
//
//    @Override
//    public DataType getKey() {
//        return key;
//    }
//
//    @Override
//    public DataType getValue() {
//        return value;
//    }
//
//    @Override
//    public DataType setValue(DataType value) {
//        DataType old = this.value;
//        this.value = value;
//        return old;
//    }
//
//    public int getHash() {
//        return hash;
//    }
//}