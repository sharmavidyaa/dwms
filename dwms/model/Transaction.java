package dwms.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private final String txnId;
    private final TransactionType type;
    private final Double amount;
    private final String note;
    private final LocalDateTime timestamp;

    public Transaction(TransactionType type, Double amount, String note) {
        this.txnId = UUID.randomUUID().toString().substring(0, 8);
        this.type = type;
        this.amount = amount;
        this.note = note;
        this.timestamp = LocalDateTime.now();
    }

    public Transaction(TransactionType type, Double amount) {
        this(type, amount, "N/A");
    }

    public String getTxnId() { return txnId; }
    public TransactionType getType() { return type; }
    public Double getAmount() { return amount; }
    public String getNote() { return note; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + type + ": ₹" + amount + " (Note: " + note + ")";
    }
}