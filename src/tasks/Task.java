package tasks;
import java.util.Objects;


public class Task {
    private String name;
    private String description;
    private int id;
    private Status status;

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

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        //this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        return id + "," + TaskTypes.TASK.name() + "," + name + ","  + status + "," + description + ",";
    }

    public static Task fromString(String s) {
        String[] parts = s.split(",");
        Task task = new Task(parts[2], parts[4], Status.valueOf(parts[3]));
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

}
