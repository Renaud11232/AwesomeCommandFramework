package be.renaud11232.awesomecommand.arity;

/**
 * The {@link InvalidArityException} exception class is thrown when an argument arity could not be parsed as an
 * {@link Arity} object. For instance, when the given string does not respect the right format.
 */
public class InvalidArityException extends IllegalArgumentException {

    /**
     * {@inheritDoc}
     *
     * @see IllegalArgumentException#IllegalArgumentException()
     */
    @SuppressWarnings("unused")
    public InvalidArityException() {
    }

    /**
     * {@inheritDoc}
     *
     * @see IllegalArgumentException#IllegalArgumentException(String)
     */
    public InvalidArityException(String s) {
        super(s);
    }

    /**
     * {@inheritDoc}
     *
     * @see IllegalArgumentException#IllegalArgumentException(String, Throwable)
     */
    @SuppressWarnings("unused")
    public InvalidArityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     *
     * @see IllegalArgumentException#IllegalArgumentException(Throwable)
     */
    @SuppressWarnings("unused")
    public InvalidArityException(Throwable cause) {
        super(cause);
    }
}
