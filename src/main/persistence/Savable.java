package persistence;

import org.json.JSONObject;

// requires the classes of a serializable model to have toJson method for serialization when saving
public interface Savable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
