package persistence;

import model.Account;
import model.Master;
import model.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonReader {
    private Master master;
    private String path;

    public JsonReader(String path) throws IOException {
        this.path = path;
    }

    public Master load() throws IOException {
        StringBuilder fileContent = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> fileContent.append(s));
        }
        JSONObject jsonMaster = new JSONObject(fileContent.toString());
        master = new Master(jsonMaster.getString("user"));
        parseAccounts(jsonMaster.getJSONArray("accounts"));
        parseAllTransactions(jsonMaster.getJSONArray("allTransactions"));
        master.setTotalBalance(jsonMaster.getInt("totalBalance"));
        return master;
    }

    private void parseAccounts(JSONArray accounts) {
        for (Object json : accounts) {
            JSONObject nextAccount = (JSONObject) json;
            int index = nextAccount.getInt("index");
            String name = nextAccount.getString("name");
            boolean isPos = nextAccount.getBoolean("isPos");
            int balance = nextAccount.getInt("balance");
            List<Transaction> entries = parseEntries(nextAccount.getJSONArray("entries"));
            Account account = new Account(index, name, isPos, balance, entries);
            master.addAccount(account);
        }
    }


    private void parseAllTransactions(JSONArray transactions) {
        for (Object json : transactions) {
            JSONObject nextTransaction = (JSONObject) json;
            int index = nextTransaction.getInt("index");
            String type = nextTransaction.getString("type");
            String date = nextTransaction.getString("date");
            int value = nextTransaction.getInt("value");
            String from = nextTransaction.getString("from");
            String to = nextTransaction.getString("to");
            master.addTransaction(index, type, date, value, from, to);
        }
    }

    private List<Transaction> parseEntries(JSONArray transactions) {
        List<Transaction> entries = new ArrayList<>();
        for (Object json : transactions) {
            JSONObject nextEntry = (JSONObject) json;
            int index = nextEntry.getInt("index");
            String type = nextEntry.getString("type");
            String date = nextEntry.getString("date");
            int value = nextEntry.getInt("value");
            String from = nextEntry.getString("from");
            String to = nextEntry.getString("to");
            Transaction entry = new Transaction(index, type, date, value, from, to);
            entries.add(entry);
        }
        return entries;
    }
}
