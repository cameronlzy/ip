import java.util.List;

public class UI {
    public static void line() {
        System.out.println("____________________________________________________________");
    }

    public static void greet() {
        line();
        System.out.println("     Hello! I'm Rex");
        System.out.println("     What can I do for you?");
        line();
    }

    public static void bye() {
        line();
        System.out.println("     Bye. Hope to see you again soon!");
        line();
    }

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

    public static void list(List<Task> tasks) {
        line();
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("     " + (i + 1) + "." + tasks.get(i));
        }
        line();
    }

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
