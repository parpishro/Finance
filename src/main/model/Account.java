package model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// any compartment of asset allocations that can hold value (in $) such as cash, bank accounts, investment account, ...
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final int index;
    private int balance;
    private final boolean isPos;
    private final ArrayList<Transaction> entries;

    // REQUIRES: index and name must unique
    // EFFECT: Constructs an account with given index (unique), name, and isPos (whether it holds positive or negative
    // values) Any account (like cash) that has user's holdings (such as money or investment) must have positive value
    // (isPos = true) and any account that represents users borrowings (like credit cards or loans) must have negative
    // value (isPos = false)
    public Account(int index, String name, boolean isPos) {
        this.index = index;
        this.name = name;
        this.isPos = isPos;
        balance = 0;
        entries = new ArrayList<>();
    }

    // REQUIRE: Type must be one of the Category type: "Earning", "Spending", "Investing", "Saving", "Lending",
    //          or "Borrowing"/ date must be valid date with YYYY-MM-DD format/ from must be one of existing accounts.
    // MODIFIES: this
    // EFFECT: Creates an entry from given arguments and adds it to account's entry list and updates account balance.
    public void addEntry(Master master, String type, String date, int value, Account from, Account to) {
        Transaction entry = master.addTransaction(type, date, value, from, to);
        if (entry != null) {
            entries.add(entry);
            balance -= value;
            to.entries.add(entry);
            to.setBalance(value);
        }
    }

    private void setBalance(int amount) {
        balance += amount;
    }

    // REQUIRE: Given index must be a valid entry index of the account.
    // MODIFIES: this
    // EFFECT: Removes an entry with a given index from the account's entry list and updates the account's balance.
    public void removeEntry(int index) {
        int counter = 0;
        for (Transaction entry: entries) {
            if (entry.getIndex() == index) {
                entries.remove(counter);
                balance -= entry.getValue();
            }
            counter++;
        }
    }

    public int getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public boolean getIsPos() {
        return isPos;
    }

    public int getIndex() {
        return index;
    }

    public List<Transaction> getEntries() {
        return entries;
    }

}
