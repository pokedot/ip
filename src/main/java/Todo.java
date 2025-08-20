import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Todo extends Task {

    Todo(String description) throws MissingTaskException {
        super(parse(description));
    }

    private static String parse(String description) throws MissingTaskException {
        Pattern pattern = Pattern.compile("todo (.*)");
        Matcher matcher = pattern.matcher(description);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            throw new MissingTaskException();
        }
    }

    public String toString() {
        if (this.getDone()) {
            return "[T][X] " + this.getItem();
        } else {
            return "[T][ ] " + this.getItem();
        }
    }

}
