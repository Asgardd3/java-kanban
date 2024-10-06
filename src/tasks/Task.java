package tasks;
import java.time.Duration;
import java.time.LocalDateTime;

import java.util.Objects;


public class Task {
    private String name;
    private String description;
    private int id;
    private Status status;
    private Duration duration;
    private LocalDateTime startTime;


    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), id);
    }

    public Task(String name, String description, Status status, LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.description = description;
        //this.id = id;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return id + "," + TaskTypes.TASK.name() + "," + name + "," + status + "," + description + "," + startTime + "," + duration;
    }

    public static Task fromString(String s) {
        String[] parts = s.split(",");
        LocalDateTime startTime = null;
        Duration duration = null;
        if (!parts[5].equals("null")) {
            startTime = LocalDateTime.parse(parts[5]);
        }

        if (!parts[6].equals("null")) {
            duration = Duration.parse(parts[6]);
        }
        Task task = new Task(parts[2], parts[4], Status.valueOf(parts[3]), startTime, duration);
        task.setId(Integer.parseInt(parts[0]));
        return task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        if (startTime == null || duration == null) {
            return null;
        }
        return startTime.plus(duration);
    }

}
