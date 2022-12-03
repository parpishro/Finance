package ui.gui;

import model.Account;
import model.EventLog;
import model.Master;
import model.Transaction;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.List;



// Represents a graphical user interface for finance app
public class FinAppGUI extends JFrame {

    private static final int MAIN_WIDTH = 800;
    private static final int MAIN_HEIGHT = 600;
    private static final int ACCOUNT_PANE_WIDTH = MAIN_WIDTH / 2;
    private static final int ACCOUNT_PANE_HEIGHT = MAIN_HEIGHT / 2;
    private static final int BALANCE_PANE_WIDTH = MAIN_WIDTH - ACCOUNT_PANE_WIDTH;
    private static final int BALANCE_PANE_HEIGHT = ACCOUNT_PANE_HEIGHT;
    private static final int TRANSACTION_PANE_WIDTH = MAIN_WIDTH;
    private static final int TRANSACTION_PANE_HEIGHT = MAIN_HEIGHT - ACCOUNT_PANE_HEIGHT;

    private static final int ACC_BALANCE_WIDTH = MAIN_WIDTH;
    private static final int ACC_BALANCE_HEIGHT = MAIN_HEIGHT / 4;
    private static final int ACC_TRANSACTION_WIDTH = MAIN_WIDTH;
    private static final int ACC_TRANSACTION_HEIGHT = MAIN_HEIGHT - ACC_BALANCE_HEIGHT;

    private static final String MAIN_TITLE = "Finance Manager";

    private static final String PATH = "./data/";


    private JDesktopPane desktop;
    private JPanel accPane;
    private JPanel blPane;
    private JPanel trPane;

    private final Master master;
    private Account account;

    // EFFECT: constructs a finance app gui with a given master and sets up the frame and its panes
    public FinAppGUI(Master master) {

        this.master = master;

        setupMain();
        setupPanes();
        setVisible(true);
    }






    // Constructor Helpers:

    // 1. Main Desktop Frame:

