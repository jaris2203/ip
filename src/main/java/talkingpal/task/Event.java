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
        try {
            int firstSpace = input.indexOf(' ');
            int fromPos = input.indexOf(" /from ");
            int toPos = input.indexOf(" /to ");

            String command = input.substring(0, firstSpace).trim();
            String eventDesc = input.substring(firstSpace + 1, fromPos).trim();
            if (eventDesc.isBlank()) {
                throw new EmptyDescriptionException("event");
            }
            String from = input.substring(fromPos + " /from ".length(), toPos).trim();
            String to = input.substring(toPos + " /to ".length()).trim();
            if (from.isBlank() || to.isBlank()) {
                throw new EmptyDateException("event");
            }

            return new String[] { command, eventDesc, from, to };
        } catch (IndexOutOfBoundsException e) {
            throw new TalkingPalException("Gimme more details pleaseee");
        }
    }

    /**
     * Converts a user input string into an {@code Event} object.
     *
     * @param input Raw user input.
     * @return An {@code Event} configured using the user input.
     * @throws TalkingPalException If parsing fails or the dates cannot be parsed.
     */
    public static Event inputToEvent(String input) throws TalkingPalException{
        try {
            String[] details = parseEvent(input);
            return new Event(
                    details[1], DateParser.parse(details[2]), DateParser.parse(details[3]));
        } finally {

        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateParser.formatForStorage(from)
                + " to: " + DateParser.formatForStorage(to) + ")\n";
    }
}
