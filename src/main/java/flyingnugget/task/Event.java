package flyingnugget.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an {@code Event} task, a type of {@code Task}
 * that includes a start and end date.
 * <p>
 * The task description is parsed from user input.
 * </p>
 */
public class Event extends Task {
    private final LocalDate start;
    private final LocalDate end;

    /**
     * Creates a new {@code Event} task from the given description.
     *
     * @param description the user input containing the "event" keyword, task description, start date, and end date.
     * @throws MissingTaskException if the task description, the start date, and/or the end date is missing.
     */
    public Event(String description) throws MissingTaskException, DateTimeParseException {
        super(parse(description)[0]);
        this.start = LocalDate.parse(parse(description)[1]);
        this.end = LocalDate.parse(parse(description)[2]);
    }

    /**
     * Creates an {@code Event} task from its serialized string form
     *
     * @param isDone the state of completion of the {@code Event}.
     * @param item the {@code Event} item.
     * @param start the {@code Event} start date.
     * @param end the {@code Event} end date.
     */
    Event(boolean isDone, String item, String start, String end) {
        super(isDone, item);
        this.start = LocalDate.parse(start);
        this.end = LocalDate.parse(end);
    }

    /**
     * Extracts the {@code Event} description, start date, and end date from the user input.
     *
     * @param description the raw input string.
     * @return the parsed task description, start date, and end date.
     * @throws MissingTaskException if the task description, the start date, and/or the end date is missing.
     */
    private static String[] parse(String description) throws MissingTaskException {
        Pattern pattern = Pattern.compile("event (.*?) /from (.*?) /to (.*)");
        Matcher matcher = pattern.matcher(description);
        if (matcher.matches()) {
            String item = matcher.group(1);
            String start = matcher.group(2);
            String end = matcher.group(3);
            return new String[]{item, start, end};
        } else {
            throw new MissingTaskException();
        }
    }

    /**
     * Converts the {@code Event} to a serialized string to be saved to a text file for future retrieval.
     *
     * @return a string that corresponds to the {@code Event}.
     */
    @Override
    public String serializeString() {
        return "E|"
                + this.getIsDone() + "|"
                + this.getItem() + "|"
                + this.start + "|"
                + this.end;
    }

    /**
     * Returns a string representation of the {@code Event} task.
     *
     * @return a string showing task type, completion status, task description, start date, and end date.
     */
    @Override
    public String toString() {
        return "[E]"
                + (this.getIsDone() ? "[X] " : "[ ] ")
                + this.getItem() + " (from: "
                + this.start.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + " to: "
                + this.end.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
