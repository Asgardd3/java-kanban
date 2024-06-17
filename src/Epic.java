import java.util.ArrayList;
public class Epic extends Task {
    private ArrayList<Integer> subTasksId = new ArrayList<>();
    private Status status;

    public Epic(String name, String description, ArrayList<SubTask> subTasks, Status status, Integer id) {
        super(name, description, id);
    }

    public Epic(String name, String description, Integer id) {
        super(name, description, id);
    }

    public ArrayList<Integer> getSubTasksId() {
        return subTasksId;
    }

    public void addSubTasksId(Integer subTasksId) {
        this.subTasksId.add(subTasksId);
    }

    public void removeSubTasksId(Integer subTasksId) {
        for (int i = 0; i < this.subTasksId.size(); i++) {
            if (this.subTasksId.get(i).equals(subTasksId)) {
                this.subTasksId.remove(i);
                break;

            }
            //this.subTasksId.remove(subTasksId);

        }
    }
}
