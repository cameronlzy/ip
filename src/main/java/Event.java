import java.time.LocalDateTime;

class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description, TaskType.EVENT);
        this.from = from; this.to = to;
    }
    public LocalDateTime getFrom() { return from; }
    public LocalDateTime getTo() { return to; }
    public String getFromIso() { return from.toString(); }
    public String getToIso() { return to.toString(); }
    @Override public String toString() {
        return "[" + type.name().charAt(0) + "]" + status() + " " + description
                + " (from: " + DateTimeUtil.readable(from)
                + " to: " + DateTimeUtil.readable(to) + ")";
    }
}
