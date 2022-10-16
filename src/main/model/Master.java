package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// master object containing all the accounts and their associated transactions
public class Master implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Account> accounts;
    private final List<Transaction> allTransactions;
    private int totalBalance;


    // EFFECT: constructs a master object adding a default account of cash with zero total balance and no transactions
    public Master() {
        accounts = new ArrayList<>();
        Account cash = new Account(0, "Cash", true);
        accounts.add(cash);
        allTransactions = new ArrayList<>();
        totalBalance = 0;
    }

    // MODIFIES: this
    // EFFECT: adds an account to accounts with given name and isPos and creates a unique index for it
    public void addAccount(String name, boolean isPos) {
        int index = accounts.size();
        try {
            while (accounts.contains(accounts.get(index))) {
                index++;
            }
        } catch (Exception e) {
            accounts.add(new Account(index, name, isPos));
        }
    }

    // REQUIRES: given account name must be a valid account name
    // MODIFIES: this
    // EFFECT: removes the given account from accounts, if given account has zero balance, otherwise returns false
    public void removeAccount(String name) {
        Account account = getAccount(name);
        if (account.getBalance() == 0) {
            accounts.remove(account.getIndex());
        }
    }

    // MODIFIES: this
    // EFFECT: add the transaction to the allTransaction
    public Transaction addTransaction(String type, String date, int value, Account from, Account to) {
        Transaction entry = null;
        int index = allTransactions.size();
        try {
            while (allTransactions.contains(allTransactions.get(index))) {
                index++;
            }
        } catch (Exception e) {
            entry = new Transaction(index, type, date, value, to, from);
            allTransactions.add(entry);
            if (type.equals("Earning")) {
                totalBalance += value;
            } else if (type.equals("Spending")) {
                totalBalance -= value;
            }
        }
        return entry;
    }

    public void removeTransaction(int index) {
        int counter = 0;
        for (Transaction transaction: allTransactions) {
            if (transaction.getIndex() == index) {
                allTransactions.remove(counter);
                totalBalance -= transaction.getValue();
                transaction.getFrom().removeEntry(index);
                transaction.getTo().removeEntry(index);
            }
            counter++;
        }
    }


    // REQUIRES: account name must be a valid account name
    // EFFECT: search accounts for the given name and returns it
    public Account getAccount(String name) {
        Account found = null;
        for (Account account : accounts) {
            if (account.getName().equals(name)) {
                found = account;
                break;
            }
        }
        return found;
    }

    public int getTotalBalance() {
        return totalBalance;
    }

    public List<Transaction> getMasterAccount() {
        return allTransactions;
    }
}
