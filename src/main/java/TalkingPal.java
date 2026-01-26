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

            String mainCommand;
            try {
                mainCommand = checkCommand(userInput);
            } catch (TalkingPalException e) {
                System.out.println(e.getMessage());
                taskList.printAllTasks();
                userInput = scanner.nextLine();
                continue;
            }
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
                case "delete": {
                    int taskNo = Integer.parseInt(userInput.trim().split("\\s+")[1]);
                    try {
                        taskList.delete(taskNo);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "todo": {
                    try {
                        String[] details = Todo.parseTodo(userInput);
                        String desc = details[1];
                        taskList.add(new Todo(desc));
                    } catch (TalkingPalException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "deadline": {
                    try {
                        String[] details = Deadline.parseDeadline(userInput);
                        taskList.add(new Deadline(details[1], details[2]));
                    } catch (TalkingPalException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "event": {
                    try {
                        String[] details = Event.parseEvent(userInput);
                        taskList.add(new Event(details[1], details[2], details[3]));
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

            // Print all tasks at end of every operation
            taskList.printAllTasks();
            userInput = scanner.nextLine();
        }
        // Greet and exit
        exitChat(userName);
        scanner.close();

    }


    // Helper function to determine command (except bye)
    public static String checkCommand(String userInput) throws TalkingPalException {
        String[] parts = userInput.trim().split("\\s+", 2);
        if (parts.length <= 1) {
            throw new TalkingPalException("Come on, I need a little bit more elaboration");
        }
        return parts[0].toLowerCase();
    }

    public static void exitChat(String userName) {
        System.out.println(lineDivider
                + String.format("Bye %s! ", userName)
                + "Hope to see you again soon!\n"
                + lineDivider);
    }

}
