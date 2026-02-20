# TalkingPal

TalkingPal is a simple **CLI-based task manager** written in **Java 17**.  
It helps you track tasks (e.g., todos / deadlines / events), mark them as done, delete them, search for tasks, and **undo** recent changes.

---

## Features

- Add tasks to your list
- Mark / unmark tasks as done
- Delete tasks
- Find tasks by keyword (case-insensitive)
- **Undo** previous actions (supports multiple undos)

---

## Setting up in IntelliJ IDEA

**Prerequisites:** JDK 17, IntelliJ IDEA (latest version recommended)

1. Open IntelliJ IDEA  
   (If you’re not on the welcome screen, click `File` > `Close Project` first.)
2. Open the project:
   1. Click `Open`
   2. Select the project directory
   3. Click `OK`
   4. Accept default prompts if any appear
3. Configure the project to use **JDK 17**:
   - Follow JetBrains guide: https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk
   - Set **Project language level** to `SDK default`
4. Run the app:
   - Locate `src/main/java/talkingpal/Launcher.java`
   - Right-click > `Run talkingpal.Launcher.main()`

If setup is correct, you should see the program start in the Run console.

> **Warning:** Keep `src/main/java` as the root folder for Java source files. Tools like Gradle expect this structure.

---

## Usage

Talk to TalkingPal by typing commands into the console.

### Common commands (examples)

> Note: The exact command formats may differ depending on your current parser/command classes.

- Add a task
   - `todo read book`
   - `deadline return book /by 2026-02-21`
   - `event meetup /at 2026-03-01`
- List tasks
   - `list`
- Mark / unmark a task (1-based indexing)
   - `mark 1`
   - `unmark 1`
- Delete a task
   - `delete 2`
- Find tasks by keyword
   - `find book`
- Undo the most recent change
   - `undo`

---

## Undo behavior

TalkingPal supports **multi-level undo**.

- Before any mutating command (e.g., add/delete/mark/unmark), the task list takes a **deep-copy snapshot**
- These snapshots are stored in a stack
- `undo` reverts the task list to the most recent snapshot

This prevents the common Java pitfall where “old state” accidentally points to the same object as the current list.

---

## Project Structure (high-level)

- `talkingpal.task`
   - `Task` and task subclasses (e.g., Todo/Deadline/Event)
   - `TaskList` (stores tasks + undo stack)
- `talkingpal.*`
   - Parser / Commands / Exceptions 

