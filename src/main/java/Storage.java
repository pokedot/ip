import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final String filePath;

    Storage(String filePath) {
        this.filePath = filePath;
    }

    public List<Task> load() throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        file.createNewFile();

        BufferedReader br = new BufferedReader(new FileReader(file));
        List<Task> tasks = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            Task task = Task.read(line);
            tasks.add(task);
        }
        return tasks;
    }

    public void save(List<Task> tasks) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (Task task : tasks) {
            bw.write(task.serialize());
            bw.newLine();
        }
    }
}
