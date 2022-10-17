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


    // EFFECT: constructs a master object consisting of empty accounts list and allTransaction list with 0 totalBalance
    public Master() {
        accounts = new ArrayList<>();
        allTransactions = new ArrayList<>();
        totalBalance = 0;
    }

    // REQUIRES: a unique (non-existing) account name
    // MODIFIES: this
    // EFFECT: adds the given account to the accounts after updating its default index (-1) with a unique index, and
    // updates the total balance
    public void addAccount(Account account) {
        int index = accounts.size();
        if (index != 0) {
            int highestIndex = accounts.get(index - 1).getIndex();
            while (highestIndex >= index) {
                index++;
            }
        }
        account.setIndex(index);
        accounts.add(account);
        totalBalance += account.getBalance();
    }

    // REQUIRES: given account name must be a (existing) account name, for the given account balance = 0
    // MODIFIES: this
    // EFFECT: removes the account with given name from accounts
    public void removeAccount(String name) {
        int listIndex = 0;
        for (Account account: accounts) {
            if (account.getName().equals(name)) {
                accounts.remove(listIndex);
                break;
            }
            listIndex++;
        }
    }

    // REQUIRE: 'type' must be one of the transaction category types: "Earning", "Spending", "Transfer", "Investing",
    // "Saving", "Lending", or "Borrowing", 'date' must be valid date with "YYYY-MM-DD" format, 'from' and 'to' must be
    // one of existing accounts
    // MODIFIES: this
    // EFFECT: create a new transaction from a generated unique index and given type, date, value, 'from' and 'to'
    // accounts and add the transaction to allTransaction list and update totalBalance (return the created transaction)
    public void addTransaction(Transaction entry) {
        int index = allTransactions.size();
        if (index != 0) {
            int highestIndex = allTransactions.get(index - 1).getIndex();
            while (highestIndex >= index) {
                index++;
            }
        }
        entry.setIndex(index);
        allTransactions.add(entry);
        updateBalance(entry.getType(), entry.getValue());
        entry.getFrom().addEntry(entry, true);
        entry.getTo().addEntry(entry, false);
    }


    // REQUIRES: given index must be a valid index (existing)
    // MODIFIES: this
    // EFFECT: find the transaction with the given index, remove it from allTransactions, update the totalBalance, and
    // return the found transaction
    public void removeTransaction(int index) {
        int listIndex = 0;
        for (Transaction entry: allTransactions) {
            if (entry.getIndex() == index) {
                allTransactions.remove(listIndex);
                updateBalance(entry.getType(), -entry.getValue());
                entry.getFrom().removeEntry(index, false);
                entry.getTo().removeEntry(index, true);
                break;
            }
            listIndex++;
        }
    }


    // REQUIRES: account name must be a valid account name
    // EFFECT: find the account with given name from accounts and returns it
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


    // EFFECT: return the total balance of all accounts
    public int getTotalBalance() {
        return totalBalance;
    }


    // EFFECT: return the list of all existing accounts
    public List<String> getAccountNames() {
        List<String> accountNames = new ArrayList<>();
        for (Account account : accounts) {
            accountNames.add(account.getName());
        }
        return accountNames;
    }


    // EFFECT: return the list of all transactions
    public List<Transaction> getMasterAccount() {
        return allTransactions;
    }


    public List<Account> getAccountList() {
        return accounts;
    }

    private void updateBalance(String type, int value) {
        if (type.equals("Earning")) {
            totalBalance += value;
        } else if (type.equals("Spending")) {
            totalBalance -= value;
        }
    }
}
