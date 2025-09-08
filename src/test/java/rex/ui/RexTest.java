package rex.ui;

import org.junit.jupiter.api.*;
import seedu.rex.ui.Rex;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class RexTest {
    private static String originalUserDir;
    private Path tempDir;
    private Rex rex;

    @BeforeAll
    static void rememberOriginalDir() {
        originalUserDir = System.getProperty("user.dir");
    }

    @BeforeEach
    void setUp(TestInfo info) throws Exception {
        // Create a unique temp directory for each test
        tempDir = Files.createTempDirectory("rex-test-" + info.getDisplayName().replaceAll("[^a-zA-Z0-9]", ""));
        System.setProperty("user.dir", tempDir.toAbsolutePath().toString());

        // Create data directory
        Files.createDirectories(tempDir.resolve("data"));

        // Create fresh Rex instance for each test
        rex = new Rex();
    }

    @AfterEach
    void tearDown() throws Exception {
        // Clean up temp directory completely
        if (tempDir != null && Files.exists(tempDir)) {
            deleteDirectory(tempDir);
        }

        // Restore original directory
        System.setProperty("user.dir", originalUserDir);
    }

    @AfterAll
    static void restoreOriginalDir() {
        if (originalUserDir != null) {
            System.setProperty("user.dir", originalUserDir);
        }
    }

    /**
     * Recursively delete a directory and all its contents
     */
    private void deleteDirectory(Path dir) throws Exception {
        if (Files.exists(dir)) {
            Files.walk(dir)
                    .sorted((a, b) -> b.compareTo(a)) // Delete files before directories
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            // Ignore deletion errors during cleanup
                        }
                    });
        }
    }

    @Test
    void mark_task_then_list_showsMarked() {
        // Add a task first
        rex.getResponse("todo read book");

        // Mark it as done
        String markResponse = rex.getResponse("mark 1");
        assertTrue(markResponse.contains("Nice! I've marked this task as done:"));
        assertTrue(markResponse.contains("[T][X] read book"));

        // Verify it shows as marked in list
        String listResponse = rex.getResponse("list");
        assertTrue(listResponse.contains("1.[T][X] read book"));
    }

    @Test
    void unmark_task_then_list_showsUnmarked() {
        // Add and mark a task
        rex.getResponse("todo read book");
        rex.getResponse("mark 1");

        // Unmark it
        String unmarkResponse = rex.getResponse("unmark 1");
        assertTrue(unmarkResponse.contains("OK, I've marked this task as not done yet:"));
        assertTrue(unmarkResponse.contains("[T][ ] read book"));

        // Verify it shows as unmarked in list
        String listResponse = rex.getResponse("list");
        assertTrue(listResponse.contains("1.[T][ ] read book"));
    }


    @Test
    void add_deadline_task() {
        String response = rex.getResponse("deadline submit assignment /by 2024-12-25 2359");
        assertTrue(response.contains("Got it. I've added this task:"));
        assertTrue(response.contains("[D][ ] submit assignment"));
        assertTrue(response.contains("Now you have"));
        assertTrue(response.contains("in the list"));
    }

    @Test
    void add_event_task() {
        String response = rex.getResponse("event project meeting /from 2024-12-20 1400 /to 2024-12-20 1600");
        assertTrue(response.contains("Got it. I've added this task:"));
        assertTrue(response.contains("[E][ ] project meeting"));
        assertTrue(response.contains("Now you have"));
        assertTrue(response.contains("in the list"));
    }

    @Test
    void find_tasks_by_keyword() {
        // Add multiple tasks
        rex.getResponse("todo read book");
        rex.getResponse("todo read newspaper");
        rex.getResponse("todo buy groceries");

        // Find tasks containing "read"
        String findResponse = rex.getResponse("find read");
        assertTrue(findResponse.contains("Here are the matching tasks in your list:"));
        assertTrue(findResponse.contains("read book"));
        assertTrue(findResponse.contains("read newspaper"));
        assertFalse(findResponse.contains("buy groceries"));
    }

    @Test
    void invalid_commands_show_error_messages() {
        // Invalid mark index
        String markResponse = rex.getResponse("mark 99");
        assertTrue(markResponse.contains("Invalid task number for mark"));

        // Invalid delete index
        String deleteResponse = rex.getResponse("delete 99");
        assertTrue(deleteResponse.contains("Invalid task number for delete"));

        // Invalid deadline format
        String deadlineResponse = rex.getResponse("deadline task without by");
        assertTrue(deadlineResponse.contains("Usage: deadline"));

        // Invalid event format
        String eventResponse = rex.getResponse("event task without from to");
        assertTrue(eventResponse.contains("Usage: event"));
    }

    @Test
    void bye_command_stops_running() {
        assertTrue(rex.isRunning());

        String byeResponse = rex.getResponse("bye");
        assertTrue(byeResponse.contains("Bye. Hope to see you again soon!"));
        assertFalse(rex.isRunning());
    }

    @Test
    void unknown_command_shows_help() {
        String response = rex.getResponse("invalidcommand");
        System.out.println((response));
        assertTrue(response.contains("Unknown command"));
        assertTrue(response.contains("Try 'list', 'todo'"));
    }
}
