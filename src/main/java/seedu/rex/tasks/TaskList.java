package seedu.rex.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial);
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task delete(int oneBasedIndex) {
        int i = oneBasedIndex - 1;
        return tasks.remove(i);
    }

    public Task get(int oneBasedIndex) {
        int i = oneBasedIndex - 1;
        return tasks.get(i);
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> asList() {
        return Collections.unmodifiableList(tasks);
    }
}
