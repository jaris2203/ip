package talkingpal.task;

import talkingpal.exception.*;
import talkingpal.util.DateParser;
import java.time.LocalDate;

/**
 * Represents a task with a description and a due date.
 */
public class Deadline extends Task {

    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, LocalDate by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    // Helper function to deadline details
    private static String[] parseDeadline(String input) throws TalkingPalException {
        input = input.trim().replaceAll("\\s+", " ");
        try {
            int firstSpace = input.indexOf(' ');
            int byPos = input.indexOf(" /by ");

            String command = input.substring(0, firstSpace).trim();
            String taskDesc = input.substring(firstSpace + 1, byPos).trim();
            if (taskDesc.isBlank()) {
                throw new EmptyDescriptionException("deadline");
            }
            String by = input.substring(byPos + " /by ".length()).trim();
            if (by.isBlank()) {
                throw new EmptyDateException("deadline");
            }
            return new String[]{command, taskDesc, by};
        } catch (IndexOutOfBoundsException e) {
            throw new TalkingPalException("Gimme more details pleaseee");
        }
    }
    /**
     * Converts a string input to a {@code Deadline} task object.
     *
     * @return {@code Deadline} configured using user input
     */
    public static Deadline inputToDeadline(String input) throws TalkingPalException{
        try {
            String[] details = parseDeadline(input);
            return new Deadline(details[1], DateParser.parse(details[2]));
        } finally {

        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + DateParser.formatForStorage(by) + ")\n";
    }
}
