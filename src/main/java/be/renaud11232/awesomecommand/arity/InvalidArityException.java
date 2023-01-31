package be.renaud11232.awesomecommand.arity;

public class InvalidArityException extends IllegalArgumentException {
    public InvalidArityException() {
    }

    public InvalidArityException(String s) {
        super(s);
    }

    public InvalidArityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArityException(Throwable cause) {
        super(cause);
    }
}
