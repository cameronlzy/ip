import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Rex {
    private static final Path DATA_PATH = Path.of("data", "rex.txt");
    private static void line() {
        System.out.println("____________________________________________________________");
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
                UI.list(tasks);
            } else if (input.startsWith("delete ")) {
                try {
                    int idx = Integer.parseInt(input.substring(6).trim()) - 1;
                    Task t = tasks.get(idx);
                    tasks.remove(idx);
                    UI.deleted(tasks, t);
                } catch (Exception e) {
                    UI.invalidDeleteIndex();
                }
            } else if (input.startsWith("mark ")) {
                try {
                    int idx = Integer.parseInt(input.substring(5).trim()) - 1;
                    Task t = tasks.get(idx);
                    t.markDone();
                    UI.marked(t, true);
                } catch (Exception e) {
                    UI.invalidMarkIndex();
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                    Task t = tasks.get(idx);
                    t.markUndone();
                    UI.marked(t, false);
                } catch (Exception e) {
                    UI.invalidUnmarkIndex();
                }
            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                Task t = new Todo(desc);
                tasks.add(t);
                UI.added(tasks, t);
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
                    UI.added(tasks, t);
                } catch (Exception e) {
                    UI.invalidDeadlineDate();
                }
            } else if (input.startsWith("event ")) {
                String body = input.substring(6).trim();
                int fromIdx = body.indexOf(" /from ");
                int toIdx = body.indexOf(" /to ");
                if (fromIdx < 0 || toIdx < 0 || toIdx <= fromIdx) {
                    UI.invalidEventDate();
                    continue;
                }
                String desc = body.substring(0, fromIdx).trim();
                String fromStr = body.substring(fromIdx + 7, toIdx).trim();
                String toStr = body.substring(toIdx + 5).trim();
                try {
                    Task t = new Event(desc, DateTimeUtil.parseFlexible(fromStr), DateTimeUtil.parseFlexible(toStr));
                    tasks.add(t);
                    UI.added(tasks, t);
                } catch (Exception e) {
                    UI.invalidEventDate();
                }
            } else {
                System.out.println("Unknown command.");
            }
        }

        sc.close();
    }
}
