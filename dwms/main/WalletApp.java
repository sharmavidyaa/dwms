package dwms.main;

import dwms.exception.WalletException;
import dwms.model.WalletAccount;
import dwms.service.RewardThread;
import dwms.service.StatementTask;
import dwms.service.WalletService;
import dwms.util.WalletUtils;
import java.util.Scanner;

public class WalletApp {
    public static void main(String[] args) {
        WalletService service = new WalletService();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n======= DIGITAL WALLET MANAGEMENT SYSTEM =======");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Pay / Transfer");
            System.out.println("5. Check Balance");
            System.out.println("6. Mini Statement");
            System.out.println("7. Account with Highest Balance");
            System.out.println("8. End-of-Day Processing");
            System.out.println("0. Exit");
            System.out.println("================================================");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch(choice) {
                    case 1:
                        System.out.print("Enter Type (PERSONAL/BUSINESS): ");
                        String type = scanner.nextLine();
                        System.out.print("Enter Holder Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter 10-digit Mobile: ");
                        String mobile = scanner.nextLine();
                        System.out.print("Enter Initial Deposit: ");
                        double initBal = WalletUtils.parseAmount(scanner.nextLine());
                        WalletAccount acc = service.createAccount(type, name, mobile, initBal);
                        System.out.println("Account created successfully! ID: " + acc.getAccountId());
                        break;

                    case 2:
                        System.out.print("Enter Account ID: ");
                        WalletAccount depAcc = service.findAccount(scanner.nextLine());
                        System.out.print("Enter Amount to Deposit: ");
                        double depAmt = WalletUtils.parseAmount(scanner.nextLine());
                        depAcc.deposit(depAmt);
                        System.out.println("Deposit successful. New Balance: " + WalletUtils.formatCurrency(depAcc.getBalance()));
                        
                        // Trigger background reward thread demo
                        new RewardThread(depAcc.getAccountId()).start();
                        break;

                    case 3:
                        System.out.print("Enter Account ID: ");
                        WalletAccount witAcc = service.findAccount(scanner.nextLine());
                        System.out.print("Enter Amount to Withdraw: ");
                        double witAmt = WalletUtils.parseAmount(scanner.nextLine());
                        if (witAcc.withdraw(witAmt)) {
                            System.out.println("Withdrawal successful. New Balance: " + WalletUtils.formatCurrency(witAcc.getBalance()));
                        } else {
                            System.out.println("Withdrawal failed: Insufficient balance.");
                        }
                        break;

                    case 4:
                        System.out.print("Enter Source Account ID: ");
                        WalletAccount from = service.findAccount(scanner.nextLine());
                        System.out.print("Enter Destination Account ID: ");
                        WalletAccount to = service.findAccount(scanner.nextLine());
                        System.out.print("Enter Transfer Amount: ");
                        double transferAmt = WalletUtils.parseAmount(scanner.nextLine());
                        service.pay(from, to, transferAmt);
                        break;

                    case 5:
                        System.out.print("Enter Account ID: ");
                        WalletAccount balAcc = service.findAccount(scanner.nextLine());
                        System.out.println("Balance: " + WalletUtils.formatCurrency(balAcc.getBalance()));
                        break;

                    case 6:
                        System.out.print("Enter Account ID: ");
                        WalletAccount stmtAcc = service.findAccount(scanner.nextLine());
                        WalletAccount.Statement statement = stmtAcc.new Statement();
                        statement.print();
                        
                        // Run parallel task
                        new Thread(new StatementTask(stmtAcc.getAccountId())).start();
                        break;

                    case 7:
                        WalletAccount high = service.getRegistry().findHighestBalance();
                        if (high != null) {
                            System.out.println("Highest Balance Account: " + high.getAccountId() + " (" + high.getHolderName() + ") with " + WalletUtils.formatCurrency(high.getBalance()));
                        } else {
                            System.out.println("No accounts registered.");
                        }
                        break;

                    case 8:
                        service.endOfDayProcessing();
                        break;

                    case 0:
                        running = false;
                        System.out.println("Exiting application. Thank you!");
                        break;

                    default:
                        System.out.println("Invalid option. Please choose between 0 and 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[Error] Please enter a valid numeric value.");
            } catch (WalletException e) {
                System.out.println("[Business Rule Error] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("[Unexpected Error] " + e.getMessage());
            }
        }
        scanner.close();
    }
}