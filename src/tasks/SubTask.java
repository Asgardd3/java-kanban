package tasks;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String name, String description, Status status, int epicId, LocalDateTime startDate, Duration duration) {
        super(name, description, status, startDate, duration);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int id) {
        if (id != this.getId()) {
            this.epicId = id;
        }
    }

    @Override
    public String toString() {
        return getId() + "," + TaskTypes.SUBTASK.name() + "," + getName() + "," + getStatus() + "," + getDescription() + "," + getEpicId() + "," + getDuration() + "," + getStartTime();
    }

    public static SubTask fromString(String s) {
        String[] data = s.split(",");
        String name = data[2];
        String description = data[4];
        Status status = Status.valueOf(data[3]);
        int epicId = Integer.parseInt(data[5]);
        LocalDateTime startTime;
        Duration duration;
        if (!data[5].equals("null")) {
            startTime = LocalDateTime.parse(data[5]);
        }

        if (!data[6].equals("null")) {
            duration = Duration.parse(data[6]);
        }
        SubTask subTask = new SubTask(name, description, status, epicId, LocalDateTime.parse(data[6]), Duration.parse(data[7]));
        subTask.setId(Integer.parseInt(data[0]));
        return subTask;
    }
}