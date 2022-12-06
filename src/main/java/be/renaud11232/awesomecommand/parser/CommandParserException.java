package be.renaud11232.awesomecommand.parser;

/**
 * The {@link CommandParserException} exception class is used to signal any issue when parsing a command
 */
public class CommandParserException extends RuntimeException {

    /**
     * {@inheritDoc}
     *
     * @see RuntimeException#RuntimeException()
     */
    @SuppressWarnings("unused")
    public CommandParserException() {
        super();
    }

    /**
     * {@inheritDoc}
     *
     * @see RuntimeException#RuntimeException(String)
     */
    @SuppressWarnings("unused")
    public CommandParserException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     *
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public CommandParserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     *
     * @see RuntimeException#RuntimeException(Throwable)
     */
    @SuppressWarnings("unused")
    public CommandParserException(Throwable cause) {
        super(cause);
    }

    /**
     * {@inheritDoc}
     *
     * @see RuntimeException#RuntimeException(String, Throwable, boolean, boolean)
     */
    @SuppressWarnings("unused")
    protected CommandParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
