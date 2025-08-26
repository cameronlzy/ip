package tasks;

import utils.DateTimeUtil;

import java.time.LocalDateTime;
/**
 * Represents an Event task that has a description and a start/end date-time.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs a new Event task.
     *
     * @param description the description of the event
     * @param from        the start date/time of the event
     * @param to          the end date/time of the event
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    /** @return the start date/time */
    public LocalDateTime getFrom() { return from; }

    /** @return the end date/time */
    public LocalDateTime getTo() { return to; }

    /** @return ISO string for start date/time, for storage */
    public String getFromIso() { return from.toString(); }

    /** @return ISO string for end date/time, for storage */
    public String getToIso() { return to.toString(); }

    @Override
    public String toString() {
        return "[" + type.name().charAt(0) + "]" + status() + " " + description
                + " (from: " + DateTimeUtil.readable(from)
                + " to: " + DateTimeUtil.readable(to) + ")";
    }
}
