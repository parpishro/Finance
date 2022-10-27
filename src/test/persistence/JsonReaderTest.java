package persistence;

import model.Account;
import model.Master;
import model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
    private Account a1;
    private Account a2;
    private Transaction t1;
    private Transaction t2;


    @BeforeEach
    void setup() {
        a1 = new Account(0, "Cash", true, 1000);
        a2 = new Account(1, "Out", false, 0);
        t1 = new Transaction(0, "Spending", "2022-10-10", 50, "Cash", "Out");
        t2 = new Transaction(1, "Spending", "2022-10-11", 40, "Cash", "Out");
    }

    @Test
    void testReaderNoUserFile() throws IOException {
        JsonReader loader = new JsonReader("./data/noFile.json");
        try {
            Master master = loader.load();
            assertEquals("user", master.getUser());
            fail();
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMasterFile() throws IOException {
        JsonReader loader = new JsonReader("./data/testEmptyMaster.json");
        try {
            Master master = loader.load();
            assertEquals("user", master.getUser());
            assertEquals(0, master.numberOfAccounts());
            assertEquals(0, master.getTotalBalance());
            assertEquals(0, master.numberOfTransactions());
        } catch (IOException e) {
            fail("Failed to load the empty file!");
        }
    }


    @Test
    void testReaderSingleAccountNoTransaction() throws IOException {
        JsonReader loader = new JsonReader("./data/testSingleAccountNoTransaction.json");
        try {
            Master master = loader.load();
            assertEquals("user", master.getUser());
            assertEquals(1, master.numberOfAccounts());
            assertEquals(1000, master.getTotalBalance());
            assertEquals(0, master.numberOfTransactions());
            assertEquals("Cash", master.getAccount("Cash").getName());
            assertEquals(1000, master.getAccount("Cash").getBalance());
            checkAccount(a1.getIndex(), a1.getName(), a1.getIsPos(), a1.getBalance(), master.getAccount("Cash"));
            assertEquals(0, master.getAccount("Cash").getEntries().size());
        } catch (IOException e) {
            fail("Failed to load the master file with single account and no transaction!");
        }
    }

    @Test
    void testReaderMultipleAccountsMultipleTransactions() throws IOException {
        JsonReader loader = new JsonReader("./data/testMultipleAccountsMultipleTransactions.json");
        try {
            Master master = loader.load();
            assertEquals("user", master.getUser());
            assertEquals(2, master.numberOfAccounts());
            assertEquals(910, master.getTotalBalance());
            assertEquals(2, master.numberOfTransactions());
            checkAccount(a1.getIndex(), a1.getName(), a1.getIsPos(),
                    a1.getBalance() - t1.getValue() - t2.getValue() , master.getAccount("Cash"));
            checkAccount(a2.getIndex(), a2.getName(), a2.getIsPos(),
                    a2.getBalance() + t1.getValue() + t2.getValue(), master.getAccount("Out"));
            assertEquals(2, master.getAccount("Cash").getEntries().size());
            assertEquals(910, master.getAccount("Cash").getBalance());
            assertEquals(2, master.getAccount("Out").getEntries().size());
            assertEquals(90, master.getAccount("Out").getBalance());
            checkEntry(t1.getIndex(), t1.getType(), t1.getDate(), t1.getValue(), t1.getFrom(), t1.getTo(),
                    master.getAllTransactions().get(0));
            checkEntry(t2.getIndex(), t2.getType(), t2.getDate(), t2.getValue(), t2.getFrom(), t2.getTo(),
                    master.getAllTransactions().get(1));
        } catch (IOException e) {
            fail("Failed to load the master file with multiple accounts and multiple transactions!");
        }
    }
}

