package Backend.data_type;

public class BinaryIntegerType extends DataType {
    private int value;

    public BinaryIntegerType(int value) {
        this.value = value;
    }

    @Override
    public double diff(DataType other) {
        if (!(other instanceof BinaryIntegerType)) {
            throw new IllegalArgumentException("Can only compare BinaryInteger with BinaryInteger");
        }
        return Math.abs(this.value - ((BinaryIntegerType) other).value);
    }

    @Override
    public int compareTo(DataType other) {
        if (!(other instanceof BinaryIntegerType)) {
            throw new IllegalArgumentException("Can only compare BinaryInteger with BinaryInteger");
        }
        return Integer.compare(this.value, ((BinaryIntegerType) other).value);
    }

    @Override
    public String toString() {
        return Integer.toBinaryString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BinaryIntegerType that = (BinaryIntegerType) obj;
        return this.value == that.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    public BinaryIntegerType bitwiseAnd(BinaryIntegerType other) {
        if (other == null) {
            throw new IllegalArgumentException("Argument to bitwiseAnd cannot be null");
        }
        return new BinaryIntegerType(this.value & other.value);
    }


}
