package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Savable;

import java.util.ArrayList;
import java.util.List;

// master object containing all the accounts and their associated transactions
public class Master implements Savable {
    private final String user;
    private int totalBalance;
    private final List<Account> accounts;
    private final List<Transaction> allTransactions;

    // REQUIRES: given user's name must non-empty and consisting of ony letters
    // EFFECT: constructs a master object consisting of a given user's name, zero total balance, empty accounts list,
    //         and empty allTransaction list
    public Master(String user) {
        this.user = user;
        totalBalance = 0;
        accounts = new ArrayList<>();
        allTransactions = new ArrayList<>();
    }

    // REQUIRES: a unique (non-existing) account name
    // MODIFIES: this
    // EFFECT: adds the given account to the accounts after updating its default index (-1) with a unique index, and
    //         updates the total balance
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
    //          "Saving", "Lending", or "Borrowing", 'date' must be valid date with "YYYY-MM-DD" format, value must be
    //          > 0 , 'from' and 'to' must be valid (existing) account names
    // MODIFIES: this
    // EFFECT: given a transaction, update its default index uniquely. update totalBalance, add it to allTransaction
    //         list, and both 'from' and 'to' accounts' entries list.
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
        this.getAccount(entry.getFrom()).addEntry(entry, true);
        this.getAccount(entry.getTo()).addEntry(entry, false);
    }

    // REQUIRE: 'type' must be one of the transaction category types: "Earning", "Spending", "Transfer", "Investing",
    //          "Saving", "Lending", or "Borrowing", 'date' must be valid date with "YYYY-MM-DD" format, value must be
    //          > 0 , 'from' and 'to' must be valid (existing) account names
    // MODIFIES: this
    // EFFECT: given a transaction components, create a transaction and add it to the allTransaction list
    public void addTransaction(int index, String type, String date, int value, String from, String to) {
        Transaction transaction = new Transaction(index, type, date, value, from, to);
        allTransactions.add(transaction);
    }


    // REQUIRES: given index must be a valid index (existing)
    // MODIFIES: this
    // EFFECT: find the transaction with the given index, remove it from allTransactions, update the totalBalance, and
    //         return the found transaction
    public void removeTransaction(int index) {
        int listIndex = 0;
        for (Transaction entry: allTransactions) {
            if (entry.getIndex() == index) {
                allTransactions.remove(listIndex);
                updateBalance(entry.getType(), -entry.getValue());
                this.getAccount(entry.getFrom()).removeEntry(index, false);
                this.getAccount(entry.getTo()).removeEntry(index, true);
                break;
            }
            listIndex++;
        }
    }

    // REQUIRES: type must be a valid type, value must be > 0
    // MODIFIES: this
    // EFFECT: update the total balance if its value is coming in (income) or going out (spending)
    private void updateBalance(String type, int value) {
        if (type.equals("Earning")) {
            totalBalance += value;
        } else if (type.equals("Spending")) {
            totalBalance -= value;
        }
    }

    // EFFECT: return number of accounts in the master
    public int numberOfAccounts() {
        return accounts.size();
    }

    // EFFECT: return number of transactions in the master
    public int numberOfTransactions() {
        return allTransactions.size();
    }

    // getters

    public String getUser() {
        return user;
    }

    public int getTotalBalance() {
        return totalBalance;
    }

    public List<Transaction> getAllTransactions() {
        return allTransactions;
    }

    public List<Account> getAccounts() {
        return accounts;
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

    // EFFECT: return the list of all existing accounts
    public List<String> getAccountNames() {
        List<String> accountNames = new ArrayList<>();
        for (Account account : accounts) {
            accountNames.add(account.getName());
        }
        return accountNames;
    }

    // setters

    public void setTotalBalance(int totalBalance) {
        this.totalBalance = totalBalance;
    }

    // serialization

    // EFFECT: create a Json object from the master components and return it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("user", user);
        json.put("totalBalance", totalBalance);
        json.put("accounts", accountsToJson());
        json.put("allTransactions", allTransactionsToJson());
        return json;
    }

    // EFFECT: create a Json Array from the master's accounts list and return it
    private JSONArray accountsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Account account : accounts) {
            jsonArray.put(account.toJson());
        }
        return jsonArray;
    }

    // EFFECT: create a Json Array from the master's allTransactions list and return it
    private JSONArray allTransactionsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Transaction entry : allTransactions) {
            jsonArray.put(entry.toJson());
        }
        return jsonArray;
    }
}
