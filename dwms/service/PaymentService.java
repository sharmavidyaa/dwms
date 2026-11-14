package dwms.service;

import dwms.exception.WalletException;
import dwms.model.WalletAccount;

public interface PaymentService {
    void pay(WalletAccount from, WalletAccount to, double amount) throws WalletException;

    default void printReceipt(WalletAccount from, WalletAccount to, double amount) {
        System.out.println("[RECEIPT] Successfully transferred ₹" + amount + " from " + from.getAccountId() + " to " + to.getAccountId());
    }

    static boolean isValidMobile(String mobile) {
        return mobile != null && mobile.matches("\\d{10}");
    }

    private void logTransaction(String details) {
        System.out.println("[LOG] " + details);
    }
}