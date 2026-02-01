public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, String by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    // Helper function to deadline details
    public static String[] parseDeadline(String input) throws TalkingPalException {
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

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")\n";
    }
}
