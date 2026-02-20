## Use of AI Assistance (ChatGPT)

I used ChatGPT as a coding assistant for two main areas in this project:

1. **Undo functionality design and bug-fixing**
2. **Writing and improving Javadocs**

---

## 1) Undo Functionality

### Problem encountered
My initial UNDO implementation was bugged because I stored the “old task list” as a reference:

- I did `oldTasks = tasks;`
- This caused `oldTasks` to point to the same `ArrayList` object as `tasks`
- Any later modifications to `tasks` also affected `oldTasks`, so UNDO did not restore the original state correctly.

### AI guidance received
ChatGPT explained the difference between:
- **Reference assignment** (aliasing) vs
- **Copying / snapshotting state**

It recommended using a **snapshot approach** before every mutating operation, and highlighted that:

- A *shallow copy* (`new ArrayList<>(tasks)`) is not sufficient if `Task` objects are mutable (e.g., mark/unmark changes the `Task` object).
- A **deep copy** is required for correct undo when tasks are mutated.

### What I implemented
I implemented an **undo stack** that stores deep-copied snapshots of the task list:

- Added an `undoStack` (`Deque<ArrayList<Task>>`) to store previous states.
- Added a `snapshot()` method that deep-copies the task list by calling `Task.copy()` on each task.
- Called `snapshot()` before every mutating operation (`add`, `delete`, `markTask`, `unmarkTask`).
- Implemented `undo()` by popping the latest snapshot from `undoStack`.

This enabled **multiple levels of undo**, instead of only toggling between two states.

---

## 2) Javadocs

### AI guidance received
ChatGPT helped generate and refine Javadocs to ensure they were:
- Consistent with what each method actually does
- Clear about index conventions (0-based vs 1-based)
- Explicit about side effects (mutating methods vs non-mutating methods like `find`)
- Explicit about error cases (`IllegalArgumentException`, `IllegalStateException`)
- Documented private helper methods when they affect important behavior (e.g., snapshotting)

### What I implemented
I added:
- Class-level documentation describing responsibilities and undo design
- Method-level documentation for all public methods (params, returns, throws)
- Documentation for helper methods (`snapshot()` and `addWithoutUndo()`), especially where they affect undo history
