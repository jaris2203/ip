package talkingpal.ui;

import java.util.Scanner;

/**
 * Handles user interaction for the TalkingPal application via the command line.
 * Responsible for displaying messages, reading user input, and printing errors.
 */
public class Ui {
    private static final String LINE_DIVIDER  = " ____________________________________________\n";

    private Scanner scanner;
    private String userName;

    public Ui() {
        this.scanner = new Scanner(System.in);
        userName = "";
    }

    /**
     * Prompts the user for their name and stores it for subsequent messages.
     */
    public void askForUserName() {
        System.out.println(LINE_DIVIDER + "Hello! I'm TalkingPal\n");
        System.out.println("What is your name?\n" + LINE_DIVIDER);
        this.userName = scanner.nextLine();
    }

    /**
     * Prints the greeting message that invites the user to enter a command.
     */
    public void greet() {
        System.out.println(LINE_DIVIDER
                + String.format("Greetings %s! What can I do for you?\n", userName)
                + LINE_DIVIDER);
    }

    public String getNextInput() {
        return scanner.nextLine();
    }

    /**
     * Prints a user-friendly error message for the given exception.
     *
     * @param e The exception to be displayed to the user.
     */
    public void printGenericException(Exception e) {
        System.out.println(e.getMessage());
    }

    /**
     * Prints the farewell message and closes the input scanner.
     */
    public void exitChat() {
        System.out.println(LINE_DIVIDER
                + String.format("Bye %s! ", userName)
                + "Hope to see you again soon!\n"
                + LINE_DIVIDER);
        scanner.close();
    }
}
