package talkingpal.task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;
    public static final String LINE_DIVIDER  = " ____________________________________________\n";

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void markTask(int i) {
        int idx = i - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new IllegalArgumentException("Invalid task number: " + i);
        }
        tasks.get(idx).markAsDone();
    }

    public void unmarkTask(int i) {
        int idx = i - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new IllegalArgumentException("Invalid task number: " + i);
        }
        tasks.get(idx).markAsUndone();
    }

    public void add(Task task) {
        tasks.add(task);
        System.out.println(LINE_DIVIDER
                + "Added to task list: "
                + task
                + LINE_DIVIDER);
    }

    /**
     * Finds tasks in task list that contains specific word in task description.
     *
     * @return String of tasks that contains given word
     * @param word Keyword to be used to find task.
     */
    public String find(String word) {
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

    public Task get(int i) {
        return tasks.get(i);
    }

    public void printAllTasks() {
        System.out.println(LINE_DIVIDER);
        System.out.println(this.toString());
        System.out.println(LINE_DIVIDER);
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
