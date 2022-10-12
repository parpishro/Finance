package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Master implements Serializable {
    private ArrayList<Account> accounts;

    public Master() {
        accounts = new ArrayList<>();
    }

    public void addAccount(String n, boolean p) {
        accounts.add(new Account(n, p));
    }

    public Account getAccount(String name) {
        int index = 0;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getName().equals(name)) {
                index = i;
            }
        }
        return accounts.get(index);
    }

    public double getBalance() {
        double balance = 0;
        for (int i = 0; i < accounts.size(); i++) {
            balance += accounts.get(i).getBalance();
        }
        return balance;
    }
}
