import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTasksIds = new ArrayList<>();

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

    public Epic fromString(String string) {
        String[] data = string.split(";");
        String name = data[0];
        String description = data[1];
        Status status = Status.valueOf(data[2]);
        Epic epic = new Epic(name, description);
        epic.setStatus(status);
        return epic;
    }

    public void removeAll() {
        this.subTasksIds.clear();
    }
}
