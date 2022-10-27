package model;

import org.json.JSONObject;
import persistence.Savable;

import java.io.Serializable;

public class Transaction implements Savable {
    private static final long serialVersionUID = 1L;
    private int index;
    private String type;
    private String date;
    private int value;
    private String from;
    private String to;

    // REQUIRES: index >= 0 and is unique (not one of existing transaction indices), type is one of: "Earning",
    // "Spending", "Investing", "Saving", "Lending", "Borrowing", date is in YYYY-MM-DD format, value > 0, "from" is one
    // of existing accounts, "to" is one of existing accounts
    // EFFECT: constructs a transaction entry with given index, type, date, value, from (account), and to (account)
    public Transaction(int index, String type, String date, int value, String from, String to) {
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

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
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

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("index", index);
        json.put("type", type);
        json.put("date", date);
        json.put("value", value);
        json.put("from", from);
        json.put("to", to);
        return json;
    }
}
