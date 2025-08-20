import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Deadline task, which is a type of Task with a due date.
 * The description is parsed from the user input.
 */
public class Deadline extends Task {
    private final String deadline;

    /**
     * Creates a new Deadline task from the given description.
     *
     * @param description the user input containing the "deadline" keyword, task description, and due date
     * @throws MissingTaskException if the task description and/or the due date is missing
     */
    Deadline(String description) throws MissingTaskException {
        super(parse(description)[0]);
        this.deadline = parse(description)[1];
    }

    /**
     * Extracts the task description and due date from the user input.
     *
     * @param description the raw input string
     * @return the parsed task description and due date
     * @throws MissingTaskException if the task description and/or the due date is missing
     */
    private static String[] parse(String description) throws MissingTaskException {
        Pattern pattern = Pattern.compile("deadline (.*?) /by (.*?)");
        Matcher matcher = pattern.matcher(description);
        if (matcher.matches()) {
            String item = matcher.group(1);
            String deadline = matcher.group(2);
            return new String[]{item, deadline};
        } else {
            throw new MissingTaskException();
        }
    }

    /**
     * Returns a string representation of the Deadline task.
     *
     * @return a string showing task type, completion status, task description, and due date
     */
    @Override
    public String toString() {
        return "[D]" + (this.getDone() ? "[X] " : "[ ] ") + this.getItem() + " (by: " + this.deadline + ")";
    }
}
