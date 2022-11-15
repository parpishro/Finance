package ui.consule;

import model.Account;
import model.Master;
import model.Transaction;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.max;

// user interface of the app that gets commands and information from the user and display, edit, load, or save records
public class FinanceApp {
    private static final String PATH = "./data/";
    private Master master;
    private final Scanner input;
    private String memory;
    private boolean continuing;

    // EFFECT: construct a FinanceApp object which asks user whether to run the app after loading an existing file (y),
    //         or to run the app after creating a new master object (n), or in the case of invalid code, informs the
    //         user and provides the option to quit (q).
    public FinanceApp() throws IOException {
        input = new Scanner(System.in);
        continuing = true;
        while (continuing) {
            System.out.println("Are you an existing user? [Yes (y) - No (n) - Quit (q)]");
            String init = input.next();
            switch (init) {
                case "y":
                    if (runLoader()) {
                        runApp();
                    }
                    break;
                case "n":
                    runCreator();
                    runApp();
                    break;
                case "q":
                    continuing = false;
                    break;
                default:
                    System.out.println("Invalid code! Please try again.");
            }
        }
    }

    // MODIFIES: this, jsonReader
    // EFFECT: Loads the json file, with user-given name, from the data folder if exists, otherwise inform the user.
    private boolean runLoader() {
        System.out.println("Please enter your user name:");
        String user = input.next();
        try {
            JsonReader jsonReader = new JsonReader(PATH + user + ".json");
            master = jsonReader.load();
            System.out.println("Master file for " + master.getUser() + " was loaded successfully from " + PATH);
        } catch (IOException e) {
            System.out.println("Given user name does not exist!");
            return false;
        }
        return true;
    }

    // MODIFIES: this
    // EFFECT: creates new master file for new user with their name (input by user))
    private void runCreator() {
        System.out.println("Please enter a unique user name to create a master account:");
        String user = input.next();
        master = new Master(user);
    }

    // MODIFIES: this
    // EFFECT: branch out to different functionalities based on user-requested action: edit, view, save, or quit
    private void runApp() throws FileNotFoundException {
        while (continuing) {
            displayMasterMenu();
            switch (input.next()) {
                case "e":
                    runEditor();
                    break;
                case "v":
                    runViewer();
                    break;
                case "s":
                    runSaver(master.getUser());
                    break;
                case "q":
                    continuing = false;
                    break;
                default:
                    System.out.println("Invalid code, Please try again!");
            }
        }
    }

    // EFFECT: displays the master menu to the user
    private void displayMasterMenu() {
        System.out.println("Enter a command shortcut: "
                + "\n Edit loaded records (e)"
                + "\n View loaded records (v)"
                + "\n Save records on file (s)"
                + "\n Quit application (q)");
    }



    // MODIFIES: this
    // EFFECT: get user input to select: enter, or remove transaction and add, remove or edit accounts
    public void runEditor() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter edit option:\n 1. Enter a transaction\n 2. Remove a transaction"
                + "\n 3. Add an account\n 4. Remove an account \n 5. Edit an account");
        switch (input.nextInt()) {
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
        }
    }

    // EFFECT: displays balance and record for accounts and master (overall)
    public void runViewer() {
        Scanner input = new Scanner(System.in);
        displayAccountMenu();
        memory = input.next();
        displayViewMenu();
        switch (input.nextInt()) {
            case 1:
                viewAccountBalances();
                break;
            case 2:
                viewRecentTransactions();
                break;
            case 3:
                viewAllTransactions();
        }
    }

    // MODIFIES: jsonWriter
    // EFFECT: save the current master onto file (overwrites it if the file already exist)
    private void runSaver(String user) throws FileNotFoundException {
        JsonWriter jsonWriter = new JsonWriter(PATH + user + ".json");
        jsonWriter.save(master);
        System.out.println("Records were successfully saved into " + master.getUser() + " master file at " + PATH);
    }

    // edit options:

    // MODIFIES: this
    // EFFECT: adds a transaction to master (and associated accounts) with components given by user
    private void enterTransaction() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter transaction type, date, value, 'from' account, and 'to' account: ");
        String type = input.next();
        String date = input.next();
        int value = (int) (input.nextDouble() * 100);
        String from = input.next();
        String to = input.next();
        master.addTransaction(new Transaction(-1, type, date, value, from, to));
    }

    // MODIFIES: this
    // EFFECT: removes a transaction from master (and associated accounts) with its index given by user
    private void removeTransaction() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the index of the transaction to be deleted: ");
        int index = (int) input.nextDouble();
        master.removeTransaction(index);
    }

    // MODIFIES: this
    // EFFECT: adds an account to master with components given by user
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

    // MODIFIES: this
    // EFFECT: removes an account to master with components given by user
    private void removeAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the name of account to be deleted: ");
        for (String name : master.getAccountNames()) {
            System.out.println("* " + name);
        }
        master.removeAccount(input.next());
    }

    // MODIFIES: this
    // EFFECT: edits the information of an already existing account in the  master with components given by user
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

    // EFFECT: displays viewing menu
    private void displayViewMenu() {
        System.out.println("Enter view option: "
                + "\n 1. Show balances"
                + "\n 2. View 5 recent transactions"
                + "\n 3. View all transactions");
    }

    // EFFECT: displays list of accounts and master for user to choose
    private void displayAccountMenu() {
        List<String> accountNames;
        System.out.println("Choose an account from the following:");
        accountNames = master.getAccountNames();
        for (String name : accountNames) {
            System.out.println(" * " + name);
        }
        System.out.println(" * Master");
    }

    // EFFECT: displays the account (or master) balance
    private void viewAccountBalances() {
        if (memory.equals("Master")) {
            System.out.println("Total balance is " + (double) master.getTotalBalance() / 100);
        } else {
            System.out.println("Balance of " + memory + " : " + (double) master.getAccount(memory).getBalance() / 100);
        }
    }

    // EFFECT: displays all the transactions of the chosen account (master)
    private void viewAllTransactions() {
        if (memory.equals("Master")) {
            printTransactions(master.getAllTransactions(), master.getAllTransactions().size());
        } else {
            printTransactions(master.getAccount(memory).getEntries(),
                    master.getAccount(memory).getEntries().size());
        }
    }

    // EFFECT: displays the last 5 transactions of the chosen account (master)
    private void viewRecentTransactions() {
        if (memory.equals("Master")) {
            printTransactions(master.getAllTransactions(), 5);
        } else {
            printTransactions(master.getAccount(memory).getEntries(), 5);
        }
    }

    // EFFECT: prints the requested transactions on the screen
    private void printTransactions(List<Transaction> transactionList, int entryNum) {
        for (int i = max(0, (transactionList.size() - entryNum)); i < transactionList.size(); i++) {
            System.out.println(transactionList.get(i).getIndex() + " | "
                    + transactionList.get(i).getType() + " | "
                    + transactionList.get(i).getDate() + " | "
                    + (transactionList.get(i).getValue() / 100) + " | "
                    + transactionList.get(i).getFrom() + " | "
                    + transactionList.get(i).getTo());
        }
    }
}
