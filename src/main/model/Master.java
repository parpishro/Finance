package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// master object containing all the accounts and their associated transactions
public class Master implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Account> accounts;
    private List<Transaction> allTransactions;
    private int totalBalance;


    // EFFECT: constructs a master object adding a default account of cash with zero total balance and no transactions
    public Master() {
        accounts = new ArrayList<>();
        allTransactions = new ArrayList<>();
        totalBalance = 0;
    }

    // MODIFIES: this
    // EFFECT: adds an account to accounts with given name and isPos and creates a unique index for it
    public void addAccount(String name, boolean isPos, int balance) {
        int index = accounts.size();
        try {
            while (accounts.contains(accounts.get(index))) {
                index++;
            }
        } catch (Exception e) {
            accounts.add(new Account(index, name, isPos, balance));
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

    // REQUIRE: 'type' must be one of the transaction category types: "Earning", "Spending", "Transfer", "Investing",
    // "Saving", "Lending", or "Borrowing", 'date' must be valid date with "YYYY-MM-DD" format, 'from' and 'to' must be
    // one of existing accounts
    // MODIFIES: this
    // EFFECT: add the transaction to the allTransaction
    public Transaction addTransaction(String type, String date, int value, Account from, Account to) {
        int index = generateIndex();
        Transaction entry = new Transaction(index, type, date, value, to, from);
        allTransactions.add(entry);
        updateBalance(type, value);
        return entry;
    }


    private void updateBalance(String type, int value) {
        if (type.equals("Earning")) {
            totalBalance += value;
        } else if (type.equals("Spending")) {
            totalBalance -= value;
        }
    }



    private int generateIndex() {
        int index = allTransactions.size();
        while (!(allTransactions.get(index) == null)) {
                index++;
            }
        return index;
    }

    public Transaction removeTransaction(int index) {
        Transaction transaction = null;
        int listIndex = 0;
        for (Transaction entry: allTransactions) {
            if (entry.getIndex() == index) {
                allTransactions.remove(listIndex);
                updateBalance(entry.getType(), entry.getValue());
                transaction = entry;
            }
            listIndex++;
        }
        return transaction;
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
