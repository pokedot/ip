package flyingnugget;

/**
 * Represents the different types of commands or actions
 * that can be issued by the user in the application.
 * <p>
 * This enum defines supported commands such as creating tasks
 * ({@code TODO}, {@code DEADLINE}, {@code EVENT}),
 * modifying tasks ({@code MARK}, {@code UNMARK}),
 * deleting tasks ({@code DELETE}),
 * retrieving task information ({@code LIST}, {@code FIND}),
 * and handling unrecognized input ({@code UNKNOWN}).
 * </p>
 */
public enum Action {
    BYE,
    LIST,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    FIND,
    UNKNOWN;

    /**
     * Matches a string input to a corresponding Action enum, which is not case-sensitive.
     * If a match cannot be found, UNKNOWN is returned.
     *
     * @param input the user input string
     * @return the corresponding Action
     */
    public static Action findAction(String input) {
        if (input == null || input.isBlank()) {
            return UNKNOWN;
        }
        try {
            String command = input.strip().split(" ")[0].toUpperCase();
            return Action.valueOf(command);
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
