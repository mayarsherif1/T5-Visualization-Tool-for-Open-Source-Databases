package Backend.Exception;

public class InvalidTypeValueException extends Exception {

    private static final long serialVersionUID = 2439897185322277576L;

    public InvalidTypeValueException(String message) {
        super(message);
    }

}