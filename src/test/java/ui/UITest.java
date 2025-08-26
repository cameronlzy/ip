package ui;

import org.junit.jupiter.api.*;
import tasks.Todo;
import tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UITest {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream out;

    private static final String LINE = "____________________________________________________________";

    @BeforeEach
    void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private String capture() {
        return out.toString();
    }

    private static String L() { return System.lineSeparator(); }

    @Test
    void testLine() {
        UI.line();
        assertEquals(LINE + L(), capture());
    }

    @Test
    void testGreet() {
        UI.greet();
        String expected = String.join(L(),
                LINE,
                "     Hello! I'm Rex",
                "     What can I do for you?",
                LINE
        ) + L();
        assertEquals(expected, capture());
    }

    @Test
    void testBye() {
        UI.bye();
        String expected = String.join(L(),
                LINE,
                "     Bye. Hope to see you again soon!",
                LINE
        ) + L();
        assertEquals(expected, capture());
    }

    @Test
    void testList() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("read book"));
        tasks.add(new Todo("write report"));

        UI.list(tasks);

        String expected = String.join(L(),
                LINE,
                "     Here are the tasks in your list:",
                "     1.[T][ ] read book",
                "     2.[T][ ] write report",
                LINE
        ) + L();

        assertEquals(expected, capture());
    }

    @Test
    void testAdded() {
        List<Task> tasks = new ArrayList<>();
        Task t = new Todo("read book");
        tasks.add(t);

        UI.added(tasks, t);

        String expected = String.join(L(),
                LINE,
                "     Got it. I've added this task:",
                "       " + t,
                "     Now you have 1 task in the list.",
                LINE
        ) + L();

        assertEquals(expected, capture());
    }

    @Test
    void testMarkedDone() {
        Task t = new Todo("read book");
        t.markDone();

        UI.marked(t, true);

        String expected = String.join(L(),
                LINE,
                "     Nice! I've marked this task as done:",
                "       " + t,
                LINE
        ) + L();

        assertEquals(expected, capture());
    }

    @Test
    void testMarkedUndone() {
        Task t = new Todo("read book");
        t.markUndone();

        UI.marked(t, false);

        String expected = String.join(L(),
                LINE,
                "     OK, I've marked this task as not done yet:",
                "       " + t,
                LINE
        ) + L();

        assertEquals(expected, capture());
    }

    @Test
    void testDeleted() {
        List<Task> tasks = new ArrayList<>();
        Task t1 = new Todo("read book");
        Task t2 = new Todo("write report");
        tasks.add(t1);
        tasks.add(t2);

        // simulate removal of t2
        tasks.remove(1);
        UI.deleted(tasks, t2);

        String expected = String.join(L(),
                LINE,
                "     Noted. I've removed this task:",
                "       " + t2,
                "     Now you have 1 task in the list.",
                LINE
        ) + L();

        assertEquals(expected, capture());
    }

    @Test
    void testInvalidDeleteIndex() {
        UI.invalidDeleteIndex();
        assertEquals("Invalid task number for delete." + L(), capture());
    }

    @Test
    void testInvalidMarkIndex() {
        UI.invalidMarkIndex();
        assertEquals("Invalid task number for mark." + L(), capture());
    }

    @Test
    void testInvalidUnmarkIndex() {
        UI.invalidUnmarkIndex();
        assertEquals("Invalid task number for unmark." + L(), capture());
    }

    @Test
    void testUsageDeadline() {
        UI.usageDeadline();
        assertEquals("Usage: deadline <description> /by <yyyy-MM-dd[ HHmm]>" + L(), capture());
    }

    @Test
    void testInvalidDeadlineDate() {
        UI.invalidDeadlineDate();
        assertEquals("Invalid date/time. Try formats like 2019-12-02 1800 or 2/12/2019 1800." + L(), capture());
    }

    @Test
    void testUsageEvent() {
        UI.usageEvent();
        assertEquals("Usage: event <desc> /from <yyyy-MM-dd[ HHmm]> /to <yyyy-MM-dd[ HHmm]>" + L(), capture());
    }

    @Test
    void testInvalidEventDate() {
        UI.invalidEventDate();
        assertEquals("Invalid date/time for event. Use 2019-12-02 1800 or 2/12/2019 1800." + L(), capture());
    }

    @Test
    void testUnknownCommand() {
        UI.unknownCommand();
        assertEquals("Unknown command." + L(), capture());
    }

    @Test
    void testSavingError() {
        UI.savingError();
        assertEquals("Error Saving" + L(), capture());
    }
}
