public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String until) {
        super(description);
        this.from = from;
        this.to = until;
    }

    public static String[] parseEvent(String input) {
        input = input.trim().replaceAll("\\s+", " ");

        int firstSpace = input.indexOf(' ');
        int fromPos = input.indexOf(" /from ");
        int toPos = input.indexOf(" /to ");

        String command = input.substring(0, firstSpace).trim();
        String title = input.substring(firstSpace + 1, fromPos).trim();
        String from = input.substring(fromPos + " /from ".length(), toPos).trim();
        String to = input.substring(toPos + " /to ".length()).trim();

        return new String[] { command, title, from, to };
    }


    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from
                + " to: " + to + ")\n";
    }
}
