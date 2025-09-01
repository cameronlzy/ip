package rex.tasks;

import org.junit.jupiter.api.Test;
import seedu.rex.tasks.Task;
import seedu.rex.tasks.Todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void blankToDo() {
        assertEquals("[T][ ] read book", new Todo("read book").toString());
    }

    @Test
    public void markedToDo() {
        Task testToDo = new Todo("read book");
        testToDo.markDone();
        assertEquals("[T][X] read book", testToDo.toString());
    }
}
