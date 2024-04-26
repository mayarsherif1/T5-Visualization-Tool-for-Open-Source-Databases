package Backend.data_type;

public abstract class DataType implements Comparable<DataType> {

    public abstract double diff(DataType dataType);

    public abstract int compareTo(DataType dataType);

    public abstract String toString();

    public static boolean isSupported(String typeName) {
        switch (typeName.toLowerCase()) {
            case "bool":
            case "boolean":
            case "decimal":
            case "integer":
            case "timestamp":
            case "datetime":
            case "varchar":
                return true;
            default:
                return false;
        }
    }
}