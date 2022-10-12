package model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private String type;
    private String date;
    private double value;
    private String from;
    private String to;

    public Transaction(String type, String date, double value, String from, String to) {
        this.type = type;
        this.date = date;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

}
