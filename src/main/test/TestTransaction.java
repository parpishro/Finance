package test;

import model.Account;
import model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTransaction {

    private int index;
    private String type;
    private String date;
    private int value;
    private Account from;
    private Account to;

    private Account acc1;
    private Account acc2;


    private Transaction entry;


    @BeforeEach
    void runBefore() {
        acc1 = new Account(0, "Cash", true, 1000);
        acc2 = new Account(1, "Bank", true, 20000);

        index = 1;
        type = "Transfer";
        date = "2022-10-10";
        value = 2500;
        from = acc1;
        to = acc2;


        entry = new Transaction(index, type, date, value, from, to);

    }

    @Test
    void testSetIndex() {
        entry.setIndex(2);
        assertEquals(2, entry.getIndex());
    }

    @Test
    void testSetType() {
        entry.setType("Spending");
        assertEquals("Spending", entry.getType());
    }

    @Test
    void testSetDate() {
        entry.setDate("2022-10-11");
        assertEquals("2022-10-11", entry.getDate());
    }

    @Test
    void testSetValue() {
        entry.setValue(3500);
        assertEquals(3500, entry.getValue());
    }

    @Test
    void testSetFrom() {
        Account acc = new Account(2, "Credit", false, -10000);
        entry.setFrom(acc);
        assertEquals(acc, entry.getFrom());
    }

    @Test
    void testSetTo() {
        Account acc = new Account(2, "Credit", false, -10000);
        entry.setTo(acc);
        assertEquals(acc, entry.getTo());
    }

    @Test
    void testGetIndex() {
        assertEquals(1, entry.getIndex());
    }

    @Test
    void testGetType() {
        assertEquals("Transfer", entry.getType());
    }

    @Test
    void testGetDate() {
        assertEquals("2022-10-10", entry.getDate());
    }

    @Test
    void testGetValue() {
        assertEquals(2500, entry.getValue());
    }

    @Test
    void testGetFrom() {
        assertEquals(acc1, entry.getFrom());
    }

    @Test
    void testGetTo() {
        assertEquals(acc2, entry.getTo());
    }


}
