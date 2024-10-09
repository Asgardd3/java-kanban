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

    public static TaskManager loadFromFile(File file) throws ManagerSaveException, TaskOverloadException {
        TaskManager taskManager = new FileBackedTaskManager(file.getPath());
        try {
            //Пропустить первую строку файла
            String fileStr = Files.readString(file.toPath());
            ArrayList<String> fileStrList = new ArrayList<>(Arrays.asList(fileStr.split("\n")));
            fileStrList.remove(0);

            fileStrList.stream()
                    //.map(str -> str.split(","))
                    .map(str -> {
                        String[] parts = str.split(",");
                        TaskTypes type = TaskTypes.valueOf(parts[1]);
                        return switch (type) {
                            case TASK -> Task.fromString(str);
                            case SUBTASK -> SubTask.fromString(str);
                            case EPIC -> Epic.fromString(str);
                            default -> throw new IllegalArgumentException("Unknown task type: " + type);
                        };
                    })
                    .forEach(task -> {
                        if (task instanceof Task) {
                            try {
                                taskManager.addTask((Task) task);
                            } catch (ManagerSaveException e) {
                                throw new RuntimeException(e);
                            } catch (TaskOverloadException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (task instanceof SubTask) {
                            try {
                                taskManager.addSubTask((SubTask) task);
                            } catch (ManagerSaveException e) {
                                throw new RuntimeException(e);
                            } catch (TaskOverloadException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (task instanceof Epic) {
                            try {
                                taskManager.addEpic((Epic) task);
                            } catch (ManagerSaveException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });



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
            getAllTasks()
                    .forEach(task -> {
                        try {
                            out.write(task + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

            getAllSubTasks()
                    .forEach(subtask -> {
                        try {
                            out.write(subtask + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });


            getAllEpics()
                    .forEach(epic -> {
                        try {
                            out.write(epic + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });


        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    @Override
    public void addTask(Task task) throws ManagerSaveException, TaskOverloadException {
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
    public void addSubTask(SubTask subTask) throws ManagerSaveException, TaskOverloadException {
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
