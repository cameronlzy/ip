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

}
