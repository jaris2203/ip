package talkingpal.command;

import talkingpal.exception.TalkingPalException;

/**
 * Parses user input into {@code Command} and extracts command arguments.
 */
public class CommandParser {

    private static final String WHITESPACE_REGEX = "\\s+";

    private CommandParser() {
        // Utility class
    }

    /**
     * Parses the first word of user input into a {@code Command}.
     *
     * @param userInput Raw user input.
     * @return Parsed {@code Command}.
     * @throws TalkingPalException If input is empty or command is unknown.
     */
    public static Command parseCommand(String userInput) throws TalkingPalException {
        String trimmed = requireNonBlank(userInput);
        String commandWord = trimmed.split(WHITESPACE_REGEX, 2)[0];
        return Command.parse(commandWord);
    }

    /**
     * Returns the arguments after the command word.
     *
     * @param userInput Raw user input.
     * @return Arguments string, or empty string if none.
     * @throws TalkingPalException If input is empty.
     */
    public static String getArguments(String userInput) throws TalkingPalException {
        String trimmed = requireNonBlank(userInput);
        String[] parts = trimmed.split(WHITESPACE_REGEX, 2);
        return (parts.length == 2) ? parts[1].trim() : "";
    }

    /**
     * Parses a task number from input.
     *
     * @param userInput Raw user input.
     * @return Task number.
     * @throws TalkingPalException If missing or invalid.
     */
    public static int parseTaskNumber(String userInput) throws TalkingPalException {
        String args = getArguments(userInput);
        if (args.isEmpty()) {
            throw new TalkingPalException("Please provide a task number.");
        }

        try {
            return Integer.parseInt(args);
        } catch (NumberFormatException e) {
            throw new TalkingPalException("Task number must be an integer.");
        }
    }

    private static String requireNonBlank(String userInput) throws TalkingPalException {
        if (userInput == null) {
            throw new TalkingPalException("Input cannot be null.");
        }

        String trimmed = userInput.trim();
        if (trimmed.isEmpty()) {
            throw new TalkingPalException("Input cannot be empty.");
        }

        return trimmed;
    }
}
