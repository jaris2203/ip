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

---

## Project Structure (high-level)

- `talkingpal.task`
   - `Task` and task subclasses (e.g., Todo/Deadline/Event)
   - `TaskList` (stores tasks + undo stack)
- `talkingpal.*`
   - Parser / Commands / Exceptions / Ui

