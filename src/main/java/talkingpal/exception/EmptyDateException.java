package talkingpal.exception;

public class EmptyDateException extends TalkingPalException {
    public EmptyDateException(String command) {
        super("Date cannot be empty for command: " + command);
    }
}