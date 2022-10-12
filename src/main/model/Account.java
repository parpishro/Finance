package model;


import java.io.Serializable;
import java.util.ArrayList;

// any compartment that can hold value ($) such as Cash, Bank Accounts, ...
public class Account implements Serializable {
    private final String name;
    private double balance;
    private final boolean isPos;
    private ArrayList<Entry> entries;

    public Account(String name, boolean isPos) {
        this.name = name;
        this.isPos = isPos;
        this.balance = 0;
        this.entries = new ArrayList<>();
    }

    public void addEntry(String type, String date, double value, String from) {
        Entry entry = new Entry(type, date, value, from);
        entries.add(entry);
        balance += value;
    }

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
