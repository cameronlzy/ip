package tasks;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    public void blankEvent() {
        LocalDateTime from = LocalDateTime.of(2019, 12, 2, 14, 0); // 2019-12-02 14:00
        LocalDateTime to = LocalDateTime.of(2019, 12, 2, 16, 0);   // 2019-12-02 16:00
        Event e = new Event("project meeting", from, to);
        assertEquals(
                "[E][ ] project meeting (from: Dec 2 2019, 2:00pm to: Dec 2 2019, 4:00pm)",
                e.toString()
        );
    }

    @Test
    public void markedEvent() {
        LocalDateTime from = LocalDateTime.of(2019, 12, 2, 14, 0); // 2019-12-02 14:00
        LocalDateTime to = LocalDateTime.of(2019, 12, 2, 16, 0);   // 2019-12-02 16:00
        Event e = new Event("project meeting", from, to);
        e.markDone();
        assertEquals(
                "[E][X] project meeting (from: Dec 2 2019, 2:00pm to: Dec 2 2019, 4:00pm)",
                e.toString()
        );
    }

}
