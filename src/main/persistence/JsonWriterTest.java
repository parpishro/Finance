package persistence;

import model.Account;
import model.Master;
import model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    private Master master;
    private Account account1;
    private Account account2;
    private Transaction t1;
    private Transaction t2;

    @BeforeEach
    void setup() {
        master = new Master("user");
        account1 = new Account(0, "Cash", true, 1000);
        account2 = new Account(1, "Out", false, 0);
        t1 = new Transaction(0, "Spending", "2022-10-10", 50, "Cash", "Out");
        t2 = new Transaction(1, "Spending", "2022-10-11", 40, "Cash", "Out");
    }


    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter saver = new JsonWriter("./da\ta/badFileName.json");
            saver.save(master);
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyMaster() {
        try {
            JsonWriter saver = new JsonWriter("./data/testEmptyMaster.json");
            saver.save(master);
            JsonReader loader = new JsonReader("./data/testEmptyMaster.json");
            Master master = loader.load();
            assertEquals("user", master.getUser());
            assertEquals(0, master.numberOfAccounts());
            assertEquals(0, master.getTotalBalance());
            assertEquals(0, master.numberOfTransactions());
        } catch (IOException e) {
            fail("Exception should not have been thrown for saving empty master file!");
        }
    }

    @Test
    void testWriterSingleAccountNoTransaction() {
        try {
            JsonWriter saver = new JsonWriter("./data/testSingleAccountNoTransaction.json");
            master.addAccount(account1);
            saver.save(master);

            JsonReader loader = new JsonReader("./data/testSingleAccountNoTransaction.json");
            Master master = loader.load();
            assertEquals("user", master.getUser());
            assertEquals(1, master.numberOfAccounts());
            assertEquals(1000, master.getTotalBalance());
            assertEquals(0, master.numberOfTransactions());
            assertEquals("Cash", master.getAccount("Cash").getName());
            assertEquals(1000, master.getAccount("Cash").getBalance());
            assertEquals(0, master.getAccount("Cash").getEntries().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown for saving master file with one account and no transaction");
        }
    }

    @Test
    void testWriterMultipleAccountMultipleTransaction() {
        try {
            JsonWriter saver = new JsonWriter("./data/testMultipleAccountsMultipleTransactions.json");
            master.addAccount(account1);
            master.addAccount(account2);
            master.addTransaction(t1);
            master.addTransaction(t2);
            saver.save(master);

            JsonReader loader = new JsonReader("./data/testMultipleAccountsMultipleTransactions.json");
            Master master = loader.load();
            assertEquals("user", master.getUser());
            assertEquals(2, master.numberOfAccounts());
            assertEquals(910, master.getTotalBalance());
            assertEquals("Cash", master.getAccount("Cash").getName());
            assertEquals("Out", master.getAccount("Out").getName());
            assertEquals(2, master.getAccount("Cash").getEntries().size());
            assertEquals(910, master.getAccount("Cash").getBalance());
            assertEquals(2, master.getAccount("Out").getEntries().size());
            assertEquals(90, master.getAccount("Out").getBalance());

        } catch (IOException e) {
            fail("Exception should not have been thrown for saving master file with 2 account and 2 transactions");
        }
    }
}


