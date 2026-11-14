package dwms.service;

import dwms.exception.*;
import dwms.model.*;

public class WalletService implements PaymentService {
    private final AccountRegistry<WalletAccount> registry = new AccountRegistry<>();

    public WalletAccount createAccount(String type, String name, String mobile, double initialBalance) throws WalletException {
        if (!PaymentService.isValidMobile(mobile)) {
            throw new WalletException("Invalid mobile number. Must be 10 digits.");
        }
        WalletAccount account;
        if (type.equalsIgnoreCase("BUSINESS")) {
            account = new BusinessWallet(name, mobile, initialBalance);
        } else {
            account = new PersonalWallet(name, mobile, initialBalance);
        }
        registry.add(account);
        return account;
    }

    public WalletAccount findAccount(String id) throws AccountNotFoundException {
        WalletAccount acc = registry.find(id);
        if (acc == null) {
            throw new AccountNotFoundException("Account not found with ID: " + id);
        }
        return acc;
    }

    @Override
    public synchronized void pay(WalletAccount from, WalletAccount to, double amount) throws WalletException {
        if (from.equals(to)) {
            throw new WalletException("Cannot transfer money to the same account.");
        }
        double fee = from.calculateFee(amount);
        double totalRequired = amount + fee;

        if (from.getBalance() < totalRequired) {
            throw new InsufficientFundsException("Insufficient funds. Required: ₹" + totalRequired + " (includes fee ₹" + fee + ")");
        }

        if (amount > from.getAccountType().getDailyLimit()) {
            throw new DailyLimitExceededException("Amount exceeds daily limit for " + from.getAccountType());
        }

        try {
            from.debit(totalRequired);
            to.credit(amount);
            from.getTransactions().add(new Transaction(TransactionType.TRANSFER, amount, "To: " + to.getAccountId()));
            to.getTransactions().add(new Transaction(TransactionType.TRANSFER, amount, "From: " + from.getAccountId()));
            printReceipt(from, to, amount);
        } finally {
            System.out.println("[AUDIT] Transfer operation finalized for IDs: " + from.getAccountId() + " -> " + to.getAccountId());
        }
    }

    public void endOfDayProcessing() {
        System.out.println("Executing end-of-day batch processing across all registered accounts...");
    }

    public AccountRegistry<WalletAccount> getRegistry() {
        return registry;
    }
}