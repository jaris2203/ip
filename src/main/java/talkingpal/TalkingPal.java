package talkingpal;

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

        ui.greet();
        String userInput = ui.getNextInput();
        // Get user input repeatedly until bye is said
        while (!userInput.equalsIgnoreCase("bye")) {
            try {
                Command mainCommand = Command.parse(userInput);
                switch (mainCommand) {
                    case LIST: {
                        break; // We auto print at end of every operation
                    }
                    case MARK: {
                        int taskNo = Integer.parseInt(userInput.trim().split("\\s+")[1]);
                        try {
                            taskList.markTask(taskNo);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    case UNMARK: {
                        int taskNo = Integer.parseInt(userInput.trim().split("\\s+")[1]);
                        try {
                            taskList.unmarkTask(taskNo);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    case DELETE: {
                        int taskNo = Integer.parseInt(userInput.trim().split("\\s+")[1]);
                        try {
                            taskList.delete(taskNo);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    case TODO: {
                        try {
                            taskList.add(Todo.inputToTodo(userInput));
                        } catch (TalkingPalException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    case DEADLINE: {
                        try {
                            taskList.add(Deadline.inputToDeadline(userInput));
                        } catch (TalkingPalException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    case EVENT: {
                        try {
                            taskList.add(Event.inputToEvent(userInput));
                        } catch (TalkingPalException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    default: {
                        System.out.println("Sorry! I am too stupid to recognise that command **HITS OWN HEAD**");
                        userInput = ui.getNextInput();
                        continue;
                    }
                }
            } catch (TalkingPalException e) {
                System.out.println(e.getMessage());
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
