package talkingpal.task;

import talkingpal.exception.*;

public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String until) {
        super(description);
        this.from = from;
        this.to = until;
    }

    public Event(String description, String from, String until, boolean isDone) {
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


    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from
                + " to: " + to + ")\n";
    }
}
