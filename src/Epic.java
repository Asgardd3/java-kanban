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
        this.subTasksIds.add(subTasksId);
    }

    public void removeSubTasksId(int subTasksId) {
        if (subTasksIds != null) {
            subTasksIds.remove((Integer) subTasksId);
        }
    }

    public void removeAll() {
        this.subTasksIds.clear();
    }
}
