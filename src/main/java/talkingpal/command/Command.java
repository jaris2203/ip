package talkingpal.command;

import talkingpal.exception.TalkingPalException;

public enum Command {
    MARK,
    UNMARK,
    LIST,
    BYE,
    TODO,
    EVENT,
    DEADLINE,
    DELETE;

    public static Command parse(String input) throws TalkingPalException {
        String firstWord = getFirstWord(input);
        if (firstWord.trim().isEmpty()) {
            throw new TalkingPalException("Unfortunately, I cannot read your mind :<");
        }
        try {
            return Command.valueOf(firstWord.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new TalkingPalException("Unknown command: " + firstWord);
        }
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
}
