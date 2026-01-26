import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

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
    }

    public Task get(int i) {
        return tasks.get(i);
    }

    public void printAllTasks() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        System.out.println();
        StringBuilder output = new StringBuilder("Current Tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            String taskString = String.format("%d. %s", i + 1, tasks.get(i).toString());
            output.append(taskString);
        }
        return output.toString();
    }

}
