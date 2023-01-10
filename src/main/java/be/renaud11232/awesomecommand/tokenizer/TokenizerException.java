package be.renaud11232.awesomecommand.tokenizer;

/**
 * The {@link TokenizerException} exception class is thrown when a {@link CommandTokenizer} could not tokenize a given
 * input due to incorrect formatting.
 */
public class TokenizerException extends IllegalArgumentException {

    /**
     * {@inheritDoc}
     *
     * @see IllegalArgumentException#IllegalArgumentException()
     */
    @SuppressWarnings("unused")
    public TokenizerException() {
    }

    /**
     * {@inheritDoc}
     *
     * @see IllegalArgumentException#IllegalArgumentException(String)
     */
    public TokenizerException(String s) {
        super(s);
    }

    /**
     * {@inheritDoc}
     *
     * @see IllegalArgumentException#IllegalArgumentException(String, Throwable)
     */
    @SuppressWarnings("unused")
    public TokenizerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     *
     * @see IllegalArgumentException#IllegalArgumentException(Throwable)
     */
    @SuppressWarnings("unused")
    public TokenizerException(Throwable cause) {
        super(cause);
    }
}
