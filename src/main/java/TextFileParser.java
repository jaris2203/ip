import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses lines in the saved tasks text format into {@code Task} objects.
 */
public class TextFileParser {
    private static final Pattern LINE_PATTERN =
            Pattern.compile("^\\[(?<type>[TDE])\\]\\[(?<done>[ X])\\]\\s*(?<body>.+)$");

    /**
     * Parses a single saved task line into a {@code Task}.
     *
     * @param rawLine The raw line read from the save file.
     * @return The parsed {@code Task}.
     * @throws TalkingPalException If the line format is invalid.
     */
    public static Task parseTaskLine(String rawLine) throws TalkingPalException {
        String line = rawLine.trim();

        if (line.isEmpty()) {
            throw new TalkingPalException("Empty line cannot be parsed.");
        }

        // Remove numbering
        line = line.replaceFirst("^\\d+\\.\\s*", "");

        Matcher matcher = LINE_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new TalkingPalException("Invalid task format: " + rawLine);
        }

        char type = matcher.group("type").charAt(0);
        boolean isDone = "X".equals(matcher.group("done"));
        String body = matcher.group("body").trim();

        if (type == 'T') {
            return new Todo(body, isDone);
        }

        if (type == 'D') {
            return parseDeadline(body, isDone);
        }

        if (type == 'E') {
            return parseEvent(body, isDone);
        }
        throw new TalkingPalException("Unknown task type: " + type);
    }

    private static Task parseDeadline(String body, boolean isDone) throws TalkingPalException {
        String marker = " (by: ";
        if (!body.endsWith(")") || !body.contains(marker)) {
            throw new TalkingPalException("Invalid deadline format: " + body);
        }

        int markerIndex = body.lastIndexOf(marker);
        String description = body.substring(0, markerIndex).trim();
        String by = body.substring(markerIndex + marker.length(), body.length() - 1).trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new TalkingPalException("Deadline description/by cannot be empty: " + body);
        }

        return new Deadline(description, by, isDone);
    }

    private static Task parseEvent(String body, boolean isDone) throws TalkingPalException {
        String marker = " (from: ";
        if (!body.endsWith(")") || !body.contains(marker)) {
            throw new TalkingPalException("Invalid event format: " + body);
        }

        int markerIndex = body.lastIndexOf(marker);
        String description = body.substring(0, markerIndex).trim();
        String inside = body.substring(markerIndex + marker.length(), body.length() - 1).trim();

        String toMarker = " to: ";
        int toIndex = inside.indexOf(toMarker);
        if (toIndex < 0) {
            throw new TalkingPalException("Invalid event format (missing 'to:'): " + body);
        }

        String from = inside.substring(0, toIndex).trim();
        String to = inside.substring(toIndex + toMarker.length()).trim();

        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new TalkingPalException("Event description/from/to cannot be empty: " + body);
        }

        return new Event(description, from, to, isDone);
    }
}
