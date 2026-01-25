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
        String userInput = scanner.nextLine();
        while (!Objects.equals(userInput, "bye")) {
            System.out.println(lineDivider
                    + userInput + "\n"
                    + lineDivider);
            userInput = scanner.nextLine();
        }

        // Greet and exit
        exitChat();
        scanner.close();
    }


    public static void exitChat() {
        System.out.println(lineDivider
                + "Bye. Hope to see you again soon!\n"
                + lineDivider);
    }

}
