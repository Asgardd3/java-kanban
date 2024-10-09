package managers;

import tasks.*;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    public List<Task> getHistory();

    //Задачи

    void addTask(Task task) throws ManagerSaveException, TaskOverloadException;

    ArrayList<Task> getAllTasks();

    void deleteAllTasks() throws ManagerSaveException;

    Task getTaskById(int id);

    void updateTask(Task task) throws ManagerSaveException;

    void deleteTaskById(int id) throws ManagerSaveException;

    void addSubTask(SubTask subTask) throws ManagerSaveException, TaskOverloadException;

    ArrayList<SubTask> getAllSubTasks();

    void deleteAllSubTasks() throws ManagerSaveException;

    SubTask getSubTaskById(int id);

    void updateSubTask(SubTask subTask) throws ManagerSaveException;

    void deleteSubTaskById(int id) throws ManagerSaveException;

    void addEpic(Epic epic) throws ManagerSaveException;

    ArrayList<Epic> getAllEpics();

    void deleteAllEpics() throws ManagerSaveException;

    Epic getEpicById(int id);

    void updateEpic(Epic epic) throws ManagerSaveException;

    void deleteEpicById(int id) throws ManagerSaveException;

    ArrayList<SubTask> getAllSubTasksByEpicId(int id);
}
