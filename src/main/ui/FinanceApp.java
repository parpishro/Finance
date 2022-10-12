package ui;

import model.*;

import java.io.*;

import java.util.Scanner;


public class FinanceApp {

    private Master master;
    private Bookkeeping bookkeeping;



    public FinanceApp() throws IOException, ClassNotFoundException {

        boolean continuing = true;
        String command;

        initialize();

        while (continuing) {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter a command ('q': Quit, 'n': New Transaction, 'b': Balance)");
            command = input.next();

            switch (command) {
                case "q":
                    continuing = false;
                    quitSafe();
                    break;
                case "n":
                    newEntry();
                    break;
                case "b":
                    double balance = getBalance();
                    System.out.println(balance);
                    break;
                default:
                    System.out.println("Try again!");
            }

        }
    }


    private void initialize() throws IOException, ClassNotFoundException {
        try {
            master = IO.loadMaster();
            bookkeeping = IO.loadBookkeeping();
        } catch (FileNotFoundException e) {
            master = new Master();
            master.addAccount("Cash", true);
            bookkeeping = new Bookkeeping();
        }

    }

    private void quitSafe() {
        try {
            IO.saveMaster(master);
            IO.saveBookkeeping(bookkeeping);
        } catch (IOException e) {
            System.out.println("something went wrong!");
        }

    }

    private void newEntry() {
        Scanner inputType = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter transaction type: ");
        String type = inputType.nextLine();

        Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter transaction date, value, 'from' account, and 'to' account: ");
        String date = input.next();
        double value = input.nextDouble();
        String from = input.next();

        Account acc = master.getAccount(from);
        acc.addEntry(type, date, value, from);
    }

    private double getBalance() {
        return master.getBalance();
    }
}
