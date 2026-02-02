package talkingpal;

import talkingpal.task.*;
import talkingpal.command.Command;
import talkingpal.exception.TalkingPalException;
import talkingpal.util.Storage;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TalkingPal {

    public static final String LINE_DIVIDER  = " ____________________________________________\n";

    public static void main(String[] args) {

        System.out.println(LINE_DIVIDER + "Hello! I'm TalkingPal\n");

        // Get user's name
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your name?\n" + LINE_DIVIDER);
        String userName = scanner.nextLine();

        // Gets task list from text file, if none creates empty
        TaskList taskList = Storage.createNewTasklist();
        taskList.printAllTasks();

        System.out.println(LINE_DIVIDER
                + String.format("Greetings %s! What can I do for you?\n", userName)
                + LINE_DIVIDER);
        String userInput = scanner.nextLine();
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
                        userInput = scanner.nextLine();
                        continue;
                    }
                }
            } catch (TalkingPalException e) {
                System.out.println(e.getMessage());
            }
            // Print all tasks at end of every operation
            taskList.printAllTasks();
            userInput = scanner.nextLine();
        }
        // Save tasks, greet and exit
        try {
            Storage.saveTasks(taskList);
        } catch (IOException e) {
            System.err.println("Could not save tasks: " + e.getMessage());
        }
        exitChat(userName);
        scanner.close();

    }

    public static void exitChat(String userName) {
        System.out.println(LINE_DIVIDER
                + String.format("Bye %s! ", userName)
                + "Hope to see you again soon!\n"
                + LINE_DIVIDER);
    }

}
