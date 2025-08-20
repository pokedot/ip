import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event extends Task {
    private final String start;
    private final String end;

    Event(String description) throws MissingTaskException {
        super(parse(description)[0]);
        this.start = parse(description)[1];
        this.end = parse(description)[2];
    }

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

    public String toString() {
        if (this.getDone()) {
            return "[E][X] " + this.getItem() + " (from: " + this.start + " to: " + this.end + ")";
        } else {
            return "[E][ ] " + this.getItem() + " (from: " + this.start + " to: " + this.end + ")";
        }
    }
}
