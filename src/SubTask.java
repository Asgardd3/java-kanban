public class SubTask extends Task {
    private int epicId;

    public SubTask(String name, String description,Status status,int epicId) {
        super(name, description,status);
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
        return getId() + "," + TaskTypes.SUBTASK.name() + "," + getName() + ","  + getStatus() + "," + getDescription() + "," + getEpicId();
    }

    public static SubTask fromString(String s) {
        String[] data = s.split(",");
        String name = data[2];
        String description = data[4];
        Status status = Status.valueOf(data[3]);
        int epicId = Integer.parseInt(data[5]);
        SubTask subTask = new SubTask(name, description, status, epicId);
        subTask.setId(Integer.parseInt(data[0]));
        return subTask;
    }
}