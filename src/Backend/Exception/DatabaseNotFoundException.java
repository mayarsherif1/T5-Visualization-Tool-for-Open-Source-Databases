package Backend.Exception;

public class DatabaseNotFoundException extends Exception {
    public DatabaseNotFoundException(String message) {
        super(message);
    }

    public DatabaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}