package model;

import java.io.Serializable;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int index;
    private final String type;
    private final String date;
    private final int value;
    private final Account from;
    private final Account to;

    public Transaction(int index, String type, String date, int value, Account from, Account to) {
        this.index = index;
        this.type = type;
        this.date = date;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    public int getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public int getValue() {
        return value;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

}
