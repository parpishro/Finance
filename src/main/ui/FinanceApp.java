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

    // EFFECT: construct a FinanceApp object based on existing file "master.txt". If file does not exist (the first
    // the app is used) then it creates the file "master.txt". In either case after loading, or constructing a master
    // object, it runs the app
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
                    + "\n Enter or edit information (e)"
                    + "\n View master records (m)"
                    + "\n View account records (a)"
                    + "\n Quit safely (q)");
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
    // EFFECT: get user input to select: enter, or remove transaction and add, or remove accounts
    public void editInfo() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter edit option:\n1. Enter a transaction\n3. Remove a transaction"
                + "\n 4. Add an account\n 4. Edit an account");

        int editOption = input.nextInt();
        switch (editOption) {
            case 1:
                enterTransaction();
                break;
            case 2:
                removeTransaction();
                break;
            case 3:
                addAccount();
                break;
            case 4:
                removeAccount();
                break;
            case 5:
                editAccount();
            default:
                System.out.println("invalid code! Please try again!");
        }
    }



    // EFFECT: shows information about master account
    public void viewMaster() {
        List<Transaction> masterAccount = master.getMasterAccount();
        switch (viewOption()) {
            case 1:
                System.out.println(master.getTotalBalance() / 100);
                break;
            case 2:
                printTransactions(masterAccount, 21);
                break;
            case 3:
                printTransactions(masterAccount, masterAccount.size());
        }
    }


    // EFFECT: shows information about user-given account
    public void viewAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Select an account from the following:");
        for (String name : master.getAccountNames()) {
            System.out.println("* " + name);
        }
        Account account = master.getAccount(input.next());
        List<Transaction> listEntries = account.getEntries();
        switch (viewOption()) {
            case 1:
                System.out.println(account.getBalance() / 100);
                break;
            case 2:
                printTransactions(listEntries, 21);
                break;
            case 3:
                printTransactions(listEntries, listEntries.size());
        }
    }

    // MODIFIES: master.txt file on data folder
    // EFFECT: writes the serialized updated master object on master.txt file and informs user if fails to do so
    public void quitSafe() {
        try {
            IO.saveMaster(master);
            System.out.println("master.txt file was updated successfully!");
        } catch (IOException e) {
            System.out.println("master.txt file update was failed!");
        }
    }

    // edit options:

    private void enterTransaction() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter transaction type, date, value, 'from' account, and 'to' account: ");
        String type = input.next();
        String date = input.next();
        int value = (int) input.nextDouble() * 100;
        Account from = master.getAccount(input.next());
        Account to = master.getAccount(input.next());
        master.addTransaction(new Transaction(-1, type, date, value, from, to));
    }


    private void removeTransaction() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the index of the transaction to be deleted: ");
        int index = (int) input.nextDouble();
        master.removeTransaction(index);
    }

    private void addAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the name, isPos (whether it holds own or borrowed value), and initial balance of the "
                + "account to be created: ");
        String accountName = input.next();
        boolean isPos = input.nextBoolean();
        int balance = (int) input.nextDouble() * 100;
        Account account = new Account(-1, accountName, isPos, balance);
        master.addAccount(account);
    }


    private void removeAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the name of account to be deleted: ");
        for (String name : master.getAccountNames()) {
            System.out.println("* " + name);
        }
        master.removeAccount(input.next());
    }

    private void editAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the name of account to be edited: ");
        for (String name : master.getAccountNames()) {
            System.out.println("* " + name);
        }
        Account account = master.getAccount(input.next());
        System.out.println("Enter the operation:\n1. Change Index\n2. Change Name\n3. Change isPos\n4. Change Balance");
        switch (input.nextInt()) {
            case 1:
                account.setIndex(input.nextInt());
                break;
            case 2:
                account.setName(input.next());
                break;
            case 3:
                account.setIsPos(input.nextBoolean());
                break;
            case 4:
                account.setBalance((int) input.nextDouble() * 100);
        }
    }



    // view options:

    private int viewOption() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter view option: "
                + "\n 1. Show balance"
                + "\n 2. View 20 recent transactions"
                + "\n 3. View all transactions");
        return input.nextInt();
    }

    private void printTransactions(List<Transaction> transactionList, int entryNum) {
        for (int i = max(0, (transactionList.size() - entryNum)); i < transactionList.size(); i++) {
            System.out.println(transactionList.get(i).getIndex() + " | "
                    + transactionList.get(i).getType() + " | "
                    + transactionList.get(i).getDate() + " | "
                    + (transactionList.get(i).getValue() / 100) + " | "
                    + transactionList.get(i).getFrom().getName() + " | "
                    + transactionList.get(i).getTo().getName());
        }
    }













}
