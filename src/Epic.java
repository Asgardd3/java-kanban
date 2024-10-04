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

    public void removeAll() {
        this.subTasksIds.clear();
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

}
