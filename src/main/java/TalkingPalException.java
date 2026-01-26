public class TalkingPalException extends Exception {
    public TalkingPalException(String message) {
        super(message);
    }
}

class EmptyDescriptionException extends TalkingPalException {
    public EmptyDescriptionException(String command) {
        super("Description cannot be empty for command: " + command);
    }
}

class EmptyDateException extends TalkingPalException {
    public EmptyDateException(String command) {
        super("Date cannot be empty for command: " + command);
    }
}
