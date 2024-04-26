package Backend.data_type;

public class GenericType extends DataType {
    private final String value;

    public GenericType(String value) {
        this.value = value;
    }

    /**
     *
     * @return the value of the data type.
     */
    public String getValue() {
        return value;
    }

    @Override
    public double diff(DataType dataType) {
        return 0;
    }

    @Override
    public int compareTo(DataType dataType) {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }

}