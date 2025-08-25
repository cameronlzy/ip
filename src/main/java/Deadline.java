class Deadline extends Task {
    private final String by;
    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    public String getBy() { return by; }

    @Override
    public String toString() {
        return "[" + type.name().charAt(0) + "]" + status() + " " + description + " (by: " + by + ")";
    }
}
