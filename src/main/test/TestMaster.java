package test;

import model.Account;
import model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMaster {

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

        entry1 = new Transaction(1, "Earning", "2022-10-10", 10000, acc4, acc2);
        entry2 = new Transaction(2, "Transfer", "2022-10-11", 5000, acc2, acc1);
        entry3 = new Transaction(3, "Spending", "2022-10-12", 3000, acc2, acc3);
        entry4 = new Transaction(4, "Spending", "2022-10-13", 15000, acc5, acc3);
        entry5 = new Transaction(5, "Transfer", "2022-10-14", 10000, acc2, acc5);
    }


    @Test
    void testMaster() {
        acc4.addEntry(entry1, true);
        acc2.addEntry(entry1, false);
        acc2.addEntry(entry2, true);
        acc1.addEntry(entry2, false);
        acc2.addEntry(entry3, true);
        acc3.addEntry(entry3, false);
        acc5.addEntry(entry4, true);
        acc3.addEntry(entry4, false);
        acc2.addEntry(entry5, true);
        acc5.addEntry(entry5, false);
    }

}
