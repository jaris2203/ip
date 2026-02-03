package talkingpal;

import talkingpal.command.CommandParser;
import talkingpal.task.*;
import talkingpal.command.Command;
import talkingpal.exception.TalkingPalException;
import talkingpal.util.Storage;
import talkingpal.ui.Ui;
import java.io.IOException;


public class TalkingPal {

    private Ui ui;
    private TaskList taskList;

    public TalkingPal() {
        ui = new Ui();
    }

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
}
