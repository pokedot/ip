package flyingnugget;

import flyingnugget.task.TaskList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {

    @Test
    public void testTodo() {
        Storage storage = new Storage("./data/FlyingNugget.txt");
        TaskList taskList = new TaskList(storage);

        List<String> output = Parser.parseAndExecute("todo eat dinner", taskList);
        assertEquals("Ok~! FlyingNugget will help LittleNuggy add this task!", output.get(0));
        assertTrue(output.get(1).contains("[T][ ] eat dinner"));
        assertEquals(1, taskList.size());
    }
}
