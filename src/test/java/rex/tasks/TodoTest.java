package rex.tasks;

import org.junit.jupiter.api.Test;
import seedu.rex.tasks.Task;
import seedu.rex.tasks.Todo;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Used ChatGPT to generate JavaDocs.
 *
 * Unit tests for the {@link seedu.rex.tasks.Todo} class.
 */
public class TodoTest {

    /**
     * Tests that a new Todo is unmarked and its string output
     * matches the expected format.
     */
    @Test
    public void blankToDo() {
        assertEquals("[T][ ] read book", new Todo("read book").toString());
    }

    /**
     * Tests that marking a Todo as done updates its string
     * output with an "X" marker.
     */
    @Test
    public void markedToDo() {
        Task testToDo = new Todo("read book");
        testToDo.markDone();
        assertEquals("[T][X] read book", testToDo.toString());
    }
}
