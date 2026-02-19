package talkingpal.task;

import talkingpal.exception.EmptyDescriptionException;
import talkingpal.exception.TalkingPalException;

/**
 * Represents a to-do task with a description.
 */
public class Todo extends Task {

    /**
     * Creates a new {@code Todo} task that is initially not done.
     *
     * @param userInput Task description (non-blank).
     */
    public Todo(String userInput) {
        super(userInput);
    }

    /**
     * Creates a new {@code Todo} task with an explicit done/undone status.
     *
     * @param userInput Task description (non-blank).
     * @param isDone Whether the task is done.
     */
    public Todo(String userInput, boolean isDone) {
        super(userInput, isDone);
    }

    /**
     * Parses a raw user input string into todo components.
     * <p>
     * Expected format: {@code todo <description>}
     * </p>
     *
     * @param input Raw user input.
     * @return Array of {command, description}.
     * @throws TalkingPalException If the description is missing/blank.
     */
    private static String[] parseTodo(String input) throws TalkingPalException {
        String[] details = input.trim().split("\\s+", 2);
        assert details.length == 2 : "split(...,2) should yield 2 parts when description exists";
        String taskDesc = details[1];
        if (taskDesc.isBlank()) {
            throw new EmptyDescriptionException("todo");
        }
        String command = details[0];
        return new String[]{command, taskDesc};
    }

    /**
     * Converts a raw user input string into a {@code Todo} object.
     *
     * @param input Raw user input.
     * @return A {@code Todo} configured using the user input.
     * @throws TalkingPalException If parsing fails or description is blank.
     */
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
