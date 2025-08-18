abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() { this.isDone = true; }
    public void markUndone() { this.isDone = false; }

    protected String status() { return isDone ? "[X]" : "[ ]"; }
    protected abstract String type();

    @Override
    public String toString() {
        return "[" + type() + "]" + status() + " " + description;
    }
}
