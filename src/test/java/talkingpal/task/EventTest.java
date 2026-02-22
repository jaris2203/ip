package talkingpal.task;

import org.junit.jupiter.api.Test;
import talkingpal.exception.EmptyDateException;
import talkingpal.exception.EmptyDescriptionException;
import talkingpal.exception.TalkingPalException;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    @Test
    public void parseEvent_validInput_normalisesWhitespaceAndExtractsFields() throws TalkingPalException {
        String input = "   event   project meeting   /from   2026-02-23   /to   2026-02-24   ";

        String[] parts = Event.parseEvent(input);

        assertArrayEquals(
                new String[] {"event", "project meeting", "2026-02-23", "2026-02-24"},
                parts
        );
    }

    @Test
    public void parseEvent_blankDescription_throwsEmptyDescriptionException() {
        String input = "event   /from 2026-02-23 /to 2026-02-24";

        assertThrows(EmptyDescriptionException.class, () -> Event.parseEvent(input));
    }

    @Test
    public void parseEvent_missingFromOrTo_throwsEmptyDateException() {
        String missingFrom = "event party /from   /to 2026-02-24";
        String missingTo = "event party /from 2026-02-23 /to   ";

        assertThrows(EmptyDateException.class, () -> Event.parseEvent(missingFrom));
        assertThrows(EmptyDateException.class, () -> Event.parseEvent(missingTo));
    }

    @Test
    public void parseEvent_invalidFormat_throwsTalkingPalException() {
        String input = "event party 2026-02-23 2026-02-24";

        assertThrows(TalkingPalException.class, () -> Event.parseEvent(input));
    }
}