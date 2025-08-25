import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public final class Storage {
    private Storage() {
    }

    public static List<Task> load(Path path) throws IOException {
        if (!Files.exists(path)) return new ArrayList<>();
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        List<Task> tasks = new ArrayList<>();
        for (String line : lines) {
            String[] parts = Arrays.stream(line.split("\\|"))
                    .map(String::trim).toArray(String[]::new);
            if (parts.length < 3) continue;
            String type = parts[0];
            boolean done = "1".equals(parts[1]);
            switch (type) {
                case "T": {
                    String desc = parts[2];
                    Task t = new Todo(desc);
                    if (done) t.markDone();
                    tasks.add(t);
                    break;
                }
                case "D": {
                    if (parts.length < 4) break;
                    String desc = parts[2];
                    String by = parts[3];
                    Task t = new Deadline(desc, by);
                    if (done) t.markDone();
                    tasks.add(t);
                    break;
                }
                case "E": {
                    if (parts.length < 5) break;
                    String desc = parts[2];
                    String from = parts[3];
                    String to = parts[4];
                    Task t = new Event(desc, from, to);
                    if (done) t.markDone();
                    tasks.add(t);
                    break;
                }
                default:
                    break;
            }
        }
        return tasks;
    }

    public static void save(Path path, List<Task> tasks) throws IOException {
        Path dir = path.getParent();
        if (dir != null && !Files.exists(dir)) Files.createDirectories(dir);
        List<String> lines = new ArrayList<>(tasks.size());
        for (Task t : tasks) lines.add(serialise(t));
        Files.write(path, lines, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    private static String serialise(Task t) {
        if (t instanceof Todo) {
            return String.join(" | ", "T", t.isDone() ? "1" : "0", t.getDescription());
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return String.join(" | ", "D", t.isDone() ? "1" : "0", d.getDescription(), d.getBy());
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return String.join(" | ", "E", t.isDone() ? "1" : "0", e.getDescription(), e.getFrom(), e.getTo());
        }
        return String.join(" | ", "T", t.isDone() ? "1" : "0", t.getDescription());
    }

}
