package talkingpal.task;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Represents a mutable list of {@link Task} objects.
 * <p>
 * This class supports common task list operations such as add, delete, mark and unmark.
 * It also provides an undo mechanism by storing deep-copied snapshots of the task list
 * before each mutating operation.
 * </p>
 * <p>
 * Undo is implemented using a stack of previous task list states. Each snapshot is a deep copy
 * of the current list (via {@link Task#copy()}) so that later mutations do not affect earlier states.
 * </p>
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private final Deque<ArrayList<Task>> undoStack;

    /**
     * Constructs an empty {@code TaskList} with an empty undo history.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        this.undoStack = new ArrayDeque<>();
    }

    /**
     * Marks the task as done by its 1-based task number.
     *
     * @param i 1-based task number.
     * @throws IllegalArgumentException If the task number is out of range.
     */
    public void markTask(int i) {
        snapshot();
        int idx = i - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new IllegalArgumentException("Invalid task number: " + i);
        }
        tasks.get(idx).markAsDone();
    }

    /**
     * Marks the task as not done by its 1-based task number.
     *
     * @param i 1-based task number.
     * @throws IllegalArgumentException If the task number is out of range.
     */
    public void unmarkTask(int i) {
        snapshot();
        int idx = i - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new IllegalArgumentException("Invalid task number: " + i);
        }
        tasks.get(idx).markAsUndone();
    }

    public void add(Task task) {
        assert task != null : "TaskList.add() should not receive null";
        snapshot();
        tasks.add(task);
    }


    /**
     * Finds tasks in task list that contains specific word in task description.
     *
     * @return String of tasks that contains given word
     * @param word Keyword to be used to find task.
     */
    public String find(String word) {
        assert word != null : "find word must not be null";
        word = word.toLowerCase();
        TaskList filteredList = new TaskList();
        for (Task task : tasks) {
            if (task.getName().toLowerCase().contains(word)) {
                filteredList.addWithoutUndo(task);
            }
        }
        String line = String.format("Total %d matching tasks found:\n", filteredList.size());
        StringBuilder output = new StringBuilder(line);
        for (int i = 0; i < filteredList.size(); i++) {
            String taskString = String.format("%d. %s", i + 1, filteredList.get(i).toString());
            output.append(taskString);
        }
        return output.toString();
    }

    /**
     * Returns the task at the given index (0-based).
     *
     * @param i 0-based index into the underlying list.
     * @return Task at the given index.
     */
    public Task get(int i) {
        assert i >= 0 && i < tasks.size() : "TaskList.get uses 0-based index";
        return tasks.get(i);
    }

    public int size() {
        return this.tasks.size();
    }

    public void delete(int i) {
        snapshot();
        int idx = i - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new IllegalArgumentException("Invalid task number: " + i);
        }
        this.tasks.remove(idx);
    }

    // Used ChatGPT to help debug and improve Undo method. The improved version now uses a stack of
    // all previous task list states that allows us to undo multiple commands instead of only the most recent.
    /**
     * Reverts the task list to the most recent snapshot taken before the last mutating command.
     *
     * @throws IllegalStateException if there is no earlier state to revert to.
     */
    public void undo() {
        if (undoStack.isEmpty()) {
            throw new IllegalStateException("Nothing to undo.");
        }
        tasks = undoStack.pop();
    }

    /**
     * Saves a deep-copied snapshot of the current task list onto the undo stack.
     * <p>
     * Each {@link Task} is copied via {@link Task#copy()} to prevent later mutations
     * from affecting previously saved states.
     * </p>
     */
    private void snapshot() {
        ArrayList<Task> snap = new ArrayList<>(tasks.size());
        for (Task t : tasks) {
            snap.add(t.copy()); // requires Task.copy() implemented in subclasses
        }
        undoStack.push(snap);
    }

    private void addWithoutUndo(Task task) {
        tasks.add(task);
    }

    @Override
    public String toString() {
        String line = String.format("Current Tasks (Total %d):\n", this.size());
        StringBuilder output = new StringBuilder(line);
        for (int i = 0; i < tasks.size(); i++) {
            String taskString = String.format("%d. %s", i + 1, tasks.get(i).toString());
            output.append(taskString);
        }
        return output.toString();
    }

}
