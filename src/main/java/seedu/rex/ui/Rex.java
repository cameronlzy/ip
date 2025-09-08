package seedu.rex.ui;

import seedu.rex.tasks.Task;
import seedu.rex.tasks.TaskList;
import seedu.rex.tasks.Todo;
import seedu.rex.tasks.Deadline;
import seedu.rex.tasks.Event;

import seedu.rex.utils.DateTimeUtil;
import seedu.rex.utils.Parser;
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

    // Messages to avoid magic strings
    private static final String UNKNOWN_COMMAND_MSG =
            "Unknown command. Try 'list', 'todo', 'deadline', 'event', 'mark', 'unmark', 'delete', 'find', or 'bye'.";

    private static final String ERR_DELETE_IDX = "Invalid task number for delete.";
    private static final String ERR_MARK_IDX   = "Invalid task number for mark.";
    private static final String ERR_UNMARK_IDX = "Invalid task number for unmark.";
    private static final String ERR_TODO_EMPTY = "Todo description cannot be empty!";
    private static final String ERR_FIND_USAGE = "Usage: find <keyword>";

    private static final String USAGE_DEADLINE =
            "Usage: deadline <description> /by <yyyy-MM-dd[ HHmm]>";
    private static final String USAGE_EVENT =
            "Usage: event <desc> /from <yyyy-MM-dd[ HHmm]> /to <yyyy-MM-dd[ HHmm]>";

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
     * Exits the chatbot, saves the current task list, and returns a farewell message.
     *
     * @return the farewell message string
     */
    private String handleBye() {
        isRunning = false;
        try {
            Storage.save(DATA_PATH, new ArrayList<>(taskList.asList()));
        } catch (Exception e) {
            return "Bye. Hope to see you again soon!\n(Warning: Error saving tasks)";
        }
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Handles the {@code list} command by returning all tasks in the task list.
     *
     * @return a formatted string of all tasks, or a message if the list is empty
     */
    private String handleList() {
        List<Task> tasks = taskList.asList();
        if (tasks.isEmpty()) return "Your task list is empty!";
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Handles the {@code delete} command by removing a task at the specified index.
     *
     * @param args the argument string containing the index
     * @return a confirmation message if deletion is successful,
     *         or an error message if the index is invalid
     */
    private String handleDelete(String args) {
        Integer idx = safeParseIndex(args);
        if (idx == null) return ERR_DELETE_IDX;
        try {
            Task removed = taskList.delete(idx);
            return "Noted. I've removed this task:\n  " + removed +
                    "\nNow you have " + pluralizeTasks(taskList.asList().size()) + " in the list.";
        } catch (Exception e) {
            return ERR_DELETE_IDX;
        }
    }

    /**
     * Handles the {@code mark} command by marking a task as done.
     *
     * @param args the argument string containing the index
     * @return a success message if marking succeeds, or an error message otherwise
     */
    private String handleMark(String args) {
        Integer idx = safeParseIndex(args);
        if (idx == null) return ERR_MARK_IDX;
        try {
            Task t = taskList.get(idx);
            t.markDone();
            return "Nice! I've marked this task as done:\n  " + t;
        } catch (Exception e) {
            return ERR_MARK_IDX;
        }
    }

    /**
     * Handles the {@code unmark} command by marking a task as not done.
     *
     * @param args the argument string containing the index
     * @return a success message if unmarking succeeds, or an error message otherwise
     */
    private String handleUnmark(String args) {
        Integer idx = safeParseIndex(args);
        if (idx == null) return ERR_UNMARK_IDX;
        try {
            Task t = taskList.get(idx);
            t.markUndone();
            return "OK, I've marked this task as not done yet:\n  " + t;
        } catch (Exception e) {
            return ERR_UNMARK_IDX;
        }
    }

    /**
     * Handles the {@code todo} command by creating a new {@link Todo} task.
     *
     * @param args the argument string containing the task description
     * @return a confirmation message, or an error message if description is empty
     */
    private String handleTodo(String args) {
        if (args.isEmpty()) return ERR_TODO_EMPTY;
        return addAndAcknowledge(new Todo(args));
    }

    /**
     * Handles the {@code deadline} command by creating a new {@link Deadline} task.
     *
     * @param args the argument string containing the description and deadline
     * @return a confirmation message, or an error message if input is invalid
     */
    private String handleDeadline(String args) {
        try {
            String[] parts = Parser.parseDeadline(args); // [desc, byStr]
            String desc = parts[0];
            String byStr = parts[1];
            Task t = new Deadline(desc, DateTimeUtil.parseFlexible(byStr));
            return addAndAcknowledge(t);
        } catch (IllegalArgumentException e) {
            return e.getMessage() != null ? e.getMessage() : USAGE_DEADLINE;
        } catch (Exception e) {
            return "Invalid date/time. Try formats like 2019-12-02 1800 or 2/12/2019 1800.";
        }
    }

    /**
     * Handles the {@code event} command by creating a new {@link Event} task.
     *
     * @param args the argument string containing the description, start, and end
     * @return a confirmation message, or an error message if input is invalid
     */
    private String handleEvent(String args) {
        try {
            String[] parts = Parser.parseEvent(args); // [desc, fromStr, toStr]
            String desc    = parts[0];
            String fromStr = parts[1];
            String toStr   = parts[2];
            Task t = new Event(desc, DateTimeUtil.parseFlexible(fromStr), DateTimeUtil.parseFlexible(toStr));
            return addAndAcknowledge(t);
        } catch (IllegalArgumentException e) {
            return e.getMessage() != null ? e.getMessage() : USAGE_EVENT;
        } catch (Exception e) {
            return "Invalid date/time for event. Use 2019-12-02 1800 or 2/12/2019 1800.";
        }
    }

    /**
     * Handles the {@code find} command by searching for tasks containing a keyword.
     * Includes handling of partial text matchz
     * @param args the argument string containing the search keyword
     * @return a list of matching tasks, or an error/no-match message
     */
    private String handleFind(String args) {
        String q = args.trim();
        if (q.isEmpty()) return ERR_FIND_USAGE;
        String query = q.toLowerCase();
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        int count = 0;
        for (Task t : taskList.asList()) {
            String d = t.getDescription();
            if (d != null && d.toLowerCase().contains(query)) {
                sb.append(++count).append(".").append(t).append("\n");
            }
        }
        if (count == 0) return "No matching tasks found.";
        return sb.toString().trim();
    }

    /**
     * Adds a task to the task list and returns an acknowledgment message.
     *
     * @param t the task to add
     * @return a confirmation message including the new task and task count
     */
    private String addAndAcknowledge(Task t) {
        taskList.add(t);
        return "Got it. I've added this task:\n  " + t +
                "\nNow you have " + pluralizeTasks(taskList.asList().size()) + " in the list.";
    }

    /**
     * Formats the number of tasks into a pluralized string.
     *
     * @param n the number of tasks
     * @return a string like "1 task" or "3 tasks"
     */
    private static String pluralizeTasks(int n) {
        return n + (n == 1 ? " task" : " tasks");
    }

    /**
     * Safely parses a string into an integer index.
     * <p>
     * This method trims the string and attempts to convert it into an
     * {@code Integer}. If the string is {@code null}, blank, or not a valid
     * number, the method returns {@code null} instead of throwing an exception.
     * <p>
     * Useful for parsing user-supplied task indices where invalid or missing
     * input should be handled gracefully.
     *
     * @param s the string to parse
     * @return the parsed integer index, or {@code null} if parsing fails
     */
    private static Integer safeParseIndex(String s) {
        if (s == null || s.isBlank()) return null;
        try { return Integer.parseInt(s.trim()); }
        catch (NumberFormatException e) { return null; }
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

        final String trimmed = input.trim();
        if (trimmed.isEmpty()) return UNKNOWN_COMMAND_MSG;

        final String cmd  = Parser.getCommand(trimmed);
        final String args = Parser.getArguments(trimmed);

        switch (cmd) {
            case "bye":      return handleBye();
            case "list":     return handleList();
            case "delete":   return handleDelete(args);
            case "mark":     return handleMark(args);
            case "unmark":   return handleUnmark(args);
            case "todo":     return handleTodo(args);
            case "deadline": return handleDeadline(args);
            case "event":    return handleEvent(args);
            case "find":     return handleFind(args);
            default:         return UNKNOWN_COMMAND_MSG;
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
