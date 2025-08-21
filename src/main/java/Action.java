public enum Action {
    BYE,
    LIST,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    UNKNOWN;

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
