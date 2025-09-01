package seedu.rex.tasks;

/**
 * Represents a simple Todo task with only a description.
 * A Todo task does not have any associated date/time.
 */
public class Todo extends Task {
    /**
     * Constructs a new Todo task.
     *
     * @param description the description of the todo task
     */
    public Todo(String description) {
        super(description, TaskType.TODO);
    }
}
