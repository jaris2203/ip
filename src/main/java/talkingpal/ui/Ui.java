package talkingpal.ui;

import java.util.Scanner;

public class Ui {
    private static final String LINE_DIVIDER  = " ____________________________________________\n";

    private Scanner scanner;
    private String userName;

    public Ui() {
        this.scanner = new Scanner(System.in);
        userName = "";
    }

    public void askForUserName() {
        System.out.println(LINE_DIVIDER + "Hello! I'm TalkingPal\n");
        System.out.println("What is your name?\n" + LINE_DIVIDER);
        this.userName = scanner.nextLine();
    }

    public void greet() {
        System.out.println(LINE_DIVIDER
                + String.format("Greetings %s! What can I do for you?\n", userName)
                + LINE_DIVIDER);
    }

    public String getNextInput() {
        return scanner.nextLine();
    }

    public void printGenericException(Exception e) {
        System.out.println(e.getMessage());
    }

    public void print(String output) {
        System.out.println(output);
    }

    public void exitChat() {
        System.out.println(LINE_DIVIDER
                + String.format("Bye %s! ", userName)
                + "Hope to see you again soon!\n"
                + LINE_DIVIDER);
        scanner.close();
    }
}
