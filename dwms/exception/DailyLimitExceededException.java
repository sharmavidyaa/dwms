package dwms.exception;

public class DailyLimitExceededException extends WalletException {
    public DailyLimitExceededException(String message) {
        super(message);
    }
}