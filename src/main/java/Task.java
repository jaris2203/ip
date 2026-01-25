public class Task {
    private String name;
    private boolean isDone;

    public Task(String name) {
        this.name = name;
        this.isDone = false; // Default false status
    }

    public void markAsDone() {
        this.isDone = true;
    }
    public String getName() {
        return name;
    }
    public boolean isDone() {
        return isDone;
    }
    public void editName(String newName) {
        this.name = newName;
    }

    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + name;
    }
}
