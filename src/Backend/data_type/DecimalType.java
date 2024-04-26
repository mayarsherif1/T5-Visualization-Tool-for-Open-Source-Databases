package Backend.data_type;

import java.io.Serializable;

public class DecimalType extends DataType implements Serializable {

    private static final long serialVersionUID = -240691003965584504L;
    private final Double decimal;

    public DecimalType(Double decimal) {
        this.decimal = decimal;
    }

    @Override
    public double diff(DataType dataType) {
        return -1;
    }

    @Override
    public int compareTo(DataType dataType) {
        DecimalType type = (DecimalType) dataType;
        return decimal.compareTo(type.decimal);
    }

    @Override
    public boolean equals(Object o) {
        DecimalType type = (DecimalType) o;
        return decimal.equals(type.decimal);
    }

    @Override
    public String toString() {
        return decimal.toString();
    }

    public Double getDecimal() {
        return decimal;
    }
}