package talkingpal;

import talkingpal.command.CommandParser;
import talkingpal.task.TaskList;
import talkingpal.task.Todo;
import talkingpal.task.Event;
import talkingpal.task.Deadline;
import talkingpal.command.Command;
import talkingpal.exception.TalkingPalException;
import talkingpal.util.Storage;
import java.io.IOException;

/**
 * The main entry point of the TalkingPal application.
 * <p>
 * Handles the program flow by coordinating the UI, command parsing, task list operations,
 * and saving/loading of tasks.
 */

public class TalkingPal {

    private TaskList taskList;
    private boolean isUserNamed;
    private String userName;

    public TalkingPal() {
        isUserNamed = false;
        userName = "";
        this.taskList = Storage.createNewTasklist();
    }


    public String getCurrentTasks() {
        return this.taskList.toString();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String userInput) {
        if (!isUserNamed) {
            isUserNamed = true;
            return String.format("Greetings %s! What can I do for you?\n", userInput);
        }
        // Determine instruction
        StringBuilder out = new StringBuilder();
        if (!userInput.equalsIgnoreCase("bye")) {
            String newResponse = processCommand(userInput);
            out.append(newResponse);
            // Always return updated task list
            return out.append(this.getCurrentTasks()).toString();

        } else {
            try {
                out.append(Storage.saveTasks(taskList));
            } catch (IOException e) {
                String newLine = "Could not save tasks: " + e.getMessage();
                out.append(newLine);
            }
            out.append(String.format("\nGoodbye %s!", userName));
            return out.toString();
        }
    }

    private String processCommand(String userInput) {
        StringBuilder out = new StringBuilder();
        int taskNo;
        try {
            Command cmd = CommandParser.parseCommand(userInput);
            switch (cmd) {
                case LIST:
                    break; // We auto print at end of every operation
                case MARK:
                    taskNo = CommandParser.parseTaskNumber(userInput);
                    try {
                        taskList.markTask(taskNo);
                    } catch (IllegalArgumentException e) {
                        out.append(e.getMessage());
                    }
                    break;
                case UNMARK:
                    taskNo = CommandParser.parseTaskNumber(userInput);
                    try {
                        taskList.unmarkTask(taskNo);
                    } catch (IllegalArgumentException e) {
                        out.append(e.getMessage());
                    }
                    break;
                case DELETE:
                    taskNo = CommandParser.parseTaskNumber(userInput);
                    try {
                        taskList.delete(taskNo);
                    } catch (IllegalArgumentException e) {
                        out.append(e.getMessage());
                    }
                    break;
                case TODO:
                    try {
                        taskList.add(Todo.inputToTodo(userInput));
                        out.append("Added new task!\n");
                    } catch (TalkingPalException e) {
                        out.append(e.getMessage());
                    }
                    break;
                case DEADLINE:
                    try {
                        taskList.add(Deadline.inputToDeadline(userInput));
                        out.append("Added new task!\n");
                    } catch (TalkingPalException e) {
                        out.append(e.getMessage());
                    }
                    break;
                case EVENT:
                    try {
                        taskList.add(Event.inputToEvent(userInput));
                        out.append("Added new task!\n");
                    } catch (TalkingPalException e) {
                        out.append(e.getMessage());
                    }
                    break;
                case FIND:
                    return taskList.find(CommandParser.getArguments(userInput));
                default:
                    return "Sorry! I am too stupid to recognise that command **HITS OWN HEAD**";

            }
        } catch (TalkingPalException e) {
            out.append(e.getMessage());
        }
        return out.toString();
    }

}
