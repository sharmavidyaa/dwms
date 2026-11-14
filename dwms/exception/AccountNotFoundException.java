package dwms.exception;

public class AccountNotFoundException extends WalletException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}