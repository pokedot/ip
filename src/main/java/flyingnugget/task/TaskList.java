package flyingnugget.task;

import flyingnugget.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;
    private final Storage storage;

    public TaskList(Storage storage) {
        this.tasks = new ArrayList<>();
        this.storage = storage;
    }

    public void loadTasks() throws IOException {
        List<Task> newTasks = this.storage.load();
        this.tasks.clear();
        this.tasks.addAll(newTasks);
    }

    public void saveTasks() throws IOException {
        this.storage.save(this.tasks);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void deleteTask(int taskNumber) {
        this.tasks.remove(taskNumber - 1);
    }

    public Task getTask(int taskNumber) {
        return this.tasks.get(taskNumber - 1);
    }

    public List<Task> getAllTasks() {
        return this.tasks;
    }

    public String markTaskAsDone(int taskNumber) {
        return this.getTask(taskNumber).markAsDone();
    }

    public String markTaskAsUndone(int taskNumber) {
        return this.getTask(taskNumber).markAsUndone();
    }

    public int size() {
        return this.tasks.size();
    }
}
