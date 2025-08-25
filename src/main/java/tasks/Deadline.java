package tasks;

import utils.DateTimeUtil;

import java.time.LocalDateTime;
public class Deadline extends Task {
    private final LocalDateTime by;
    public Deadline(String description, LocalDateTime by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }
    public LocalDateTime getBy() { return by; }
    public String getByIso() { return by.toString(); } // for storage
    @Override public String toString() {
        return "[" + type.name().charAt(0) + "]" + status() + " " + description
                + " (by: " + DateTimeUtil.readable(by) + ")";
    }
}
