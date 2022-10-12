package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Bookkeeping implements Serializable {
    private ArrayList<Category> categories;

    public Bookkeeping() {
        categories = new ArrayList<>();
        categories.add(new Category("Earning", true));
        categories.add(new Category("Spending", false));
        categories.add(new Category("Investing", true));
        categories.add(new Category("Saving", true));
        categories.add(new Category("Lending", true));
        categories.add(new Category("Borrowing", false));
    }
}


