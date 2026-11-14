package dwms.exception;

/**
 * Base checked exception for all digital wallet rule violations.
 */
public class WalletException extends Exception {
    public WalletException(String message) {
        super(message);
    }
    
    public WalletException(String message, Throwable cause) {
        super(message, cause);
    }
}