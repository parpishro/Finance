package model;

import java.util.Date;

public class Entry {
    private String type;
    private Date date;
    private double value;
    private String from;
    private String to;

    public Entry(String type, Date date, double value, String from, String to) {
        this.type = type;
        this.date = date;
        this.value = value;
        this.from = from;
        this.to = to;
    }
}
