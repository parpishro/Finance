package persistence;

import model.Master;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of master to file
public class JsonWriter {
    private final PrintWriter writer;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String path) throws FileNotFoundException {
        this.writer = new PrintWriter(path);
    }


    // MODIFIES: this
    // EFFECTS: writes JSON representation of master to file
    public void save(Master master) {
        JSONObject json = master.toJson();
        writer.print(json.toString(2));
        writer.close();
    }


}

