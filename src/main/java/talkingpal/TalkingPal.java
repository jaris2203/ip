package talkingpal;

import talkingpal.command.CommandParser;
import talkingpal.task.*;
import talkingpal.command.Command;
import talkingpal.exception.TalkingPalException;
import talkingpal.util.Storage;
import talkingpal.ui.Ui;
import java.io.IOException;

/**
 * The main entry point of the TalkingPal application.
 * <p>
 * Handles the program flow by coordinating the UI, command parsing, task list operations,
 * and saving/loading of tasks.
 */

public class TalkingPal {

    private Ui ui;
    private TaskList taskList;
    private boolean isUserNamed;
    private String userName;

    public TalkingPal() {
        ui = new Ui();
        isUserNamed = false;
        userName = "";
        this.taskList = Storage.createNewTasklist();
    }

    public void initialiseTaskList() {
        // Gets task list from text file, if none creates empty
        this.taskList = Storage.createNewTasklist();
    }

    public String getCurrentTasks() {
        return this.taskList.toString();
    }

    private String processCommand(String userInput) {
        if (!isUserNamed) {
            isUserNamed = true;
            return String.format("Greetings %s! What can I do for you?\n", userInput);
        }
        // Determine instruction
        StringBuilder out = new StringBuilder();
        if (!userInput.equalsIgnoreCase("bye")) {
            try {
                int taskNo;
                // Determine type of action command
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

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return processCommand(input);
    }

    /*
    public void run() {
        // Get user's name
        ui.askForUserName();

        // Gets task list from text file, if none creates empty
        this.taskList = Storage.createNewTasklist();
        taskList.printAllTasks();

        // Greets and gets next command
        ui.greet();
        String userInput = ui.getNextInput();

        // Get user input repeatedly until bye is said
        while (!userInput.equalsIgnoreCase("bye")) {
            try {
                int taskNo;

                // Determine type of action command
                Command cmd = CommandParser.parseCommand(userInput);
                switch (cmd) {
                case LIST:
                    break; // We auto print at end of every operation
                case MARK:
                    taskNo = CommandParser.parseTaskNumber(userInput);
                    try {
                        taskList.markTask(taskNo);
                    } catch (IllegalArgumentException e) {
                        ui.printGenericException(e);
                    }
                    break;
                case UNMARK:
                    taskNo = CommandParser.parseTaskNumber(userInput);
                    try {
                        taskList.unmarkTask(taskNo);
                    } catch (IllegalArgumentException e) {
                        ui.printGenericException(e);
                    }
                    break;
                case DELETE:
                    taskNo = CommandParser.parseTaskNumber(userInput);
                    try {
                        taskList.delete(taskNo);
                    } catch (IllegalArgumentException e) {
                        ui.printGenericException(e);
                    }
                    break;
                case TODO:
                    try {
                        taskList.add(Todo.inputToTodo(userInput));
                    } catch (TalkingPalException e) {
                        ui.printGenericException(e);
                    }
                    break;
                case DEADLINE:
                    try {
                        taskList.add(Deadline.inputToDeadline(userInput));
                    } catch (TalkingPalException e) {
                        ui.printGenericException(e);
                    }
                    break;
                case EVENT:
                    try {
                        taskList.add(Event.inputToEvent(userInput));
                    } catch (TalkingPalException e) {
                        ui.printGenericException(e);
                    }
                    break;
                case FIND:
                    String filteredList = taskList.find(CommandParser.getArguments(userInput));
                    ui.print(filteredList);
                    userInput = ui.getNextInput();
                    continue;
                default:
                    System.out.println("Sorry! I am too stupid to recognise that command **HITS OWN HEAD**");
                    userInput = ui.getNextInput();
                    continue;
                }
            } catch (TalkingPalException e) {
                ui.printGenericException(e);
            }

            // Print all tasks at end of every operation
            taskList.printAllTasks();
            userInput = ui.getNextInput();
        }

        // Save tasks, greet and exit
        try {
            Storage.saveTasks(taskList);
        } catch (IOException e) {
            System.err.println("Could not save tasks: " + e.getMessage());
        }
        ui.exitChat();

    }
    public static void main(String[] args) {
        new TalkingPal().run();
    }
    */
}
