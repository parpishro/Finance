package persistence;

import model.Account;
import model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkAccount(int index, String name, boolean isPos, int balance, Account account) {
        assertEquals(index, account.getIndex());
        assertEquals(name, account.getName());
        assertEquals(isPos, account.getIsPos());
        assertEquals(balance, account.getBalance());
    }

    protected void checkEntry(int index, String type, String date, int value, String from, String to, Transaction t) {
        assertEquals(index, t.getIndex());
        assertEquals(type, t.getType());
        assertEquals(date, t.getDate());
        assertEquals(value, t.getValue());
        assertEquals(from, t.getFrom());
        assertEquals(to, t.getTo());
    }
}
