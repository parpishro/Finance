package persistence;

import model.Master;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {


    @BeforeEach
    void setup() {
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
            assertEquals("Cash", master.getAccount("Cash").getName());
            assertEquals("Out", master.getAccount("Out").getName());
            assertEquals(2, master.getAccount("Cash").getEntries().size());
            assertEquals(910, master.getAccount("Cash").getBalance());
            assertEquals(2, master.getAccount("Out").getEntries().size());
            assertEquals(90, master.getAccount("Out").getBalance());
        } catch (IOException e) {
            fail("Failed to load the master file with multiple accounts and multiple transactions!");
        }
    }
}

