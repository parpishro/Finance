package ui;

import model.Account;
import model.Category;

import java.text.ParseException;

public class StartApp {
    private Account cash;
    private Category earning;
    private Category spending;
    private Category investing;
    private Category saving;
    private Category lending;
    private Category borrowing;

    public StartApp() {
        initApp();
    }

    private void initApp() {
        cash = new Account("Cash", true);
        earning = new Category();
    }


}
