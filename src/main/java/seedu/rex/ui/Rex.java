package seedu.rex.ui;

import seedu.rex.tasks.Task;
import seedu.rex.tasks.TaskList;
import seedu.rex.tasks.Todo;
import seedu.rex.tasks.Deadline;
import seedu.rex.tasks.Event;

import seedu.rex.utils.DateTimeUtil;
import seedu.rex.utils.Storage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code Rex} class represents the entry point for the chatbot application.
 * <p>
 * Rex is a simple task manager that supports different task types
 * (Todo, Deadline, Event). It can store tasks persistently to a file
 * and load them back when restarted.
 * <p>
 * Supported commands:
 * <ul>
 *   <li>{@code list} - Display all tasks</li>
 *   <li>{@code todo <description>} - Add a todo task</li>
 *   <li>{@code deadline <description> /by <date>} - Add a deadline task</li>
 *   <li>{@code event <description> /from <date> /to <date>} - Add an event task</li>
 *   <li>{@code mark <index>} - Mark a task as done</li>
 *   <li>{@code unmark <index>} - Mark a task as not done</li>
 *   <li>{@code delete <index>} - Delete a task</li>
 *   <li>{@code find <keyword>} - Find tasks containing keyword</li>
 *   <li>{@code bye} - Exit the program</li>
 * </ul>
 */
public class Rex {
    private static final Path DATA_PATH = Path.of("data", "rex.txt");
    private TaskList taskList;
    private boolean isRunning;

    /**
     * Constructor for Rex chatbot.
     * Initializes the task list by loading from storage or creating a new one.
     */
    public Rex() {
        try {
            List<Task> loaded = Storage.load(DATA_PATH);
            taskList = new TaskList(loaded);
        } catch (Exception e) {
            taskList = new TaskList();
        }
        isRunning = true;
    }

    /**
     * Prints a horizontal line for formatting.
     */
    private static void line() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Gets the welcome message for the chatbot.
     *
     * @return The welcome message string.
     */
    public String getWelcomeMessage() {
        return "Hello! I'm Rex\nWhat can I do for you?";
    }

