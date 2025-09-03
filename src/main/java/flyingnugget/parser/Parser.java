package flyingnugget.parser;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import flyingnugget.task.Deadline;
import flyingnugget.task.Event;
import flyingnugget.task.MissingTaskException;
import flyingnugget.task.Task;
import flyingnugget.task.TaskList;
import flyingnugget.task.Todo;

/**
 * Parses user input and executes the corresponding commands.
 * <p>
 * Also validates inputs, handling exceptions from invalid commands,
 * and returns helpful messages to guide the user toward valid usage.
 * </p>
 */
public class Parser {

    /**
     * Takes in a string input and executes various actions based on the action enum,
     * returning a list of strings as output to be shown to the user and modifying the task list if needed.
     *
     * @param input the input entered by the user
     * @param taskList the user's current list of tasks
     * @return a list of strings which are messages to be shown to the user
     */
    public static List<String> parseAndExecute(String input, TaskList taskList) {
        Action action = Action.findAction(input);
        List<String> lines = new ArrayList<>();

        switch (action) {
        case BYE:
            lines.add("Bye! See LittleNuggy soon!");
            break;
        case LIST:
            lines.add("Ok~! Here are LittleNuggy's tasks!");
            List<Task> tasks = taskList.getAllTasks();
            for (int i = 0; i < tasks.size(); i++) {
                lines.add((i + 1) + "." + tasks.get(i));
            }
            if (tasks.isEmpty()) {
                lines.add("  (Nothing to see here.)");
                lines.add("Seems like LittleNuggy is very free right now!");
            }
            break;
        case MARK:
            try {
                int taskNumber = Integer.parseInt(input.split(" ")[1]);
                String taskInfo = taskList.markTaskAsDone(taskNumber);
                taskList.saveTasks();
                lines.add("Sugoi desu! LittleNuggy finished LittleNuggy's task!");
                lines.add("  " + taskInfo);
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
                String taskInfo = taskList.markTaskAsUndone(taskNumber);
                taskList.saveTasks();
                lines.add("Nani? LittleNuggy lied to FlyingNugget?");
                lines.add("  " + taskInfo);
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
                lines.addAll(Parser.addTask(new Todo(input), taskList));
                taskList.saveTasks();
            } catch (MissingTaskException e) {
                lines.add("Where is LittleNuggy's todo?");
                lines.add("(Ensure your todo is of the following format: \"todo [task]\".)");
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case DEADLINE:
            try {
                lines.addAll(Parser.addTask(new Deadline(input), taskList));
                taskList.saveTasks();
            } catch (MissingTaskException e) {
                lines.add("When is LittleNuggy's deadline?");
                lines.add("(Ensure your deadline is of the following format: \"deadline [task] /by [dueDate]\".)");
            } catch (DateTimeParseException e) {
                lines.add("FlyingNugget needs a valid deadline to help LittleNuggy!");
                lines.add("(Ensure your deadline is in the following format: YYYY-MM-DD.)");
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case EVENT:
            try {
                lines.addAll(Parser.addTask(new Event(input), taskList));
                taskList.saveTasks();
            } catch (MissingTaskException e) {
                lines.add("When is LittleNuggy's event?");
                lines.add("(Ensure your event is of the following format: \"event [task] /from [start] /to [end]\".)");
            } catch (DateTimeParseException e) {
                lines.add("FlyingNugget needs a valid start and end date to help LittleNuggy!");
                lines.add("(Ensure your start and end dates are in the following format: YYYY-MM-DD.)");
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case DELETE:
            try {
                int taskNumber = Integer.parseInt(input.split(" ")[1]);
                lines.addAll(Parser.deleteTask(taskNumber, taskList));
                taskList.saveTasks();
            } catch (NumberFormatException e) {
                lines.add("FlyingNugget does not have LittleNuggy's task number!");
                lines.add("(Ensure your delete is of the following format: \"delete [number]\".)");
            } catch (IndexOutOfBoundsException e) {
                lines.add("FlyingNugget does not see this task number on LittleNuggy's list!");
                lines.add("(Type \"list\" to see all your current tasks and their numbers.)");
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case FIND:
            try {
                String keyword = input.split(" ", 2)[1];
                lines.add("Ok~! Here are all of LittleNuggy's related tasks!");
                List<Task> filteredTasks = taskList.find(keyword);
                for (Task filteredTask : filteredTasks) {
                    lines.add("  " + filteredTask);
                }
                if (filteredTasks.isEmpty()) {
                    lines.add("  (Nothing to see here.)");
                    lines.add("Seems like LittleNuggy has no related tasks with that keyword!");
                }
            } catch (IndexOutOfBoundsException e) {
                lines.add("FlyingNugget does not have a keyword to search with!");
                lines.add("(Ensure your find is of the following format: \"find [keyword(s)]\".)");
            }
            break;
        case UNKNOWN:
        default:
            lines.add("FlyingNugget has never heard that before!");
        }
        return lines;
    }

    /**
     * Adds a task to the task list if the action is a todo, deadline, or event,
     * and returns a list of corresponding messages to be shown to the user.
     *
     * @param task the task to be added to the task list
     * @param taskList the user's current list of tasks
     * @return a list of strings which are messages to be shown to the user
     */
    private static List<String> addTask(Task task, TaskList taskList) {
        List<String> lines = new ArrayList<>();
        taskList.addTask(task);
        lines.add("Ok~! FlyingNugget will help LittleNuggy add this task!");
        lines.add("  " + task.toString());
        if (taskList.size() == 1) {
            lines.add("Now LittleNuggy has 1 task on LittleNuggy's list!");
        } else {
            lines.add("Wow! LittleNuggy is getting busy!");
            lines.add("Now LittleNuggy has " + taskList.size() + " tasks on LittleNuggy's list!");
        }
        return lines;
    }

    /**
     * Removes a task from the task list if the action is a delete,
     * and returns a list of corresponding messages to be shown to the user.
     *
     * @param taskNumber the task number to be deleted from the task list
     * @param taskList the user's current list of tasks
     * @return a list of strings which are messages to be shown to the user
     */
    private static List<String> deleteTask(int taskNumber, TaskList taskList) {
        List<String> lines = new ArrayList<>();
        Task task = taskList.getTask(taskNumber);
        taskList.deleteTask(taskNumber);
        lines.add("Ok~! FlyingNugget will remove this task for LittleNuggy!");
        lines.add("  " + task.toString());
        if (taskList.size() == 1) {
            lines.add("Now LittleNuggy has 1 task on LittleNuggy's list!");
        } else {
            lines.add("Now LittleNuggy has " + taskList.size() + " tasks on LittleNuggy's list!");
        }
        return lines;
    }
}
