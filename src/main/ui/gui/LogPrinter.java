package ui.gui;

import model.Event;
import model.EventLog;

// Defines behaviours that event log printers must support.
public class LogPrinter {

    private String logs;

	// REQUIRES:
	// MODIFIES:
	// EFFECTS: Prints the log
    public void printLog(EventLog el) {
        for (Event next : el) {
            logs = logs + next.toString() + "\n\n";
        }
        System.out.println(logs);
    }
}
