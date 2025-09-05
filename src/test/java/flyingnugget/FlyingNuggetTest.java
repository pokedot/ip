package flyingnugget;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class FlyingNuggetTest {

    @Test
    public void testActions_validStrings() throws IOException {
        Path inputPath = Paths.get("src/test/java/flyingnugget/testActions_validStrings_input.txt");
        String testInput = Files.readString(inputPath);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            FlyingNugget bot = new FlyingNugget();
            bot.run();
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }

        Path expectedPath = Paths.get("src/test/java/flyingnugget/testActions_validStrings_expectedOutput.txt");
        List<String> expectedOutput = Files.readAllLines(expectedPath);

        String actualOutput = outputStream.toString().trim();
        List<String> actualLines = Arrays.asList(actualOutput.split("\\R"));

        assertEquals(expectedOutput.size(), actualLines.size(), "Number of output lines do not match");
        for (int i = 0; i < expectedOutput.size(); i++) {
            assertEquals(expectedOutput.get(i).trim(), actualLines.get(i).trim(), "Mismatch at line " + (i + 1));
        }

    }
}
