import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;
    public static String lineDivider  = " ____________________________________________\n";

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
        System.out.println(lineDivider
                + "Added to task list: "
                + task
                + lineDivider);
    }

    public Task get(int i) {
        return tasks.get(i);
    }

    public void printAllTasks() {
        System.out.println(this.toString());
    }
    public int size() {
        return this.tasks.size();
    }

    @Override
    public String toString() {
        System.out.println();
        String line = String.format("Current Tasks (Total %d):\n", this.size());
        StringBuilder output = new StringBuilder(line);
        for (int i = 0; i < tasks.size(); i++) {
            String taskString = String.format("%d. %s", i + 1, tasks.get(i).toString());
            output.append(taskString);
        }
        return output.toString();
    }

}
