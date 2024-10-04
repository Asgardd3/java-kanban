package tasks;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTasksIds = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
    }

    public ArrayList<Integer> getSubTasksIds() {
        return subTasksIds;
    }

    public void addSubTasksId(int subTasksId) {
        if (subTasksId != getId()) {
        this.subTasksIds.add(subTasksId);
        }
    }

    public void removeSubTasksId(int subTasksId) {
        if (subTasksIds != null) {
            subTasksIds.remove((Integer) subTasksId);
        }
    }

    @Override
    public String toString() {
        return getId() + "," + TaskTypes.EPIC.name() + "," + getName() + "," + getStatus() + "," + getDescription() + ",";
    }

    public static Epic fromString(String string) {
        String[] data = string.split(",");
        String name = data[2];
        String description = data[4];
        Status status = Status.valueOf(data[3]);
        Epic epic = new Epic(name, description);
        epic.setStatus(status);
        epic.setId(Integer.parseInt(data[0]));
        return epic;
    }

    public void removeAll() {
        this.subTasksIds.clear();
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

}
