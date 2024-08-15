import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

    //Пусть новый менеджер получает файл для автосохранения в своём конструкторе и сохраняет его в поле.
    private String filePath;

    public FileBackedTaskManager(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file.getPath());
        try {
            //Пропустить первую строку файла
            String fileStr = Files.readString(file.toPath());
            ArrayList<String> fileStrList = new ArrayList<>(Arrays.asList(fileStr.split("\n")));
            fileStrList.remove(0);
            for (String str : fileStrList) {
                String[] parts = str.split(",");
                switch (TaskTypes.valueOf(parts[1])) {
                    case TASK:
                        taskManager.addTask(Task.fromString(str));
                        break;
                    case SUBTASK:
                        taskManager.addSubTask(SubTask.fromString(str));
                        break;
                    case EPIC:
                        taskManager.addEpic(Epic.fromString(str));
                        break;
                }
            }

            return taskManager;
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return new FileBackedTaskManager(file.getPath());
        }




    }

    //Создайте метод save без параметров — он будет сохранять текущее состояние менеджера в указанный файл.
    public void save() throws ManagerSaveException {
        try (Writer out = new FileWriter(this.filePath)){
            //Сохраняем список задач в файл
            //Сохраняем заголовок
            out.write("id,type,name,status,description,epic\n");
            for (Task task : getAllTasks()) {
                out.write(task + "\n");
            }

            for (SubTask subtask : getAllSubTasks()) {
                out.write(subtask + "\n");
            }

            for (Epic epic : getAllEpics()) {
                out.write(epic + "\n");
            }

        }
        catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        try {
            save();
        }
        catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        try {
            save();
        }
        catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        try {
            save();
        }
        catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        try {
            save();
        }
        catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        try {
            save();
        }
        catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        try {
            save();
        }
        catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        try {
            save();
        }
        catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteSubTaskById(int id) {
        super.deleteSubTaskById(id);
        try {
            save();
        }
        catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        try {
            save();
        }
        catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        try {
            save();
        }
        catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        try {
            save();
        }
        catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        try {
            save();
        }
        catch (ManagerSaveException e) {
            System.out.println(e.getMessage());
        }
    }
}
