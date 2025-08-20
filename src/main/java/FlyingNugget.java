import java.util.*;

public class FlyingNugget {
    private static final String INTRO = """
            Hello from
             _____ _       _             _   _                        _
            |  ___| |_   _(_)_ __   __ _| \\ | |_   _  __ _  __ _  ___| |_
            | |_  | | | | | | '_ \\ / _` |  \\| | | | |/ _` |/ _` |/ _ \\ __|
            |  _| | | |_| | | | | | (_| | |\\  | |_| | (_| | (_| |  __/ |_
            |_|   |_|\\__, |_|_| |_|\\__, |_| \\_|\\__,_|\\__, |\\__, |\\___|\\__|
                     |___/         |___/             |___/ |___/
            """;
    private static final String NAME = "FlyingNugget";
    private static final String LINE = "\t____________________________________________________________";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();
        boolean isRunning = true;
        System.out.println(INTRO);
        printBox("Hello! I'm " + NAME,
                "What can I do for you?");

        while (isRunning) {
            String input = scanner.nextLine().strip();
            String action = input.split(" ")[0];
            List<String> lines = new ArrayList<>();

            switch (action) {
                case "bye":
                    isRunning = false;
                    lines.add("Bye. Hope to see you again soon!");
                    break;

                case "list":
                    lines.add("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        lines.add((i + 1) + "." + tasks.get(i));
                    }
                    break;

                case "mark": {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    lines.add("Nice! I've marked this task as done:");
                    lines.add("  " + tasks.get(taskNumber - 1).markAsDone());
                    break;
                }

                case "unmark": {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    lines.add("OK, I've marked this task as not done yet:");
                    lines.add("  " + tasks.get(taskNumber - 1).markAsUndone());
                    break;
                }

                case "todo":
                    addTask(new Todo(input), tasks, lines);
                    break;

                case "deadline":
                    addTask(new Deadline(input), tasks, lines);
                    break;

                case "event":
                    addTask(new Event(input), tasks, lines);
                    break;
            }
            printBox(lines.toArray(new String[0]));
        }
        scanner.close();
    }

    private static void addTask(Task task, List<Task> tasks, List<String> lines) {
        tasks.add(task);
        lines.add("Got it. I've added this task:");
        lines.add("  " + task.toString());
        if (tasks.size() == 1) {
            lines.add("Now you have " + tasks.size() + " task in the list.");
        } else {
            lines.add("Now you have " + tasks.size() + " tasks in the list.");
        }
    }

    private static void printBox(String... messages) {
        System.out.println(LINE);
        for (String msg : messages) {
            System.out.println("\t" + msg);
        }
        System.out.println(LINE);
    }

}
