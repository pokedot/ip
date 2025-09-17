package flyingnugget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import flyingnugget.task.Deadline;
import flyingnugget.task.Event;
import flyingnugget.task.Task;
import flyingnugget.task.Todo;

public class TaskTest {

    @Test
    public void taskRead_differentTaskTypes() {
        String todoSerialized = "T|false|complete homework";
        Task todoTask = Task.read(todoSerialized);
        assertInstanceOf(Todo.class, todoTask);
        assertEquals("complete homework", todoTask.getItem());
        assertFalse(todoTask.getIsDone());

        String deadlineSerialized = "D|true|submit report|2024-12-25";
        Task deadlineTask = Task.read(deadlineSerialized);
        assertInstanceOf(Deadline.class, deadlineTask);
        assertEquals("submit report", deadlineTask.getItem());
        assertTrue(deadlineTask.getIsDone());

        String eventSerialized = "E|false|team meeting|2024-01-15|2024-01-15";
        Task eventTask = Task.read(eventSerialized);
        assertInstanceOf(Event.class, eventTask);
        assertEquals("team meeting", eventTask.getItem());
        assertFalse(eventTask.getIsDone());

        String invalidSerialized = "X|false|invalid task";
        assertThrows(IllegalArgumentException.class, () -> Task.read(invalidSerialized));
    }
}
