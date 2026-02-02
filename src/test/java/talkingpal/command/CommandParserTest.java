package talkingpal.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Random;


public class CommandParserTest {

    private static final String[] TWO_WORD_COMMANDS = new String[]{"mark", "unmark", "delete"};

    @Test
    public void parseTaskNumberTest() {
        Random r = new Random();
        int testNo = r.nextInt(100); // Random integer from 0 - 100
        for (String cmd : TWO_WORD_COMMANDS) {
            try {
                String fullCmd = cmd + " " + testNo;
                assertEquals(testNo, CommandParser.parseTaskNumber(fullCmd));
            } catch (Exception e) {
                fail();
            }

        }
    }
}
