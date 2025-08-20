/** Represents a Task which tracks an item and its completion state.
 * This is the base class for Todo, Deadline, and Event.
 */
public class Task {
    private boolean done;
    private final String item;

    /**
     * Creates a new Task with the item.
     * By default, the task is not done.
     *
     * @param item the description of the task
     */
    Task(String item) {
        this.done = false;
        this.item = item;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean getDone() {
        return this.done;
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
        this.done = true;
        return this.toString();
    }

    /**
     * Marks the task as undone and returns its string representation.
     *
     * @return the string representation of the task after marking it as undone
     */
    public String markAsUndone() {
        this.done = false;
        return this.toString();
    }

    /**
     * Returns a string representation of the task.
     * Subclasses should override this to provide task-specific formatting.
     *
     * @return the string representation of the task
     */
    @Override
    public String toString() {
        return (done ? "[X] " : "[ ] ") + item;
    }
}
