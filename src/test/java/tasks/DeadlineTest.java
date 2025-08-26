package tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void blankDeadline() {
        LocalDateTime by = LocalDateTime.of(2019, 12, 2, 18, 0);
        Deadline d = new Deadline("return book", by);
        assertEquals("[D][ ] return book (by: Dec 2 2019, 6:00pm)", d.toString());
    }

    @Test
    public void markedDeadline() {
        LocalDateTime by = LocalDateTime.of(2019, 12, 2, 18, 0);
        Deadline d = new Deadline("return book", by);
        d.markDone();
        assertEquals("[D][X] return book (by: Dec 2 2019, 6:00pm)", d.toString());
    }
}
