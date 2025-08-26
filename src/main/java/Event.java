import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an Event task, which is a type of Task with a start date and end date.
 * The description is parsed from the user input.
 */
public class Event extends Task {
    private final String start;
    private final String end;

    /**
     * Creates a new Event task from the given description.
     *
     * @param description the user input containing the "event" keyword, task description, start date, and end date
     * @throws MissingTaskException if the task description, the start date, and/or the end date is missing
     */
    Event(String description) throws MissingTaskException {
        super(parse(description)[0]);
        this.start = parse(description)[1];
        this.end = parse(description)[2];
    }

    Event(boolean isDone, String item, String start, String end) {
        super(isDone, item);
        this.start = start;
        this.end = end;
    }

    /**
     * Extracts the task description, start date, and end date from the user input.
     *
     * @param description the raw input string
     * @return the parsed task description, start date, and end date
     * @throws MissingTaskException if the task description, the start date, and/or the end date is missing
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

    @Override
    public String serialize() {
        return "E|" + this.getIsDone() + "|" + this.getItem() + "|" + this.start + "|" + this.end;
    }

    /**
     * Returns a string representation of the Event task.
     *
     * @return a string showing task type, completion status, task description, start date, and end date
     */
    @Override
    public String toString() {
        return "[E]" + (this.getIsDone() ? "[X] " : "[ ] ") + this.getItem() + " (from: " + this.start + " to: " + this.end + ")";
    }
}
