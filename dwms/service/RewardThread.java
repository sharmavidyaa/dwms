package dwms.service;

public class RewardThread extends Thread {
    private final String accountId;

    public RewardThread(String accountId) {
        super("RewardThread-" + accountId);
        this.accountId = accountId;
    }

    @Override
    public void run() {
        try {
            System.out.println("[Background Thread] Calculating rewards for " + accountId + "...");
            Thread.sleep(500); // Simulate background calculation
            System.out.println("[Background Thread] Reward points credited successfully for " + accountId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}