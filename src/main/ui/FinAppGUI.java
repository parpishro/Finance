package ui;

import model.Account;
import model.Master;
import model.Transaction;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


public class FinAppGUI extends JFrame {

    private static final int MAIN_WIDTH = 800;
    private static final int MAIN_HEIGHT = 600;
    private static final int MENU_WIDTH = MAIN_WIDTH / 2;
    private static final int MENU_HEIGHT = MAIN_HEIGHT / 2;
    private static final int BL_WIDTH = MAIN_WIDTH - MENU_WIDTH;
    private static final int BL_HEIGHT = MENU_HEIGHT;
    private static final int TR_WIDTH = MAIN_WIDTH;
    private static final int TR_HEIGHT = MAIN_HEIGHT - MENU_HEIGHT;

    private static final int ACC_BL_WIDTH = MAIN_WIDTH;
    private static final int ACC_BL_HEIGHT = MAIN_HEIGHT / 4;
    private static final int ACC_TR_WIDTH = MAIN_WIDTH;
    private static final int ACC_TR_HEIGHT = MAIN_HEIGHT - ACC_BL_HEIGHT;

    private static final String MAIN_TITLE = "Finance Manager";
    private static final String INIT_TITLE = "Start Finance App";
    private static final String VIEW_DESCRIPTOR = "View";
    private static final String ADD_DESCRIPTOR = "Add";
    private static final String REMOVE_DESCRIPTOR = "Remove";
    private static final String EDIT_DESCRIPTOR = "Edit";

    private JDesktopPane desktop;
    private BlBox blBox;
    private TrBox trBox;
    private JPanel accMenu;
    private JPanel balancePanel;
    private AcEntry acEntry;
    private TrEntry trEntry;
    private SaveButton saveButton;

    private Master master;

    public FinAppGUI() throws IOException {

        master = (new Init()).getMaster();

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());


        setContentPane(desktop);
        setTitle(MAIN_TITLE);
        setSize(MAIN_WIDTH, MAIN_HEIGHT);

        addMenu();
        addBalancePanel(master.getTotalBalance(), BL_WIDTH, BL_HEIGHT);
        addAccountsPanel();

        addTransactionsPanel();





        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);



    }

    private void addTransactionsPanel() {
    }

    private void addBalancePanel(int balance, int width, int height) {
        balancePanel = new JPanel();
        balancePanel.setLayout(new GridLayout(1, 1));
        JLabel balanceText = new JLabel(Double.toString(balance / 100.0) + " $");
        balanceText.setFont(balanceText.getFont().deriveFont(50f));
        balancePanel.add(balanceText, BorderLayout.CENTER);
        balanceText.setHorizontalAlignment(0);
        setBorder(balancePanel, "Balance");
        balancePanel.setVisible(true);
        balancePanel.setSize(width, height);
        balancePanel.setLocation(MAIN_WIDTH - MENU_WIDTH, 0);
        desktop.add(balancePanel);


    }

    /**
     * Adds menu bar.
     */
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu accountMenu = new JMenu("Account");
        addMenuItem(accountMenu, new AddAccountAction(), null);
        addMenuItem(accountMenu, new RemoveEntryAction(), null);
        addMenuItem(accountMenu, new EditAccountAction(), null);
        menuBar.add(accountMenu);

        JMenu transactionMenu = new JMenu("Transaction");
        //systemMenu.setMnemonic('y');
        addMenuItem(transactionMenu, new AddEntryAction(), KeyStroke.getKeyStroke("control T"));
        //addMenuItem(transactionMenu, new RemoveEntryAction(),null);
        //addMenuItem(transactionMenu, new EditEntryAction(),null);
        menuBar.add(transactionMenu);

        setJMenuBar(menuBar);
    }

    /**
     * Adds an item with given handler to the given menu
     * @param theMenu  menu to which new item is added
     * @param action   handler for new menu item
     * @param accelerator    keystroke accelerator for this menu item
     */
    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    private void setBorder(JPanel panel, String title) {
        TitledBorder border = new TitledBorder(title);
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        panel.setBorder(border);
    }

    /**
     * Helper to add control buttons.
     */
    private void addAccountsPanel() {
        accMenu = new JPanel();
        accMenu.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        int numAccounts = master.numberOfAccounts();
        buttonPanel.setLayout(new GridLayout(numAccounts,1));
        for (Account account: master.getAccounts()) {
            JButton btn = new JButton(account.getName());
            btn.setSize(MENU_WIDTH, MENU_HEIGHT / numAccounts);
            btn.addActionListener(new AccountMenuAction(account));
            buttonPanel.add(btn);
        }
        accMenu.add(buttonPanel, BorderLayout.CENTER);
        setBorder(accMenu, "Accounts");
        accMenu.setVisible(true);
        accMenu.setSize(MENU_WIDTH, MENU_HEIGHT);
        desktop.add(accMenu);
    }

    /**
     * Helper to centre main application window on desktop
     */
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }


    /**
     * Represents action to be taken when user wants to add a new code
     * to the system.
     */
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
            int balance = Integer.parseInt(bal.getText()) * 100;
            if (result == JOptionPane.OK_OPTION) {
                Account account = new Account(-1, accountName, isPos, balance);
                master.addAccount(account);
                desktop.remove(accMenu);
                addAccountsPanel();
            }
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
                desktop.remove(accMenu);
                addAccountsPanel();
            }
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
                accountName = newName.getText();
                Boolean isPos = Boolean.parseBoolean(pos.getText());
                int balance = Integer.parseInt(bal.getText()) * 100;
                if (result2 == JOptionPane.OK_OPTION) {
                    account.setIndex(Integer.parseInt(index.getText()));
                    account.setName(newName.getText());
                    account.setIsPos(Boolean.parseBoolean(pos.getText()));
                    account.setBalance(Integer.parseInt(bal.getText()) * 100);
                }
            }
        }

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
        }

    }

    /**
     * Represents the action to be taken when the user wants to add a new
     * sensor to the system.
     */
    private class AccountMenuAction extends AbstractAction implements ActionListener {


        private Account account;

        AccountMenuAction(Account account) {
            super(account.getName());
            this.account = account;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            evt.getActionCommand();
            accMenu.setVisible(false);
            JPanel accFrame = new JPanel();
            accFrame.setVisible(true);
            accFrame.setSize(MENU_WIDTH, MENU_HEIGHT);
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
