package talkingpal.task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Marks the task as done by its 1-based task number.
     *
     * @param i 1-based task number.
     * @throws IllegalArgumentException If the task number is out of range.
     */
    public void markTask(int i) {
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
        int idx = i - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new IllegalArgumentException("Invalid task number: " + i);
        }
        tasks.get(idx).markAsUndone();
    }

    public void add(Task task) {
        assert task != null : "TaskList.add() should not receive null";
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
                filteredList.add(task);
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
        int idx = i - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new IllegalArgumentException("Invalid task number: " + i);
        }
        this.tasks.remove(idx);
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
