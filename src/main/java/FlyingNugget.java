import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The FlyingNugget class is a task management chatbot
 * that allows users to add/delete tasks and mark/unmark their completions.
 * It supports Todo, Event, and Deadline tasks.
 */
public class FlyingNugget {
    private static final String INTRO = """
         _____ _       _             _   _                        _
        |  ___| |_   _(_)_ __   __ _| \\ | |_   _  __ _  __ _  ___| |_
        | |_  | | | | | | '_ \\ / _` |  \\| | | | |/ _` |/ _` |/ _ \\ __|
        |  _| | | |_| | | | | | (_| | |\\  | |_| | (_| | (_| |  __/ |_
        |_|   |_|\\__, |_|_| |_|\\__, |_| \\_|\\__,_|\\__, |\\__, |\\___|\\__|
                 |___/         |___/             |___/ |___/
        """;
    private static final String LINE = "\t____________________________________________________________";

    /**
     * The main entry point for the chatbot.
     * Reads user input and performs actions like adding/deleting/marking/unmarking tasks
     * and displaying the current list of tasks.
     *
     * @param args (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        List<Task> tasks = new ArrayList<>();
        Storage storage = new Storage("./data/FlyingNugget.txt");
        try {
            tasks = storage.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(INTRO);
        printBox("Yaho-! FlyingNugget's FlyingNugget!",
                "What can FlyingNugget do for LittleNuggy?");

        while (isRunning) {
            String input = scanner.nextLine();
            Action action = Action.findAction(input);
            List<String> lines = new ArrayList<>();

            switch (action) {
            case BYE:
                isRunning = false;
                lines.add("Bye! See LittleNuggy soon!");
                break;
            case LIST:
                lines.add("Ok~! Here are LittleNuggy's tasks!");
                for (int i = 0; i < tasks.size(); i++) {
                    lines.add((i + 1) + "." + tasks.get(i));
                }
                break;
            case MARK:
                try {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    String taskInfo = tasks.get(taskNumber - 1).markAsDone();
                    lines.add("Sugoi desu! LittleNuggy finished LittleNuggy's task!");
                    lines.add("  " + taskInfo);
                    storage.save(tasks);
                } catch (NumberFormatException e) {
                    lines.add("FlyingNugget does not have LittleNuggy's task number!");
                    lines.add("(Ensure your mark is of the following format: \"mark [number]\".)");
                } catch (IndexOutOfBoundsException e) {
                    lines.add("FlyingNugget does not see this task number in LittleNuggy's list!");
                    lines.add("(Type \"list\" to see all your current tasks and their numbers.)");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case UNMARK:
                try {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    String taskInfo = tasks.get(taskNumber - 1).markAsUndone();
                    lines.add("Nani? LittleNuggy lied to FlyingNugget?");
                    lines.add("  " + taskInfo);
                    storage.save(tasks);
                } catch (NumberFormatException e) {
                    lines.add("FlyingNugget does not have LittleNuggy's task number!");
                    lines.add("(Ensure your unmark is of the following format: \"unmark [number]\".)");
                } catch (IndexOutOfBoundsException e) {
                    lines.add("FlyingNugget does not see this task number on LittleNuggy's list!");
                    lines.add("(Type \"list\" to see all your current tasks and their numbers.)");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case TODO:
                try {
                    addTask(new Todo(input), tasks, lines);
                    storage.save(tasks);
                } catch (MissingTaskException e) {
                    lines.add("Where is LittleNuggy's todo?");
                    lines.add("(Ensure your todo is of the following format: \"todo [task]\".)");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case DEADLINE:
                try {
                    addTask(new Deadline(input), tasks, lines);
                    storage.save(tasks);
                } catch (MissingTaskException e) {
                    lines.add("When is LittleNuggy's deadline?");
                    lines.add("(Ensure your deadline is of the following format: \"deadline [task] /by [dueDate]\".)");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case EVENT:
                try {
                    addTask(new Event(input), tasks, lines);
                    storage.save(tasks);
                } catch (MissingTaskException e) {
                    lines.add("When is LittleNuggy's event?");
                    lines.add("(Ensure your event is of the following format: \"event [task] /from [start] /to [end]\".)");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case DELETE:
                try {
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    deleteTask(taskNumber, tasks, lines);
                    storage.save(tasks);
                } catch (NumberFormatException e) {
                    lines.add("FlyingNugget does have LittleNuggy's task number!");
                    lines.add("(Ensure your delete is of the following format: \"delete [number]\".)");
                } catch (IndexOutOfBoundsException e) {
                    lines.add("FlyingNugget does not see this task number on LittleNuggy's list!");
                    lines.add("(Type \"list\" to see all your current tasks and their numbers.)");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case UNKNOWN:
                lines.add("FlyingNugget has never heard that before!");
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
     * @param lines the current list of messages to print
     */
    private static void addTask(Task task, List<Task> tasks, List<String> lines) {
        tasks.add(task);
        lines.add("Ok~! FlyingNugget will help LittleNuggy add this task!");
        lines.add("  " + task.toString());
        if (tasks.size() == 1) {
            lines.add("Now LittleNuggy has 1 task on LittleNuggy's list!");
        } else {
            lines.add("Wow! LittleNuggy is getting busy!");
            lines.add("Now LittleNuggy has " + tasks.size() + " tasks on LittleNuggy's list!");
        }
    }

    /**
     * Deletes a task from the task list and prints its corresponding messages.
     *
     * @param taskNumber the index of the task to remove from tasks
     * @param tasks the current list of tasks
     * @param lines the current list of messages to print
     */
    private static void deleteTask(int taskNumber, List<Task> tasks, List<String> lines) {
        Task task = tasks.remove(taskNumber - 1);
        lines.add("Ok~! FlyingNugget will remove this task for LittleNuggy!");
        lines.add("  " + task.toString());
        if (tasks.size() == 1) {
            lines.add("Now LittleNuggy has 1 task on LittleNuggy's list!");
        } else {
            lines.add("Now LittleNuggy has " + tasks.size() + " tasks on LittleNuggy's list!");
        }
    }

    /**
     * Prints all messages per action in an enclosed box with a tab indent.
     *
     * @param messages one or more strings to print in the box
     */
    private static void printBox(String... messages) {
        System.out.println(LINE);
        for (String msg : messages) {
            System.out.println("\t" + msg);
        }
        System.out.println(LINE);
    }
}
