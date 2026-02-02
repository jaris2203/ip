package talkingpal.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DeadlineTest {

    @Test
    public void inputToDeadlineTest() {
        LocalDate testTime = LocalDate.parse("2026-12-31");
        String testInput = "deadline Test Description /by Dec 31 2026";
        String testInput2 = "deadline Test Description /by 31 December 2026"; //Test other format
        Deadline control = new Deadline(
                "Test Description",
                testTime);
        try {
            assertEquals(
                    control.toString(),
                    Deadline.inputToDeadline(testInput).toString());
            assertEquals(
                    control.toString(),
                    Deadline.inputToDeadline(testInput2).toString());
        } catch (Exception e) {
            fail();
        }
    }
}
