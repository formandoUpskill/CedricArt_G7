package artsy;

public class ArtsyException extends Exception {

    // Constructor with no arguments
    public ArtsyException() {
        super();
    }

    // Constructor that accepts a message
    public ArtsyException(String message) {
        super(message);
    }

    // Constructor that accepts a message and a cause
    public ArtsyException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts a cause
    public ArtsyException(Throwable cause) {
        super(cause);
    }
}