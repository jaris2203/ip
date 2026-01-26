public enum Command {
    MARK,
    UNMARK,
    LIST,
    BYE,
    TODO,
    EVENT,
    DEADLINE,
    DELETE;

    public static Command parse(String firstWord) throws TalkingPalException {
        if (firstWord == null || firstWord.trim().isEmpty()) {
            throw new TalkingPalException("Unfortunately, I cannot read your mind :<");
        }

        try {
            return Command.valueOf(firstWord.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new TalkingPalException("Unknown command: " + firstWord);
        }
    }
}
