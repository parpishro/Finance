package model;

import java.util.ArrayList;

// any compartment that can hold value ($) such as Cash, Bank Accounts, ...
public class Account {
    private String name;
    private int balance;
    private boolean isPos;
    private ArrayList<Entry> data;

    public Account(String name, boolean isPos) {
        this.name = name;
        this.isPos = isPos;
        this.balance = 0;
        this.data = new ArrayList<Entry>();
    }

}
