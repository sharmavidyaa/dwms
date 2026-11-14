package dwms.util;

import dwms.exception.InvalidAmountException;
import java.text.NumberFormat;
import java.util.Locale;

public final class WalletUtils {
    private WalletUtils() {}

    public static double parseAmount(String text) throws InvalidAmountException {
        try {
            double amount = Double.parseDouble(text.trim());
            if (amount <= 0) {
                throw new InvalidAmountException("Amount must be greater than zero.");
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new InvalidAmountException("Invalid numeric format: " + text, e);
        }
    }

    public static String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        return formatter.format(amount);
    }
}