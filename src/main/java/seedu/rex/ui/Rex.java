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
 *   <li>{@code bye} - Exit the program</li>
 * </ul>
 */

public class Rex {
    private static final Path DATA_PATH = Path.of("data", "rex.txt");
    /**
     * Prints a horizontal line for formatting.
     */
    private static void line() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Entry point for the Rex chatbot.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskList taskList;
        try {
            List<Task> loaded = Storage.load(DATA_PATH);
            taskList = new TaskList(loaded);
        } catch (Exception e) {
            taskList = new TaskList();
        }
        line();
        System.out.println("     Hello! I'm Rex");
        System.out.println("     What can I do for you?");
        line();

        while (true) {
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                line();
                System.out.println("     Bye. Hope to see you again soon!");
                line();
                try { Storage.save(DATA_PATH, new ArrayList<>(taskList.asList())); } catch (Exception ignored) {
                    System.out.println("Error Saving");
                }
                break;
            } else if (input.equalsIgnoreCase("list")) {
                UI.list(taskList.asList());
            } else if (input.startsWith("delete ")) {
                try {
                    int idx1 = Integer.parseInt(input.substring(6).trim());
                    Task removed = taskList.delete(idx1);
                    UI.deleted(taskList.asList(), removed);
                } catch (Exception e) {
                    UI.invalidDeleteIndex();
                }
            } else if (input.startsWith("mark ")) {
                try {
                    int idx1 = Integer.parseInt(input.substring(5).trim());
                    Task t = taskList.get(idx1);
                    t.markDone();
                    UI.marked(t, true);
                } catch (Exception e) {
                    UI.invalidMarkIndex();
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int idx1 = Integer.parseInt(input.substring(7).trim());
                    Task t = taskList.get(idx1);
                    t.markUndone();
                    UI.marked(t, false);
                } catch (Exception e) {
                    UI.invalidUnmarkIndex();
                }
            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                Task t = new Todo(desc);
                taskList.add(t);
                UI.added(taskList.asList(), t);
            } else if (input.startsWith("deadline ")) {
                String body = input.substring(9).trim();
                int sep = body.indexOf(" /by ");
                if (sep < 0) {
                    UI.usageDeadline();
                    continue;
                }
                String desc = body.substring(0, sep).trim();
                String byStr = body.substring(sep + 5).trim();
                try {
                    Task t = new Deadline(desc, DateTimeUtil.parseFlexible(byStr));
                    taskList.add(t);
                    UI.added(taskList.asList(), t);
                } catch (Exception e) {
                    UI.invalidDeadlineDate();
                }
            } else if (input.startsWith("event ")) {
                String body = input.substring(6).trim();
                int fromIdx = body.indexOf(" /from ");
                int toIdx = body.indexOf(" /to ");
                if (fromIdx < 0 || toIdx < 0 || toIdx <= fromIdx) {
                    UI.usageEvent();
                    continue;
                }
                String desc = body.substring(0, fromIdx).trim();
                String fromStr = body.substring(fromIdx + 7, toIdx).trim();
                String toStr = body.substring(toIdx + 5).trim();
                try {
                    Task t = new Event(desc, DateTimeUtil.parseFlexible(fromStr), DateTimeUtil.parseFlexible(toStr));
                    taskList.add(t);
                    UI.added(taskList.asList(), t);
                } catch (Exception e) {
                    UI.invalidEventDate();
                }
            } else if (input.startsWith("find")) {
                String body = input.length() >= 5 ? input.substring(5).trim() : "";
                if (body.isEmpty()) {
                    UI.usageFind();
                    continue;
                }
                UI.find(taskList.asList(), body);
            } else {
                System.out.println("Unknown command.");
            }
        }

        sc.close();
    }

    public String getResponse(String input) {
        return "Rex heard: " + input;
    }
}
