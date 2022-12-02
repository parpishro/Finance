package model;

import java.util.Calendar;
import java.util.Date;


// Represents an alarm system event.
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

	// REQuIRES:
    // MODIFIES:
    // EFFECTS: Creates an event with the given description and the current date/time stamp.
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    // REQuIRES:
    // MODIFIES:
    // EFFECTS: Gets the date of this event (includes time) and return  the date of the event
    public Date getDate() {
        return dateLogged;
    }


    // REQuIRES:
    // MODIFIES:
    // EFFECTS: Gets the description of this event and return the description of the event.
    public String getDescription() {
        return description;
    }


    // REQuIRES:
    // MODIFIES:
    // EFFECTS:
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

		
        if (other.getClass() != this.getClass()) {
            return false;
        }

		
        Event otherEvent = (Event) other;
		
        return (this.dateLogged.equals(otherEvent.dateLogged)
				&& this.description.equals(otherEvent.description));
    }


    // REQuIRES:
    // MODIFIES:
    // EFFECTS:
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }


    // REQuIRES:
    // MODIFIES:
    // EFFECTS:
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
