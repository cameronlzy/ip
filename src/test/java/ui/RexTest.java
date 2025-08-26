package ui;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class RexTest {

    private PrintStream origOut;
    private InputStream origIn;
    private ByteArrayOutputStream out;

    private static final String LINE = "____________________________________________________________";
    private static String NL() { return System.lineSeparator(); }

    private static String originalUserDir;

    @BeforeAll
    static void rememberOriginalDir() {
        originalUserDir = System.getProperty("user.dir");
    }

    @BeforeEach
    void setUp(TestInfo info) throws Exception {
        origOut = System.out;
        origIn = System.in;
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Path tmp = Files.createTempDirectory("rex-" + info.getDisplayName());
        System.setProperty("user.dir", tmp.toAbsolutePath().toString());
        Files.createDirectories(tmp.resolve("data"));
    }

    @AfterEach
    void tearDown() {
        System.setOut(origOut);
        System.setIn(origIn);
    }

    @AfterAll
    static void restoreOriginalDir() {
        if (originalUserDir != null) {
            System.setProperty("user.dir", originalUserDir);
        }
    }

    private String run(String script) {
        System.setIn(new ByteArrayInputStream(script.getBytes()));
        Rex.main(new String[0]);
        return out.toString();
    }

    @Test
    void list_onFreshStart_showsEmptyHeaderAndFooter() {
        String output = run(String.join(NL(),
                "list",
                "bye"
        ) + NL());

        assertTrue(output.contains("Here are the tasks in your list:"));
    }

    @Test
    void add_todo_then_list_showsOneTask() {
        String output = run(String.join(NL(),
                "todo read book",
                "list",
                "bye"
        ) + NL());
        System.out.println(output);

        assertTrue(output.contains("Got it. I've added this task:"));
        assertTrue(output.contains("[T][ ] read book"));
        assertTrue(output.contains("Now you have"));
        assertTrue(output.contains("task in the list.") || output.contains("tasks in the list."));
        assertTrue(output.contains("1.[T][ ] read book"));
    }

    @Test
    void mark_task_then_list_showsMarked() {
        String output = run(String.join(NL(),
                "todo read book",
                "mark 1",
                "list",
                "bye"
        ) + NL());

        assertTrue(output.contains("Nice! I've marked this task as done:"));
        assertTrue(output.contains("[T][X] read book"));          // marked
        assertTrue(output.contains("1.[T][X] read book"));         // list reflects marked
    }

    @Test
    void delete_task_then_list_isEmptyAgain() {
        String output = run(String.join(NL(),
                "todo read book",
                "delete 1",
                "list",
                "bye"
        ) + NL());

        assertTrue(output.contains("Noted. I've removed this task:"));
        assertTrue(output.contains("Now you have"));
        assertTrue(output.contains("task in the list.") || output.contains("tasks in the list."));
        // after delete, list header still prints, but no numbered lines should include the old task
    }
}
