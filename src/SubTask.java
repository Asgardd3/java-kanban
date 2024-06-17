import java.util.ArrayList;

public class SubTask extends Task {
    public Integer epicId;
    private Status status;
    ArrayList<SubTask> subTasks;

    public SubTask(String name, String description,Integer id) {
        super(name, description,id);

    }
    public SubTask(String name, String description,Integer id,Status status) {
        super(name, description,id,status);

    }
}
