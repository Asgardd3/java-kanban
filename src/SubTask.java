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
}
