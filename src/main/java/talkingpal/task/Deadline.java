package talkingpal.task;

import talkingpal.exception.*;
import talkingpal.util.DateParser;
import java.time.LocalDate;

/**
 * Represents a task with a description and a due date.
 */
public class Deadline extends Task {

    protected LocalDate by;

    /**
     * Creates a new {@code Deadline} task that is initially not done.
     *
     * @param description Task description (non-blank).
     * @param by Due date of the task (non-null).
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        assert by != null : "Deadline.by must not be null";
        this.by = by;
    }

    /**
     * Creates a new {@code Deadline} task with an explicit done/undone status.
     *
     * @param description Task description (non-blank).
     * @param by Due date of the task (non-null).
     * @param isDone Whether the task is done.
     */
    public Deadline(String description, LocalDate by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Parses a raw user input string into deadline components.
     * <p>
     * Expected format: {@code deadline <description> /by <date>}
     * </p>
     *
     * @param input Raw user input.
     * @return Array of {command, description, byDateString}.
     * @throws TalkingPalException If the description/date is missing or input format is invalid.
     */
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
     * Converts a raw user input string into a {@code Deadline} object.
     *
     * @param input Raw user input.
     * @return A {@code Deadline} configured using the user input.
     * @throws TalkingPalException If parsing fails or the date cannot be parsed.
     */
    public static Deadline inputToDeadline(String input) throws TalkingPalException {
        String[] details = parseDeadline(input);
        assert details.length == 3 : "parseDeadline must return 3 fields";
        assert details[1] != null && !details[1].isBlank() : "Deadline description must be non-blank";
        assert details[2] != null && !details[2].isBlank() : "Deadline date string must be non-blank";
        return new Deadline(details[1], DateParser.parse(details[2]));
    }

    @Override
    public Task copy() {
        return new Deadline(this.name, this.by, this.isDone);
    }

    @Override
    public String toString() {
        assert by != null : "Deadline.by must not be null when formatting";
        return "[D]" + super.toString()
                + " (by: " + DateParser.formatForStorage(by) + ")\n";
    }
}
