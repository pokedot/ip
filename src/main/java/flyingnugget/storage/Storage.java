package flyingnugget.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import flyingnugget.task.Task;

/**
 * Handles saving and loading of task lists to and from a specified file path.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the task list from the file path.
     *
     * @return a list of tasks that corresponds to the content in the file path.
     * @throws IOException if an I/O error occurs while creating, opening, or reading the file.
     */
    public List<Task> load() throws IOException {
        assert filePath != null : "filePath should not be null";
        assert !filePath.isEmpty() : "filePath should not be empty";
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        file.createNewFile();
        List<Task> tasks = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Task task = Task.read(line);
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * Saves the task list to the file path.
     *
     * @throws IOException if an I/O error occurs while saving the file.
     */
    public void save(List<Task> tasks) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                bw.write(task.serializeString());
                bw.newLine();
            }
        }
    }
}
