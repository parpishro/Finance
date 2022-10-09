package model;

import java.util.Date;

// a financial transaction that moves value ($) in, out, or between owned accounts
public class Transaction {
    private String type;
    private Date date;
    private double value;
    private Account from;
    private Account to;

    public Transaction(String type, Date date, double value, Account from, Account to) {
        this.type = type;
        this.date = date;
        this.value = value;
        this.from = from;
        this.to = to;
    }





}
