package flyingnugget;

import java.util.List;
import java.util.Scanner;

import flyingnugget.parser.Parser;
import flyingnugget.storage.Storage;
import flyingnugget.task.TaskList;
import flyingnugget.ui.Ui;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

/**
 * A task management chatbot that allows users to add, delete,
 * mark, and unmark tasks.
 * <p>
 * Supports three types of tasks: {@code Todo}, {@code Event}, and {@code Deadline}.
 * </p>
 */
public class FlyingNugget {
    private final TaskList taskList;
    private boolean isRunning = true;

    /**
     * Creates a {@code FlyingNugget} chatbot instance.
     */
    public FlyingNugget() {
        Storage storage = new Storage("./data/FlyingNugget.txt");
        this.taskList = new TaskList(storage);
        try {
            this.taskList.loadTasks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The main entry point for the text-based UI of the chatbot.
     * Initializes the application and starts the main loop.
     *
     * @param args (not used)
     */
    public static void main(String[] args) {
        new FlyingNugget().run();
    }

    /**
     * Runs the FlyingNugget chatbot.
     * Initializes storage and loads tasks, then processes user commands until "bye" is entered.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
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
     * The main method to retrieve responses for the GUI of the chatbot.
     * If a "bye" is entered by the user, there is a brief delay before the app closes.
     *
     * @param input the message entered by the user
     * @return a String of the chatbot's response to the user input
     */
    public String getResponse(String input) {
        List<String> messages = Parser.parseAndExecute(input, taskList);
        String response = String.join("\n", messages);
        if (input != null && input.trim().equalsIgnoreCase("bye")) {
            Platform.runLater(() -> {
                PauseTransition delay = new PauseTransition(Duration.millis(1000));
                delay.setOnFinished(event -> Platform.exit());
                delay.play();
            });
        }
        return response;
    }
}
