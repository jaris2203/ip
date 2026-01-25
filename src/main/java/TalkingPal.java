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
        ArrayList<Task> taskList = new ArrayList<>();
        String userInput = scanner.nextLine();
        while (!userInput.equalsIgnoreCase("bye")) {

            // Print all tasks and resume getting new tasks when user replies list
            if (userInput.equalsIgnoreCase("list")) {
                printAllTasks(taskList);
                userInput = scanner.nextLine();
                continue;
            }

            // Marking and unmarking of tasks


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

    public static void printAllTasks(ArrayList<Task> taskList) {
        System.out.println("Current Tasks:\n");
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i) == null) break;
            String output = String.format("%d. %s", i + 1, taskList.get(i).toString());
            System.out.println(output);
        }
    }

    public static void exitChat(String userName) {
        System.out.println(lineDivider
                + String.format("Bye %s! ", userName)
                + "Hope to see you again soon!\n"
                + lineDivider);
    }

}
