package Backend.data_type;

import Backend.Exception.InvalidTypeValueException;
import Backend.Utility;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;

public class TimestampType extends DataType implements Serializable {
    private final Timestamp timestamp;

    private static final long serialVersionUID = 8410489057933198854L;

    public TimestampType(String dateTime) throws InvalidTypeValueException {
        this.timestamp = parseTimestamp(dateTime);
    }
    public static Timestamp parseTimestamp(String string) throws InvalidTypeValueException {
        /**
         * Matches strings of the form:<br><br>
         * <b>YYYY-MM-DD HH:MM:SS</b>
         */
        System.out.println("Date being parsed: " + string);
        Matcher matcher = Utility.getMatcher(string, "\\A\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}\\:\\d{2}\\:\\d{2}\\z");
        if (matcher.matches()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return new Timestamp(dateFormat.parse(string).getTime());
            } catch (ParseException e) {
                e.printStackTrace();

            }
        }
        throw new InvalidTypeValueException("The value '" + string + "' is not a date. Value must be of the format YYYY-MM-DD HH:MM:SS");
    }

    @Override
    public double diff(DataType dataType) {
        return -1;
    }

    @Override
    public int compareTo(DataType dataType) {
        TimestampType type = (TimestampType) dataType;
        return timestamp.compareTo(type.timestamp);
    }

    @Override
    public String toString() {
        return timestamp.toString();
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}