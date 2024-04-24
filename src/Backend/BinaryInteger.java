package Backend;

public class BinaryInteger extends DataType {
    private int value;

    public BinaryInteger(int value) {
        this.value = value;
    }

    @Override
    public double diff(DataType other) {
        if (!(other instanceof BinaryInteger)) {
            throw new IllegalArgumentException("Can only compare BinaryInteger with BinaryInteger");
        }
        return Math.abs(this.value - ((BinaryInteger) other).value);
    }

    @Override
    public int compareTo(DataType other) {
        if (!(other instanceof BinaryInteger)) {
            throw new IllegalArgumentException("Can only compare BinaryInteger with BinaryInteger");
        }
        return Integer.compare(this.value, ((BinaryInteger) other).value);
    }

    @Override
    public String toString() {
        return Integer.toBinaryString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BinaryInteger that = (BinaryInteger) obj;
        return this.value == that.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    public BinaryInteger bitwiseAnd(BinaryInteger other) {
        if (other == null) {
            throw new IllegalArgumentException("Argument to bitwiseAnd cannot be null");
        }
        return new BinaryInteger(this.value & other.value);
    }


}
