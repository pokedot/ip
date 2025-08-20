import java.util.*;

/**
 * The FlyingNugget class is a task management chatbot
 * that allows users to add/delete tasks and mark/unmark their completions.
 * It supports Todo, Event, and Deadline tasks.
 */
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

    /**
     * The main entry point for the chatbot.
     * Reads user input and performs actions like adding/deleting/marking/unmarking tasks
     * and displaying the current list of tasks.
     *
     * @param args (not used)
     * @throws UnknownActionException if the user enters an unknown action
     */
    public static void main(String[] args) throws UnknownActionException {
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

                case "mark":
                    try {
                        int taskNumber = Integer.parseInt(input.split(" ")[1]);
                        String taskInfo = tasks.get(taskNumber - 1).markAsDone();
                        lines.add("Nice! I've marked this task as done:");
                        lines.add("  " + taskInfo);
                    } catch (NumberFormatException e) {
                        lines.add("I need a valid number to help!");
                        lines.add("(Ensure your mark is of the following format: \"mark [number]\".)");
                    } catch (IndexOutOfBoundsException e) {
                        lines.add("This task number does not exist in my list!");
                        lines.add("(Type \"list\" to see all your current tasks and their numbers.)");
                    }
                    break;


                case "unmark":
                    try {
                        int taskNumber = Integer.parseInt(input.split(" ")[1]);
                        String taskInfo = tasks.get(taskNumber - 1).markAsUndone();
                        lines.add("OK, I've marked this task as not done yet:");
                        lines.add("  " + taskInfo);
                    } catch (NumberFormatException e) {
                        lines.add("I need a valid number to help!");
                        lines.add("(Ensure your unmark is of the following format: \"unmark [number]\".)");
                    } catch (IndexOutOfBoundsException e) {
                        lines.add("This task number does not exist in my list!");
                        lines.add("(Type \"list\" to see all your current tasks and their numbers.)");
                    }
                    break;

                case "todo":
                    try {
                        addTask(new Todo(input), tasks, lines);
                    } catch (MissingTaskException e) {
                        lines.add("I need a task to schedule your todo!");
                        lines.add("(Ensure your todo is of the following format: \"todo [task]\".)");
                    }
                    break;


                case "deadline":
                    try {
                        addTask(new Deadline(input), tasks, lines);
                    } catch (MissingTaskException e) {
                        lines.add("I need a task and a due date to schedule your deadline!");
                        lines.add("(Ensure your deadline is of the following format: \"deadline [task] /by [dueDate]\".)");
                    }
                    break;

                case "event":
                    try {
                        addTask(new Event(input), tasks, lines);
                    } catch (MissingTaskException e) {
                        lines.add("I need a task, start date, and end date to schedule your event!");
                        lines.add("(Ensure your event is of the following format: \"event [task] /from [start] /to [end]\".)");
                    }
                    break;

                default:
                    try {
                        throw new UnknownActionException();
                    } catch (UnknownActionException e) {
                        lines.add("I have never seen that action before!");
                    }
            }
            printBox(lines.toArray(new String[0]));
        }
        scanner.close();
    }

    /**
     * Adds a task to the task list and prints its corresponding messages.
     *
     * @param task the Task object to add
     * @param tasks the current list of tasks
     * @param lines the current List of messages to print
     */
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

    /**
     * Prints all messages per action in an enclosed box.
     *
     * @param messages one or more Strings to print in the box
     */
    private static void printBox(String... messages) {
        System.out.println(LINE);
        for (String msg : messages) {
            System.out.println("\t" + msg);
        }
        System.out.println(LINE);
    }
}
