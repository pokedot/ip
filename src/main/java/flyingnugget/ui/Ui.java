package flyingnugget.ui;

/**
 * Handles all user interface outputs for the {@code FlyingNugget} chatbot.
 */
public class Ui {
    private static final String INTRO = """
         _____ _       _             _   _                        _
        |  ___| |_   _(_)_ __   __ _| \\ | |_   _  __ _  __ _  ___| |_
        | |_  | | | | | | '_ \\ / _` |  \\| | | | |/ _` |/ _` |/ _ \\ __|
        |  _| | | |_| | | | | | (_| | |\\  | |_| | (_| | (_| |  __/ |_
        |_|   |_|\\__, |_|_| |_|\\__, |_| \\_|\\__,_|\\__, |\\__, |\\___|\\__|
                 |___/         |___/             |___/ |___/
        """;
    private static final String LINE = "\t____________________________________________________________";

    /**
     * Prints the introduction message when the chatbot starts.
     */
    public static void showIntro() {
        System.out.println(INTRO);
        printBox("Yaho-! FlyingNugget's FlyingNugget!",
                "What can FlyingNugget do for LittleNuggy?");
    }

    public static void showMessages(String[] messages) {
        printBox(messages);
    }

    public static void showMessage(String message) {
        printBox(message);
    }

    /**
     * Prints all messages per {@code Action} in an enclosed box with a tab indent.
     *
     * @param messages one or more strings to print in the box.
     */
    private static void printBox(String... messages) {
        System.out.println(LINE);
        for (String msg : messages) {
            System.out.println("    " + msg);
        }
        System.out.println(LINE);
    }
}
