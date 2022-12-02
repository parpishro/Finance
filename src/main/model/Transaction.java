package model;

import org.json.JSONObject;
import persistence.Savable;

// Represents a single transaction entry containing index, type, date, value, and from/to accounts
public class Transaction implements Savable {
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
        EventLog.getInstance().logEvent(new Event("Index of a transaction entry was edited"));
    }

    public void setType(String type) {
        this.type = type;
        EventLog.getInstance().logEvent(new Event("Type of a transaction entry was edited"));
    }

    public void setDate(String date) {
        this.date = date;
        EventLog.getInstance().logEvent(new Event("Date of a transaction entry was edited"));
    }

    public void setValue(int value) {
        this.value = value;
        EventLog.getInstance().logEvent(new Event("Value of a transaction entry was edited"));
    }

    public void setFrom(String from) {
        this.from = from;
        EventLog.getInstance().logEvent(new Event("From account of a transaction entry was edited"));
    }

    public void setTo(String to) {
        this.to = to;
        EventLog.getInstance().logEvent(new Event("To account of a transaction entry was edited"));
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

    // EFFECT: create a Json object from the transaction components and return it
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
