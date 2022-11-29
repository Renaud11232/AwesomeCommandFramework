package be.renaud11232.awesomecommand.parser;

public class CommandParserException extends RuntimeException {

    public CommandParserException() {
        super();
    }

    public CommandParserException(String message) {
        super(message);
    }

    public CommandParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandParserException(Throwable cause) {
        super(cause);
    }

    protected CommandParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
