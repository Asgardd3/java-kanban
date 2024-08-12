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

    public SubTask fromString(String s){
        String[] data = s.split(";");
        String name = data[0];
        String description = data[1];
        Status status = Status.valueOf(data[2]);
        int epicId = Integer.parseInt(data[3]);
        return new SubTask(name, description, status, epicId);
    }


}
