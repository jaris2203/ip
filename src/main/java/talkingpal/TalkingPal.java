package talkingpal;

import talkingpal.task.*;
import talkingpal.command.Command;
import talkingpal.exception.TalkingPalException;
import talkingpal.util.TextFileParser;

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
    private static final Path SAVE_PATH = Paths.get("data", "talkingpal.TalkingPal.txt");

    public static void main(String[] args) {

        System.out.println(LINE_DIVIDER + "Hello! I'm TalkingPal\n");

        // Get user's name
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your name?\n" + LINE_DIVIDER);
        String userName = scanner.nextLine();

        // Gets task list from text file, if none creates empty
        TaskList taskList = createNewTasklist();
        taskList.printAllTasks();

        System.out.println(LINE_DIVIDER
                + String.format("Greetings %s! What can I do for you?\n", userName)
                + LINE_DIVIDER);
        String userInput = scanner.nextLine();
        // Get user input repeatedly until bye is said
        while (!userInput.equalsIgnoreCase("bye")) {
            try {
                Command mainCommand = Command.parse(getFirstWord(userInput));
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
            saveTasks(taskList);
            System.out.println("Saved tasks to: " + SAVE_PATH.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Could not save tasks: " + e.getMessage());
        }
        exitChat(userName);
        scanner.close();

    }

    // Helper function to save current task list to text file
    private static void saveTasks(TaskList taskList) throws IOException {
        String fullText = taskList.toString();
        int newlineIndex = fullText.indexOf('\n');
        if (newlineIndex == -1) {
            return;
        }
        String content = fullText.substring(newlineIndex + 1); //remove first line of tasks summary
        Files.createDirectories(SAVE_PATH.getParent()); // ensure data/ exists
        Files.writeString(SAVE_PATH, content, StandardCharsets.UTF_8);
    }

    // Helper function to read data input.txt and return populated taskList, else empty
    private static TaskList createNewTasklist() {
        TaskList output = new TaskList();
        boolean isLoaded = true;

        Path inputPath = Paths.get("data", "TalkingPal.txt"); // ip/data/TalkingPal.txt
        try (BufferedReader br = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                // process each line
                if (line.trim().isEmpty()) {
                    continue;
                }
                output.add(TextFileParser.parseTaskLine(line));
            }
        } catch (NoSuchFileException e) {
            System.err.println("File not found: " + inputPath.toAbsolutePath());
            isLoaded = false;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            isLoaded = false;
        } catch (TalkingPalException e) {
            System.err.println("Parse error: " + e.getMessage());
        }
        if (isLoaded) {
            System.out.println("Loaded data from TalkingPal.txt file!");
        }
        return output;
    }

    // Helper function to get first word
    public static String getFirstWord(String userInput) throws TalkingPalException {
        String[] parts = userInput.trim().split("\\s+", 2);
        // Reject one word commands (Except list and bye)
        if (parts.length <= 1) {
            if (!(parts[0].equalsIgnoreCase("bye") || parts[0].equalsIgnoreCase("list"))) {
                throw new TalkingPalException("Come on, I need a little bit more elaboration");
            }
        }
        return parts[0].toLowerCase();
    }

    public static void exitChat(String userName) {
        System.out.println(LINE_DIVIDER
                + String.format("Bye %s! ", userName)
                + "Hope to see you again soon!\n"
                + LINE_DIVIDER);
    }

}
