package talkingpal.task;

import talkingpal.exception.*;
import java.time.LocalDate;
import talkingpal.util.DateParser;

/**
 * Represents a {@link Task} that occurs within a time range (from - to).
 * <p>
 * Invariant: {@code from} and {@code to} should be non-null for a valid {@code Event}.
 * </p>
 */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    /**
     * Creates a new {@code Event} task that is initially not done.
     *
     * @param description Event description (non-blank).
     * @param from Start date (non-null).
     * @param until End date (non-null).
     */
    public Event(String description, LocalDate from, LocalDate until) {
        super(description);
        assert from != null && until != null : "Event dates must not be null";
        assert !until.isBefore(from) : "Event end date must be on/after start date";
        this.from = from;
        this.to = until;
    }

    /**
     * Creates a new {@code Event} task with an explicit done/undone status.
     *
     * @param description Event description (non-blank).
     * @param from Start date (non-null).
     * @param until End date (non-null).
     * @param isDone Whether the task is done.
     */
    public Event(String description, LocalDate from, LocalDate until, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = until;
    }

    /**
     * Parses a raw user input string into event components.
     * <p>
     * Expected format: {@code event <description> /from <date> /to <date>}
     * </p>
     *
     * @param input Raw user input.
     * @return Array of {command, description, fromDateString, toDateString}.
     * @throws TalkingPalException If the description/dates are missing or input format is invalid.
     */
    public static String[] parseEvent(String input) throws TalkingPalException {
        input = input.trim().replaceAll("\\s+", " ");

        int firstSpace = input.indexOf(' ');
        if (firstSpace == -1) {
            throw new TalkingPalException("Command me regarding events properly. I am rather unintelligent.\n");
        }

        String command = input.substring(0, firstSpace).trim();
        String rest = input.substring(firstSpace + 1).trim();

        java.util.regex.Pattern p = java.util.regex.Pattern.compile("^(.*?)\\s*/from\\s*(.*?)\\s*/to\\s*(.*)$");
        java.util.regex.Matcher m = p.matcher(rest);

        if (!m.matches()) {
            throw new TalkingPalException("Command me regarding events properly. I am rather unintelligent.\n");
        }

        String eventDesc = m.group(1).trim();
        String from = m.group(2).trim();
        String to = m.group(3).trim();

        if (eventDesc.isBlank()) {
            throw new EmptyDescriptionException("event");
        }
        if (from.isBlank() || to.isBlank()) {
            throw new EmptyDateException("event");
        }

        return new String[] { command, eventDesc, from, to };
    }

    /**
     * Converts a user input string into an {@code Event} object.
     *
     * @param input Raw user input.
     * @return An {@code Event} configured using the user input.
     * @throws TalkingPalException If parsing fails or the dates cannot be parsed.
     */
    public static Event inputToEvent(String input) throws TalkingPalException{
        String[] details = parseEvent(input);
        assert details.length == 4 : "parseEvent must return 4 fields";
        return new Event(
                details[1], DateParser.parse(details[2]), DateParser.parse(details[3]));
    }

    @Override
    public Task copy() {
        return new Event(this.name, this.from, this.to, this.isDone);
    }

    @Override
    public String toString() {
        assert from != null && to != null : "Event dates must not be null when formatting";
        return "[E]" + super.toString() + " (from: " + DateParser.formatForStorage(from)
                + " to: " + DateParser.formatForStorage(to) + ")\n";
    }

}
