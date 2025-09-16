package rex.tasks;

import org.junit.jupiter.api.Test;
import seedu.rex.tasks.Event;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Used ChatGPT to generate JavaDocs.
 *
 * Unit tests for the {@link seedu.rex.tasks.Event} class.
 */
public class EventTest {

    /**
     * Tests that a newly created Event has the correct description
     * and time range, and is unmarked by default.
     */
    @Test
    public void blankEvent() {
        LocalDateTime from = LocalDateTime.of(2019, 12, 2, 14, 0);
        LocalDateTime to = LocalDateTime.of(2019, 12, 2, 16, 0);
        Event e = new Event("project meeting", from, to);
        assertEquals(
                "[E][ ] project meeting (from: Dec 2 2019, 2:00pm to: Dec 2 2019, 4:00pm)",
                e.toString()
        );
    }

    /**
     * Tests that an Event can be marked done and that
     * its string representation updates correctly.
     */
    @Test
    public void markedEvent() {
        LocalDateTime from = LocalDateTime.of(2019, 12, 2, 14, 0);
        LocalDateTime to = LocalDateTime.of(2019, 12, 2, 16, 0);
        Event e = new Event("project meeting", from, to);
        e.markDone();
        assertEquals(
                "[E][X] project meeting (from: Dec 2 2019, 2:00pm to: Dec 2 2019, 4:00pm)",
                e.toString()
        );
    }
}
