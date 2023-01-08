package be.renaud11232.awesomecommand.adapter;

/**
 * The {@link UnsupportedTypeAdapterException} exception class is used to signal that a {@link ArgumentValueAdapter}
 * is not able to convert an argument to the desired type.
 */
public class UnsupportedTypeAdapterException extends Exception {


    /**
     * {@inheritDoc}
     *
     * @see Exception#Exception()
     */
    @SuppressWarnings("unused")
    public UnsupportedTypeAdapterException() {
    }


    /**
     * {@inheritDoc}
     *
     * @see Exception#Exception(String)
     */
    public UnsupportedTypeAdapterException(String message) {
        super(message);
    }


    /**
     * {@inheritDoc}
     *
     * @see Exception#Exception(String, Throwable)
     */
    @SuppressWarnings("unused")
    public UnsupportedTypeAdapterException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * {@inheritDoc}
     *
     * @see Exception#Exception(Throwable)
     */
    @SuppressWarnings("unused")
    public UnsupportedTypeAdapterException(Throwable cause) {
        super(cause);
    }


    /**
     * {@inheritDoc}
     *
     * @see Exception#Exception(String, Throwable, boolean, boolean)
     */
    @SuppressWarnings("unused")
    protected UnsupportedTypeAdapterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
