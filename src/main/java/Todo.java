public class Todo extends Task {

    //Task without deadline
    public Todo(String userInput) {
        super(userInput);
    }

    public static String[] parseTodo(String input) {
        String[] details = input.trim().split("\\s+", 2);
        String taskDesc = details[1];
        String command = details[0];
        return new String[]{command, taskDesc};
    }

    @Override
    public String toString() {
        return "[T]" + super.toString() + "\n";
    }
}
