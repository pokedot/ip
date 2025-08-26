package flyingnugget.task;

/** Represents a Task which tracks an item and its completion state.
 * This is the base class for Todo, Deadline, and Event.
 */
public class Task {
    private boolean isDone;
    private final String item;

    /**
     * Creates a new Task with the item.
     * By default, the task is not done.
     *
     * @param item the description of the task
     */
    Task(String item) {
        this.isDone = false;
        this.item = item;
    }

    Task(boolean isDone, String item) {
        this.isDone = isDone;
        this.item = item;
    }

    public static Task read(String serializedTask) {
        String[] parts = serializedTask.split("\\|");
        String taskType = parts[0];
        Task task = null;
        switch (taskType) {
        case "T":
            task = new Todo(Boolean.parseBoolean(parts[1]), parts[2]);
            break;
        case "D":
            task = new Deadline(Boolean.parseBoolean(parts[1]), parts[2], parts[3]);
            break;
        case "E":
            task = new Event(Boolean.parseBoolean(parts[1]), parts[2], parts[3], parts[4]);
            break;
        }
        return task;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return the task description
     */
    public String getItem() {
        return this.item;
    }

    /**
     * Marks the task as done and returns its string representation.
     *
     * @return the string representation of the task after marking it as done
     */
    public String markAsDone() {
        this.isDone = true;
        return this.toString();
    }

    /**
     * Marks the task as undone and returns its string representation.
     *
     * @return the string representation of the task after marking it as undone
     */
    public String markAsUndone() {
        this.isDone = false;
        return this.toString();
    }

    public String serialize() {
        return this.isDone + " | " + this.item;
    }

    /**
     * Returns a string representation of the task.
     * Subclasses should override this to provide task-specific formatting.
     *
     * @return the string representation of the task
     */
    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + item;
    }
}
