package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Savable;

import java.util.ArrayList;
import java.util.List;

// Represents any compartment of monetary allocations holding values (in $) such as cash, bank accounts, investment, ...
// (positive) or credit, loan, ... (negative)
public class Account implements Savable {
    private int index;
    private String name;
    private boolean isPos;
    private int balance;
    private final List<Transaction> entries;

    // REQUIRES: index and name must be unique, when isPos = true, balance >= 0, and when isPos = false, balance <= 0
    // EFFECT: constructs an account with given index, name, and isPos (true represents accounts with user's holdings
    //         and false represents accounts with user's borrowings) with empty list of entries (used to add account).
    public Account(int index, String name, boolean isPos, int balance) {
        this.index = index;
        this.name = name;
        this.isPos = isPos;
        this.balance = balance;
        entries = new ArrayList<>();
    }

    // REQUIRES: index and name must be unique, when isPos = true, balance >= 0, and when isPos = false, balance <= 0
    // EFFECT: constructs an account with given index, name, and isPos (true represents accounts with user's holdings
    //         and false represents accounts with user's borrowings) with a list of entries (used to load master).
    public Account(int index, String name, boolean isPos, int balance, List<Transaction> entries) {
        this.index = index;
        this.name = name;
        this.isPos = isPos;
        this.balance = balance;
        this.entries = entries;
    }

    // REQUIRE: entry must be a valid transaction
    // MODIFIES: this
    // EFFECT: adds a given entry to account entries and updates account balance based on whether it is 'from' or 'to'.
    public void addEntry(Transaction entry, boolean subtract) {
        entries.add(entry);
        setBalance(entry.getValue(), subtract);
        EventLog.getInstance().logEvent(new Event("A transaction entry was added to "
                + name
                + " account and its balance was updated"));
    }

    // REQUIRE: Given index must be a valid (existing) entry index of the account.
    // MODIFIES: this
    // EFFECT: Removes an entry with a given index from the account's entry list and updates the account's balance
    //         whether it was 'from' or 'to' account
    public void removeEntry(int index, boolean subtract) {
        int listIndex = 0;
        for (Transaction entry: entries) {
            if (entry.getIndex() == index) {
                entries.remove(listIndex);
                setBalance(entry.getValue(), subtract);
                EventLog.getInstance().logEvent(
                        new Event("A transaction entry was removed from "
                                + name
                                + " account and its balance was updated"));
                break;
            }
            listIndex++;
        }
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECT: updates the account balance after adding or removing entries
    private void setBalance(int amount, boolean subtract) {
        if (subtract) {
            balance -= amount;
        } else {
            balance += amount;
        }
    }

    // MODIFIES: this
    // EFFECT: set balance manually (mainly to edit a mistake in initialization of a new account)
    public void setBalance(int amount) {
        balance = amount;
        EventLog.getInstance().logEvent(new Event("Balance of " + name + " account was updated manually"));
    }

    // REQUIRES: unique index (non-repeating)
    // MODIFIES: this
    // EFFECT: edits the index of an account
    public void setIndex(int index) {
        this.index = index;
    }

    // REQUIRES: unique name (non-repeating)
    // MODIFIES: this
    // EFFECT: edits the name of an account
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECT: edits the isPos status of an account
    public void setIsPos(boolean isPos) {
        this.isPos = isPos;
    }

    // getters

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public boolean getIsPos() {
        return isPos;
    }

    public int getBalance() {
        return balance;
    }

    public List<Transaction> getEntries() {
        return entries;
    }

    // serialization

    // EFFECT: create a Json object from the account components and return it
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("index", index);
        json.put("name", name);
        json.put("isPos", isPos);
        json.put("balance", balance);
        json.put("entries", entriesToJson());
        return json;
    }

    // EFFECT: create a Json Array from the account's entries list and return it
    private JSONArray entriesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Transaction entry : entries) {
            jsonArray.put(entry.toJson());
        }
        return jsonArray;
    }
}
