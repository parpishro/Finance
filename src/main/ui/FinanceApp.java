package ui;

import model.Account;
import model.Category;
import model.Entry;
import model.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static jdk.internal.misc.OSEnvironment.initialize;


public class FinanceApp {
    private Category category;
    private Account account;
    private Transaction transaction;
    private Scanner input;

    public FinanceApp() throws ParseException {
        runApp();
    }

    private void runApp() throws ParseException {

        boolean continuing = true;
        String command = null;

        initialize();

        while (continuing) {
            System.out.println("Enter a command ('q': Quit, 'n': New Transaction, 'b': Balance)");
            command = input.next();
            switch (command) {
                case "q":
                    continuing = false;
                    break;
                case "n":
                    newEntry();
                    break;
                case "b":
                    getBalance();
                    break;;
                default:
                    System.out.println("Try again!");

            }

        }

        Scanner inputType = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter transaction type: ");
        String type = inputType.nextLine();

        Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter transaction date, value, 'from' account, and 'to' account: ");
        Date date = new SimpleDateFormat("yyyy-mm-dd").parse(input.nextLine());
        double value = input.nextDouble();
        String from = input.nextLine();
        String to = input.next();


        Entry entry = new Entry(type, date, value, from, to);
    }



}
