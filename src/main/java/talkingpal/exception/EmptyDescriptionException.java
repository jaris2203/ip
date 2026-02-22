package talkingpal.exception;

public class EmptyDescriptionException extends TalkingPalException {
    public EmptyDescriptionException(String command) {
        super("Description cannot be empty for command: " + command + "\n");
    }
}