    // MODIFIES: this
    // EFFECT: sets up the main desktop panel with a title and dimension for constructor
    private void setupMain() {

        desktop = new JDesktopPane();
        setContentPane(desktop);
        desktop.addMouseListener(new DesktopFocusAction());

        setTitle(MAIN_TITLE);
        setSize(MAIN_WIDTH, MAIN_HEIGHT);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        centreOnScreen();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                JsonWriter jsonWriter;
                try {
                    jsonWriter = new JsonWriter(PATH + master.getUser() + ".json");
                    jsonWriter.save(master);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                (new LogPrinter()).printLog(EventLog.getInstance());
                System.exit(0);
            }
        });
    }





    // MODIFIES: this
    // EFFECT: Helper to centre main application window on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }


    // MODIFIES: this
    // EFFECT: sets up the menu bar, accounts pain, balance pane, and transactions pane for constructor
    private void setupPanes() {

        menu();
        accountsPane();
        balancePane(true);
        transactionsPane(true);
    }


    // 2. Menu Bar

    // MODIFIES: this
    // EFFECT: Adds the menu bar for constructor's menu() helper with three tab: Account, Transaction, and Save
    private void menu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu accountMenu = new JMenu("Account");
        addMenuItem(accountMenu, new AddAccountAction(), null);
        addMenuItem(accountMenu, new RemoveAccountAction(), null);
        addMenuItem(accountMenu, new EditAccountAction(), null);
        menuBar.add(accountMenu);

        JMenu transactionMenu = new JMenu("Transaction");
        addMenuItem(transactionMenu, new AddEntryAction(), KeyStroke.getKeyStroke("control T"));
        addMenuItem(transactionMenu, new RemoveEntryAction(),null);
        addMenuItem(transactionMenu, new EditEntryAction(),null);
        menuBar.add(transactionMenu);

        JMenu saveMenu = new JMenu("Save");
        addMenuItem(saveMenu, new SaveAction(), KeyStroke.getKeyStroke("control S"));
        menuBar.add(saveMenu);

        setJMenuBar(menuBar);
    }


    // REQUIRES: non-null menu
    // MODIFIES: theMenu
    // EFFECT: Adds an item with given handler to the given menu
    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {

        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }


    // 3. Accounts Pane

    // MODIFIES: this
    // EFFECT: sets up the account pane for constructor by listing the existing accounts as press-able buttons
    private void accountsPane() {

        accPane = new JPanel();
        accPane.setLayout(new BorderLayout());
        setBorder(accPane, "Accounts");
        accPane.setSize(ACCOUNT_PANE_WIDTH, ACCOUNT_PANE_HEIGHT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(master.numberOfAccounts(),1));

        for (Account account: master.getAccounts()) {
            JButton btn = new JButton(account.getName());
            btn.setSize(ACCOUNT_PANE_WIDTH, ACCOUNT_PANE_HEIGHT / master.numberOfAccounts());
            btn.addActionListener(new PressAccountAction(account));
            buttonPanel.add(btn);
        }

        accPane.add(buttonPanel);
        desktop.add(accPane);
    }


    // 4. Balance Pane

    // MODIFIES: this
    // EFFECT: sets the balance pane for constructor displaying total (master view) and account (account view) balance
    private void balancePane(boolean isMaster) {

        blPane = new JPanel();
        blPane.setLayout(new GridLayout(1, 1));

        JLabel balanceText;

        if (isMaster) {
            setBorder(blPane, "Total Balance");
            blPane.setSize(BALANCE_PANE_WIDTH, BALANCE_PANE_HEIGHT);
            blPane.setLocation(MAIN_WIDTH - BALANCE_PANE_WIDTH, 0);
            balanceText = new JLabel((master.getTotalBalance() / 100.0) + " $");
        } else {
            setBorder(blPane, account.getName() + " Balance");
            blPane.setSize(ACC_BALANCE_WIDTH, ACC_BALANCE_HEIGHT);
            balanceText = new JLabel((account.getBalance() / 100.0) + " $");
            addBackButton();
        }

        balanceText.setFont(balanceText.getFont().deriveFont(50f));
        balanceText.setHorizontalAlignment(0);

        blPane.add(balanceText, BorderLayout.CENTER);
        desktop.add(blPane);
    }


    // MODIFIES: this
    // EFFECT: adds a back button in account view to the top left balance pane
    private void addBackButton() {
        JButton backBtn;
        backBtn = new JButton("Back");
        backBtn.setSize(10, 10);
        backBtn.setLocation(5, 5);
        backBtn.addActionListener(new BackButtonAction());
        blPane.add(backBtn);
    }


    // 5. Transaction Pane

    // MODIFIES: this
    // EFFECT: sets the transaction pane for constructor displaying list of recent master transaction (master view) and
    //         account transactions (account view)
    private void transactionsPane(boolean isMaster) {

        trPane = new JPanel();
        trPane.setLayout(new GridLayout(1, 1));
        setBorder(trPane, "Transactions");

        JTable trTable;


        if (isMaster) {
            trPane.setSize(TRANSACTION_PANE_WIDTH, TRANSACTION_PANE_HEIGHT);
            trPane.setLocation(0, ACCOUNT_PANE_HEIGHT);
            trTable = fillTrTable(master.getAllTransactions());
        } else {
            trPane.setSize(ACC_TRANSACTION_WIDTH, ACC_TRANSACTION_HEIGHT);
            trPane.setLocation(0, ACC_BALANCE_HEIGHT);
            trTable = fillTrTable(account.getEntries());
        }


        JScrollPane scrollPane = new JScrollPane(trTable);
        trTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        trTable.setShowHorizontalLines(true);

        trPane.add(scrollPane, BorderLayout.CENTER);
        desktop.add(trPane);
    }


    // REQUIRES: a non-empty list of transactions
    // MODIFIES : trTable
    // EFFECT: given a list of transactions creates and fills a java table
    private JTable fillTrTable(List<Transaction> entries) {

        String [][] data = new String[entries.size()][6];
        List<Transaction> transactions = entries.subList(0, entries.size());

        for (int i = 0; i < entries.size(); i++) {
            data[i][0] = Integer.toString(transactions.get(i).getIndex());
            data[i][1] = transactions.get(i).getType();
            data[i][2] = transactions.get(i).getDate();
            data[i][3] = Double.toString(transactions.get(i).getValue() / 100.0);
            data[i][4] = transactions.get(i).getFrom();
            data[i][5] = transactions.get(i).getTo();
        }

        String[] columnNames = { "Index", "Type", "Date", "Value", "From", "To"};
        return new JTable(data, columnNames);
    }


    // 6. Other Helpers:


    // REQUIRES: non-null panel
    // MODIFIES: panel
    // EFFECT: sets up a border for given panel with a given title
    private void setBorder(JPanel panel, String title) {
        TitledBorder border = new TitledBorder(title);
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        border.setTitleColor(Color.BLUE);
        panel.setBorder(border);
    }


    // Action Internal Classes:


    // 2. Adding a New Account

    // Represents action to be taken when user wants to add an account to the master.
    private class AddAccountAction extends AbstractAction {


        // EFFECT: constructs add account action class
        AddAccountAction() {
            super("Add Account");
        }


        // EFFECT: when add account is selected, start a pop-up menu with fields for new account detail, if Ok was
        //         selected, removes account, and refresh panes
        @Override
        public void actionPerformed(ActionEvent evt) {

            JPanel addAccount = new JPanel();
            JTextField name = new JTextField(5);
            JTextField pos = new JTextField(5);
            JTextField bal = new JTextField(5);

            addAccount.add(new JLabel("Name: "));
            addAccount.add(name);
            addAccount.add(Box.createHorizontalStrut(15));
            addAccount.add(new JLabel("Is Positive: "));
            addAccount.add(pos);
            addAccount.add(Box.createHorizontalStrut(15));
            addAccount.add(new JLabel("Balance: "));
            addAccount.add(bal);

            int result = JOptionPane.showConfirmDialog(null, addAccount,
                    "Please Enter Account Information", JOptionPane.OK_CANCEL_OPTION);

            String accountName = name.getText();
            boolean isPos = Boolean.parseBoolean(pos.getText());
            int balance = (int) (Double.parseDouble(bal.getText()) * 100);

            if (result == JOptionPane.OK_OPTION) {
                Account account = new Account(-1, accountName, isPos, balance);
                master.addAccount(account);
            }

            refreshPanes(true);
        }

    }


    // 3. Removing an Account


    // Represents action to be taken when user wants to remove an account from master.
    private class RemoveAccountAction extends AbstractAction {


         // EFFECT: constructs remove account action class
        RemoveAccountAction() {
            super("Remove Account");
        }


         // EFFECT: when remove account is selected, start a pop-up menu with field for the name of account to be
         //         removed, if Ok was selected, removes account, and refresh panes
        @Override
        public void actionPerformed(ActionEvent evt) {

            JPanel removeAccount = new JPanel();
            JTextField name = new JTextField(5);

            removeAccount.add(new JLabel("Name: "));
            removeAccount.add(name);

            int result = JOptionPane.showConfirmDialog(null, removeAccount,
                    "Please Enter Account Name", JOptionPane.OK_CANCEL_OPTION);

            String accountName = name.getText();

            if (result == JOptionPane.OK_OPTION) {
                master.removeAccount(accountName);
                refreshPanes(true);
            }


        }
    }


    // Represents action to be taken when user wants to edit account an information in the master.
    private class EditAccountAction extends AbstractAction {


        // EFFECT: constructs edit account action class
        EditAccountAction() {
            super("Edit Account");
        }


        // EFFECT: when edit account is selected, start a pop-up menu with fields to edit account detail, if Ok was
        //         selected, removes account, and refresh panes
        @Override
        public void actionPerformed(ActionEvent evt) {

            JPanel editAccount = new JPanel();
            JTextField name = new JTextField(5);
            editAccount.add(new JLabel("Name: "));
            editAccount.add(name);

            int result = JOptionPane.showConfirmDialog(null, editAccount,
                    "Please Enter Account Name", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                Account account = master.getAccount(name.getText());
                JTextField index = new JTextField(Integer.toString(account.getIndex()), 5);
                JTextField newName = new JTextField(account.getName(), 5);
                JTextField pos = new JTextField(Boolean.toString(account.getIsPos()), 5);
                JTextField bal = new JTextField(Integer.toString(account.getBalance() / 100),5);

                editAccount = setDefaultInfo(index, newName, pos, bal);

                result = JOptionPane.showConfirmDialog(null, editAccount,
                        "Please Edit Account Information", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    account.setIndex(Integer.parseInt(index.getText()));
                    account.setName(newName.getText());
                    account.setIsPos(Boolean.parseBoolean(pos.getText()));
                    account.setBalance(Integer.parseInt(bal.getText()) * 100);
                }
            }

            refreshPanes(true);
        }


        // MODIFIES: editAccount
        // EFFECT: given fields add them to the editing fields as defaults
        private JPanel setDefaultInfo(JTextField index, JTextField newName, JTextField pos, JTextField bal) {

            JPanel editAccount = new JPanel();

            editAccount.add(new JLabel("Index: "));
            editAccount.add(index);
            editAccount.add(Box.createHorizontalStrut(15));

            editAccount.add(new JLabel("Name: "));
            editAccount.add(newName);
            editAccount.add(Box.createHorizontalStrut(15));

            editAccount.add(new JLabel("Is Positive: "));
            editAccount.add(pos);
            editAccount.add(Box.createHorizontalStrut(15));

            editAccount.add(new JLabel("Balance: "));
            editAccount.add(bal);
            return editAccount;
        }

    }


    // Represents action to be taken when user wants to add a new transaction.
    private class AddEntryAction extends AbstractAction {


        // EFFECT: constructs add transaction action class
        AddEntryAction() {
            super("Add Transaction Entry");
        }


        // EFFECT: when add transaction is selected, start a pop-up menu with fields to add transaction info, if Ok was
        //         selected, add the transaction, and refresh panes
        @Override
        public void actionPerformed(ActionEvent evt) {


            JTextField type = new JTextField(5);
            JTextField date = new JTextField(5);
            JTextField value = new JTextField(5);
            JTextField from = new JTextField(5);
            JTextField to = new JTextField(5);

            JPanel addEntry = setEntryFields(type, date, value, from, to);
            int result = JOptionPane.showConfirmDialog(null, addEntry,
                    "Please Enter Transaction Detail", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                master.addTransaction(new Transaction(-1, type.getText(), date.getText(),
                        (int) (Double.parseDouble(value.getText()) * 100), from.getText(), to.getText()));
            }

            refreshPanes(true);
        }

        private JPanel setEntryFields(JTextField type, JTextField date, JTextField value,
                                      JTextField from, JTextField to) {

            JPanel addEntry = new JPanel();

            addEntry.add(new JLabel("Type: "));
            addEntry.add(type);
            addEntry.add(Box.createHorizontalStrut(15));

            addEntry.add(new JLabel("Date: "));
            addEntry.add(date);
            addEntry.add(Box.createHorizontalStrut(15));

            addEntry.add(new JLabel("Value: "));
            addEntry.add(value);
            addEntry.add(Box.createHorizontalStrut(15));

            addEntry.add(new JLabel("From: "));
            addEntry.add(from);
            addEntry.add(Box.createHorizontalStrut(15));

            addEntry.add(new JLabel("To: "));
            addEntry.add(to);

            return addEntry;
        }
    }

    // Represents action to be taken when user wants to remove a transaction.
    private class RemoveEntryAction extends AbstractAction {


        // EFFECT: constructs remove transaction action class
        RemoveEntryAction() {
            super("Remove Transaction Entry");
        }


        // EFFECT: when remove transaction is selected, start a pop-up menu with the index of the transaction to be
        //         deleted, if Ok was selected, remove the transaction, and refresh panes
        @Override
        public void actionPerformed(ActionEvent evt) {

            JPanel removeEntry = new JPanel();
            JTextField index = new JTextField(5);
            removeEntry.add(new JLabel("Index: "));
            removeEntry.add(index);

            int result = JOptionPane.showConfirmDialog(null, removeEntry,
                    "Please Enter the Index of Transaction", JOptionPane.OK_CANCEL_OPTION);
            int entryIndex = Integer.parseInt(index.getText());

            if (result == JOptionPane.OK_OPTION) {
                master.removeTransaction(entryIndex);
            }

            refreshPanes(true);
        }

    }

    // Represents action to be taken when user wants to edit a transaction.
    private class EditEntryAction extends AbstractAction {


        // EFFECT: constructs edit transaction action class
        EditEntryAction() {
            super("Edit Transaction Entry");
        }


        // EFFECT: when edit transaction is selected, start a pop-up menu with fields to edit transaction info, if Ok
        //         was selected, edit the transaction, and refresh panes
        @Override
        public void actionPerformed(ActionEvent evt) {

            JPanel editEntry = new JPanel();
            JTextField index = new JTextField(5);
            editEntry.add(new JLabel("Index: "));
            editEntry.add(index);

            int result = JOptionPane.showConfirmDialog(null, editEntry,
                    "Please Enter Transaction Index", JOptionPane.OK_CANCEL_OPTION);
            int entryIndex = Integer.parseInt(index.getText());

            if (result == JOptionPane.OK_OPTION) {
                Transaction entry = master.getAllTransactions().get(entryIndex);
                JTextField changeIndex = new JTextField(Integer.toString(entry.getIndex()), 5);
                JTextField type = new JTextField(entry.getType(), 5);
                JTextField date = new JTextField(entry.getDate(), 5);
                JTextField value = new JTextField(Double.toString(entry.getValue() / 100.0),5);
                JTextField from = new JTextField(entry.getFrom(), 5);
                JTextField to = new JTextField(entry.getTo(), 5);

                editEntry = setDefaultFields(changeIndex, type, date, value, from, to);
                result = JOptionPane.showConfirmDialog(null, editEntry,
                        "Please Edit Transaction Details", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    setFields(entry, changeIndex, type, date, value, from, to);
                }
            }

            refreshPanes(true);
        }


        // MODIFIES: editEntry
        // EFFECT: set the default fields based on current elements of the transaction
        private JPanel setDefaultFields(JTextField changeIndex, JTextField type, JTextField date,
                                        JTextField value, JTextField from, JTextField to) {

            JPanel editEntry;

            editEntry = new JPanel();
            editEntry.add(new JLabel("Index: "));
            editEntry.add(changeIndex);
            editEntry.add(Box.createHorizontalStrut(15));

            editEntry.add(new JLabel("Type: "));
            editEntry.add(type);
            editEntry.add(Box.createHorizontalStrut(15));

            editEntry.add(new JLabel("Date: "));
            editEntry.add(date);
            editEntry.add(Box.createHorizontalStrut(15));

            editEntry.add(new JLabel("Value: "));
            editEntry.add(value);
            editEntry.add(Box.createHorizontalStrut(15));

            editEntry.add(new JLabel("From: "));
            editEntry.add(from);
            editEntry.add(Box.createHorizontalStrut(15));

            editEntry.add(new JLabel("To: "));
            editEntry.add(to);

            return editEntry;
        }


        // MODIFIES: this
        // EFFECT: remove the entry from master and accounts and create a new entry in master and accounts based on
        //         user given input
        private void setFields(Transaction entry, JTextField changeIndex, JTextField type,
                               JTextField date, JTextField value, JTextField from, JTextField to) {

            master.removeTransaction(entry.getIndex());
            master.addTransaction(Integer.parseInt(changeIndex.getText()), type.getText(), date.getText(),
                    (int) (Double.parseDouble(value.getText()) * 100), from.getText(), to.getText());
        }
    }



    // Represents action to be taken when user selects save button
    private class SaveAction extends AbstractAction {


        // EFFECT: constructs a sae action class
        SaveAction() {
            super("Save Data");
        }


        // EFFECT: when save is selected, start a pop-up menu to confirm, if Ok was selected, save the information
        @Override
        public void actionPerformed(ActionEvent evt) {

            JPanel saveData = new JPanel();
            saveData.add(new JLabel("Do you want to save?"));

            JsonWriter jsonWriter;
            int result = JOptionPane.showConfirmDialog(null, saveData,
                    "Save Data", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    jsonWriter = new JsonWriter(PATH + master.getUser() + ".json");
                    jsonWriter.save(master);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }



    // Represents the action to be taken when the user selects an account
    private class PressAccountAction extends AbstractAction implements ActionListener {


        // EFFECT: constructs a sae action class
        PressAccountAction(Account account) {

            super(account.getName());
        }


        // EFFECT: when save is selected, start a pop-up menu to confirm, if Ok was selected, save the information
        @Override
        public void actionPerformed(ActionEvent evt) {

            account = master.getAccount(evt.getActionCommand());
            accPane.setVisible(false);
            blPane.setVisible(false);
            trPane.setVisible(false);
            refreshPanes(false);
        }
    }


    //
    private class BackButtonAction extends AbstractAction implements ActionListener {

        // EFFECT: constructs a sae action class
        BackButtonAction() {

            super(master.getUser());
        }


        // EFFECT: when back button is selected, revert to the master view and refresh the pane
        @Override
        public void actionPerformed(ActionEvent evt) {

            blPane.setVisible(false);
            trPane.setVisible(false);
            refreshPanes(true);
        }
    }


    // Represents action to be taken when user clicks desktop to switch focus. (Needed for key handling.)
    private class DesktopFocusAction extends MouseAdapter {

        // MODIFIES: this
        // EFFECT: when user clicks on main window (desktop), focus shifts there.
        @Override
        public void mouseClicked(MouseEvent e) {

            FinAppGUI.this.requestFocusInWindow();
        }
    }

    // Internal Class Helpers:


    // MODIFIES: this
    // EFFECT: refresh panes with new information when anything changes
    private void refreshPanes(boolean isMaster) {
        desktop.remove(accPane);
        desktop.remove(blPane);
        desktop.remove(trPane);
        if (isMaster) {
            accountsPane();
            balancePane(true);
            transactionsPane(true);
        } else {
            balancePane(false);
            transactionsPane(false);
            setVisible(true);
        }

    }


}

