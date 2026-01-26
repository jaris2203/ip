public class Todo extends Task {

    //Task without deadline
    public Todo(String userInput) {
        super(userInput);
    }

    public static String[] parseTodo(String input) throws TalkingPalException {
        String[] details = input.trim().split("\\s+", 2);
        String taskDesc = details[1];
        if (taskDesc.isBlank()) {
            throw new EmptyDescriptionException("todo");
        }
        String command = details[0];
        return new String[]{command, taskDesc};
    }

    @Override
    public String toString() {
        return "[T]" + super.toString() + "\n";
    }
}
