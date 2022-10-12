package model;

import java.io.Serializable;
import java.util.Date;

public class Entry implements Serializable {
    private String type;
    private String date;
    private double value;
    private String from;

    public Entry(String type, String date, double value, String from) {
        this.type = type;
        this.date = date;
        this.value = value;
        this.from = from;
    }

    public double getValue() {
        return value;
    }
}
