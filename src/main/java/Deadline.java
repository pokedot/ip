import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deadline extends Task {
    private final String deadline;

    Deadline(String description) throws MissingTaskException {
        super(parse(description)[0]);
        this.deadline = parse(description)[1];
    }

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

    public String toString() {
        if (this.getDone()) {
            return "[D][X] " + this.getItem() + " (by: " + this.deadline + ")";
        } else {
            return "[D][ ] " + this.getItem() + " (by: " + this.deadline + ")";
        }
    }
}
