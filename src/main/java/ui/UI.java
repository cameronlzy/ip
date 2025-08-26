package ui;

import tasks.Task;
import java.util.List;

/**
 * The {@code UI} class encapsulates all user interface printing logic
 * for the Rex chatbot.
 * <p>
 * This class provides reusable static methods to display responses
 * such as greetings, task additions, task deletions, errors, and usage messages.
 */
public class UI {

    /**
     * Prints a horizontal line for formatting output.
     */
    public static void line() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints the greeting message shown when Rex starts.
     */
    public static void greet() {
        line();
        System.out.println("     Hello! I'm Rex");
        System.out.println("     What can I do for you?");
        line();
    }

    /**
     * Prints the farewell message shown when Rex exits.
     */
    public static void bye() {
        line();
        System.out.println("     Bye. Hope to see you again soon!");
        line();
    }

    /**
     * Prints a message after a task is successfully added.
     *
     * @param tasks The current list of tasks.
     * @param t     The task that was added.
     */
    public static void added(List<Task> tasks, Task t) {
        line();
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + t);
        System.out.println("     Now you have "
                + tasks.size()
                + (tasks.size() > 1 ? " tasks" : " task")
                + " in the list.");
        line();
    }

    /**
     * Prints the current list of tasks.
     *
     * @param tasks The list of tasks to display.
     */
    public static void find(List<Task> tasks, String query) {
        String q = query.toLowerCase();
        line();
        System.out.println("     Here are the matching tasks in your list:");
        int n = 0;
        for (Task t : tasks) {
            String d = t.getDescription();
            if (d != null && d.toLowerCase().contains(q)) {
                System.out.println("     " + (++n) + "." + t);
            }
        }
        line();
    }

    public static void list(List<Task> tasks) {
        line();
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("     " + (i + 1) + "." + tasks.get(i));
        }
        line();
    }

    /**
     * Prints a message when a task is marked/unmarked.
     *
     * @param t    The task being updated.
     * @param done {@code true} if the task was marked as done, {@code false} otherwise.
     */
    public static void marked(Task t, boolean done) {
        line();
        if (done) {
            System.out.println("     Nice! I've marked this task as done:");
        } else {
            System.out.println("     OK, I've marked this task as not done yet:");
        }
        System.out.println("       " + t);
        line();
    }

    /**
     * Prints a message when a task is deleted.
     *
     * @param tasks The current list of tasks.
     * @param t     The task that was deleted.
     */
    public static void deleted(List<Task> tasks, Task t) {
        line();
        System.out.println("     Noted. I've removed this task:");
        System.out.println("       " + t);
        System.out.println("     Now you have "
                + tasks.size()
                + (tasks.size() > 1 ? " tasks" : " task")
                + " in the list.");
        line();
    }

    // --- Error and usage messages ---

    public static void invalidDeleteIndex() {
        System.out.println("Invalid task number for delete.");
    }

    public static void invalidMarkIndex() {
        System.out.println("Invalid task number for mark.");
    }

    public static void invalidUnmarkIndex() {
        System.out.println("Invalid task number for unmark.");
    }

    public static void usageDeadline() {
        System.out.println("Usage: deadline <description> /by <yyyy-MM-dd[ HHmm]>");
    }

    public static void usageFind() {
        System.out.println("Usage: find <keyword>");
    }

    public static void invalidDeadlineDate() {
        System.out.println("Invalid date/time. Try formats like 2019-12-02 1800 or 2/12/2019 1800.");
    }

    public static void usageEvent() {
        System.out.println("Usage: event <desc> /from <yyyy-MM-dd[ HHmm]> /to <yyyy-MM-dd[ HHmm]>");
    }

    public static void invalidEventDate() {
        System.out.println("Invalid date/time for event. Use 2019-12-02 1800 or 2/12/2019 1800.");
    }

    public static void unknownCommand() {
        System.out.println("Unknown command.");
    }

    public static void savingError() {
        System.out.println("Error Saving");
    }
}
