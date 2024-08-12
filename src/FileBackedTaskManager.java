import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager implements TaskManager extends {

    //Пусть новый менеджер получает файл для автосохранения в своём конструкторе и сохраняет его в поле.
    private String filePath;

    public FileBackedTaskManager(String filePath) {
        this.filePath = filePath;
        //Читаем из файл список задач
        tasks = readTasksFromFile(filePath);
        Files.readStr
    }
    //Создайте метод save без параметров — он будет сохранять текущее состояние менеджера в указанный файл.
    public void save() {
        try (Writer out = new FileWriter(this.filePath)){
            //Сохраняем список задач в файл

            for (Task task : tasks) {
                out.write(task + "\n");
            }

            for (Subtask subtask : subtasks) {
                out.write(subtask + "\n");
            }

            for (Epic epic : epics){
                out.write(epic + "\n");
            }

        }
        catch (Exception e) {
            System.out.println("Произошла ошибка во время записи файла.");
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void deleteSubTaskById(int id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }
}
