package managers;

import tasks.*;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class FileBackedTaskManager extends InMemoryTaskManager {

    //Пусть новый менеджер получает файл для автосохранения в своём конструкторе и сохраняет его в поле.
    private String filePath;

    public FileBackedTaskManager(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public static TaskManager loadFromFile(File file) throws ManagerSaveException {
        TaskManager taskManager = new FileBackedTaskManager(file.getPath());
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new ManagerSaveException("Can't read form file: " + file.getName());
        }
    }

    //Создайте метод save без параметров — он будет сохранять текущее состояние менеджера в указанный файл.
    protected void save() throws ManagerSaveException {
        try (Writer out = new FileWriter(this.filePath)) {
            //Сохраняем список задач в файл
            //Сохраняем заголовок
            out.write("id,type,name,status,description,epic,duration,startTime\n");
            for (Task task : getAllTasks()) {
                out.write(task + "\n");
            }

            for (SubTask subtask : getAllSubTasks()) {
                out.write(subtask + "\n");
            }

            for (Epic epic : getAllEpics()) {
                out.write(epic + "\n");
            }

        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    @Override
    public void addTask(Task task) throws ManagerSaveException {
        super.addTask(task);
        save();
    }

    @Override
    public void updateTask(Task task) throws ManagerSaveException {
        super.updateTask(task);
        save();
    }

    @Override
    public void deleteTaskById(int id) throws ManagerSaveException {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) throws ManagerSaveException {
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void deleteAllTasks() throws ManagerSaveException {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllSubTasks() throws ManagerSaveException {
        super.deleteAllSubTasks();
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) throws ManagerSaveException {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void deleteSubTaskById(int id) throws ManagerSaveException {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void addEpic(Epic epic) throws ManagerSaveException {
        super.addEpic(epic);
        save();
    }

    @Override
    public void deleteAllEpics() throws ManagerSaveException {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void updateEpic(Epic epic) throws ManagerSaveException {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteEpicById(int id) throws ManagerSaveException {
        super.deleteEpicById(id);
        save();
    }
}
