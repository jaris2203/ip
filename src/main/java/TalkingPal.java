import java.util.Scanner;

public class TalkingPal {

    public static String lineDivider  = " ____________________________________________\n";

    public static void main(String[] args) {

        System.out.println(lineDivider + "Hello! I'm TalkingPal\n");
        System.out.println("What can I do for you?\n" + lineDivider);

        // Get user input
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        // Greet and exit
        exitChat();
        scanner.close();
    }

    public static void exitChat() {
        System.out.println(lineDivider + "Bye. Hope to see you again soon!\n");
    }
}
