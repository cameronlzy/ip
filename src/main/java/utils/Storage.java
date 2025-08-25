package utils;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Storage {
    private Storage() {
    }

    public static List<Task> load(Path path) throws IOException {
        if (!Files.exists(path)) {
            Path dir = path.getParent();
            if (dir != null && !Files.exists(dir)) Files.createDirectories(dir);
            Files.createFile(path);
            return new ArrayList<>();
        }

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
                    Task t = new Todo(parts[2]);
                    if (done) t.markDone();
                    tasks.add(t);
                    break;
                }
                case "D": {
                    if (parts.length < 4) break;
                    LocalDateTime by = tryParse(parts[3]);
                    Task t = new Deadline(parts[2], by);
                    if (done) t.markDone();
                    tasks.add(t);
                    break;
                }
                case "E": {
                    if (parts.length < 5) break;
                    LocalDateTime from = tryParse(parts[3]);
                    LocalDateTime to = tryParse(parts[4]);
                    Task t = new Event(parts[2], from, to);
                    if (done) t.markDone();
                    tasks.add(t);
                    break;
                }
            }
        }
        return tasks;
    }

    private static LocalDateTime tryParse(String s) {
        try {
            return LocalDateTime.parse(s);
        } catch (Exception ignore) {
            return DateTimeUtil.parseFlexible(s);
        }
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
            return String.join(" | ", "D", t.isDone() ? "1" : "0", d.getDescription(), d.getByIso());
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return String.join(" | ", "E", t.isDone() ? "1" : "0", e.getDescription(), e.getFromIso(), e.getToIso());
        }
        return String.join(" | ", "T", t.isDone() ? "1" : "0", t.getDescription());
    }

}
