package Backend.data_type;

import java.io.Serializable;

public class IntegerType extends DataType implements Serializable {

    private static final long serialVersionUID = 3302671401075163802L;

    private final Integer integer;

    public IntegerType(Integer integer) {
        this.integer = integer;
    }

    public double diff(DataType key) {
        IntegerType type = (IntegerType) key;
        return integer - type.integer;
    }

    @Override
    public int compareTo(DataType dataType) {
        IntegerType type = (IntegerType) dataType;
        return integer.compareTo(type.integer);
    }

    @Override
    public boolean equals(Object o) {
        IntegerType type = (IntegerType) o;
        return integer.equals(type.integer);
    }

    @Override
    public int hashCode() {
        return integer;
    }
    public int getInteger() {
        return integer;
    }

    public String toString() {
        return integer.toString();
    }
}