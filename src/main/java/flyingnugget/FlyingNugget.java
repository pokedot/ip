package flyingnugget;

import java.util.List;
import java.util.Scanner;

import flyingnugget.task.TaskList;

/**
 * A task management chatbot that allows users to add, delete,
 * mark, and unmark tasks.
 * <p>
 * Supports three types of tasks: {@code Todo}, {@code Event}, and {@code Deadline}.
 * </p>
 */
public class FlyingNugget {

    /**
     * Runs the FlyingNugget chatbot.
     * Initializes storage and loads tasks, then processes user commands until "bye" is entered.
     */
    public void run() {
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

    /**
     * The main entry point for the chatbot.
     * Initializes the application and starts the main loop.
     *
     * @param args (not used)
     */
    public static void main(String[] args) {
        new FlyingNugget().run();
    }
}
