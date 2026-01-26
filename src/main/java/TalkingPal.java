import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class TalkingPal {

    public static String lineDivider  = " ____________________________________________\n";

    public static void main(String[] args) {

        System.out.println(lineDivider + "Hello! I'm TalkingPal\n");

        // Get user's name
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your name?\n" + lineDivider);
        String userName = scanner.nextLine();
        System.out.println(lineDivider
                + String.format("Greetings %s! What can I do for you?\n", userName)
                + lineDivider);

        // Get user input repeatedly until bye is said
        TaskList taskList = new TaskList();
        String userInput = scanner.nextLine();
        while (!userInput.equalsIgnoreCase("bye")) {
            String mainCommand = checkCommand(userInput);
            switch (mainCommand) {
                case "list": {
                    break; // We auto print at end of every operation
                }
                case "mark": {
                    int taskNo = Integer.parseInt(userInput.trim().split("\\s+")[1]);
                    try {
                        taskList.markTask(taskNo);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "unmark": {
                    int taskNo = Integer.parseInt(userInput.trim().split("\\s+")[1]);
                    try {
                        taskList.unmarkTask(taskNo);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "todo": {
                    String[] details = Todo.parseTodo(userInput);
                    String desc = details[1];
                    taskList.add(new Todo(desc));
                    break;
                }
                case "deadline": {
                    String[] details = Deadline.parseDeadline(userInput);
                    taskList.add(new Deadline(details[1], details[2]));
                    break;
                }
                case "event": {
                    String[] details = Event.parseEvent(userInput);
                    taskList.add(new Event(details[1], details[2], details[3]));
                    break;
                }
                default: {
                    taskList.add(new Todo(userInput)); // Take default as normal Todo
                }
            }

            // Print all tasks at end of every operation
            taskList.printAllTasks();
            userInput = scanner.nextLine();
        }
        // Greet and exit
        exitChat(userName);
        scanner.close();

    }


    // Helper function to determine command (except bye)
    public static String checkCommand(String userInput) {
        String[] parts = userInput.trim().split("\\s+", 2);
        return parts[0].toLowerCase();
    }

    public static void exitChat(String userName) {
        System.out.println(lineDivider
                + String.format("Bye %s! ", userName)
                + "Hope to see you again soon!\n"
                + lineDivider);
    }

}
