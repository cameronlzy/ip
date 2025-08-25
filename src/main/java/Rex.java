import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Rex {
    private static final Path DATA_PATH = Path.of("data", "rex.txt");
    private static void line() {
        System.out.println("____________________________________________________________");
    }

    private static void added(List<Task> tasks, Task t) {
        line();
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + t);
        System.out.println("     Now you have "
                + tasks.size()
                + (tasks.size() > 1 ? " tasks" : " task")
                + " in the list.");
        line();
    }

    private static void list(List<Task> tasks) {
        line();
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("     " + (i + 1) + "." + tasks.get(i));
        }
        line();
    }

    private static void okMarked(Task t, boolean done) {
        line();
        if (done) {
            System.out.println("     Nice! I've marked this task as done:");
        } else {
            System.out.println("     OK, I've marked this task as not done yet:");
        }
        System.out.println("       " + t);
        line();
    }

    private static void okDeleted(List<Task> tasks, Task t) {
        line();
        System.out.println("     Noted. I've removed this task:");
        System.out.println("       " + t);
        System.out.println("     Now you have "
                + tasks.size()
                + (tasks.size() > 1 ? " tasks" : " task")
                + " in the list.");
        line();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Task> tasks;
        try {
            tasks = Storage.load(DATA_PATH);
        } catch (Exception e) {
            tasks = new ArrayList<>();
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
                try { Storage.save(DATA_PATH, tasks); } catch (Exception ignored) {
                    System.out.println("Error Saving");
                }
                break;
            } else if (input.equalsIgnoreCase("list")) {
                list(tasks);
            } else if (input.startsWith("delete ")) {
                try {
                    int idx = Integer.parseInt(input.substring(6).trim()) - 1;
                    Task t = tasks.get(idx);
                    tasks.remove(idx);
                    okDeleted(tasks, t);
                } catch (Exception e) {
                    System.out.println("Invalid task number for delete.");
                }
            } else if (input.startsWith("mark ")) {
                try {
                    int idx = Integer.parseInt(input.substring(5).trim()) - 1;
                    Task t = tasks.get(idx);
                    t.markDone();
                    okMarked(t, true);
                } catch (Exception e) {
                    System.out.println("Invalid task number for mark.");
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                    Task t = tasks.get(idx);
                    t.markUndone();
                    okMarked(t, false);
                } catch (Exception e) {
                    System.out.println("Invalid task number for unmark.");
                }
            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                Task t = new Todo(desc);
                tasks.add(t);
                added(tasks, t);
            } else if (input.startsWith("deadline ")) {
                String body = input.substring(9).trim();
                int sep = body.indexOf(" /by ");
                if (sep < 0) {
                    System.out.println("Usage: deadline <description> /by <time>");
                    continue;
                }
                String desc = body.substring(0, sep).trim();
                String byStr = body.substring(sep + 5).trim();
                try {
                    Task t = new Deadline(desc, DateTimeUtil.parseFlexible(byStr));
                    tasks.add(t);
                    added(tasks, t);
                } catch (Exception e) {
                    System.out.println("Invalid date/time. Try formats like 2019-12-02 1800 or 2/12/2019 1800.");
                }
            } else if (input.startsWith("event ")) {
                String body = input.substring(6).trim();
                int fromIdx = body.indexOf(" /from ");
                int toIdx = body.indexOf(" /to ");
                if (fromIdx < 0 || toIdx < 0 || toIdx <= fromIdx) {
                    System.out.println("Usage: event <desc> /from <yyyy-MM-dd[ HHmm]> /to <yyyy-MM-dd[ HHmm]>");
                    continue;
                }
                String desc = body.substring(0, fromIdx).trim();
                String fromStr = body.substring(fromIdx + 7, toIdx).trim();
                String toStr = body.substring(toIdx + 5).trim();
                try {
                    Task t = new Event(desc, DateTimeUtil.parseFlexible(fromStr), DateTimeUtil.parseFlexible(toStr));
                    tasks.add(t);
                    added(tasks, t);
                } catch (Exception e) {
                    System.out.println("Invalid date/time for event. Use 2019-12-02 1800 or 2/12/2019 1800.");
                }
            } else {
                System.out.println("Unknown command.");
            }
        }

        sc.close();
    }
}
