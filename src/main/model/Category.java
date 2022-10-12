package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {

    private String type;
    private boolean isPos;
    private double total;
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Category(String type, boolean isPos) {
        this.type = type;
        this.isPos = isPos;
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
        total += t.getValue();
    }

    public void removeTransaction(int index) {
        total -= transactions.get(index).getValue();
        transactions.remove(index);
    }

    public String getType() {
        return type;
    }

    public double getTotal() {
        return total;
    }

    public boolean getIsPos() {
        return isPos;
    }
}
