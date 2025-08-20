import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlyingNugget {
    private static final String INTRO = """
            Hello from
             _____ _       _             _   _                        _
            |  ___| |_   _(_)_ __   __ _| \\ | |_   _  __ _  __ _  ___| |_
            | |_  | | | | | | '_ \\ / _` |  \\| | | | |/ _` |/ _` |/ _ \\ __|
            |  _| | | |_| | | | | | (_| | |\\  | |_| | (_| | (_| |  __/ |_
            |_|   |_|\\__, |_|_| |_| \\__, |_| \\_| \\__,_| \\__, |\\__, |\\___|\\__|
                     |___/         |___/             |___/ |___/ |___/
            """;

    private static final String NAME = "FlyingNugget";
    private static final String LINE = "\t____________________________________________________________";
    private static final Pattern MARK = Pattern.compile("mark (\\d+)");
    private static final Pattern UNMARK = Pattern.compile("unmark (\\d+)");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();
        System.out.println(INTRO);
        printBox("Hello! I'm " + NAME,
                "What can I do for you?");

        while (true) {
            String input = scanner.nextLine();
            Matcher markMatcher = MARK.matcher(input);
            Matcher unmarkMatcher = UNMARK.matcher(input);
            List<String> lines = new ArrayList<>();
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                lines.add("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    lines.add((i + 1) + "." + tasks.get(i));
                }
            } else if (markMatcher.matches()) {
                int taskNumber = Integer.parseInt(markMatcher.group(1));
                lines.add("Nice! I've marked this task as done:");
                lines.add("  " + tasks.get(taskNumber - 1).markAsDone());
            } else if (unmarkMatcher.matches()) {
                int taskNumber = Integer.parseInt(unmarkMatcher.group(1));
                lines.add("OK, I've marked this task as not done yet:");
                lines.add("  " + tasks.get(taskNumber - 1).markAsUndone());
            } else {
                tasks.add(new Task(input));
                lines.add("added: " + input);
            }
            printBox(lines.toArray(new String[0]));
        }

        printBox("Bye. Hope to see you again soon!");
        scanner.close();
    }

    private static void printBox(String... messages) {
        System.out.println(LINE);
        for (String msg : messages) {
            System.out.println("\t" + msg);
        }
        System.out.println(LINE);
    }

}
