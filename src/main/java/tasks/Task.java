package tasks;

enum TaskType {
    TODO, DEADLINE, EVENT
}

public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    public Task(String description, TaskType type) {
        this.description = description;
        this.type = type;
        this.isDone = false;
    }

    public void markDone() { this.isDone = true; }
    public void markUndone() { this.isDone = false; }

    protected String status() { return isDone ? "[X]" : "[ ]"; }

    public String getDescription() { return description; }
    public boolean isDone() { return isDone; }

    @Override
    public String toString() {
        return "[" + type.name().charAt(0) + "]" + status() + " " + description;
    }
}
