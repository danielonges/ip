import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDate date;
    private LocalTime time;

    public Event(String taskDescription, String dateAsString, String timeAsString) {
        super(taskDescription);
        this.date = LocalDate.parse(dateAsString);
        this.time = LocalTime.parse(timeAsString);
    }

    @Override
    public String toString() {
        return "[E][" + (done ? "X" : " ") + "] " + taskDescription + " (" + date.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ", " + time + ")";
    }
}
