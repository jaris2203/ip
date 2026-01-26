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

            // Print all tasks and resume getting new tasks when user replies list
            if (userInput.equalsIgnoreCase("list")) {
                taskList.printAllTasks();
                userInput = scanner.nextLine();
                continue;
            }

            // Marking and unmarking of tasks
            String[] parts = userInput.trim().split("\\s+"); // Split by whitespace
            if (parts.length == 2 && parts[1].matches("\\d+")) {

                // Process mark/unmark command to specified task number
                int taskNo = Integer.parseInt(parts[1]);
                if (parts[0].equalsIgnoreCase("mark")) {
                    try {
                        taskList.markTask(taskNo);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    taskList.printAllTasks(); // Print all tasks after update
                    userInput = scanner.nextLine();
                    continue;
                } else if (parts[0].equalsIgnoreCase("unmark")) {
                    try {
                        taskList.unmarkTask(taskNo);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    taskList.printAllTasks();
                    userInput = scanner.nextLine();
                    continue;
                }
                //Default as add input to tasks
            }

            // Add to task list for standard reply + Wait for next entry
            taskList.add(new Task(userInput));
            System.out.println(lineDivider
                    + "Added to task list: "
                    + userInput + "\n"
                    + lineDivider);
            userInput = scanner.nextLine();
        }
        // Greet and exit
        exitChat(userName);
        scanner.close();

    }

    public static void checkCommand(String userInput) {

    }

    public static void exitChat(String userName) {
        System.out.println(lineDivider
                + String.format("Bye %s! ", userName)
                + "Hope to see you again soon!\n"
                + lineDivider);
    }

}
