package talkingpal.task;

import talkingpal.exception.EmptyDescriptionException;
import talkingpal.exception.TalkingPalException;

public class Todo extends Task {

    //Task without deadline
    public Todo(String userInput) {
        super(userInput);
    }

    public Todo(String userInput, boolean isDone) {
        super(userInput, isDone);
    }

    private static String[] parseTodo(String input) throws TalkingPalException {
        String[] details = input.trim().split("\\s+", 2);
        String taskDesc = details[1];
        if (taskDesc.isBlank()) {
            throw new EmptyDescriptionException("todo");
        }
        String command = details[0];
        return new String[]{command, taskDesc};
    }

    public static Todo inputToTodo(String input) throws TalkingPalException{
        String[] details = parseTodo(input);
        String desc = details[1];
        if (desc.isBlank()) {
            throw new EmptyDescriptionException("todo");
        }
        return new Todo(desc);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString() + "\n";
    }
}
