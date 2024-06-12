public class Task {
    private String name;
    private String description;
    private static Integer id = 1;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
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

    public static Integer getId() {
        return id;
    }

    public static void setId(Integer id) {
        Task.id = id;
    }
}
