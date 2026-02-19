package talkingpal.command;

import talkingpal.exception.TalkingPalException;

/**
 * Represents the supported user commands in TalkingPal.
 */
public enum Command {
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    DELETE("delete"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    BYE("bye"),
    FIND("find"),
    UNDO("undo");

    private final String keyword;

    Command(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Parses a command word into a {@code Command}.
     *
     * @param commandWord The first word of the user input.
     * @return The corresponding {@code Command}.
     * @throws TalkingPalException If the command word is null/blank or unknown.
     */
    public static Command parse(String commandWord) throws TalkingPalException {
        if (commandWord == null) {
            throw new TalkingPalException("Command cannot be null.");
        }
        String trimmed = commandWord.trim();
        if (trimmed.isEmpty()) {
            throw new TalkingPalException("Command cannot be empty.");
        }
        String lowered = trimmed.toLowerCase();

        for (Command command : Command.values()) {
            if (command.keyword.equals(lowered)) {
                return command;
            }
        }
        throw new TalkingPalException("Unknown command: " + commandWord);
    }

}
