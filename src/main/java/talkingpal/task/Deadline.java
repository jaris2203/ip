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
        assert by != null : "Deadline.by must not be null";
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
    public static Deadline inputToDeadline(String input) throws TalkingPalException {
        String[] details = parseDeadline(input);
        assert details.length == 3 : "parseDeadline must return 3 fields";
        assert details[1] != null && !details[1].isBlank() : "Deadline description must be non-blank";
        assert details[2] != null && !details[2].isBlank() : "Deadline date string must be non-blank";
        return new Deadline(details[1], DateParser.parse(details[2]));
    }


    @Override
    public String toString() {
        assert by != null : "Deadline.by must not be null when formatting";
        return "[D]" + super.toString()
                + " (by: " + DateParser.formatForStorage(by) + ")\n";
    }
}
