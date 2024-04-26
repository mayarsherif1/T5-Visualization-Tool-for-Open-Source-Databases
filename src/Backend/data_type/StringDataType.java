package Backend.data_type;

public class StringDataType extends DataType {
    private String data;

    public StringDataType(String data) {
        this.data = data;
    }

    @Override
    public double diff(DataType dataType) {
        if (dataType instanceof StringDataType) {
            return this.data.equals(((StringDataType) dataType).data) ? 0 : 1;
        }
        return 1;
    }

    @Override
    public int compareTo(DataType dataType) {
        if (dataType instanceof StringDataType) {
            return this.data.compareTo(((StringDataType) dataType).data);
        }
        throw new IllegalArgumentException("Invalid comparison between different data types.");
    }

    @Override
    public String toString() {
        return this.data;
    }
}
