public class Task {
    private boolean done;
    private final String item;

    Task(String item) {
        this.done = false;
        this.item = item;
    }

    public boolean getDone() {
        return this.done;
    }

    public String getItem() {
        return this.item;
    }

    public String markAsDone() {
        this.done = true;
        return this.toString();
    }

    public String markAsUndone() {
        this.done = false;
        return this.toString();
    }
}
