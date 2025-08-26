package tasks;

enum TaskType {
    TODO, DEADLINE, EVENT
}

/**
        * Abstract base class representing a generic task.
        * A Task has a description, a completion status, and a type (TODO, DEADLINE, EVENT).
        *
        * Subclasses should provide additional fields (e.g. date/time) where necessary
 * and override {@code toString()} to format their details.
        */
public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    /**
     * Constructs a new Task with the given description and type.
     *
     * @param description the description of the task
     * @param type        the type of task (TODO, DEADLINE, EVENT)
     */
    public Task(String description, TaskType type) {
        this.description = description;
        this.type = type;
        this.isDone = false;
    }

    /** Marks this task as completed. */
    public void markDone() { this.isDone = true; }

    /** Marks this task as not completed. */
    public void markUndone() { this.isDone = false; }

    /** @return "[X]" if task is done, "[ ]" otherwise */
    protected String status() { return isDone ? "[X]" : "[ ]"; }

    /** @return description of the task */
    public String getDescription() { return description; }

    /** @return true if the task is done */
    public boolean isDone() { return isDone; }

    @Override
    public String toString() {
        return "[" + type.name().charAt(0) + "]" + status() + " " + description;
    }
}
