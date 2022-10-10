package model;

import java.util.Date;

enum Type {
    EARNING,
    SPENDING,
    INVESTING,
    SAVING,
    LENDING,
    BORROWING
}

public class Category {

    private Type type;



    private Date date;
    private String type;
    private int value;
    private Account from;
    private Account to;


}
