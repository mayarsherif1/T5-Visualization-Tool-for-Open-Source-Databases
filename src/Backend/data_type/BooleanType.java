package Backend.data_type;
import Backend.Exception.InvalidTypeValueException;

import java.io.Serializable;

public class BooleanType extends DataType implements Serializable {
    private final boolean bool;

    private static final long serialVersionUID = -8942317279081394033L;

    public BooleanType(boolean bool) {
        this.bool = bool;
    }

    public static boolean parseBoolean(String string) throws InvalidTypeValueException {
        switch (string.toLowerCase()) {
            case "1":
            case "t":
            case "true":
                return true;
            case "0":
            case "f":
            case "false":
                return false;
            default:
                throw new InvalidTypeValueException("Value '" + string + "' is not a boolean. Value csan only be 1, t, true, 0, f, or false only");
        }
    }

    @Override
    public double diff(DataType dataType) {
        return -1;
    }

    @Override
    public int compareTo(DataType dataType) {
        BooleanType type = (BooleanType) dataType;
        if (bool == type.bool) {
            return 0;
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        BooleanType type = (BooleanType) o;
        return bool == type.bool;
    }

    public boolean getBoolean() {
        return bool;
    }

    @Override
    public String toString() {
        return bool + "";
    }

}