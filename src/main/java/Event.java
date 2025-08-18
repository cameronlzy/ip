class Event extends Task {
    private final String from;
    private final String to;
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }
    @Override protected String type() { return "E"; }
    @Override public String toString() {
        return "[" + type() + "]" + status() + " " + description + " (from: " + from + " to: " + to + ")";
    }
}
