package model;

import java.io.Serializable;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private int index;
    private String type;
    private String date;
    private int value;
    private Account from;
    private Account to;

    // REQUIRES: index >= 0 and is unique (not one of existing transaction indices), type is one of: "Earning",
    // "Spending", "Investing", "Saving", "Lending", "Borrowing", date is in YYYY-MM-DD format, value > 0, "from" is one
    // of existing accounts, "to" is one of existing accounts
    // EFFECT: constructs a transaction entry with given index, type, date, value, from (account), and to (account)
    public Transaction(int index, String type, String date, int value, Account from, Account to) {
        this.index = index;
        this.type = type;
        this.date = date;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    // setters

    public void setIndex(int index) {
        this.index = index;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public void setTo(Account to) {
        this.to = to;
    }


    // getters

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
