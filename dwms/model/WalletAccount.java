package dwms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class WalletAccount {
    private static int accountCount = 1000;

    private final String accountId;
    private String holderName;
    private String mobile;
    protected double balance;
    private double dailyDebitedToday;
    private final List<Transaction> transactions;

    public WalletAccount(String holderName, String mobile, double initialBalance) {
        this.accountId = "ACC" + (++accountCount);
        this.holderName = holderName;
        this.mobile = mobile;
        this.balance = initialBalance;
        this.dailyDebitedToday = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getAccountId() { return accountId; }
    public String getHolderName() { return holderName; }
    public String getMobile() { return mobile; }
    public double getBalance() { return balance; }
    public List<Transaction> getTransactions() { return transactions; }

    public void deposit(double amount) {
        this.balance += amount;
        transactions.add(new Transaction(TransactionType.DEPOSIT, amount));
    }

    public void deposit(double amount, String note) {
        this.balance += amount;
        transactions.add(new Transaction(TransactionType.DEPOSIT, amount, note));
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction(TransactionType.WITHDRAW, amount));
            return true;
        }
        return false;
    }

    public void credit(double amount) {
        this.balance += amount;
    }

    public void debit(double amount) {
        this.balance -= amount;
    }

    public abstract double calculateFee(double amount);
    public abstract AccountType getAccountType();

    // Inner class for statement generation
    public class Statement {
        public void print() {
            System.out.println("=== Statement for " + accountId + " (" + holderName + ") ===");
            System.out.println("Current Balance: ₹" + balance);
            System.out.println("Transaction History:");
            for (Transaction t : transactions) {
                System.out.println(" - " + t);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalletAccount that = (WalletAccount) o;
        return accountId.equals(that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
}