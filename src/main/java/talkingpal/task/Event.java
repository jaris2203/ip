package talkingpal.task;

import talkingpal.exception.*;
import java.time.LocalDate;
import talkingpal.util.DateParser;

/**
 * Represents a task with a description and a start and end date.
 */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    public Event(String description, LocalDate from, LocalDate until) {
        super(description);
        assert from != null && until != null : "Event dates must not be null";
        assert !until.isBefore(from) : "Event end date must be on/after start date";
        this.from = from;
        this.to = until;
    }

    public Event(String description, LocalDate from, LocalDate until, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = until;
    }

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
     * Converts a string input to a {@code Event} task object.
     *
     * @return {@code Event} configured using user input
     */
    public static Event inputToEvent(String input) throws TalkingPalException{
        String[] details = parseEvent(input);
        assert details.length == 4 : "parseEvent must return 4 fields";
        return new Event(
                details[1], DateParser.parse(details[2]), DateParser.parse(details[3]));
    }

    @Override
    public String toString() {
        assert from != null && to != null : "Event dates must not be null when formatting";
        return "[E]" + super.toString() + " (from: " + DateParser.formatForStorage(from)
                + " to: " + DateParser.formatForStorage(to) + ")\n";
    }

}
