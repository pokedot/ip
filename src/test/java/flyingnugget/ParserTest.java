package flyingnugget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import flyingnugget.parser.Parser;
import flyingnugget.storage.Storage;
import flyingnugget.task.TaskList;

public class ParserTest {
    private Storage storage;
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        storage = new Storage("./data/FlyingNuggetTest.txt");
        taskList = new TaskList(storage);
        try {
            taskList.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseAndExecute_todo_validString() {
        List<String> output = Parser.parseAndExecute("todo eat dinner", taskList);
        assertEquals("Ok~! FlyingNugget will help LittleNuggy add this task!", output.get(0));
        assertTrue(output.get(1).contains("[T][ ] eat dinner"));
        assertEquals(1, taskList.size());
    }
}
