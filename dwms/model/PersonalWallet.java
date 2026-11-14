package dwms.model;

public class PersonalWallet extends WalletAccount {
    public PersonalWallet(String holderName, String mobile, double initialBalance) {
        super(holderName, mobile, initialBalance);
    }

    @Override
    public double calculateFee(double amount) {
        return 0.0; // No fee for personal wallets
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.PERSONAL;
    }
}