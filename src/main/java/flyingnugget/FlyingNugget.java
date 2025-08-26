package flyingnugget;

import flyingnugget.task.TaskList;

import java.util.List;
import java.util.Scanner;

/**
 * The FlyingNugget class is a task management chatbot
 * that allows users to add/delete tasks and mark/unmark their completions.
 * It supports Todo, Event, and Deadline tasks.
 */
public class FlyingNugget {

    /**
     * The main entry point for the chatbot.
     * Initializes the application and starts the main loop.
     *
     * @param args (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        Storage storage = new Storage("./data/FlyingNugget.txt");

        TaskList taskList = new TaskList(storage);
        try {
            taskList.loadTasks();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Ui.showIntro();
        while (isRunning) {
            String input = scanner.nextLine().strip();
            try {
                List<String> messages = Parser.parseAndExecute(input, taskList);
                Ui.showMessages(messages.toArray(new String[0]));
                if (input.equalsIgnoreCase("bye")) {
                    isRunning = false;
                }
            } catch (Exception e) {
                Ui.showMessage("An error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
