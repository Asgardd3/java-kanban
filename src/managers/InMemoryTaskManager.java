package managers;

import tasks.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private int id = 1;
    private Map<Integer, Task> tasks = new HashMap<Integer, Task>();
    private Map<Integer, SubTask> subTasks = new HashMap<Integer, SubTask>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void addHistory(Task task)  {
        historyManager.add(task);
    }

    private int getId() {
        return this.id++;
    }

    //Задачи
    @Override
    public void addTask(Task task) throws ManagerSaveException {
        task.setId(getId());
        tasks.put(task.getId(), task);
    }

    public void printAllTasks() {
        for (Task task : tasks.values()) {
            System.out.println(task.toString());
        }
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteAllTasks() throws ManagerSaveException {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task  = tasks.get(id);
        addHistory(task);
        return task;
    }

    @Override
    public void updateTask(Task task) throws ManagerSaveException {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Задачи с такой ID(" + task.getId() + ") не существует");

        }
    }

    @Override
    public void deleteTaskById(int id) throws ManagerSaveException {
        tasks.remove(id);
        historyManager.remove(id);
    }

    //Подзадачи
    public void printAllSubTasks() {
        for (SubTask subTask : subTasks.values()) {
            System.out.println(subTask.toString());
        }
    }

    @Override
    public void addSubTask(SubTask subTask) throws ManagerSaveException {
        subTask.setId(getId());
        if (epics.containsKey(subTask.getEpicId())) {
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicId());
            epic.addSubTasksId(subTask.getId());
            evaluateEpicStatus(epic);
        }
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void deleteAllSubTasks() throws ManagerSaveException {
        for (SubTask subTask : subTasks.values()) {
            historyManager.remove(subTask.getId());
        }
        subTasks.clear();
        for (Epic epicLoop : epics.values()) {
            epicLoop.removeAll();
            evaluateEpicStatus(epicLoop);
        }


    }

    @Override
    public SubTask getSubTaskById(int id) {
        addHistory(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public void updateSubTask(SubTask subTask) throws ManagerSaveException {

        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            if (epics.get(subTask.getEpicId()) != null) {
                evaluateEpicStatus(epics.get(subTask.getEpicId()));
            }
        } else {
            System.out.println("Подзадачи с такой ID(" + subTask.getId() + ") не существует");

        }



    }
    //Ищем, в каком эпике есть данная подзадача и
    // обновляем его статус

    @Override
    public void deleteSubTaskById(int id) throws ManagerSaveException {
        SubTask subTask = subTasks.remove(id);
        Epic epic = epics.get(subTask.getEpicId());
        epic.removeSubTasksId(subTask.getId());
        evaluateEpicStatus(epic);
        historyManager.remove(id);
    }


    //Эпики
    public void printAllEpics() {
        for (Epic epic : epics.values()) {
            System.out.println(epic.toString());
        }
    }

    @Override
    public void addEpic(Epic epic) throws ManagerSaveException {
        epic.setId(getId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllEpics() throws ManagerSaveException {
        for (Epic epic : epics.values()) {
            historyManager.remove(epic.getId());
        }
        for (SubTask subTask : subTasks.values()) {
            historyManager.remove(subTask.getId());
        }
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Epic getEpicById(int id) {
        addHistory(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void updateEpic(Epic epic) throws ManagerSaveException {
        //Пооользователь не может переопределить статус эпика
        Epic epicUpd  = epics.get(epic.getId());
        epicUpd.setName(epic.getName());
        epicUpd.setDescription(epic.getDescription());
    }

    @Override
    public void deleteEpicById(int id) throws ManagerSaveException {
        Epic epic = epics.remove(id);
        for (int subtaskToRemove : epic.getSubTasksIds()) {
            subTasks.remove(subtaskToRemove);
            historyManager.remove(subtaskToRemove);
        }
        historyManager.remove(id);
    }

    @Override
    public ArrayList<SubTask> getAllSubTasksByEpicId(int id) {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        Epic epic = epics.get(id);

        for (int o : epic.getSubTasksIds()) {

            subTasks.add(this.subTasks.get(o));

        }
        return subTasks;
    }

    private void evaluateEpicStatus(Epic epic) {

        //Ели у эпика нет подзадач или они все NEW, то статус должен быть NEW
        //Проверить, есть ли задачи в статусе NEW, если нет, то статус должен стать NEW
        boolean isActualHaveTasksNew = false;
        boolean isActualHaveTasksDone = false;
        boolean isActualHaveTasksInProgress = false;

        if (epic.getSubTasksIds() == null || epic.getSubTasksIds().size() == 0) { //epics.size() == 0 ||
            epic.setStatus(Status.NEW);
            return;
        }

        for (int subTaskId : epic.getSubTasksIds()) {
            switch (subTasks.get(subTaskId).getStatus()) {
                case NEW:
                    isActualHaveTasksNew = true;
                    break;
                case DONE:
                    isActualHaveTasksDone = true;
                    break;
                case IN_PROGRESS:
                    isActualHaveTasksInProgress = true;

            }
            if (subTasks.get(subTaskId).getStartTime() < epic.getStartTime()) {
                epic.setStartTime(subTasks.get(subTaskId).getStartTime());
            }

            if (subTasks.get(subTaskId).getEndTime() > epic.getEndTime()) {
                epic.setEndTime(subTasks.get(subTaskId).getEndTime());
            }

            epic.setDuration(epic.getDuration().plus(subTasks.get(subTaskId).getDuration()));

        }

        if (isActualHaveTasksNew && !isActualHaveTasksDone && !isActualHaveTasksInProgress) { //epics.size() == 0 ||
            epic.setStatus(Status.NEW);
            return;
        }

        //Если все подзадачи DONE, то эпик считается завершённым
        if (!isActualHaveTasksNew && isActualHaveTasksDone && !isActualHaveTasksInProgress) {
            epic.setStatus(Status.DONE);
            return;
        }
        epic.setStatus(Status.IN_PROGRESS);

        //Дата старта эпика

        //Продолжительность эпика — сумма продолжительностей всех его подзадач. Время начала — дата старта самой ранней подзадачи, а время завершения — время окончания самой поздней из задач
        //сумма продолжительностей всех подзадач
        //дата начала самой ранней подзадачи
        //дата окончания самой поздней подзадачи




    }
}

