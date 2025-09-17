package flyingnugget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import flyingnugget.storage.Storage;
import flyingnugget.task.Deadline;
import flyingnugget.task.Event;
import flyingnugget.task.MissingTaskException;
import flyingnugget.task.Task;
import flyingnugget.task.TaskList;
import flyingnugget.task.Todo;

public class TaskListTest {

    @TempDir
    Path tempDir;

    private TaskList taskList;
    private Path dataFile;

    @BeforeEach
    public void setUp() throws IOException {
        dataFile = tempDir.resolve("FlyingNuggetTest.txt");
        Storage storage = new Storage(dataFile.toString());
        taskList = new TaskList(storage);
        taskList.clear();
    }

    @Test
    public void addAndGetTask_success() throws MissingTaskException {
        taskList.addTask(new Todo("todo read book"));
        taskList.addTask(new Todo("todo write code"));

        assertEquals(2, taskList.size());
        assertEquals("read book", taskList.getTask(1).getItem());
        assertEquals("write code", taskList.getTask(2).getItem());
    }

    @Test
    public void deleteTask_validIndex_removesCorrectTask() throws MissingTaskException {
        taskList.addTask(new Todo("todo task one"));
        taskList.addTask(new Todo("todo task two"));
        taskList.addTask(new Todo("todo task three"));

        taskList.deleteTask(2);
        assertEquals(2, taskList.size());
        assertEquals("task one", taskList.getTask(1).getItem());
        assertEquals("task three", taskList.getTask(2).getItem());
    }

    @Test
    public void markAndUnmark_validTask_updatesStateAndToString() throws MissingTaskException {
        taskList.addTask(new Todo("todo hydrate"));

        String marked = taskList.markTaskAsDone(1);
        assertTrue(taskList.getTask(1).getIsDone());
        assertTrue(marked.startsWith("[T][X] "));

        String unmarked = taskList.markTaskAsUndone(1);
        assertFalse(taskList.getTask(1).getIsDone());
        assertTrue(unmarked.startsWith("[T][ ] "));
    }

    @Test
    public void find_caseInsensitiveKeyword_returnsTask() throws MissingTaskException {
        taskList.addTask(new Todo("todo buy milk"));
        taskList.addTask(new Todo("todo buy cookies"));
        taskList.addTask(new Todo("todo study math"));

        List<Task> buy = taskList.find("buy");
        assertEquals(2, buy.size());
        assertTrue(buy.stream().allMatch(t -> t.getItem().contains("buy")));

        List<Task> none = taskList.find("Buy");
        assertEquals(0, none.size());
    }

    @Test
    public void taskListFind_multipleTasks() throws MissingTaskException {
        taskList.addTask(new Todo("todo buy groceries"));
        taskList.addTask(new Todo("todo buy milk"));
        taskList.addTask(new Todo("todo study for exam"));
        taskList.addTask(new Todo("todo buy books"));

        List<Task> buyTasks = taskList.find("buy");
        assertEquals(3, buyTasks.size());
        assertTrue(buyTasks.stream().allMatch(task -> task.getItem().contains("buy")));

        List<Task> milkTasks = taskList.find("milk");
        assertEquals(1, milkTasks.size());
        assertEquals("buy milk", milkTasks.get(0).getItem());

        List<Task> emptyTasks = taskList.find("nonexistent");
        assertEquals(0, emptyTasks.size());

        List<Task> caseSensitiveTasks = taskList.find("Buy");
        assertEquals(0, caseSensitiveTasks.size());
    }

    @Test
    public void reschedule_deadline_updatesDate() throws MissingTaskException {
        taskList.addTask(new Deadline("deadline submit report /by 2025-12-31"));
        assertTrue(taskList.canRescheduleWith(1, "/by 2026-01-01"));
        Task updated = taskList.reschedule(1, "/by 2026-01-01");
        assertTrue(updated.toString().contains("2026"));
    }

    @Test
    public void reschedule_event_updatesDates() throws MissingTaskException {
        taskList.addTask(new Event("event conference /from 2025-01-10 /to 2025-01-12"));
        assertTrue(taskList.canRescheduleWith(1, "/from 2025-02-01 /to 2025-02-03"));
        Task updated = taskList.reschedule(1, "/from 2025-02-01 /to 2025-02-03");
        assertTrue(updated.toString().contains("Feb"));
    }

    @Test
    public void reschedule_todo_noUpdate() throws MissingTaskException {
        taskList.addTask(new Todo("todo simple"));
        assertFalse(taskList.canRescheduleWith(1, "/by 2025-01-01"));
        Task same = taskList.reschedule(1, "/by 2025-01-01");
        assertEquals("simple", same.getItem());
    }

    @Test
    public void saveAndLoad_roundTrip_persistsTasks() throws Exception {
        taskList.addTask(new Todo("todo alpha"));
        taskList.addTask(new Deadline("deadline beta /by 2025-12-25"));
        taskList.addTask(new Event("event gamma /from 2025-01-01 /to 2025-01-02"));

        taskList.saveTasks();
        assertTrue(Files.size(dataFile) > 0);

        TaskList reloaded = new TaskList(new Storage(dataFile.toString()));
        reloaded.loadTasks();
        assertEquals(3, reloaded.size());
        assertTrue(reloaded.getTask(1).toString().startsWith("[T]"));
        assertTrue(reloaded.getTask(2).toString().startsWith("[D]"));
        assertTrue(reloaded.getTask(3).toString().startsWith("[E]"));
    }

    @Test
    public void getTask_invalidIndex_assertion() throws MissingTaskException {
        taskList.addTask(new Todo("todo only one"));
        assertThrows(AssertionError.class, () -> taskList.getTask(0));
        assertThrows(AssertionError.class, () -> taskList.getTask(2));
    }

    @Test
    public void deleteTask_invalidIndex_assertion() throws MissingTaskException {
        taskList.addTask(new Todo("todo only one"));
        assertThrows(AssertionError.class, () -> taskList.deleteTask(0));
        assertThrows(AssertionError.class, () -> taskList.deleteTask(2));
    }
}
