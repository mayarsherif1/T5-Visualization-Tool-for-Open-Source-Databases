package Backend;

public class TreeException extends Exception {
    public TreeException(String message){
        super(message);
    }
    public TreeException(String message, Throwable cause){
        super(message,cause);
    }
}