    /**
     * Processes user input and returns the appropriate response.
     * Uses the existing UI methods by capturing their System.out.print output.
     *
     * @param input The user's input command.
     * @return The response string from Rex.
     */
    public String getResponse(String input) {
        assert input != null : "Input should never be null";

        String trimmedInput = input.trim();
        String lower = trimmedInput.toLowerCase();

        if (lower.equals("bye")) {
            isRunning = false;
            try {
                Storage.save(DATA_PATH, new ArrayList<>(taskList.asList()));
            } catch (Exception e) {
                return "Bye. Hope to see you again soon!\n(Warning: Error saving tasks)";
            }
            return "Bye. Hope to see you again soon!";

        } else if (lower.equals("list")) {
            if (taskList.asList().isEmpty()) {
                return "Your task list is empty!";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Here are the tasks in your list:\n");
            List<Task> tasks = taskList.asList();
            for (int i = 0; i < tasks.size(); i++) {
                sb.append((i + 1)).append(".").append(tasks.get(i)).append("\n");
            }
            return sb.toString().trim();

        } else if (lower.startsWith("delete")) {
            // FIX: Check length before substring
            if (trimmedInput.length() <= 7) {
                return "Invalid task number for delete.";
            }
            try {
                int idx = Integer.parseInt(trimmedInput.substring(7).trim());
                Task removed = taskList.delete(idx);
                return "Noted. I've removed this task:\n  " + removed +
                        "\nNow you have " + taskList.asList().size() +
                        (taskList.asList().size() == 1 ? " task" : " tasks") + " in the list.";
            } catch (Exception e) {
                return "Invalid task number for delete.";
            }

        } else if (lower.startsWith("mark ")) {
            // FIX: Check length before substring
            if (trimmedInput.length() <= 5) {
                return "Invalid task number for mark.";
            }
            try {
                int idx = Integer.parseInt(trimmedInput.substring(5).trim());
                Task t = taskList.get(idx);
                t.markDone();
                return "Nice! I've marked this task as done:\n  " + t;
            } catch (Exception e) {
                return "Invalid task number for mark.";
            }

        } else if (lower.startsWith("unmark ")) {
            // FIX: Check length before substring
            if (trimmedInput.length() <= 7) {
                return "Invalid task number for unmark.";
            }
            try {
                int idx = Integer.parseInt(trimmedInput.substring(7).trim());
                Task t = taskList.get(idx);
                t.markUndone();
                return "OK, I've marked this task as not done yet:\n  " + t;
            } catch (Exception e) {
                return "Invalid task number for unmark.";
            }

        } else if (lower.startsWith("todo ")) {
            // FIX: Check length before substring
            if (trimmedInput.length() <= 5) {
                return "Todo description cannot be empty!";
            }
            String desc = trimmedInput.substring(5).trim();
            if (desc.isEmpty()) {
                return "Todo description cannot be empty!";
            }
            Task t = new Todo(desc);
            taskList.add(t);
            return "Got it. I've added this task:\n  " + t +
                    "\nNow you have " + taskList.asList().size() +
                    (taskList.asList().size() == 1 ? " task" : " tasks") + " in the list.";

        } else if (lower.startsWith("deadline ")) {
            // FIX: Check length before substring
            if (trimmedInput.length() <= 9) {
                return "Usage: deadline <description> /by <yyyy-MM-dd[ HHmm]>";
            }
            String body = trimmedInput.substring(9).trim();
            int sep = body.indexOf(" /by ");
            if (sep < 0) {
                return "Usage: deadline <description> /by <yyyy-MM-dd[ HHmm]>";
            }
            String desc = body.substring(0, sep).trim();
            String byStr = body.substring(sep + 5).trim();
            if (desc.isEmpty()) {
                return "Deadline description cannot be empty!";
            }
            try {
                Task t = new Deadline(desc, DateTimeUtil.parseFlexible(byStr));
                taskList.add(t);
                return "Got it. I've added this task:\n  " + t +
                        "\nNow you have " + taskList.asList().size() +
                        (taskList.asList().size() == 1 ? " task" : " tasks") + " in the list.";
            } catch (Exception e) {
                return "Invalid date/time. Try formats like 2019-12-02 1800 or 2/12/2019 1800.";
            }

        } else if (lower.startsWith("event ")) {
            // FIX: Check length before substring
            if (trimmedInput.length() <= 6) {
                return "Usage: event <desc> /from <yyyy-MM-dd[ HHmm]> /to <yyyy-MM-dd[ HHmm]>";
            }
            String body = trimmedInput.substring(6).trim();
            int fromIdx = body.indexOf(" /from ");
            int toIdx = body.indexOf(" /to ");
            if (fromIdx < 0 || toIdx < 0 || toIdx <= fromIdx) {
                return "Usage: event <desc> /from <yyyy-MM-dd[ HHmm]> /to <yyyy-MM-dd[ HHmm]>";
            }
            String desc = body.substring(0, fromIdx).trim();
            String fromStr = body.substring(fromIdx + 7, toIdx).trim();
            String toStr = body.substring(toIdx + 5).trim();
            if (desc.isEmpty()) {
                return "Event description cannot be empty!";
            }
            try {
                Task t = new Event(desc, DateTimeUtil.parseFlexible(fromStr), DateTimeUtil.parseFlexible(toStr));
                taskList.add(t);
                return "Got it. I've added this task:\n  " + t +
                        "\nNow you have " + taskList.asList().size() +
                        (taskList.asList().size() == 1 ? " task" : " tasks") + " in the list.";
            } catch (Exception e) {
                return "Invalid date/time for event. Use 2019-12-02 1800 or 2/12/2019 1800.";
            }

        } else if (lower.startsWith("find")) {
            String body = trimmedInput.length() >= 5 ? trimmedInput.substring(5).trim() : "";
            if (body.isEmpty()) {
                return "Usage: find <keyword>";
            }
            String query = body.toLowerCase();
            StringBuilder sb = new StringBuilder();
            sb.append("Here are the matching tasks in your list:\n");
            int count = 0;
            for (Task t : taskList.asList()) {
                String desc = t.getDescription();
                if (desc != null && desc.toLowerCase().contains(query)) {
                    sb.append(++count).append(".").append(t).append("\n");
                }
            }
            if (count == 0) {
                return "No matching tasks found.";
            }
            return sb.toString().trim();

        } else {
            return "Unknown command. Try 'list', 'todo', 'deadline', 'event', 'mark', 'unmark', 'delete', 'find', or 'bye'.";
        }
    }

    /**
     * Checks if the chatbot is still running.
     *
     * @return True if the chatbot should continue running, false otherwise.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Entry point for the Rex chatbot.
     * This method now uses the getResponse method for processing commands.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Rex rex = new Rex();

        line();
        System.out.println("     " + rex.getWelcomeMessage().replace("\n", "\n     "));
        line();

        while (rex.isRunning()) {
            String input = sc.nextLine();
            String response = rex.getResponse(input);

            line();
            // Format multi-line responses with proper indentation
            printIndented(response.split("\n"));
            line();
        }

        sc.close();
    }

    private static void printIndented(String... lines) {
        line();
        for (String l : lines) {
            System.out.println("     " + l);
        }
        line();
    }
}
