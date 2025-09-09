package flyingnugget.task;

/**
 * Represents a {@code Task} which tracks an item and its completion state.
 * This is the base class for {@code Todo}, {@code Deadline}, and {@code Event}.
 */
public class Task {
    private boolean isDone;
    private final String item;

    /**
     * Creates a new {@code Task} with the item.
     * By default, the {@code Task} is not done.
     *
     * @param item the description of the {@code Task}.
     */
    Task(String item) {
        this.isDone = false;
        this.item = item;
    }

    /**
     * Creates a new {@code Task} with its completion state and item.
     *
     * @param isDone the completion state of the {@code Task}.
     * @param item the description of the {@code Task}.
     */
    Task(boolean isDone, String item) {
        this.isDone = isDone;
        this.item = item;
    }

    /**
     * Takes in the serialized form of a {@code Task} and returns a
     * {@code Todo}, {@code Deadline}, or {@code Event} with its respective attributes based on its task type.
     *
     * @param serializedTask the serialized form of a {@code Task}.
     * @return {@code Task} which corresponds to its serialized form.
     */
    public static Task read(String serializedTask) {
        String[] parts = serializedTask.split("\\|");
        String taskType = parts[0];
        Task task;
        switch (taskType) {
        case "T":
            assert parts.length == 3 : "number of parts should be 3";
            task = new Todo(Boolean.parseBoolean(parts[1]), parts[2]);
            break;
        case "D":
            assert parts.length == 4 : "number of parts should be 4";
            task = new Deadline(Boolean.parseBoolean(parts[1]), parts[2], parts[3]);
            break;
        case "E":
            assert parts.length == 5 : "number of parts should be 5";
            task = new Event(Boolean.parseBoolean(parts[1]), parts[2], parts[3], parts[4]);
            break;
        default:
            throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
        return task;
    }

    /**
     * Returns the completion status of the {@code Task}.
     *
     * @return true if the {@code Task} is done, false otherwise.
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Returns the description of the {@code Task}.
     *
     * @return the {@code Task} description.
     */
    public String getItem() {
        return this.item;
    }

    /**
     * Marks the {@code Task} as done and returns its string representation.
     *
     * @return the string representation of the {@code Task} after marking it as done.
     */
    public String markAsDone() {
        this.isDone = true;
        return this.toString();
    }

    /**
     * Marks the {@code Task} as undone and returns its string representation.
     *
     * @return the string representation of the {@code Task} after marking it as undone.
     */
    public String markAsUndone() {
        this.isDone = false;
        return this.toString();
    }

    public String serializeString() {
        return this.isDone + " | " + this.item;
    }

    /**
     * Returns a string representation of the {@code Task}.
     * Subclasses should override this to provide task-specific formatting.
     *
     * @return the string representation of the {@code Task}.
     */
    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + item;
    }
}
