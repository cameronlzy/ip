package rex.tasks;

import org.junit.jupiter.api.Test;
import seedu.rex.tasks.Deadline;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Used ChatGPT to generate JavaDocs.
 *
 * Unit tests for the {@link seedu.rex.tasks.Deadline} class.
 */
public class DeadlineTest {

    /**
     * Tests that a newly created Deadline is not marked done
     * and its string output matches the expected format.
     */
    @Test
    public void blankDeadline() {
        LocalDateTime by = LocalDateTime.of(2019, 12, 2, 18, 0);
        Deadline d = new Deadline("return book", by);
        assertEquals("[D][ ] return book (by: Dec 2 2019, 6:00pm)", d.toString());
    }

    /**
     * Tests that marking a Deadline as done changes its
     * string output to include an "X" marker.
     */
    @Test
    public void markedDeadline() {
        LocalDateTime by = LocalDateTime.of(2019, 12, 2, 18, 0);
        Deadline d = new Deadline("return book", by);
        d.markDone();
        assertEquals("[D][X] return book (by: Dec 2 2019, 6:00pm)", d.toString());
    }
}
