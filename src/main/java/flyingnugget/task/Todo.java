package flyingnugget.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Todo task, which is a type of Task without a specific deadline or event time.
 * The description is parsed from the user input.
 */
public class Todo extends Task {

    /**
     * Creates a new Todo task from the given description.
     *
     * @param description the user input containing the "todo" keyword and task description
     * @throws MissingTaskException if no task description is provided
     */
    public Todo(String description) throws MissingTaskException {
        super(parse(description));
    }

    Todo(boolean isDone, String item) {
        super(isDone, item);
    }

    /**
     * Extracts the task description from the user input.
     *
     * @param description the raw input string
     * @return the parsed task description
     * @throws MissingTaskException if no task description is provided
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

    @Override
    public String serialize() {
        return "T|" + this.getIsDone() + "|" + this.getItem();
    }

    /**
     * Returns a string representation of the Todo task.
     *
     * @return a string showing task type, completion status, and task description
     */
    @Override
    public String toString() {
        return "[T]" + (this.getIsDone() ? "[X] " : "[ ] ") + this.getItem();
    }

}
