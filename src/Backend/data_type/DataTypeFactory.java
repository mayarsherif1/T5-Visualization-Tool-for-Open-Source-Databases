package Backend.data_type;

import Backend.Exception.InvalidTypeValueException;

public class DataTypeFactory {

    public DataType makeType(String typeName, String value) throws InvalidTypeValueException {
        String val = value.replace("'", "");
        switch (typeName.toLowerCase()) {
            case "bool":
            case "boolean":

                return new BooleanType(BooleanType.parseBoolean(val));

            case "decimal":
                try {
                    return new DecimalType(Double.parseDouble(val));
                } catch (Exception e) {
                    throw new InvalidTypeValueException("Value '" + value + "' is not a decimal");
                }
            case "integer":
                try {
                    return new IntegerType(Integer.parseInt(val));
                } catch (Exception e) {
                    throw new InvalidTypeValueException("Value '" + value + "' is not an integer");
                }
            case "timestamp":
            case "datetime":
                return new TimestampType(val);

            case "varchar":
                return new VarCharType(val);
            default:
                return null;
        }

    }

}