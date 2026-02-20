package talkingpal.task;

// Used ChatGPT to make Task class abstract and include copy method for all subclasses
/**
 * Represents a task with a description and completion status.
 */
public abstract class Task {
    protected String name;
    protected boolean isDone;

    public Task(String name) {
        this(name, false);
    }

    public Task(String name, boolean isDone) {
        assert name != null && !name.isBlank() : "Task name must not be null/blank";
        this.name = name;
        this.isDone = isDone;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    public String getName() {
        return name;
    }

    public boolean isDone() {
        return isDone;
    }

    /** Returns a deep-copy of this Task object (used for UNDO snapshots). */
    public abstract Task copy();

    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + name;
    }
}
