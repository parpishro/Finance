package test;

import model.Account;
import model.Master;
import model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMaster {

    private Master master;

    private Account acc1;
    private Account acc2;
    private Account acc4;

    private Transaction entry1;
    private Transaction entry2;


    @BeforeEach
    void runBefore() {

        master = new Master();

        acc1 = new Account(1, "Cash", true, 1000);
        acc2 = new Account(2, "Bank", true, 20000);
        acc4 = new Account(4, "Income", false, 0);

        entry1 = new Transaction(1, "Earning", "2022-10-10", 10000, acc4, acc2);
        entry2 = new Transaction(2, "Transfer", "2022-10-11", 5000, acc2, acc1);


    }


    @Test
    void testAddAccountSingle() {

        master.addAccount(acc1);

        assertEquals(1, master.getAccountList().size());
        assertTrue(master.getAccountList().contains(acc1));
    }

    @Test
    void testAddAccountMultiple() {

        master.addAccount(acc1);
        master.addAccount(acc2);

        assertEquals(2, master.getAccountList().size());
        assertTrue(master.getAccountList().contains(acc1));
        assertTrue(master.getAccountList().contains(acc1));
    }

    @Test
    void testRemoveAccountSingle() {

        master.addAccount(acc1);
        master.addAccount(acc2);
        master.removeAccount("Cash");

        assertEquals(1, master.getAccountList().size());
        assertFalse(master.getAccountList().contains(acc1));
        assertTrue(master.getAccountList().contains(acc2));
    }

    @Test
    void testRemoveAccountMultiple() {

        master.addAccount(acc1);
        master.addAccount(acc2);
        master.addAccount(acc4);
        master.removeAccount("Cash");
        master.removeAccount("Bank");

        assertEquals(1, master.getAccountList().size());
        assertFalse(master.getAccountList().contains(acc1));
        assertFalse(master.getAccountList().contains(acc2));
        assertTrue(master.getAccountList().contains(acc4));
    }

    @Test
    void testAddTransactionSingle() {

        master.addAccount(acc1);
        master.addAccount(acc2);
        entry2 = new Transaction(2, "Transfer", "2022-10-11", 5000, acc2, acc1);
        master.addTransaction(entry2);

        assertEquals(1, master.getMasterAccount().size());
        assertEquals(1, master.getAccount("Cash").getEntries().size());
        assertEquals(1, master.getAccount("Bank").getEntries().size());
        assertTrue(master.getMasterAccount().contains(entry2));
        assertTrue(master.getAccount("Cash").getEntries().contains(entry2));
        assertTrue(master.getAccount("Bank").getEntries().contains(entry2));
        assertEquals(21000, master.getTotalBalance());
    }

    @Test
    void testAddTransactionMultiple() {
        master.addAccount(acc1);
        master.addAccount(acc2);
        master.addAccount(acc4);
        entry1 = new Transaction(1, "Earning", "2022-10-10", 10000, acc4, acc2);
        entry2 = new Transaction(2, "Transfer", "2022-10-11", 5000, acc2, acc1);
        master.addTransaction(entry1);
        master.addTransaction(entry2);

        assertEquals(2, master.getMasterAccount().size());
        assertEquals(1, master.getAccount("Cash").getEntries().size());
        assertEquals(2, master.getAccount("Bank").getEntries().size());
        assertEquals(1, master.getAccount("Income").getEntries().size());
        assertTrue(master.getMasterAccount().contains(entry1));
        assertTrue(master.getMasterAccount().contains(entry2));
        assertTrue(master.getAccount("Cash").getEntries().contains(entry2));
        assertTrue(master.getAccount("Bank").getEntries().contains(entry2));
        assertTrue(master.getAccount("Bank").getEntries().contains(entry1));
        assertTrue(master.getAccount("Income").getEntries().contains(entry1));
        assertEquals(31000, master.getTotalBalance());
    }

    @Test
    void testRemoveTransactionSingle() {

        master.addAccount(acc1);
        master.addAccount(acc2);
        master.addAccount(acc4);
        entry1 = new Transaction(0, "Earning", "2022-10-10", 10000, acc4, acc2);
        entry2 = new Transaction(1, "Transfer", "2022-10-11", 5000, acc2, acc1);
        master.addTransaction(entry1);
        master.addTransaction(entry2);
        master.removeTransaction(0);

        assertEquals(1, master.getMasterAccount().size());
        assertEquals(1, master.getAccount("Cash").getEntries().size());
        assertEquals(1, master.getAccount("Bank").getEntries().size());
        assertEquals(0, master.getAccount("Income").getEntries().size());
        assertFalse(master.getMasterAccount().contains(entry1));
        assertTrue(master.getMasterAccount().contains(entry2));
        assertTrue(master.getAccount("Cash").getEntries().contains(entry2));
        assertTrue(master.getAccount("Bank").getEntries().contains(entry2));
        assertFalse(master.getAccount("Bank").getEntries().contains(entry1));
        assertFalse(master.getAccount("Income").getEntries().contains(entry1));
        assertEquals(21000, master.getTotalBalance());

    }

    @Test
    void testRemoveTransactionMultiple() {

        master.addAccount(acc1);
        master.addAccount(acc2);
        master.addAccount(acc4);
        entry1 = new Transaction(0, "Earning", "2022-10-10", 10000, acc4, acc2);
        entry2 = new Transaction(1, "Transfer", "2022-10-11", 5000, acc2, acc1);
        master.addTransaction(entry1);
        master.addTransaction(entry2);
        master.removeTransaction(0);
        master.removeTransaction(1);

        assertEquals(0, master.getMasterAccount().size());
        assertEquals(0, master.getAccount("Cash").getEntries().size());
        assertEquals(0, master.getAccount("Bank").getEntries().size());
        assertEquals(0, master.getAccount("Income").getEntries().size());
        assertFalse(master.getMasterAccount().contains(entry1));
        assertFalse(master.getMasterAccount().contains(entry2));
        assertFalse(master.getAccount("Cash").getEntries().contains(entry2));
        assertFalse(master.getAccount("Bank").getEntries().contains(entry2));
        assertFalse(master.getAccount("Bank").getEntries().contains(entry1));
        assertFalse(master.getAccount("Income").getEntries().contains(entry1));
        assertEquals(21000, master.getTotalBalance());

    }

    @Test
    void testGetAccountsNames() {
        master.addAccount(acc1);
        master.addAccount(acc2);
        master.addAccount(acc4);

        assertEquals(3, master.getAccountNames().size());
        assertTrue(master.getAccountNames().contains("Cash"));
        assertTrue(master.getAccountNames().contains("Income"));
        assertTrue(master.getAccountNames().contains("Bank"));
    }



}
