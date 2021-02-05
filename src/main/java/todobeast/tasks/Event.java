package todobeast.tasks;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * A Task that represents a event-type task. Events have a time and date component.
 */
public class Event extends Task {
    private LocalDate date;
    private LocalTime time;

    public Event(String taskDescription, LocalDate date, LocalTime time) {
        super(taskDescription);
        this.date = date;
        this.time = time;
    }

    public Event(String taskDescription, boolean isDone, LocalDate date, LocalTime time) {
        super(taskDescription, isDone);
        this.date = date;
        this.time = time;
    }

    public String formatForStorage(String delimiter) {
        return "EVENT" + delimiter + (isDone ? "1" : "0") + delimiter + taskDescription + delimiter + date.toString() + delimiter + time.toString();
    }

    @Override
    public String toString() {
        return "EVENT" + Task.TASK_DELIMITER + (isDone ? "done" : " ") + Task.TASK_DELIMITER + taskDescription + Task.TASK_DELIMITER + date.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ", " + time;
    }
}