package talkingpal.util;

import talkingpal.exception.TalkingPalException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

/**
 * Parses date strings into {@code LocalDate} objects and formats dates for storage.
 */
public class DateParser {
    private static final Locale LOCALE = Locale.ENGLISH;

    /** Storage format */
    private static final DateTimeFormatter STORAGE_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy").withLocale(LOCALE);  // To use for data file parsing too

    private static final List<DateTimeFormatter> ACCEPTED_INPUT_FORMATS = List.of(
            STORAGE_FORMATTER, // Feb 02 2026
            DateTimeFormatter.ISO_LOCAL_DATE, // 2026-02-02
            DateTimeFormatter.ofPattern("d/M/yyyy").withLocale(LOCALE), // 2/2/2026
            DateTimeFormatter.ofPattern("d-M-yyyy").withLocale(LOCALE), // 2-2-2026
            DateTimeFormatter.ofPattern("d MMM yyyy").withLocale(LOCALE), // 2 Feb 2026
            DateTimeFormatter.ofPattern("d MMMM yyyy").withLocale(LOCALE) // 2 February 2026
    );

    private DateParser() {
        // Prevent instantiation of "static" class
    }

    /**
     * Parses the given date string into a {@code LocalDate}.
     *
     * @param rawDate A date string (e.g., "Feb 02 2026" or "2026-02-02").
     * @return The parsed {@code LocalDate}.
     * @throws TalkingPalException If the input is null/blank or cannot be parsed.
     */
    public static LocalDate parse(String rawDate) throws TalkingPalException {
        if (rawDate == null) {
            throw new TalkingPalException("Date cannot be null.");
        }

        String date = rawDate.trim();
        if (date.isEmpty()) {
            throw new TalkingPalException("Date cannot be empty.");
        }

        for (DateTimeFormatter formatter : ACCEPTED_INPUT_FORMATS) {
            try {
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException ignored) {

            }
        }

        throw new TalkingPalException("Invalid date: " + rawDate + ". Use format like 'Feb 02 2026'.");
    }

    /**
     * Formats a {@code LocalDate} into {@code MMM dd yyyy} for saving to output file.
     *
     * @param date The date to format.
     * @return Formatted date string (e.g., "Feb 02 2026").
     */
    public static String formatForStorage(LocalDate date) {
        return date.format(STORAGE_FORMATTER);
    }
}
