class Event extends Task {
    private final String from;
    private final String to;
    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }
    @Override
    public String toString() {
        return "[" + type.name().charAt(0) + "]" + status() + " " + description + " (from: " + from + " to: " + to + ")";
    }
}
