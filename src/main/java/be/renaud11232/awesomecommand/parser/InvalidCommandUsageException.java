package be.renaud11232.awesomecommand.parser;

/**
 * The {@link InvalidCommandUsageException} exception class is thrown when a command could not be parsed and executed due to
 * invalid arguments. For instance, when a required argument was not provided.
 */
public class InvalidCommandUsageException extends IllegalArgumentException {

    /**
     * {@inheritDoc}
     *
     * @see IllegalArgumentException#IllegalArgumentException()
     */
    @SuppressWarnings("unused")
    public InvalidCommandUsageException() {
    }

    /**
     * {@inheritDoc}
     *
     * @see IllegalArgumentException#IllegalArgumentException(String)
     */
    @SuppressWarnings("unused")
    public InvalidCommandUsageException(String s) {
        super(s);
    }

    /**
     * {@inheritDoc}
     *
     * @see IllegalArgumentException#IllegalArgumentException(String, Throwable)
     */
    public InvalidCommandUsageException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     *
     * @see IllegalArgumentException#IllegalArgumentException(Throwable)
     */
    @SuppressWarnings("unused")
    public InvalidCommandUsageException(Throwable cause) {
        super(cause);
    }
}
