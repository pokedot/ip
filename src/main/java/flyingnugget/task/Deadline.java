package flyingnugget.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a {@code Deadline} task, a type of {@code Task}
 * that includes a due date.
 * <p>
 * The task description is parsed from user input.
 * </p>
 */
public class Deadline extends Task {
    private final LocalDate deadline;

    /**
     * Creates a new {@code Deadline} task from the given description.
     *
     * @param description the user input containing the "deadline" keyword, task description, and due date.
     * @throws MissingTaskException if the task description and/or the due date is missing.
     */
    public Deadline(String description) throws MissingTaskException, DateTimeParseException {
        super(parse(description)[0]);
        this.deadline = LocalDate.parse(parse(description)[1]);
    }

    /**
     * Creates a {@code Deadline} task from its serialized string form
     *
     * @param isDone the state of completion of the deadline.
     * @param item the deadline item.
     * @param deadline the deadline date.
     */
    Deadline(boolean isDone, String item, String deadline) {
        super(isDone, item);
        this.deadline = LocalDate.parse(deadline);
    }

    /**
     * Extracts the {@code Deadline} description and due date from the user input.
     *
     * @param description the raw input string.
     * @return the parsed task description and due date.
     * @throws MissingTaskException if the task description and/or the due date is missing.
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
     * Converts the {@code Deadline} to a serialized string to be saved to a text file for future retrieval.
     *
     * @return a string that corresponds to the deadline.
     */
    @Override
    public String serializeString() {
        return "D|"
                + this.getIsDone() + "|"
                + this.getItem() + "|"
                + this.deadline;
    }

    /**
     * Returns a string representation of the {@code Deadline} task.
     *
     * @return a string showing task type, completion status, task description, and due date.
     */
    @Override
    public String toString() {
        return "[D]"
                + (this.getIsDone() ? "[X] " : "[ ] ")
                + this.getItem() + " (by: "
                + this.deadline.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
