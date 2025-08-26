package flyingnugget;

import flyingnugget.task.*;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

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
        case UNKNOWN:
            lines.add("FlyingNugget has never heard that before!");
        }
        return lines;
    }

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
