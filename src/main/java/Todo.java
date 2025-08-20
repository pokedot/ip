import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Todo extends Task {

    Todo(String description) {
        super(parse(description));
    }

    private static String parse(String description) {
        Pattern pattern = Pattern.compile("todo (.*)");
        Matcher matcher = pattern.matcher(description);
        matcher.matches();
        return matcher.group(1);
    }

    public String toString() {
        if (this.getDone()) {
            return "[T][X] " + this.getItem();
        } else {
            return "[T][ ] " + this.getItem();
        }
    }

}
