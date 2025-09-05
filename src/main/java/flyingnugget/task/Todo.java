package flyingnugget.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a {@code Todo} task, a type of {@code Task}
 * that does not have a deadline or start/ end dates.
 * <p>
 * The task description is parsed from user input.
 * </p>
 */
public class Todo extends Task {

    /**
     * Creates a new {@code Todo} task from the given description.
     *
     * @param description the user input containing the "todo" keyword and task description.
     * @throws MissingTaskException if no task description is provided.
     */
    public Todo(String description) throws MissingTaskException {
        super(parse(description));
    }

    /**
     * Creates a {@code Todo} task from its serialized string form
     *
     * @param isDone the state of completion of the todo.
     * @param item the todo item.
     */
    Todo(boolean isDone, String item) {
        super(isDone, item);
    }

    /**
     * Extracts the {@code Todo} description from the user input.
     *
     * @param description the raw input string.
     * @return the parsed task description.
     * @throws MissingTaskException if no task description is provided.
     */
    private static String parse(String description) throws MissingTaskException {
        Pattern pattern = Pattern.compile("todo (.*)");
        Matcher matcher = pattern.matcher(description);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            throw new MissingTaskException();
        }
    }

    /**
     * Converts the {@code Todo} to a serialized string to be saved to a text file for future retrieval.
     *
     * @return a string that corresponds to the {@code Todo}.
     */
    @Override
    public String serializeString() {
        return "T|" + this.getIsDone() + "|" + this.getItem();
    }

    /**
     * Returns a string representation of the {@code Todo} task.
     *
     * @return a string showing task type, completion status, and task description.
     */
    @Override
    public String toString() {
        return "[T]" + (this.getIsDone() ? "[X] " : "[ ] ") + this.getItem();
    }

}
