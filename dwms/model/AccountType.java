package dwms.model;

public enum AccountType {
    PERSONAL(10000.0, 0.0),
    BUSINESS(50000.0, 0.01);

    private final double dailyLimit;
    private final double feeRate;

    AccountType(double dailyLimit, double feeRate) {
        this.dailyLimit = dailyLimit;
        this.feeRate = feeRate;
    }

    public double getDailyLimit() {
        return dailyLimit;
    }

    public double getFeeRate() {
        return feeRate;
    }
}