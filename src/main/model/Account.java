package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Savable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// any compartment of asset allocations that can hold value (in $) such as cash, bank accounts, investment account, ...
public class Account implements Savable {
    private static final long serialVersionUID = 1L;

    private int index;
    private String name;
    private boolean isPos;
    private int balance;
    private final List<Transaction> entries;

    // REQUIRES: index and name must be unique, when isPos = true, balance >= 0, and when isPos = false, balance <= 0
    // EFFECT: constructs an account with given index, name, and isPos (true represents accounts with user's holdings
    // and false represents accounts with user's borrowings
    public Account(int index, String name, boolean isPos, int balance) {
        this.index = index;
        this.name = name;
        this.isPos = isPos;
        this.balance = balance;
        entries = new ArrayList<>();
    }


    // REQUIRES: index and name must be unique, when isPos = true, balance >= 0, and when isPos = false, balance <= 0
    // EFFECT: constructs an account with given index, name, and isPos (true represents accounts with user's holdings
    // and false represents accounts with user's borrowings
    public Account(int index, String name, boolean isPos, int balance, List<Transaction> entries) {
        this.index = index;
        this.name = name;
        this.isPos = isPos;
        this.balance = balance;
        this.entries = entries;
    }

    // REQUIRE: entry must be a valid transaction
    // MODIFIES: this
    // EFFECT: add a given entry to account entries and updates account balance.
    public void addEntry(Transaction entry, boolean subtract) {
        entries.add(entry);
        setBalance(entry.getValue(), subtract);
    }

    // REQUIRE: Given index must be a valid entry index of the account.
    // MODIFIES: this
    // EFFECT: Removes an entry with a given index from the account's entry list and updates the account's balance.
    public boolean removeEntry(int index, boolean subtract) {
        int listIndex = 0;
        for (Transaction entry: entries) {
            if (entry.getIndex() == index) {
                entries.remove(listIndex);
                setBalance(entry.getValue(), subtract);
                return true;
            }
            listIndex++;
        }
        return false;
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
    // EFFECT: set balance manually
    public void setBalance(int amount) {
        balance = amount;
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


    // EFFECT: get the index of an account
    public int getIndex() {
        return index;
    }


    // EFFECT: get the name of an account
    public String getName() {
        return name;
    }


    // EFFECT: get the isPos status of an account
    public boolean getIsPos() {
        return isPos;
    }


    // EFFECT: get the current balance of an account
    public int getBalance() {
        return balance;
    }


    // EFFECT: get the entry list of an account
    public List<Transaction> getEntries() {
        return entries;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("index", index);
        json.put("name", name);
        json.put("isPos", isPos);
        json.put("balance", balance);
        json.put("entries", entriesToJson());
        return json;
    }

    private JSONArray entriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction entry : entries) {
            jsonArray.put(entry.toJson());
        }

        return jsonArray;
    }
}
