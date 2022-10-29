package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTransaction {



    private Transaction entry;

    @BeforeEach
    void runBefore() {

        int index = 1;
        String type = "Transfer";
        String date = "2022-10-10";
        int value = 2500;
        String from = "Cash";
        String to = "Bank";


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
        entry.setFrom("Credit");
        assertEquals("Credit", entry.getFrom());
    }

    @Test
    void testSetTo() {
        entry.setTo("Credit");
        assertEquals("Credit", entry.getTo());
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
        assertEquals("Cash", entry.getFrom());
    }

    @Test
    void testGetTo() {
        assertEquals("Bank", entry.getTo());
    }


}
