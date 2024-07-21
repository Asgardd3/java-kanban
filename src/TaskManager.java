import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    public List<Task> getHistory();

    //Задачи

    void addTask(Task task);

    ArrayList<Task> getAllTasks();

    void deleteAllTasks();

    Task getTaskById(int id);

    void updateTask(Task task);

    void deleteTaskById(int id);

    void addSubTask(SubTask subTask);

    ArrayList<SubTask> getAllSubTasks();

    void deleteAllSubTasks();

    SubTask getSubTaskById(int id);

    void updateSubTask(SubTask subTask);

    void deleteSubTaskById(int id);

    void addEpic(Epic epic);

    ArrayList<Epic> getAllEpics();

    void deleteAllEpics();

    Epic getEpicById(int id);

    void updateEpic(Epic epic);

    void deleteEpicById(int id);

    ArrayList<SubTask> getAllSubTasksByEpicId(int id);
}
