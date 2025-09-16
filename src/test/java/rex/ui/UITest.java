package rex.ui;

import org.junit.jupiter.api.*;
import seedu.rex.tasks.Todo;
import seedu.rex.tasks.Task;
import seedu.rex.ui.UI;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Used ChatGPT to generate JavaDocs.
 *
 * Unit tests for the static methods in the {@link seedu.rex.ui.UI} class,
 * which handle console output formatting.
 */
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


    /**
     * Tests that UI.line() prints the horizontal line separator.
     */
    @Test
    void testLine() {
        UI.line();
        assertEquals(LINE + L(), capture());
    }

    /**
     * Tests that UI.greet() prints the expected greeting.
     */
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

    /**
     * Tests that UI.bye() prints the expected farewell message.
     */
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

    /**
     * Tests that UI.list() prints a numbered list of tasks.
     */
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

    /**
     * Tests that UI.added() prints the confirmation after adding
     * a task to the list.
     */
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

    /**
     * Tests that calling UI.marked() with a completed task
     * prints the correct "marked as done" message.
     */
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

    /**
     * Tests that calling UI.marked() with an undone task
     * prints the correct "not done yet" message.
     */
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

    /**
     * Tests that UI.deleted() prints the confirmation message
     * after removing a task and updates the task count.
     */
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

    /**
     * Tests that UI.invalidDeleteIndex() prints the correct error message
     * when an invalid delete index is provided.
     */
    @Test
    void testInvalidDeleteIndex() {
        UI.invalidDeleteIndex();
        assertEquals("Invalid task number for delete." + L(), capture());
    }

    /**
     * Tests that UI.invalidMarkIndex() prints the correct error message
     * when an invalid mark index is provided.
     */
    @Test
    void testInvalidMarkIndex() {
        UI.invalidMarkIndex();
        assertEquals("Invalid task number for mark." + L(), capture());
    }

    /**
     * Tests that UI.invalidUnmarkIndex() prints the correct error message
     * when an invalid unmark index is provided.
     */
    @Test
    void testInvalidUnmarkIndex() {
        UI.invalidUnmarkIndex();
        assertEquals("Invalid task number for unmark." + L(), capture());
    }

    /**
     * Tests that UI.usageDeadline() prints the correct usage
     * instructions for the deadline command.
     */
    @Test
    void testUsageDeadline() {
        UI.usageDeadline();
        assertEquals("Usage: deadline <description> /by <yyyy-MM-dd[ HHmm]>" + L(), capture());
    }

    /**
     * Tests that UI.invalidDeadlineDate() prints the correct error
     * message when the deadline date format is invalid.
     */
    @Test
    void testInvalidDeadlineDate() {
        UI.invalidDeadlineDate();
        assertEquals("Invalid date/time. Try formats like 2019-12-02 1800 or 2/12/2019 1800." + L(), capture());
    }

    /**
     * Tests that UI.usageEvent() prints the correct usage
     * instructions for the event command.
     */
    @Test
    void testUsageEvent() {
        UI.usageEvent();
        assertEquals("Usage: event <desc> /from <yyyy-MM-dd[ HHmm]> /to <yyyy-MM-dd[ HHmm]>" + L(), capture());
    }

    /**
     * Tests that UI.invalidEventDate() prints the correct error
     * message when the event date format is invalid.
     */
    @Test
    void testInvalidEventDate() {
        UI.invalidEventDate();
        assertEquals("Invalid date/time for event. Use 2019-12-02 1800 or 2/12/2019 1800." + L(), capture());
    }

    /**
     * Tests that UI.unknownCommand() prints the correct message
     * for an unrecognized command.
     */
    @Test
    void testUnknownCommand() {
        UI.unknownCommand();
        assertEquals("Unknown command." + L(), capture());
    }

    /**
     * Tests that UI.savingError() prints the correct error message
     * when a saving operation fails.
     */
    @Test
    void testSavingError() {
        UI.savingError();
        assertEquals("Error Saving" + L(), capture());
    }
}
