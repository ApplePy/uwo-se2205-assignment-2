package Sorts;

/**
 * Created by darryl on 2016-03-11.
 */
public class UninitializedException extends RuntimeException {


    public UninitializedException(String message) {
        super(message);
    }

    public UninitializedException(String message, Throwable cause) {
        super(message,cause);
    }

    public UninitializedException(Throwable cause) {
        super(cause);
    }

    public UninitializedException(String message, Throwable cause, boolean enableSuppression, boolean writeableStackTrace) {
        super (message, cause, enableSuppression, writeableStackTrace);
    }

    public UninitializedException() {
        super();
    }
}
