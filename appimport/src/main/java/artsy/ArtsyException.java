package artsy;

/**
 * Custom exception class for handling errors related to Artsy API operations.
 */
public class ArtsyException extends Exception {

    /**
     * Constructs a new ArtsyException with {@code null} as its detail message.
     * The cause is not initialized.
     */
    public ArtsyException() {
        super();
    }

    /**
     * Constructs a new ArtsyException with the specified detail message.
     * The cause is not initialized.
     *
     * @param message The detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public ArtsyException(String message) {
        super(message);
    }

    /**
     * Constructs a new ArtsyException with the specified detail message and cause.
     *
     * @param message The detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ArtsyException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Constructs a new ArtsyException with the specified cause and a detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of {@code cause}).
     *
     * @param cause The cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ArtsyException(Throwable cause) {
        super(cause);
    }
}