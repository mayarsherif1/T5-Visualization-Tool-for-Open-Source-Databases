package Backend.data_type;
import java.io.Serializable;

public class VarCharType extends DataType implements Serializable {
    private static final long serialVersionUID = -9166500810916250790L;
    private final String string;

    public VarCharType(String string) {
        this.string = string;
    }

    @Override
    public double diff(DataType key) {
        return -1;
    }

    @Override
    public int compareTo(DataType dataType) {
        VarCharType type = (VarCharType) dataType;
        return string.compareTo(type.string);
    }

    @Override
    public boolean equals(Object o) {
        VarCharType type = (VarCharType) o;
        return string.equals(type.string);
    }

    @Override
    public int hashCode() {
        return string.hashCode();
    }

    public String getString() {
        return string;
    }

    @Override
    public String toString() {
        return string;
    }
}