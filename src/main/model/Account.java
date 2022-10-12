package model;


import java.io.Serializable;
import java.util.ArrayList;

// any compartment of asset allocations that can hold value (in $) such as cash, bank accounts, investment account, ...
public class Account implements Serializable {
    private final String name;
    private double balance;
    private final boolean isPos;
    private final ArrayList<Entry> entries;

    // EFFECT: Constructs an account with given name and whether it holds positive or negative values
    //         Any account (like cash) that has user's money (or its equivalent) must have positive value (isPos = true)
    //         And any account (like credit card) that has a borrowed money must have negative value (isPos = false)
    public Account(String name, boolean isPos) {
        this.name = name;
        this.isPos = isPos;
        balance = 0;
        entries = new ArrayList<>();
    }

    // REQUIRE: Type must be one of the Category type: "Earning", "Spending", "Investing", "Saving", "Lending",
    //          or "Borrowing"/ date must be valid date with YYYY-MM-DD format/ from must be one of existing accounts.
    // MODIFIES: this
    // EFFECT: Creates an entry from given arguments and adds it to account's entry list and updates account balance.
    public void addEntry(String type, String date, double value, String from) {
        Entry entry = new Entry(type, date, value, from);
        entries.add(entry);
        balance += value;
    }

    // REQUIRE: Given index must be a valid entry index of the account.
    // MODIFIES: this
    // EFFECT: Removes an entry with a given index from the account's entry list and updates the account's balance.
    public void removeEntry(int index) {
        balance -= entries.get(index).getValue();
        entries.remove(index);
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public boolean getIsPos() {
        return isPos;
    }

}
