package dwms.service;

import dwms.model.WalletAccount;
import java.util.ArrayList;
import java.util.List;

public class AccountRegistry<T extends WalletAccount> {
    private final List<T> accounts = new ArrayList<>();

    public void add(T account) {
        accounts.add(account);
    }

    public T find(String accountId) {
        for (T acc : accounts) {
            if (acc.getAccountId().equalsIgnoreCase(accountId)) {
                return acc;
            }
        }
        return null;
    }

    public boolean remove(String accountId) {
        return accounts.removeIf(acc -> acc.getAccountId().equalsIgnoreCase(accountId));
    }

    public T findHighestBalance() {
        if (accounts.isEmpty()) return null;
        T highest = accounts.get(0);
        for (T acc : accounts) {
            if (acc.getBalance() > highest.getBalance()) {
                highest = acc;
            }
        }
        return highest;
    }

    public List<T> getAll() {
        return accounts;
    }
}