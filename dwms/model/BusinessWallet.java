package dwms.model;

public class BusinessWallet extends WalletAccount {
    public BusinessWallet(String holderName, String mobile, double initialBalance) {
        super(holderName, mobile, initialBalance);
    }

    @Override
    public double calculateFee(double amount) {
        return amount * AccountType.BUSINESS.getFeeRate();
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.BUSINESS;
    }
}