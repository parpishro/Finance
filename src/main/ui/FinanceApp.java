package ui;

import model.*;
import java.io.*;

import java.util.List;
import java.util.Scanner;

import static java.lang.Math.max;


// user interface of the app to get input from user, which will be used to change the files or to print out requested
// information on console.
public class FinanceApp {

    private Master master;
    private boolean continuing;

    // EFFECT: construct a FinanceApp object that runs until user requests to quit
    public FinanceApp() throws IOException, ClassNotFoundException {
        continuing = true;

        try {
            master = IO.loadMaster();
        } catch (FileNotFoundException e) {
            master = new Master();
        }
        runApp();
    }

    // MODIFIES: this
    // EFFECT: branch out based on user-requested action: edit information, view information, or quit safely
    public void runApp() {
        while (continuing) {
            Scanner command = new Scanner(System.in);
            System.out.println("Enter a command shortcut: "
                    + "\n Edit Information (e)"
                    + "\n View Master (m)"
                    + "\n View Account (a)"
                    + "\n Quit Safely (q)");
            switch (command.next()) {
                case "e":
                    editInfo();
                    break;
                case "m":
                    viewMaster();
                    break;
                case "a":
                    viewAccount();
                    break;
                case "q":
                    continuing = false;
                    quitSafe();
                    break;
            }
        }
    }

    // MODIFIES: this
    // EFFECT: get user input to select: add account, remove account, enter transaction, remove transaction
    private void editInfo() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter edit option: "
                + "\n 1. Add account"
                + "\n 2. Remove account"
                + "\n 3. Enter new transaction"
                + "\n 4. Remove a transaction");

        int editOption = Integer.parseInt(input.next());
        switch (editOption) {
            case 1:
                addAccount();
                break;
            case 2:
                removeAccount();
                break;
            case 3:
                enterTransaction();
                break;
            case 4:
                removeTransaction();
                break;
            default:
                System.out.println("Invalid edit option! Please try again!");
        }
    }


    // EFFECT: get user input to select an account (or master account) and then get user input to select view operation
    // (balance, all transactions, transactions in custom time range, or n (user-given) recent transactions.
    private void viewMaster() {
        List<Transaction> account = master.getMasterAccount();
        int viewOption = viewOption();
        switch (viewOption) {
            case 1:
                System.out.println(master.getTotalBalance() / 100);
                break;
            case 2:
                for (int i = max(0, (account.size() - 21)); i < account.size(); i++) {
                    System.out.println(account.get(i).getIndex() + " : "
                                    + account.get(i).getType() + " : "
                                    + account.get(i).getDate() + " : "
                                    + (account.get(i).getValue() / 100) + " : "
                                    + account.get(i).getFrom().getName() + " : "
                                    + account.get(i).getTo().getName());
                }
                break;
            case 3:
                System.out.println(account);
        }
    }


    private void viewAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Select an account:");
        Account account = master.getAccount(input.next());
        List<Transaction> listEntries = account.getEntries();
        int viewOption = viewOption();
        switch (viewOption) {
            case 1:
                System.out.println(account.getBalance() / 100);
                break;
            case 2:
                for (int i = max(0, (listEntries.size() - 21)); i < listEntries.size(); i++) {
                    System.out.println(listEntries.get(i));
                }
                break;
            case 3:
                System.out.println(account);
        }
    }

    private int viewOption() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter view option: "
                + "\n 1. Show balance"
                + "\n 2. View 20 recent transactions"
                + "\n 3. View all transactions");
        return Integer.parseInt(input.next());
    }



    // MODIFIES: master.txt file on data folder
    // EFFECT: writes the serialized updated master object on old master.txt file and informs user if fails to do so
    private void quitSafe() {
        try {
            IO.saveMaster(master);
            System.out.println("master.txt file was updated successfully!");
        } catch (IOException e) {
            System.out.println("master.txt file update was failed!");
        }
    }


    private void removeTransaction() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the index of the transaction to be deleted: ");
        int index = (int) input.nextDouble();
        Transaction entry = master.removeTransaction(index);
        entry.getFrom().removeEntry(index, false);
        entry.getTo().removeEntry(index, true);
    }

    private void enterTransaction() {
        Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter transaction type, date, value, 'from' account, and 'to' account: ");
        String type = input.next();
        String date = input.next();
        int value = (int) input.nextDouble() * 100;
        Account from = master.getAccount(input.next());
        Account to = master.getAccount(input.next());
        Transaction entry = master.addTransaction(type, date, value, from, to);
        from.addEntry(entry, true);
        to.addEntry(entry, false);
    }

    private void removeAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the name of account to be deleted: ");
        master.removeAccount(input.next());
    }

    private void addAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the name and isPos of account to be created: ");
        String accountName = input.next();
        boolean isPos = input.nextBoolean();
        int balance = (int) input.nextDouble() * 100;
        master.addAccount(accountName, isPos, balance);
    }
}
