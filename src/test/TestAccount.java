import model.Account;
import model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TestAccount {

    private Account acc1;
    private Account acc2;
    private Account acc3;
    private Account acc4;
    private Account acc5;

    private Transaction entry1;
    private Transaction entry2;
    private Transaction entry3;
    private Transaction entry4;
    private Transaction entry5;


    @BeforeEach
    void runBefore() {
        acc1 = new Account(1, "Cash", true, 1000);
        acc2 = new Account(2, "Bank", true, 20000);
        acc3 = new Account(3, "Expenditure", true, 0);
        acc4 = new Account(4, "Income", false, 0);
        acc5 = new Account(5, "Credit", false, -15000);

        entry1 = new Transaction(1, "Earning", "2022-10-10", 10000, "Income", "Bank");
        entry2 = new Transaction(2, "Transfer", "2022-10-11", 5000, "Bank", "Cash");
        entry3 = new Transaction(3, "Spending", "2022-10-12", 3000, "Bank", "Expenditure");
        entry4 = new Transaction(4, "Spending", "2022-10-13", 15000, "Credit", "Expenditure");
        entry5 = new Transaction(5, "Transfer", "2022-10-14", 10000, "Bank", "Credit");
    }



    @Test
    void testAddEntrySingleSubtract() {

        acc4.addEntry(entry1, true);

        assertEquals(1, acc4.getEntries().size());
        assertTrue(acc4.getEntries().contains(entry1));
        assertEquals(-10000, acc4.getBalance());
    }

    @Test
    void testAddEntrySingleAdd() {

        acc2.addEntry(entry1, false);

        assertEquals(1, acc2.getEntries().size());
        assertTrue(acc2.getEntries().contains(entry1));
        assertEquals(30000, acc2.getBalance());
    }

    @Test
    void testAddEntryMultipleSubtract() {

        acc2.addEntry(entry2, true);
        acc2.addEntry(entry3, true);

        assertEquals(2, acc2.getEntries().size());
        assertTrue(acc2.getEntries().contains(entry2));
        assertTrue(acc2.getEntries().contains(entry3));
        assertEquals(12000, acc2.getBalance());
    }

    @Test
    void testAddEntryMultipleAdd() {

        acc3.addEntry(entry3, false);
        acc3.addEntry(entry4, false);

        assertEquals(2, acc3.getEntries().size());
        assertTrue(acc3.getEntries().contains(entry3));
        assertTrue(acc3.getEntries().contains(entry4));
        assertEquals(18000, acc3.getBalance());
    }

    @Test
    void testAddEntryAddSubtract() {

        acc2.addEntry(entry1, false);
        acc2.addEntry(entry2, true);

        assertEquals(2, acc2.getEntries().size());
        assertEquals(25000, acc2.getBalance());
    }

    @Test
    void testAddEntrySubtractAdd() {

        acc5.addEntry(entry4, true);
        acc5.addEntry(entry5, false);

        assertEquals(2, acc5.getEntries().size());
        assertEquals(-20000, acc5.getBalance());
    }

    @Test
    void testRemoveEntryEmptyAccount() {
        assertFalse(acc1.removeEntry(3, false));
    }


    @Test
    void testRemoveEntrySingleAdd() {

        acc2.addEntry(entry1, false);
        acc2.removeEntry(1, true);

        assertEquals(0, acc2.getEntries().size());
        assertEquals(20000, acc2.getBalance());
    }

    @Test
    void testRemoveEntrySingleSubtract() {

        acc4.addEntry(entry1, true);
        acc4.removeEntry(1, false);

        assertEquals(0, acc4.getEntries().size());
        assertEquals(0, acc4.getBalance());
    }

    @Test
    void testRemoveEntryMultipleAdd() {

        acc3.addEntry(entry3, false);
        acc3.addEntry(entry4, false);
        acc3.removeEntry(3, true);
        acc3.removeEntry(4, true);

        assertEquals(0, acc3.getEntries().size());
        assertEquals(0, acc3.getBalance());
    }

    @Test
    void testRemoveEntryMultipleSubtract() {

        acc2.addEntry(entry2, true);
        acc2.addEntry(entry3, true);
        acc2.removeEntry(2, false);
        acc2.removeEntry(3, false);

        assertEquals(0, acc2.getEntries().size());
        assertEquals(20000, acc2.getBalance());
    }

    @Test
    void testRemoveEntryAddSubtract() {

        acc2.addEntry(entry1, false);
        acc2.addEntry(entry2, true);
        acc2.removeEntry(1, true);
        acc2.removeEntry(2, false);

        assertEquals(0, acc2.getEntries().size());
        assertEquals(20000, acc2.getBalance());
    }

    @Test
    void testRemoveEntrySubtractAdd() {

        acc5.addEntry(entry4, true);
        acc5.addEntry(entry5, false);
        acc5.removeEntry(5, true);
        acc5.removeEntry(4, false);

        assertEquals(0, acc5.getEntries().size());
        assertEquals(-15000, acc5.getBalance());
    }


    @Test
    void testSetBalanceManual() {

        acc1.setBalance(5000);

        assertEquals(5000, acc1.getBalance());
    }

    @Test
    void testSetIndex() {

        acc1.setIndex(10);

        assertEquals(10, acc1.getIndex());
    }

    @Test
    void testSetName() {

        acc2.setName("ScotiaBank");

        assertEquals("ScotiaBank", acc2.getName());
    }

    @Test
    void testSetIsPosFalse() {

        acc2.setIsPos(false);

        assertFalse(acc2.getIsPos());
    }

    @Test
    void testSetIsPosTrue() {

        acc4.setIsPos(true);

        assertTrue(acc4.getIsPos());
    }

    @Test
    void testGetEntries() {

        acc2.addEntry(entry1, false);
        acc2.addEntry(entry2, true);
        acc2.addEntry(entry3, true);
        acc2.addEntry(entry5, true);

        assertEquals(4, acc2.getEntries().size());
        assertTrue(acc2.getEntries().contains(entry1));
        assertTrue(acc2.getEntries().contains(entry2));
        assertTrue(acc2.getEntries().contains(entry3));
        assertTrue(acc2.getEntries().contains(entry5));
    }



}

