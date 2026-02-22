# TalkingPal User Guide

TalkingPal is a lightweight **CLI chatbot** that helps you manage tasks quickly using typed commands. It supports **To-Dos**, **Deadlines**, and **Events**, and lets you **list**, **complete**, **delete**, and **search** tasks.

---

## Features

### Add a To-Do

Adds a task with only a description.

**Format**
- `todo DESCRIPTION`

**Example**
- `todo read CS2103T weekly schedule`

---

### Add a Deadline

Adds a task due by a date.

**Format**
- `deadline DESCRIPTION /by DATE`

**Example**
- `deadline submit iP /by 2026-03-01`

---

### Add an Event

Adds a task that occurs within a time range.

**Format**
- `event DESCRIPTION /from DATE /to DATE`

**Examples**
- `event nus open house /from 2026-02-23 /to 2026-02-24`
- `event project sprint /from 2026-03-01 /to 2026-03-14`

**Notes**
- `DESCRIPTION` must not be blank.
- `/from` and `/to` must be present.
- Start date should be **on or before** end date.

---

### List tasks

Shows all tasks currently stored.

**Format**
- `list`

---

### Mark a task as done

Marks a task (by index) as completed.

**Format**
- `mark INDEX`

**Example**
- `mark 2`

---

### Unmark a task

Marks a completed task as not done.

**Format**
- `unmark INDEX`

**Example**
- `unmark 2`

---

### Delete a task

Removes a task from the list.

**Format**
- `delete INDEX`

**Example**
- `delete 3`

---

### Find tasks by keyword

Searches tasks whose description contains the keyword.

**Format**
- `find KEYWORD`

**Example**
- `find tutorial`

---

### Undo the last change

Reverts the most recent modifying command (e.g., add / delete / mark / unmark).

**Format**
- `undo`

> Tip: Use `undo` to recover quickly if you deleted/marked the wrong task.

---

### Exit TalkingPal

Closes the program.

**Format**
- `bye`

---

## Date format

Use `YYYY-MM-DD` (e.g., `2026-02-23`) for the most reliable parsing.

---

## Data saving

TalkingPal saves tasks locally so your list persists between runs (unless the data file is deleted).

---

## Common errors & fixes

### “Command me regarding events properly...”
Likely causes:
- Missing `/from` or `/to`
- Wrong order of parameters

Use:
- `event DESCRIPTION /from DATE /to DATE`

### Empty description
Commands like `todo`, `deadline`, and `event` require a non-empty description.

### Invalid index
Run `list` to see valid task numbers, then retry with a valid `INDEX`.

---