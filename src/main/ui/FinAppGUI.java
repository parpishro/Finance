package ui;

import model.Account;
import model.Master;
import model.Transaction;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.List;

import static java.lang.Math.max;


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

    private static final int TR_NUM = 3;

    private static final String MAIN_TITLE = "Finance Manager";

    private static final String PATH = "./data/";

    private JDesktopPane desktop;
    private JMenuBar menuBar;
    private JPanel accPane;
    private JPanel blPane;
    private JPanel trPane;

    private Master master;
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

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
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

        menuBar = new JMenuBar();

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
        setBorder(blPane, "Total Balance");

        JLabel balanceText;

        if (isMaster) {
            blPane.setSize(BALANCE_PANE_WIDTH, BALANCE_PANE_HEIGHT);
            blPane.setLocation(MAIN_WIDTH - BALANCE_PANE_WIDTH, 0);
            balanceText = new JLabel((master.getTotalBalance() / 100.0) + " $");
        } else {
            blPane.setSize(ACC_BALANCE_WIDTH, ACC_BALANCE_HEIGHT);
            blPane.setLocation(0, 0);
            balanceText = new JLabel((account.getBalance() / 100.0) + " $");
        }

        balanceText.setFont(balanceText.getFont().deriveFont(50f));
        balanceText.setHorizontalAlignment(0);

        blPane.add(balanceText, BorderLayout.CENTER);
        desktop.add(blPane);
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
            trPane.setLocation(0, MAIN_HEIGHT - ACC_TRANSACTION_HEIGHT);
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

        int trNum = max(0, entries.size() - TR_NUM);
        String [][] data = new String[entries.size() - trNum][6];
        List<Transaction> transactions = entries.subList(trNum, entries.size());

        for (int i = 0; i < entries.size() - trNum; i++) {
            data[i][0] = Integer.toString(transactions.get(i).getIndex());
            data[i][1] = transactions.get(i).getType();
            data[i][2] = transactions.get(i).getDate();
            data[i][3] = Double.toString(transactions.get(i).getValue() / 100.0);
            data[i][4] = transactions.get(i).getFrom();
            data[i][5] = transactions.get(i).getTo();
        }

        String[] columnNames = { "Index", "Type", "Date", "Value", "From", "To"};
        JTable trTable = new JTable(data, columnNames);
        return trTable;
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


    // Represents action to be taken when user wants to add a new code to the system.
    private class AddAccountAction extends AbstractAction {

        AddAccountAction() {
            super("Add Account");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JTextField name = new JTextField(5);
            JTextField pos = new JTextField(5);
            JTextField bal = new JTextField(5);

            JPanel addAccount = new JPanel();
            addAccount.add(new JLabel("Name: "));
            addAccount.add(name);
            addAccount.add(Box.createHorizontalStrut(15)); // a spacer
            addAccount.add(new JLabel("Is Positive: "));
            addAccount.add(pos);
            addAccount.add(Box.createHorizontalStrut(15)); // a spacer
            addAccount.add(new JLabel("Balance: "));
            addAccount.add(bal);

            int result = JOptionPane.showConfirmDialog(null, addAccount,
                    "Please Enter Account Information", JOptionPane.OK_CANCEL_OPTION);
            String accountName = name.getText();
            Boolean isPos = Boolean.parseBoolean(pos.getText());
            int balance = (int) (Double.parseDouble(bal.getText()) * 100);
            if (result == JOptionPane.OK_OPTION) {
                Account account = new Account(-1, accountName, isPos, balance);
                master.addAccount(account);

            }
            refreshPanes();
        }

    }

    /**
     * Represents action to be taken when user wants to add a new code
     * to the system.
     */
    private class RemoveAccountAction extends AbstractAction {

        RemoveAccountAction() {
            super("Remove Account");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JTextField name = new JTextField(5);

            JPanel removeAccount = new JPanel();
            removeAccount.add(new JLabel("Name: "));
            removeAccount.add(name);

            int result = JOptionPane.showConfirmDialog(null, removeAccount,
                    "Please Enter Account Name", JOptionPane.OK_CANCEL_OPTION);
            String accountName = name.getText();
            if (result == JOptionPane.OK_OPTION) {
                master.removeAccount(accountName);
                desktop.remove(accPane);
                accountsPane();
            }
            refreshPanes();
        }

    }

    /**
     * Represents action to be taken when user wants to add a new code
     * to the system.
     */
    private class EditAccountAction extends AbstractAction {

        EditAccountAction() {
            super("Edit Account");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JTextField name = new JTextField(5);

            JPanel editAccount = new JPanel();
            editAccount.add(new JLabel("Name: "));
            editAccount.add(name);

            int result = JOptionPane.showConfirmDialog(null, editAccount,
                    "Please Enter Account Name", JOptionPane.OK_CANCEL_OPTION);
            String accountName = name.getText();
            if (result == JOptionPane.OK_OPTION) {
                Account account = master.getAccount(accountName);
                JTextField index = new JTextField(Integer.toString(account.getIndex()), 5);
                JTextField newName = new JTextField(account.getName(), 5);
                JTextField pos = new JTextField(Boolean.toString(account.getIsPos()), 5);
                JTextField bal = new JTextField(Integer.toString(account.getBalance() / 100),5);

                editAccount = new JPanel();
                editAccount.add(new JLabel("Index: "));
                editAccount.add(index);
                editAccount.add(Box.createHorizontalStrut(15)); // a spacer
                editAccount.add(new JLabel("Name: "));
                editAccount.add(newName);
                editAccount.add(Box.createHorizontalStrut(15)); // a spacer
                editAccount.add(new JLabel("Is Positive: "));
                editAccount.add(pos);
                editAccount.add(Box.createHorizontalStrut(15)); // a spacer
                editAccount.add(new JLabel("Balance: "));
                editAccount.add(bal);

                int result2 = JOptionPane.showConfirmDialog(null, editAccount,
                        "Please Enter Account Information", JOptionPane.OK_CANCEL_OPTION);
                if (result2 == JOptionPane.OK_OPTION) {
                    account.setIndex(Integer.parseInt(index.getText()));
                    account.setName(newName.getText());
                    account.setIsPos(Boolean.parseBoolean(pos.getText()));
                    account.setBalance(Integer.parseInt(bal.getText()) * 100);
                }
            }
            refreshPanes();
        }

    }


    private void refreshPanes() {
        desktop.remove(accPane);
        accountsPane();
        desktop.remove(blPane);
        balancePane(true);
        desktop.remove(trPane);
        transactionsPane(true);
    }


    /**
     * Represents action to be taken when user wants to add a new code
     * to the system.
     */
    private class AddEntryAction extends AbstractAction {

        AddEntryAction() {
            super("Add Transaction Entry");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JPanel addEntry = new JPanel();
            JTextField type = new JTextField(5);
            JTextField date = new JTextField(5);
            JTextField value = new JTextField(5);
            JTextField from = new JTextField(5);
            JTextField to = new JTextField(5);
            addEntry.add(new JLabel("Type: "));
            addEntry.add(type);
            addEntry.add(Box.createHorizontalStrut(15)); // a spacer
            addEntry.add(new JLabel("Date: "));
            addEntry.add(date);
            addEntry.add(Box.createHorizontalStrut(15)); // a spacer
            addEntry.add(new JLabel("Value: "));
            addEntry.add(value);
            addEntry.add(Box.createHorizontalStrut(15)); // a spacer
            addEntry.add(new JLabel("From: "));
            addEntry.add(from);
            addEntry.add(Box.createHorizontalStrut(15)); // a spacer
            addEntry.add(new JLabel("To: "));
            addEntry.add(to);

            int result = JOptionPane.showConfirmDialog(null, addEntry,
                    "Please Enter Transaction Detail", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                master.addTransaction(new Transaction(-1, type.getText(), date.getText(),
                        (int) (Double.parseDouble(value.getText()) * 100), from.getText(), to.getText()));
            }
            refreshPanes();
        }

    }

    /**
     * Represents action to be taken when user wants to add a new code
     * to the system.
     */
    private class RemoveEntryAction extends AbstractAction {

        RemoveEntryAction() {
            super("Remove Transaction Entry");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JTextField index = new JTextField(5);

            JPanel removeEntry = new JPanel();
            removeEntry.add(new JLabel("Index: "));
            removeEntry.add(index);

            int result = JOptionPane.showConfirmDialog(null, removeEntry,
                    "Please Enter the Index of Transaction", JOptionPane.OK_CANCEL_OPTION);
            int entryIndex = Integer.parseInt(index.getText());
            if (result == JOptionPane.OK_OPTION) {
                master.removeTransaction(entryIndex);
            }
            refreshPanes();
        }

    }

    /**
     * Represents action to be taken when user wants to add a new code
     * to the system.
     */
    private class EditEntryAction extends AbstractAction {

        EditEntryAction() {
            super("Edit Transaction Entry");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JTextField index = new JTextField(5);

            JPanel editEntry = new JPanel();
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

                editEntry = new JPanel();
                editEntry.add(new JLabel("Index: "));
                editEntry.add(changeIndex);
                editEntry.add(Box.createHorizontalStrut(15)); // a spacer
                editEntry.add(new JLabel("Type: "));
                editEntry.add(type);
                editEntry.add(Box.createHorizontalStrut(15)); // a spacer
                editEntry.add(new JLabel("Date: "));
                editEntry.add(date);
                editEntry.add(Box.createHorizontalStrut(15)); // a spacer
                editEntry.add(new JLabel("Value: "));
                editEntry.add(value);
                editEntry.add(Box.createHorizontalStrut(15)); // a spacer
                editEntry.add(new JLabel("From: "));
                editEntry.add(from);
                editEntry.add(Box.createHorizontalStrut(15)); // a spacer
                editEntry.add(new JLabel("To: "));
                editEntry.add(to);

                int result2 = JOptionPane.showConfirmDialog(null, editEntry,
                        "Please Edit Transaction Details", JOptionPane.OK_CANCEL_OPTION);
                if (result2 == JOptionPane.OK_OPTION) {
                    entry.setIndex(Integer.parseInt(changeIndex.getText()));
                    entry.setType(type.getText());
                    entry.setDate(date.getText());
                    entry.setValue(Integer.parseInt(value.getText()) * 100);
                    entry.setFrom(from.getText());
                    entry.setTo(to.getText());
                }
            }

            refreshPanes();
        }

    }


    /**
     * Represents action to be taken when user wants to add a new code
     * to the system.
     */
    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save Data");
        }

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

    /**
     * Represents the action to be taken when the user wants to add a new
     * sensor to the system.
     */
    private class PressAccountAction extends AbstractAction implements ActionListener {


        private Account account;

        PressAccountAction(Account account) {
            super(account.getName());
            this.account = account;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            evt.getActionCommand();
            accPane.setVisible(false);
            JPanel accFrame = new JPanel();
            accFrame.setVisible(true);
            accFrame.setSize(ACCOUNT_PANE_WIDTH, ACCOUNT_PANE_HEIGHT);
            desktop.add(accFrame, BorderLayout.WEST);
            setBorder(accFrame, account.getName());
        }
    }

    /**
     * Represents action to be taken when user clicks desktop
     * to switch focus. (Needed for key handling.)
     */
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            FinAppGUI.this.requestFocusInWindow();
        }
    }


}

