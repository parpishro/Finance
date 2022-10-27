package persistence;

import model.Account;
import model.Master;
import model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {
    private Master master;
    private Account a1;
    private Account a2;
    private Transaction t1;
    private Transaction t2;

    @BeforeEach
    void setup() {
        master = new Master("user");
        a1 = new Account(0, "Cash", true, 1000);
        a2 = new Account(1, "Out", false, 0);
        t1 = new Transaction(0, "Spending", "2022-10-10", 50, "Cash", "Out");
        t2 = new Transaction(1, "Spending", "2022-10-11", 40, "Cash", "Out");
    }


    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter saver = new JsonWriter("./da\ta/badFileName.json");
            saver.save(this.master);
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyMaster() {
        try {
            JsonWriter saver = new JsonWriter("./data/testEmptyMaster.json");
            saver.save(this.master);
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
            this.master.addAccount(a1);
            saver.save(this.master);

            JsonReader loader = new JsonReader("./data/testSingleAccountNoTransaction.json");
            Master master = loader.load();
            assertEquals("user", master.getUser());
            assertEquals(1, master.numberOfAccounts());
            assertEquals(1000, master.getTotalBalance());
            assertEquals(0, master.numberOfTransactions());
            checkAccount(a1.getIndex(), a1.getName(), a1.getIsPos(), a1.getBalance(), master.getAccount("Cash"));
            assertEquals(0, master.getAccount("Cash").getEntries().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown for saving master file with one account and no transaction");
        }
    }

    @Test
    void testWriterMultipleAccountMultipleTransaction() {
        try {
            JsonWriter saver = new JsonWriter("./data/testMultipleAccountsMultipleTransactions.json");
            master.addAccount(a1);
            master.addAccount(a2);
            master.addTransaction(t1);
            master.addTransaction(t2);
            saver.save(master);

            JsonReader loader = new JsonReader("./data/testMultipleAccountsMultipleTransactions.json");
            Master master = loader.load();
            assertEquals("user", master.getUser());
            assertEquals(2, master.numberOfAccounts());
            assertEquals(910, master.getTotalBalance());
            assertEquals(2, master.numberOfTransactions());
            checkAccount(a1.getIndex(), a1.getName(), a1.getIsPos(), a1.getBalance(), master.getAccount("Cash"));
            checkAccount(a2.getIndex(), a2.getName(), a2.getIsPos(), a2.getBalance(), master.getAccount("Out"));
            assertEquals(2, master.getAccount("Cash").getEntries().size());
            assertEquals(2, master.getAccount("Out").getEntries().size());
            assertEquals(90, master.getAccount("Out").getBalance());
            checkEntry(t1.getIndex(), t1.getType(), t1.getDate(), t1.getValue(), t1.getFrom(), t1.getTo(),
                    master.getAllTransactions().get(0));
            checkEntry(t2.getIndex(), t2.getType(), t2.getDate(), t2.getValue(), t2.getFrom(), t2.getTo(),
                    master.getAllTransactions().get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown for saving master file with 2 account and 2 transactions");
        }
    }
}


