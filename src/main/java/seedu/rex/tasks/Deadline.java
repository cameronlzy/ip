package seedu.rex.tasks;

import seedu.rex.utils.DateTimeUtil;

import java.time.LocalDateTime;

/**
 * Represents a Deadline task that has a description and a due date/time.
 */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Constructs a new Deadline task.
     *
     * @param description the description of the task
     * @param by          the due date/time of the task
     */
    public Deadline(String description, LocalDateTime by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    /** @return the due date/time */
    public LocalDateTime getBy() { return by; }

    /** @return ISO 8601 string of the due date/time, for storage */
    public String getByIso() { return by.toString(); }

    @Override
    public String toString() {
        return "[" + type.name().charAt(0) + "]" + status() + " " + description
                + " (by: " + DateTimeUtil.readable(by) + ")";
    }
}
