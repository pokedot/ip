package flyingnugget.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import flyingnugget.storage.Storage;

/**
 * Represents a list of {@code Task} objects in the FlyingNugget application.
 * Provides operations to add, delete, mark, unmark and get tasks.
 */
public class TaskList {
    private final List<Task> tasks;
    private final Storage storage;


    /**
     * Creates a new {@code TaskList} and tracks its corresponding storage path.
     *
     * @param storage the file path for the task list to be saved or loaded.
     */
    public TaskList(Storage storage) {
        this.tasks = new ArrayList<>();
        this.storage = storage;
    }

    /**
     * Loads the tasks from the storage and replaces the existing tasks in the {@code TaskList}.
     *
     * @throws IOException if an I/O error occurs while loading from storage.
     */
    public void loadTasks() throws IOException {
        List<Task> newTasks = this.storage.load();
        this.tasks.clear();
        this.tasks.addAll(newTasks);
    }

    /**
     * Saves the current list of tasks to the storage.
     *
     * @throws IOException if an I/O error occurs while saving to storage.
     */
    public void saveTasks() throws IOException {
        this.storage.save(this.tasks);
    }

    /**
     * Adds a {@code Task} to the {@code TaskList}.
     *
     * @param task the task to be added.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes a {@code Task} from the {@code TaskList} using 1-indexing.
     *
     * @param taskNumber the task number to be deleted.
     */
    public void deleteTask(int taskNumber) {
        assert taskNumber > 0 : "taskNumber should be positive";
        assert taskNumber <= tasks.size() : "taskNumber should be equal or smaller than the number of tasks";
        this.tasks.remove(taskNumber - 1);
    }

    /**
     * Gets a {@code Task} from the {@code TaskList} using 1-indexing.
     *
     * @param taskNumber the task number to be retrieved.
     */
    public Task getTask(int taskNumber) {
        assert taskNumber > 0 : "taskNumber should be positive";
        assert taskNumber <= tasks.size() : "taskNumber should be equal or smaller than the number of tasks";
        return this.tasks.get(taskNumber - 1);
    }

    /**
     * Returns all tasks currently in the {@code TaskList}.
     *
     * @return the list of tasks.
     */
    public List<Task> getAllTasks() {
        return this.tasks;
    }

    /**
     * Marks the corresponding {@code Task} as done using 1-indexing.
     *
     * @param taskNumber the task number to be marked.
     * @return the string representation of the marked task.
     */
    public String markTaskAsDone(int taskNumber) {
        return this.getTask(taskNumber).markAsDone();
    }

    /**
     * Marks the corresponding {@code Task} as undone using 1-indexing.
     *
     * @param taskNumber the task number to be unmarked.
     * @return the string representation of the unmarked task.
     */
    public String markTaskAsUndone(int taskNumber) {
        return this.getTask(taskNumber).markAsUndone();
    }

    /**
     * Searches the {@code TaskList} for all tasks that match a given keyword or keywords.
     *
     * @param keyword the string to search for in the {@code TaskList}.
     * @return the list of tasks that match the given keyword.
     */
    public List<Task> find(String keyword) {
        return this.tasks.stream()
                .filter(x -> x.getItem().contains(keyword))
                .toList();
    }

    /**
     * Returns the current size of the {@code TaskList}.
     *
     * @return the number of tasks.
     */
    public int size() {
        return this.tasks.size();
    }
}
