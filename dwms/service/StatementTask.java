package dwms.service;

public class StatementTask implements Runnable {
    private final String accountId;

    public StatementTask(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public void run() {
        System.out.println("[Background Runnable] Exporting monthly statement report for " + accountId + "...");
    }
}