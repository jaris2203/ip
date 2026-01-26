public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    // Helper function to deadline details
    public static String[] parseDeadline(String input) {
        input = input.trim().replaceAll("\\s+", " ");

        int firstSpace = input.indexOf(' ');
        int byPos = input.indexOf(" /by ");

        String command = input.substring(0, firstSpace).trim();
        String taskDesc = input.substring(firstSpace + 1, byPos).trim();
        String by = input.substring(byPos + " /by ".length()).trim();

        return new String[]{command, taskDesc, by};
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")\n";
    }
}
