package talkingpal.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import talkingpal.exception.TalkingPalException;
import talkingpal.task.*;

/**
 * Handles loading and storing of task list
 */
public class Storage {
    private static final Pattern LINE_PATTERN =
            Pattern.compile("^\\[(?<type>[TDE])\\]\\[(?<done>[ X])\\]\\s*(?<body>.+)$");
    private static final Path SAVE_PATH = Paths.get("data", "TalkingPal.txt");

    /**
     * Saves ongoing task list as a text file in designated path
     *
     * @throws IOException when file path not configured properly
     */
    public static void saveTasks(TaskList taskList) throws IOException {
        String fullText = taskList.toString();
        int newlineIndex = fullText.indexOf('\n');
        if (newlineIndex == -1) {
            return;
        }
        String content = fullText.substring(newlineIndex + 1); //remove first line of tasks summary
        Files.createDirectories(SAVE_PATH.getParent()); // ensure data/ exists
        Files.writeString(SAVE_PATH, content, StandardCharsets.UTF_8);
        System.out.println("Saved tasks to: " + SAVE_PATH.toAbsolutePath());
    }

    /**
     * Initialises task list from input file.
     *
     *
     * @return {@code TaskList} with existing tasks, or empty {@code TaskList} if no input file
     */
    public static TaskList createNewTasklist() {
        TaskList output = new TaskList();
        boolean isLoaded = true;

        Path inputPath = Paths.get("data", "TalkingPal.txt"); // ip/data/TalkingPal.txt
        try (BufferedReader br = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                // process each line
                if (line.trim().isEmpty()) {
                    continue;
                }
                output.add(Storage.parseTaskLine(line));
            }
        } catch (NoSuchFileException e) {
            System.err.println("File not found: " + inputPath.toAbsolutePath());
            isLoaded = false;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            isLoaded = false;
        } catch (TalkingPalException e) {
            System.err.println("Parse error: " + e.getMessage());
        }
        if (isLoaded) {
            System.out.println("Loaded data from TalkingPal.txt file!");
        }
        return output;
    }
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

        return new Deadline(description, DateParser.parse(by), isDone);
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

        return new Event(
                description,
                DateParser.parse(from),
                DateParser.parse(to),
                isDone);
    }
}
